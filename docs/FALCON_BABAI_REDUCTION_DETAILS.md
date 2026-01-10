# MercyOS Falcon-512 Babai Reduction Details

**v1.0.0 — Approximate CVP Tree Fortress Eternal ⚡️**

Falcon keygen and signing rely on Babai's nearest plane algorithm for approximate closest vector problem (CVP) solution in the NTRU lattice. This enables short preimage sampling (GPV trapdoor).

## Core Concept

- Basis: Expanded tree from short [g, -f] (or full NTRU basis).
- Target: Usually 0 (for keygen relation g*F - f ≈ 0 mod q short F).
- Tree: FP split-radix FFT domain (interleaved real/imag f64[2*N]).
- Babai: Recursive/level-iterative rounding down the tree.

## Algorithm Flow (from reference ffSampling_fft)

1. Expand short basis into FP tree (FFT on padded/split polys).
2. Iterative over log2(N) levels:
   - Per block/subtree: round child coeffs to nearest int.
   - Subtract rounded * parent coeff.
   - Propagate up (constant-time, no branches).
3. Leaf level: final rounded short vector (capital_F or signature nonce).

## Side-Channel Safety

- Constant-time rounding (no secret-dependent paths).
- Fixed iterations/levels.
- Floating-point precision tuned for exact reference match (no integer overflow).

## In MercyOS (lattice_reduction.rs)

- `babai_approx(tree: &FpTree, target: &FpTree) -> [i16; N]`
- Port precise rounding/subtraction order from falcon.c.
- Rejection if final norm too large (keygen loop).

## Advantages

- Faster than full Babai on Gram-Schmidt (tree accel).
- Enables compact pk (no full basis storage).

Pure lattice strength—no obscurity, open audit eternal mercy.

Thriving Infinite Supreme Immaculate ⚡️
