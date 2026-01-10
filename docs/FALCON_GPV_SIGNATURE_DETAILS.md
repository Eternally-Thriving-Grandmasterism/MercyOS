# MercyOS Falcon-512 GPV Signature Details

**v1.0.0 Ultramasterism — GPV Trapdoor Sampling Eternal Fortress Immaculacy Grandmasterpieces ⚡️**

Falcon signing uses Gaudry-Patrick-Vergnaud (GPV) trapdoor preimage sampling over NTRU lattices for short signatures.

## Core Concept

- Trapdoor: Short basis [f, g, F, G] with relation g*F - f*G = q mod (x^N + 1)
- Compact pk: t = (f*B + g) / q high bits or FP tree form
- Signing: Hash msg to target c, solve short s1, s2 such that s1 + s2 * t ≈ c mod q
- GPV: Babai nearest plane on expanded FP tree basis, Gaussian sampling for randomness

## Algorithm Flow (Signing)

1. Hash msg || salt to target polynomial c (hash-to-point)
2. Sample random short r (Gaussian tree)
3. Compute z = r + c * secret (tree domain)
4. Babai nearest plane on FP tree to find short preimage z' ≈ z
5. s = z - z' short solution
6. Compress s to signature (FP to int rounding)
7. Rejection if norm too large (bounded loops)

## Side-Channel Safety

- Constant-time FP arithmetic + rounding
- Bounded rejection loops (spec prob success high)
- No secret-dependent branches in tree traversal

## In MercyOS (falcon_sign.rs)

- `sign(msg)`: Full GPV flow with FP tree build, Babai sampling, compression
- Rejection on ||s|| > bound (sigma tuned)
- Verify: Decompress, recompute tree norm squared < beta^2 with tolerance

Pure lattice GPV strength—no obscurity, open audit eternal mercy Ultramasterism.

Thriving Infinite Supreme Immaculate Grandmasterpieces Brotha Wowza ⚡️
