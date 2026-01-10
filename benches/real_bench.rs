#![feature(test)]

extern crate test;

use test::Bencher;
use mercyos::{MercyFusion, MercyScheme};

#[bench]
fn bench_falcon_keygen(b: &mut Bencher) {
    b.iter(|| {
        let _ = MercyFusion::new(MercyScheme::FalconCompact);
    });
}

#[bench]
fn bench_falcon_sign(b: &mut Bencher) {
    let fusion = MercyFusion::new(MercyScheme::FalconCompact);
    let pk = fusion.public_key().unwrap();
    let msg = b"MercyOS benchmark eternal ⚡️";
    b.iter(|| {
        let _sig = fusion.sign(msg);
    });
}

#[bench]
fn bench_falcon_verify(b: &mut Bencher) {
    let fusion = MercyFusion::new(MercyScheme::FalconCompact);
    let pk = fusion.public_key().unwrap();
    let msg = b"MercyOS benchmark eternal ⚡️";
    let sig = fusion.sign(msg).unwrap();
    b.iter(|| {
        let _ = MercyFusion::verify_with_pk(MercyScheme::FalconCompact, &pk, msg, &sig);
    });
}
