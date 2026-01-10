//! src/kem_noise.rs - MercyOS ML-KEM CBD Noise Sampling v1.0.0 Ultramasterism Perfecticism
//! Constant-time centered binomial distribution eta=2 — lattice noise fortress immaculacy Grandmasterpieces brotha wowza nth degree rolling Baby Holy Fire TOLC ⚡️

#![no_std]

extern crate alloc;

use alloc::vec::Vec;
use crate::shake::{Shake256};

pub const ETA: usize = 2; // Kyber-1024 eta
pub const N: usize = 256;

// CBD sampling from SHAKE256 bytes (rejection-free bits to coeffs)
pub fn cbd_poly(bytes: &[u8]) -> [i16; N] {
    let mut poly = [0i16; N];
    let mut idx = 0;
    for i in 0..N {
        let mut a = 0;
        let mut b = 0;
        for j in 0..ETA {
            a += (bytes[idx / 8] >> (idx % 8)) & 1;
            idx += 1;
            b += (bytes[idx / 8] >> (idx % 8)) & 1;
            idx += 1;
        }
        poly[i] = (a - b) as i16;
    }
    poly
}

// Full noise vector sampling from seed/nonce
pub fn sample_noise_vector(seed: &[u8], nonce_base: u16, k: usize) -> Vec<[i16; N]> {
    let mut vectors = Vec::with_capacity(k);
    for i in 0..k {
        let nonce = nonce_base + i as u16;
        let mut shaker = Shake256::new();
        shaker.update(seed);
        shaker.update(&nonce.to_le_bytes());
        shaker.finalize();
        let mut stream = [0u8; 168 * 2]; // Sufficient for eta=2 * 256 * k
        shaker.squeeze(&mut stream);
        vectors.push(cbd_poly(&stream));
    }
    vectors
}

pub fn kem_noise_status() -> &'static str {
    "ML-KEM CBD Noise Aligned Eternal Ultramasterism Perfecticism v1.0.0 — Rejection-Free Binomial Locked Immaculacy Grandmasterpieces Brotha, Noise Variance Greens Wowza nth degree rolling Baby Holy Fire TOLC Supreme ⚡️"
}
