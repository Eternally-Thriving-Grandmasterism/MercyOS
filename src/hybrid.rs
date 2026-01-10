// MercyOS Hybrid PQC Signatures — Forgiveness Eternal Cosmic Groove Dual Lattice Fusion

use crate::{falcon_sign, falcon_verify, falcon_keygen, dilithium_sign, dilithium_verify, dilithium_keygen};

// Hybrid Keygen: Generate both Falcon-512 and Dilithium keys, return concatenated pk || sk
pub fn hybrid_keygen() -> (Vec<u8>, Vec<u8>) {
    let (f_pk, f_sk) = falcon_keygen();
    let (d_pk, d_sk) = dilithium_keygen();

    let mut hybrid_pk = f_pk;
    hybrid_pk.extend_from_slice(&d_pk);

    let mut hybrid_sk = f_sk;
    hybrid_sk.extend_from_slice(&d_sk);

    (hybrid_pk, hybrid_sk)
}

// Hybrid Sign: Sign message with both Falcon and Dilithium, concatenate signatures (Falcon first for compact)
pub fn hybrid_sign(hybrid_sk: &[u8], message: &[u8]) -> Vec<u8> {
    // Assume fixed sizes or parse lengths — refine with actual pk/sk bounds (Falcon-512 pk~897 sk~1281, Dilithium vars)
    let falcon_sk_len = 1281;  // Example Falcon-512 sk size — adjust to actual
    let f_sk = &hybrid_sk[0..falcon_sk_len];
    let d_sk = &hybrid_sk[falcon_sk_len..];

    let f_sig = falcon_sign(f_sk, message);
    let d_sig = dilithium_sign(d_sk, message);

    let mut hybrid_sig = f_sig;
    hybrid_sig.extend_from_slice(&d_sig);
    hybrid_sig
}

// Hybrid Verify: Parse concatenated pk/sig, verify both Falcon and Dilithium
pub fn hybrid_verify(hybrid_pk: &[u8], message: &[u8], hybrid_sig: &[u8]) -> bool {
    let falcon_pk_len = 897;  // Example Falcon-512 pk size — adjust actual
    let f_pk = &hybrid_pk[0..falcon_pk_len];
    let d_pk = &hybrid_pk[falcon_pk_len..];

    // Parse sig lengths or use dynamic — assume known or prefixed
    let falcon_sig_len = 666;  // Approx Falcon-512 sig — refine
    let f_sig = &hybrid_sig[0..falcon_sig_len];
    let d_sig = &hybrid_sig[falcon_sig_len..];

    falcon_verify(f_pk, message, f_sig) && dilithium_verify(d_pk, message, d_sig)
}
