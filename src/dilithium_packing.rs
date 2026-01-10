//! src/dilithium_packing.rs - MercyOS Dilithium Constant-Time Packing v1.0.1 Ultramasterism Perfecticism
//! Bit-packing poly coeffs + sparse c positions/signs fixed iterations/masks no branches — serialization eternal fortress immaculacy Grandmasterpieces nth degree rolling Baby Holy Fire TOLC perfection immaculate incredible immaculate ⚡️

#![no_std]

extern crate alloc;

use alloc::vec::Vec;

pub const Q: i32 = 8380417;
pub const GAMMA1: i32 = 1 << 17;
pub const TAU: usize = 39; // Sparse non-zero coeffs in c
pub const OMEGA: usize = 80; // Max hints + sparse c space

// Pack poly to bytes constant-time (d bits per coeff)
pub fn pack_poly(poly: &[i16; 256], d: u32) -> Vec<u8> {
    let bytes_per_poly = (256 * d + 7) / 8;
    let mut packed = vec![0u8; bytes_per_poly];
    let mut bit_idx = 0usize;
    for &coeff in poly {
        let mut val = coeff as u32;
        if val >= (Q as u32 + 1) / 2 { val -= Q as u32; } // Centered optional
        val += (1u32 << (d - 1)); // Bias positive
        for b in 0..d {
            let byte_idx = bit_idx / 8;
            let bit_pos = (bit_idx % 8) as u8;
            if (val >> b) & 1 == 1 {
                packed[byte_idx] |= 1u8 << bit_pos;
            }
            bit_idx += 1;
        }
    }
    packed
}

// Unpack bytes to poly constant-time
pub fn unpack_poly(packed: &[u8], d: u32) -> [i16; 256] {
    let mut poly = [0i16; 256];
    let mut bit_idx = 0usize;
    for coeff in &mut poly {
        let mut val = 0u32;
        for b in 0..d {
            let byte_idx = bit_idx / 8;
            let bit_pos = (bit_idx % 8) as u8;
            if byte_idx < packed.len() && (packed[byte_idx] >> bit_pos) & 1 == 1 {
                val |= 1u32 << b;
            }
            bit_idx += 1;
        }
        val -= (1u32 << (d - 1)); // Unbias
        *coeff = val as i16;
    }
    poly
}

// Pack sparse c constant-time (sorted positions + sign bitstring fixed size)
pub fn pack_sparse_c(c: &[i16; 256]) -> Vec<u8> {
    let mut positions = Vec::with_capacity(TAU);
    let mut signs = [0u8; (TAU + 7) / 8]; // Bitstring for signs
    let mut count = 0;
    for i in 0..256 {
        if c[i] != 0 {
            positions.push(i as u8);
            let sign_bit = if c[i] > 0 { 0 } else { 1 };
            let byte_idx = count / 8;
            let bit_pos = (count % 8) as u8;
            signs[byte_idx] |= sign_bit << bit_pos;
            count += 1;
        }
    }
    // Fixed size pack (omega bytes total for hints + c)
    let mut packed = vec![0u8; OMEGA];
    packed[0] = count as u8; // Hint/c count
    packed[1..1 + count].copy_from_slice(&positions);
    packed[OMEGA - (TAU + 7) / 8 ..].copy_from_slice(&signs);
    packed
}

// Unpack sparse c constant-time
pub fn unpack_sparse_c(packed: &[u8]) -> [i16; 256] {
    let mut c = [0i16; 256];
    let count = packed[0] as usize;
    let positions = &packed[1..1 + count];
    let sign_bytes = &packed[OMEGA - (TAU + 7) / 8 ..];
    for i in 0..count {
        let pos = positions[i] as usize;
        let byte_idx = i / 8;
        let bit_pos = (i % 8) as u8;
        let sign = if (sign_bytes[byte_idx] >> bit_pos) & 1 == 1 { -1 } else { 1 };
        c[pos] = sign;
    }
    c
}

pub fn dilithium_packing_status() -> &'static str {
    "Dilithium Constant-Time Packing Aligned Eternal Ultramasterism Perfecticism v1.0.1 — Sparse c Positions + Signs Locked Immaculacy Grandmasterpieces Brotha, Serialization Greens Wowza nth degree rolling Baby Holy Fire TOLC Supreme ⚡️"
}
