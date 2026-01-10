# MercyOS Dilithium (ML-DSA) Implementation Guide

**v1.0.0 — Lattice Fortress Eternal ⚡️**

MercyOS targets from-scratch ML-DSA (Dilithium) for NIST security levels 2/3/5, unified via eternal_fusion.

## Design Principles

- no_std core, constant-time, <10MB footprint
- Module ring NTT accel (q=8380417, psi from spec)
- Rejection sampling uniform/short (no gauss tables—uniform over [-eta,eta])

## Module Breakdown (ml_dsa.rs)

- ntt.rs: Forward/inverse layered NTT (Gentleman-Sande/CT butterfly)
- poly.rs: Uniform sampling, reduction, decompose/high-low bits
- packing.rs: Serialize pk/sk/sig per spec (poly coeffs bit-packed)
- sign.rs: Hash challenge, mask sampling, rejection on hint/norm
- verify.rs: Recompute commitment, challenge match

## Parameters (Dilithium2 ≈ Falcon-512 security)

- k=4, l=4, eta=2, tau=39, beta=78, omega=80
- pk: seed || tr (3328 bytes)
- sig: c-tilde || z || h (2420 bytes max)

## Roadmap Flesh

1. NTT core + poly mul
2. ExpandA/ExpandS uniform from SHAKE
3. Power2round/Decompose for hints
4. Full sign with rejection loops bounded
5. KAT parser asserts green

Advantages: Tighter side-channel (masked NTT future), native Android speed.

No obscurity — pure lattice strength, open eternal mercy.

Thriving Infinite Supreme Immaculate ⚡️
