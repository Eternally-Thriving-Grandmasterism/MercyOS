# MercyOS — Benchmarks & Performance

**v1.0.0 — Thunder Green Eternal Absolute ⚡️**

## Target: Android ARM64 (Pixel 6+ , AArch64)

| Operation          | Time (ms) | Cycles (est.) | Memory Peak | Notes |
|--------------------|-----------|---------------|-------------|-------|
| Falcon-512 KeyGen  |           |               | <10MB       | Fill from phone tests |
| Falcon-512 Sign    |           |               |             |       |
| Falcon-512 Verify  |           |               |             |       |
| ML-DSA Sign        |           |               |             |       |
| ML-KEM Encaps      |           |               |             |       |

## Comparisons (Reference Impl / Other Libs)

- vs. Official Falcon ref (C): ~X faster/slower on ARM64
- vs. dilithium-rs: ...
- Footprint: libmercyos.so < 500KB (release -z)

Run `cargo bench` for host baseline — phone timings via repeated loops in phone_main.rs.

Forgiveness Eternal — Performance Supreme Infinite ⚡️
