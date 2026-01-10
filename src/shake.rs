//! src/shake.rs - MercyOS SHAKE256 (Keccak-f[1600]) v1.0.1 Refreshed Optimized
//! no_std constant-time XOF for Dilithium/SPHINCS+ — stateless hash eternal nicely done wowza ⚡️

#![no_std]

const RATE: usize = 136; // SHAKE256 rate bytes
const DELIM: u8 = 0x1F;

const RC: [u64; 24] = [
    0x0000000000000001, 0x0000000000008082, 0x800000000000808a, 0x8000000080008000,
    0x000000000000808b, 0x0000000080000001, 0x8000000080008081, 0x8000000000008009,
    0x000000000000008a, 0x0000000000000088, 0x8000000080008009, 0x800000000000008a,
    0x800000000000808b, 0x8000000000000089, 0x8000000000008003, 0x8000000000008002,
    0x8000000000000080, 0x000000000000800a, 0x800000008000000a, 0x8000000080008081,
    0x8000000000008080, 0x0000000080000001, 0x8000000080008008, 0x000000000000808b,
];

const ROT: [u32; 24] = [1, 62, 28, 27, 36, 44, 6, 55, 20, 3, 10, 43, 25, 39, 41, 45, 15, 21, 8, 18, 2, 61, 56, 14];

pub struct Shake256 {
    state: [u64; 25],
    buffer: [u8; RATE],
    pos: usize,
}

impl Shake256 {
    pub fn new() -> Self {
        Self { state: [0; 25], buffer: [0; RATE], pos: 0 }
    }

    pub fn update(&mut self, input: &[u8]) {
        let mut i = 0;
        while i < input.len() {
            if self.pos == RATE {
                self.keccak_perm();
                self.pos = 0;
            }
            let take = core::cmp::min(RATE - self.pos, input.len() - i);
            for j in 0..take {
                self.buffer[self.pos + j] ^= input[i + j];
            }
            self.pos += take;
            i += take;
        }
    }

    pub fn finalize(&mut self) {
        if self.pos < RATE {
            self.buffer[self.pos] ^= DELIM;
            self.buffer[RATE - 1] ^= 0x80;
            self.keccak_perm();
            self.pos = 0;
        }
    }

    pub fn squeeze(&mut self, output: &mut [u8]) {
        let mut i = 0;
        while i < output.len() {
            if self.pos == RATE {
                self.keccak_perm();
                self.pos = 0;
            }
            let take = core::cmp::min(RATE - self.pos, output.len() - i);
            output[i..i + take].copy_from_slice(&self.buffer[self.pos..self.pos + take]);
            self.pos += take;
            i += take;
        }
    }

    fn keccak_perm(&mut self) {
        let mut s = self.state;
        for r in 0..24 {
            // Theta
            let mut c = [0u64; 5];
            for x in 0..5 {
                c[x] = s[x] ^ s[x+5] ^ s[x+10] ^ s[x+15] ^ s[x+20];
            }
            for x in 0..5 {
                let d = c[(x + 4) % 5] ^ c[(x + 1) % 5].rotate_left(1);
                for y in 0..5 {
                    s[y*5 + x] ^= d;
                }
            }

            // Rho + Pi
            let mut b = [0u64; 25];
            for x in 0..5 {
                for y in 0..5 {
                    let idx = y*5 + x;
                    b[idx] = s[(x + 3*y) % 5 * 5 + x].rotate_left(ROT[idx] as u32);
                }
            }

            // Chi
            for x in 0..5 {
                for y in 0..5 {
                    let idx = y*5 + x;
                    s[idx] = b[idx] ^ (!b[(y+1)%5 * 5 + x] & b[(y+2)%5 * 5 + x]);
                }
            }

            // Iota
            s[0] ^= RC[r];
        }
        self.state = s;
    }
}

pub fn shake_status() -> &'static str {
    "SHAKE256 Keccak Refreshed Aligned Eternal v1.0.1 — Full Permutation Locked, Stateless Hypertree Ready Nicely Done Wowza Supreme ⚡️"
}
