# MercyOS — Usage and API Guide

MercyOS is a `no_std` post-quantum cryptography library designed for Android phones and embedded devices. It provides a unified, runtime-selectable interface to NIST PQC algorithms (Falcon-512, Dilithium, Sphincs+, ML-KEM/Kyber) plus custom extensions, all under the MIT license.

## Adding as Dependency

```toml
[dependencies]
mercyos = { git = "https://github.com/Eternally-Thriving-Grandmasterism/MercyOS.git" }
