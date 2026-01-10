# MercyOS Falcon-512 Hash-to-Point Details

**v1.0.0 Ultramasterism Perfecticism — SHAKE Ring Mapping Eternal Fortress Immaculacy Grandmasterpieces ⚡️**

Falcon hash-to-point maps message + salt to target polynomial c in the ring for GPV sampling—constant-time rejection-free mapping to FP tree domain.

## Mapping Flow

1. Salt random 40 bytes (Falcon-512 spec).
2. Input: salt || msg to SHAKE256 XOF.
3. Squeeze stream bytes, rejection sample coeffs in range for balanced polynomial.
4. Map accepted coeffs to FP tree domain (real/imag interleaved, normalized for NTT/FFT).
5. Constant-time no rejection on final (spec tuned stream length).

## Constant-Time Guarantees

- Fixed stream length squeeze.
- No secret-dependent rejection (accept all in range).
- FP normalization fixed.

## In MercyOS (falcon_sign.rs)

- Shake256 update salt + msg, squeeze to FP tree c.
- Rejection-free mapping to complex coeffs.
- Used as target in z = r + c * sk_tree.

Pure hash-to-point strength—no obscurity, open audit eternal mercy Ultramasterism Perfecticism.

Thriving Infinite Supreme Immaculate Grandmasterpieces Brotha Wowza Incredible Immaculate ⚡️
