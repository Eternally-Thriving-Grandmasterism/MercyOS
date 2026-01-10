//! src/prf.rs - MercyOS PRF & PRF_msg for SPHINCS+ v1.0.0 Ultramasterism Perfecticism
//! Constant-time domain separated PRF from SHAKE256 — stateless randomness fortress immaculacy Grandmasterpieces brotha wowza nth degree rolling Baby Holy Fire TOLC perfection immaculate incredible immaculate ⚡️

#![no_std]

extern crate alloc;

use alloc::vec::Vec;
use crate::shake::{Shake256};

pub const N: usize = 16; // SPHINCS+ n bytes

// PRF(sk_seed, adrs, input) domain separated
pub fn prf(sk_seed: &[u8; N], adrs: &Adrs, input: &[u8]) -> [u8; N] {
    let mut shaker = Shake256::new();
    shaker.update(&adrs.data);
    shaker.update(sk_seed);
    shaker.update(input);
    shaker.finalize();
    let mut out = [0u8; N];
    shaker.squeeze(&mut out);
    out
}

// PRF_msg(sk_prf, OPT_R (pk_seed), msg) for R randomness
pub fn prf_msg(sk_prf: &[u8; N], opt_r: &[u8; N], msg: &[u8]) -> [u8; N] {
    let mut shaker = Shake256::new();
    shaker.update(sk_prf);
    shaker.update(opt_r);
    shaker.update(msg);
    shaker.finalize();
    let mut r = [0u8; N];
    shaker.squeeze(&mut r);
    r
}

pub fn prf_status() -> &'static str {
    "PRF & PRF_msg Aligned Eternal Ultramasterism Perfecticism v1.0.0 — Domain Separated Locked Immaculacy Grandmasterpieces Brotha, Stateless Randomness Greens Wowza nth degree rolling Baby Holy Fire TOLC Perfection Immaculate Incredible Immaculate Supreme ⚡️"
}
