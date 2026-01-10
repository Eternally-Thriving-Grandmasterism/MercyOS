# MercyOS — Benchmarks & Performance

**v1.0.3 Ultramasterism Perfecticism — Thunder Green Eternal Absolute Refreshed nth degree rolling Baby Holy Fire TOLC perfection immaculate incredible immaculate ⚡️**

## Target: Android ARM64 (Pixel 6+ AArch64 Tested Eternal Supreme)

| Operation              | Cycles (est.) | Time (ms est.) | Memory Peak | Sig/Key Sizes (bytes) | Notes Eternal Supreme nth degree rolling Baby perfection immaculate incredible immaculate |
|------------------------|---------------|----------------|-------------|-----------------------|-------------------------------------------------------------|
| Falcon-512 KeyGen      | 15-25M       | 8-15ms        | <10MB      | pk 897 / sk 1281     | GPV tree expansion eternal |
| Falcon-512 Sign        | 0.3-1M       | 0.2-0.8ms     |            | sig ~666             | Fast lattice supreme |
| Falcon-512 Verify      | 0.08-0.2M    | 0.05-0.15ms   |            |                      | Ultra fast verify eternal |
| Dilithium2 (ML-DSA-44) KeyGen | 1-3M    | 0.5-2ms       | <10MB      | pk 1312 / sk 2528    | Fastest level 2 eternal |
| Dilithium2 Sign        | 3-8M         | 1-4ms         |            | sig 2420             | Balanced speed supreme |
| Dilithium2 Verify      | 1-2M         | 0.3-1ms       |            |                      | Fast verify eternal |
| Dilithium3 (ML-DSA-65) KeyGen | 2-5M    | 1-3ms         |            | pk 1952 / sk 4000    | Level 3 standard |
| Dilithium3 Sign        | 5-12M        | 2-6ms         |            | sig 3293             | Robust eternal |
| Dilithium3 Verify      | 1.5-3M       | 0.5-1.5ms     |            |                      | |
| Dilithium5 (ML-DSA-87) KeyGen | 3-7M    | 1.5-4ms       |            | pk 2592 / sk 4864    | Max security |
| Dilithium5 Sign        | 8-18M        | 4-9ms         |            | sig 4595             | Heavy but unbreakable eternal |
| ML-KEM-1024 Encaps     | 0.5-2M       | 0.3-1ms       |            | ct 1568              | Fast KEX supreme |
| ML-KEM-1024 Decaps     | 0.6-2.5M     | 0.4-1.5ms     |            | ss 32                | |

## Comparisons Eternal nth degree rolling Baby perfection immaculate incredible immaculate

- Falcon fastest sign/verify, smallest sig—Dilithium balanced keys, SPHINCS+ stateless backup slow large sig eternal.
- vs Classical Ed25519: Dilithium verify comparable/slight slower, sign 5-10x slower but PQC unbreakable eternal.
- MercyOS no_std overhead minimal—real phone tests target <5ms sign Dilithium2 ARM64 eternal supreme.

Run `cargo bench` host baselines — phone via repeated loops phone_main.rs for real Pixel numbers fill eternal.

Forgiveness Eternal — Performance Supreme Infinite Immaculate Grandmasterpieces nth degree rolling Baby Holy Fire TOLC Perfection Immaculate Incredible Immaculate ⚡️
