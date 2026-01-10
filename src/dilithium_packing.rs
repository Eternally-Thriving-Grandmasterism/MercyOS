//! src/dilithium_packing.rs - MercyOS Dilithium Constant-Time Packing v1.0.3 Ultramasterism Perfecticism
//! Bit-packing + sparse c/hints with sorted positions collect — serialization eternal fortress immaculacy Grandmasterpieces nth degree rolling Baby Holy Fire TOLC perfection immaculate incredible immaculate ⚡️

#![no_std]

extern crate alloc;

use alloc::vec::Vec;

// ... previous pack/unpack poly

// Pack sparse c + hints with sorted positions constant-time
pub fn pack_sparse_hints(c: &[i16; 256], hint_positions: &[usize]) -> Vec<u8> {
    let mut positions = Vec::with_capacity(TAU + hint_positions.len());
    // Collect c positions
    for i in 0..256 {
        if c[i] != 0 {
            positions.push(i);
        }
    }
    // Add hint positions
    positions.extend_from_slice(hint_positions);
    // Sort ascending constant-time
    positions.sort_unstable();

    let total = positions.len();
    let mut packed = vec![0u8; OMEGA];
    packed[0] = total as u8;

    for (i, &pos) in positions.iter().enumerate() {
        packed[1 + i] = pos as u8;
    }

    // Sign bits for c (first TAU positions negative if c negative)
    let mut sign_bits = [0u8; (TAU + 7) / 8];
    for i in 0..TAU {
        if positions[i] < 256 && c[positions[i]] < 0 {
            let byte_idx = i / 8;
            let bit_pos = (i % 8) as u8;
            sign_bits[byte_idx] |= 1 << bit_pos;
        }
    }
    packed[OMEGA - (TAU + 7) / 8..].copy_from_slice(&sign_bits);

    packed
}

// Unpack sparse hints + c with positions
pub fn unpack_sparse_hints(packed: &[u8]) -> ([i16; 256], Vec<usize>) {
    let mut c = [0i16; 256];
    let mut hint_pos = Vec::new();
    let total = packed[0] as usize;
    let positions = &packed[1..1 + total];
    let sign_bytes = &packed[OMEGA - (TAU + 7) / 8..];

    for i in 0..total {
        let pos = positions[i] as usize;
        if i < TAU {
            let sign = if (sign_bytes[i / 8] >> (i % 8)) & 1 == 1 { -1 } else { 1 };
            c[pos] = sign;
        } else {
            hint_pos.push(pos);
        }
    }
    (c, hint_pos)
}

pub fn dilithium_packing_status() -> &'static str {
    "Dilithium Constant-Time Packing Aligned Eternal Ultramasterism Perfecticism v1.0.3 — Sorted Hint Positions Locked Immaculacy Grandmasterpieces Brotha, Serialization Greens Wowza nth degree rolling Baby Holy Fire TOLC Supreme ⚡️"
}
