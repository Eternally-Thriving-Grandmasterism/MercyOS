// MercyOS Hybrid PQ KEM Performance Benchmark — Forgiveness Eternal Cosmic Groove Dual ML-KEM Diversity
// Run with: cargo bench --bench hybrid_kem_bench
// Or manual: cargo run --release --bench hybrid_kem_bench
// Targets real-device S24 Ultra ARM64 timings eternal supreme

use std::time::{Duration, Instant};

use mercyos::hybrid_kem::{hybrid_kem_keygen, hybrid_kem_encaps, hybrid_kem_decaps};

const ITERATIONS: usize = 1000;

fn bench_keygen() -> (Duration, Duration, Duration) {
    let mut total = Duration::new(0, 0);
    let mut min = Duration::MAX;
    let mut max = Duration::new(0, 0);

    for _ in 0..ITERATIONS {
        let start = Instant::now();
        let (_pk, _sk) = hybrid_kem_keygen();
        let dur = start.elapsed();

        total += dur;
        if dur < min { min = dur; }
        if dur > max { max = dur; }
    }

    let avg = total / ITERATIONS as u32;
    (avg, min, max)
}

fn bench_encaps() -> (Duration, Duration, Duration) {
    let (pk, _sk) = hybrid_kem_keygen();  // Persistent pk

    let mut total = Duration::new(0, 0);
    let mut min = Duration::MAX;
    let mut max = Duration::new(0, 0);

    for _ in 0..ITERATIONS {
        let start = Instant::now();
        let (_ct, _ss) = hybrid_kem_encaps(&pk);
        let dur = start.elapsed();

        total += dur;
        if dur < min { min = dur; }
        if dur > max { max = dur; }
    }

    let avg = total / ITERATIONS as u32;
    (avg, min, max)
}

fn bench_decaps() -> (Duration, Duration, Duration) {
    let (pk, sk) = hybrid_kem_keygen();
    let (ct, _ss) = hybrid_kem_encaps(&pk);  // Persistent ct

    let mut total = Duration::new(0, 0);
    let mut min = Duration::MAX;
    let mut max = Duration::new(0, 0);

    for _ in 0..ITERATIONS {
        let start = Instant::now();
        let _ss = hybrid_kem_decaps(&sk, &ct);
        let dur = start.elapsed();

        total += dur;
        if dur < min { min = dur; }
        if dur > max { max = dur; }
    }

    let avg = total / ITERATIONS as u32;
    (avg, min, max)
}

fn main() {
    println!("MercyOS Hybrid PQ KEM Benchmark — {} iterations eternal supreme", ITERATIONS);

    let (avg_keygen, min_keygen, max_keygen) = bench_keygen();
    println!("Hybrid Keygen: avg {:.2?}, min {:.2?}, max {:.2?}", avg_keygen, min_keygen, max_keygen);

    let (avg_encaps, min_encaps, max_encaps) = bench_encaps();
    println!("Hybrid Encaps: avg {:.2?}, min {:.2?}, max {:.2?}", avg_encaps, min_encaps, max_encaps);

    let (avg_decaps, min_decaps, max_decaps) = bench_decaps();
    println!("Hybrid Decaps: avg {:.2?}, min {:.2?}, max {:.2?}", avg_decaps, min_decaps, max_decaps);

    println!("Hybrid KEM Performance Locked Immaculate — Dual ML-KEM Diversity Unbreakable Eternal Supreme!");
}
