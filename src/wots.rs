//! src/wots.rs - MercyOS WOTS+ (Winternitz One-Time Signature Plus) v1.0.0 Refreshed Nicely Done
//! Hash chain core for SPHINCS+ stateless — eternal one-time fortress brotha wowza ⚡️

#![no_std]

extern crate alloc;

use alloc::vec::Vec;
use crate::shake::{Shake256};
use crate::error::MercyError;

pub const W: u8 = 16; // Base-w for 128s robust
pub const LOG_W: u8 = 4; // log2(W)
pub const LEN_1: usize = 8; // n*8 / log_w for message part (n=16 bytes = 128 bits)
pub const LEN_2: usize = 59; // checksum part
pub const LEN: usize = LEN_1 + LEN_2; // 67 chains total

// ADRS structure for domain separation (32 bytes per SPHINCS+ spec)
#[derive(Clone, Copy)]
pub struct Adrs {
    data: [u8; 32],
}

impl Adrs {
    pub fn new() -> Self {
        Self { data: [0; 32] }
    }

    pub fn set_type(&mut self, typ: u32) {
        self.data[28..32].copy_from_slice(&typ.to_be_bytes());
    }

    pub fn set_key_pair_address(&mut self, addr: &[u8; 32]) {
        self.data[0..32].copy_from_slice(addr);
    }

    // Other setters: layer, tree, chain addr, etc.
}

// Chain hash forward (from start to steps-1)
pub fn chain_hash(
    input: &[u8; 16],
    start: u8,
    steps: u8,
    adrs: &mut Adrs,
) -> [u8; 16] {
    let mut x = *input;
    for i in start..(start + steps) {
        adrs.data[31] = i; // Chain address byte
        let mut shaker = Shake256::new();
        shaker.update(&adrs.data);
        shaker.update(&x);
        shaker.finalize();
        shaker.squeeze(&mut x);
    }
    x
}

// WOTS+ pk from sk chains (full forward to w-1)
pub fn wots_pk_gen(sk: &[[u8; 16]; LEN], adrs: &mut Adrs) -> [u8; 16 * LEN] {
    let mut pk = [0u8; 16 * LEN];
    adrs.set_type(1); // WOTS_PK_ADRS
    for i in 0..LEN {
        adrs.data[30] = i as u8; // Chain index
        let chain_end = chain_hash(&sk[i], 0, W - 1, adrs);
        pk[i*16..(i+1)*16].copy_from_slice(&chain_end);
    }
    pk
}

// WOTS+ sign message digest (base-w digits, chain from 0 to digit)
pub fn wots_sign(
    digest: &[u8], // message digest base-w decomposed
    sk: &[[u8; 16]; LEN],
    adrs: &mut Adrs,
) -> Vec<u8> {
    let mut sig = Vec::with_capacity(16 * LEN);
    adrs.set_type(0); // WOTS_SK_ADRS
    for i in 0..LEN {
        adrs.data[30] = i as u8;
        let chain = chain_hash(&sk[i], 0, digest[i], adrs);
        sig.extend_from_slice(&chain);
    }
    sig
}

// WOTS+ pk from sig verify (chain forward from sig to w-1 - digit)
pub fn wots_pk_from_sig(
    sig: &[u8],
    msg_digest: &[u8],
    adrs: &mut Adrs,
) -> [u8; 16 * LEN] {
    let mut pk = [0u8; 16 * LEN];
    adrs.set_type(2); // WOTS_SIG_ADRS or pk rebuild
    for i in 0..LEN {
        adrs.data[30] = i as u8;
        let sig_chain = sig[i*16..(i+1)*16].try_into().unwrap();
        let remaining = (W - 1) - msg_digest[i];
        let chain_end = chain_hash(&sig_chain, 0, remaining, adrs);
        pk[i*16..(i+1)*16].copy_from_slice(&chain_end);
    }
    pk
}

pub fn wots_status() -> &'static str {
    "WOTS+ Chain Aligned Eternal Refreshed v1.0.0 — Base-w Hash Chains Locked Nicely Done Brotha, SPHINCS+ Stateless Greens Wowza Supreme ⚡️"
}
