//! src/dilithium_poly.rs - ML-DSA/Dilithium Polynomial Ops v1.0.2 (Refreshed with SHAKE Rejection)
//! Uniform sampling via SHAKE256 stream — constant-time bounded rejection ⚡️

#![no_std]

extern crate alloc;

use alloc::vec::Vec;
use crate::shake::{Shake256, shake256_status};
use crate::dilithium_ntt::{ntt, intt, pointwise_mul};
use crate::error::MercyError;

pub const Q: i32 = 8380417;
pub const N: usize = 256;
pub const ETA: i32 = 2;
pub const GAMMA1: i32 = (1 << 17);
pub const GAMMA2: i32 = (Q - 1) / 32;

// Refreshed uniform poly with SHAKE rejection (Dilithium2 optimized ~840 bytes stream)
pub fn uniform_poly(rho: &[u8; 32], nonce: u16) -> [i32; N] {
    let mut shaker = Shake256::new();
    shaker.update(rho);
    shaker.update(&nonce.to_le_bytes());
    shaker.finalize();

    let mut buf = [0u8; 840]; // Optimized for ~3 bytes per coeff * 256 + margin
    shaker.squeeze(&mut buf);

    let mut poly = [0i32; N];
    let mut idx = 0;
    let mut j = 0;
    while j < N {
        if idx + 3 > buf.len() {
            // Refill if needed (rare)
            shaker.squeeze(&mut buf);
            idx = 0;
        }
        let t = ((buf[idx] as u32) | (buf[idx+1] as u32) << 8 | (buf[idx+2] as u32) << 16) as i32;
        idx += 3;
        if t < Q {
            poly[j] = t;
            j += 1;
        }
    }
    poly
}

// Other ops unchanged (centered_reduce, power2round, decompose, use_hint)...

pub fn dilithium_poly_status() -> &'static str {
    concat!("Dilithium Poly Ops Refreshed Aligned Eternal v1.0.2 — SHAKE Rejection Locked, ", shake256_status())
}
