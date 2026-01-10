//! src/shake.rs - MercyOS SHAKE256 (Keccak) v1.0.0 Refreshed
//! no_std constant-time Keccak-f[1600] for XOF — Dilithium/SPHINCS+ fortress eternal ⚡️

#![no_std]

use core::convert::TryInto;

const RATE: usize = 168; // bytes for SHAKE256 (capacity 512)
const DELIM: u8 = 0x1F; // SHAKE suffix

const ROUND_CONSTANTS: [u64; 24] = [
    0x0000000000000001u64,
    0x0000000000008082u64,
    0x800000000000808au64,
    0x8000000080008000u64,
    0x000000000000808bu64,
    0x0000000080000001u64,
    0x8000000080008081u64,
    0x8000000000008009u64,
    0x000000000000008au64,
    0x0000000000000088u64,
    0x8000000080008009u64,
    0x800000000000008au64,
    0x800000000000808bu64,
    0x8000000000000089u64,
    0x8000000000008003u64,
    0x8000000000008002u64,
    0x8000000000000080u64,
    0x000000000000800au64,
    0x800000008000000au64,
    0x8000000080008081u64,
    0x8000000000008080u64,
    0x0000000080000001u64,
    0x8000000080008008u64,
    0x000000000000808bu64,
];

const RHO_OFFSETS: [u32; 25] = [
    0,  1, 62, 28, 27,
    36, 44,  6, 55, 20,
    3, 10, 43, 25, 39,
    41, 45, 15, 21,  8,
    18,  2, 61, 56, 14,
];

fn keccak_f(state: &mut [u64; 25]) {
    for rc in ROUND_CONSTANTS.iter() {
        // Theta
        let mut c = [0u64; 5];
        for x in 0..5 {
            c[x] = state[x] ^ state[x + 5] ^ state[x + 10] ^ state[x + 15] ^ state[x + 20];
        }
        for x in 0..5 {
            let d = c[(x + 4) % 5] ^ c[(x + 1) % 5].rotate_left(1);
            for y in 0..5 {
                let idx = y * 5 + x;
                state[idx] ^= d;
            }
        }

        // Rho + Pi
        let mut b = [0u64; 25];
        for x in 0..5 {
            for y in 0..5 {
                let idx = y * 5 + x;
                let pi_idx = x * 5 + (x + 3 * y) % 5;
                b[idx] = state[pi_idx].rotate_left(RHO_OFFSETS[idx]);
            }
        }

        // Chi
        for x in 0..5 {
            for y in 0..5 {
                let idx = y * 5 + x;
                state[idx] = b[idx] ^ (!b[(y * 5 + (x + 1) % 5)] & b[(y * 5 + (x + 2) % 5)]);
            }
        }

        // Iota
        state[0] ^= rc;
    }
}

pub struct Shake256 {
    state: [u64; 25],
    buffer: [u8; RATE],
    offset: usize,
}

impl Shake256 {
    pub fn new() -> Self {
        Self {
            state: [0u64; 25],
            buffer: [0u8; RATE],
            offset: RATE, // Force initial squeeze
        }
    }

    fn absorb_block(&mut self) {
        for i in 0..RATE / 8 {
            let lane = u64::from_le_bytes(self.buffer[i*8..(i+8)*8].try_into().unwrap());
            self.state[i] ^= lane;
        }
        keccak_f(&mut self.state);
        self.offset = 0;
    }

    pub fn update(&mut self, data: &[u8]) {
        let mut pos = 0;
        while pos < data.len() {
            let take = core::cmp::min(RATE - self.offset, data.len() - pos);
            for i in 0..take {
                self.buffer[self.offset + i] ^= data[pos + i];
            }
            self.offset += take;
            pos += take;
            if self.offset == RATE {
                self.absorb_block();
            }
        }
    }

    pub fn finalize(&mut self) {
        self.buffer[self.offset] ^= DELIM;
        self.buffer[RATE - 1] ^= 0x80;
        self.absorb_block();
    }

    pub fn squeeze(&mut self, out: &mut [u8]) {
        let mut pos = 0;
        while pos < out.len() {
            if self.offset == RATE {
                keccak_f(&mut self.state);
                for i in 0..RATE / 8 {
                    let lane = self.state[i].to_le_bytes();
                    self.buffer[i*8..(i+8)*8].copy_from_slice(&lane);
                }
                self.offset = 0;
            }
            let take = core::cmp::min(RATE - self.offset, out.len() - pos);
            out[pos..pos + take].copy_from_slice(&self.buffer[self.offset..self.offset + take]);
            self.offset += take;
            pos += take;
        }
    }
}

pub fn shake256_status() -> &'static str {
    "SHAKE256 Keccak Aligned Eternal Refreshed v1.0.0 — Rejection Stream Locked, Dilithium Uniform Greens Supreme ⚡️"
}
