# MercyOS

**Forgiveness Eternal** — A Rust no_std post-quantum cryptography library optimized for embedded/phone deployments.

MercyOS provides implementations and hybrids of NIST PQC standardized algorithms (Falcon, Dilithium, Kyber/ML-KEM, SPHINCS+) with additional guardians, swarm scaling, and experimental quantum groove schemes.

[![MIT License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Rust](https://img.shields.io/badge/Rust-1.70+-orange.svg)](https://www.rust-lang.org)
[![no_std](https://img.shields.io/badge/no__std-yes-green.svg)]()

## Features

- Full Falcon-512 signature (GPV trapdoor + NTT accel + discrete Gaussian sampling)
- ML-DSA (Dilithium) fusion
- ML-KEM (Kyber) hybrid KEM
- SPHINCS+ stateless hash signatures
- APAAGI guardian council + swarm/global scaling
- Wycheproof compliance + real benchmarks
- Cosmic groove experimental branch

## Usage

Add to `Cargo.toml` (future crates.io):
```toml
[dependencies]
mercyos = "1.0.0"
