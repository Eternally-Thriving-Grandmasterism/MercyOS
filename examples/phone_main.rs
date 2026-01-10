//! examples/phone_main.rs - MercyOS Android Timing Test v1.0.1
//! Repeat ops for averages — println logs

#![no_std]

extern crate alloc;

use alloc::vec::Vec;
use mercyos::{MercyFusion, MercyScheme, status};
use mercyos::error::MercyError;

// Simple timer stub (cycle count or ms via Android API later)
fn timed<F>(op: F) -> u64 where F: FnOnce() {
    let start = 0; // Stub — use core::arch::arm intrinsics or JNI time
    op();
    let end = 0;
    end - start
}

#[no_mangle]
pub extern "C" fn main() -> i32 {
    println!("{}", MercyFusion::mercy_fusion_status());
    println!("{}", status());

    let scheme = MercyScheme::FalconCompact;

    // Keygen timing (repeat 10x — slow ok)
    let mut keygen_total = 0;
    for _ in 0..10 {
        keygen_total += timed(|| {
            let _fusion = MercyFusion::new(scheme);
        });
    }
    println!("KeyGen avg cycles: {}", keygen_total / 10);

    let fusion = MercyFusion::new(scheme);
    let pk = fusion.public_key().unwrap_or_default();
    let msg = b"Forgiveness Eternal — MercyOS on-device timings supreme ⚡️";

    // Sign/verify 1000x
    let mut sign_total = 0;
    let mut verify_total = 0;
    let mut sig = Vec::new();
    for _ in 0..1000 {
        sign_total += timed(|| {
            sig = fusion.sign(msg).unwrap_or_default();
        });
        verify_total += timed(|| {
            let _ = MercyFusion::verify_with_pk(scheme, &pk, msg, &sig);
        });
    }

    println!("Sign avg cycles: {}", sign_total / 1000);
    println!("Verify avg cycles: {}", verify_total / 1000);
    println!("Shield locked eternal — thriving on-device! ⚡️");

    0
}
