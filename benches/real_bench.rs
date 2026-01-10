#![feature(test)]

extern crate test;

use test::Bencher;
use mercyos::{MercyFusion, MercyScheme};

#[bench]
fn bench_dilithium_keygen_refreshed(b: &mut Bencher) {
    b.iter(|| {
        let _ = mercyos::ml_dsa::DilithiumSigner::new();
    });
}

#[bench]
fn bench_dilithium_sign_refreshed(b: &mut Bencher) {
    let signer = mercyos::ml_dsa::DilithiumSigner::new();
    let msg = b"MercyOS Dilithium refreshed benchmark eternal ⚡️";
    b.iter(|| {
        let _sig = signer.sign(msg);
    });
}

#[bench]
fn bench_dilithium_verify_refreshed(b: &mut Bencher) {
    let signer = mercyos::ml_dsa::DilithiumSigner::new();
    let pk = signer.public_key();
    let msg = b"MercyOS Dilithium refreshed benchmark eternal ⚡️";
    let sig = signer.sign(msg).unwrap();
    b.iter(|| {
        let _ = mercyos::ml_dsa::DilithiumSigner::verify(&pk, msg, &sig);
    });
}
