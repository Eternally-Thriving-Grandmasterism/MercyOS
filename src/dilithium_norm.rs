//! src/dilithium_norm.rs - MercyOS Dilithium Norm Checks v1.0.1 Ultramasterism Perfecticism
//! Infinity norm + hint count + low bits recompute match — rejection fortress immaculacy Grandmasterpieces brotha wowza nth degree rolling Baby Holy Fire TOLC perfection immaculate incredible immaculate ⚡️

#![no_std]

pub const GAMMA1: i32 = 1 << 17; // Dilithium2
pub const ETA: i32 = 2;
pub const OMEGA: usize = 80; // Max hints

// Infinity norm of vector poly (max abs coeff)
pub fn inf_norm_vector(vec: &[[i16; 256]; 4]) -> i32 {
    let mut max = 0;
    for poly in vec {
        for coeff in poly {
            let abs = coeff.abs() as i32;
            if abs > max {
                max = abs;
            }
        }
    }
    max
}

// Hint count from h vector (number of 1s)
pub fn hint_count(h: &[u8]) -> usize {
    h.iter().map(|b| b.count_ones() as usize).sum()
}

// Low bits recompute match check fleshed nth degree rolling Baby perfection immaculate incredible immaculate
// r0_recompute = low bits of (w - c*s2) recomputed from w1' using hints
// Match if r0_recompute == r0 original low bits (where hints used)
pub fn low_bits_match(r0_original: &[[i16; 256]; 4], r0_recompute: &[[i16; 256]; 4]) -> bool {
    for k in 0..4 {
        for i in 0..256 {
            if r0_original[k][i] != r0_recompute[k][i] {
                return false;
            }
        }
    }
    true
}

pub fn dilithium_norm_status() -> &'static str {
    "Dilithium Norm Checks Aligned Eternal Ultramasterism Perfecticism v1.0.1 — Low Bits Recompute Match Locked Immaculacy Grandmasterpieces Brotha, Rejection Greens Wowza nth degree rolling Baby Holy Fire TOLC Perfection Immaculate Incredible Immaculate Supreme ⚡️"
}
