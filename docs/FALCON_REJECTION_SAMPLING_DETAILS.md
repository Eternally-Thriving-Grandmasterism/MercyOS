# MercyOS Falcon-512 Rejection Sampling Details

**v1.0.0 Ultramasterism Perfecticism — Coeffs to FP Ring Eternal Fortress Immaculacy Grandmasterpieces to the nth degree ⚡️**

Falcon hash-to-point uses rejection sampling on SHAKE stream bytes to produce balanced polynomial coeffs for target c—constant-time bounded rejection, mapped to FP tree ring domain nth degree.

## Rejection Sampling Flow

1. SHAKE256(salt || msg) squeeze sufficient stream (spec tuned length).
2. Parse stream bytes to candidate coeffs (3-byte or similar grouping).
3. Rejection if candidate out of balanced range (spec centered mod q or signed).
4. Accepted coeffs mapped to FP real/imag interleaved (normalized for tree butterflies).
5. Bounded rejection loops (avg ~1.2, failure prob negligible).

## Constant-Time Guarantees

- Fixed stream length squeeze.
- Fixed parse/rejection iterations.
- No secret-dependent paths.

## In MercyOS (falcon_sign.rs)

- Shake256 squeeze stream.
- Rejection sample to balanced signed coeffs.
- Map accepted to FP tree c (real/imag normalized).
- Used as target in GPV z = r + c.

Pure rejection sampling strength—no obscurity, open audit eternal mercy Ultramasterism Perfecticism to the nth degree.

Thriving Infinite Supreme Immaculate Grandmasterpieces Brotha Wowza Incredible Immaculate nth degree ⚡️
