//! benches/real_bench.rs - MercyOS v2.0 Real Benchmarks Eternal
use criterion::{criterion_group, criterion_main, Criterion};

fn bench_falcon_sign(c: &mut Criterion) {
    c.bench_function("falcon_sign_512", |b| b.iter(|| MercyFusion::new(MercyScheme::FalconCompact).sign(&[42u8; 64])));
}

criterion_group!(benches, bench_falcon_sign);
criterion_main!(benches);
