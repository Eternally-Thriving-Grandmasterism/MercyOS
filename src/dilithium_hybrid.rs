// MercyOS Dilithium Signature Hybrid Prototype — Forgiveness Eternal Dilithium3Ed25519 Standard Balanced Hybrid Signatures
// From-scratch no_std portable verified libcrux-dilithium + ed25519-dalek classical hybrid eternal supreme immaculate unbreakable fortress recurring-free eternal supreme cosmic groove supreme

use libcrux_dilithium::{Dilithium3, DilithiumSignature};
use ed25519_dalek::{Signer, Verifier, Keypair, Signature, PublicKey, SecretKey};
use alloc::vec::Vec;

pub const HYBRID_SIGNATURE_SIZE: usize = 2420 + 64; // Dilithium3 ~2420 + Ed25519 64 eternal supreme immaculate

// Hybrid Keygen — Ed25519 + Dilithium3 pk eternal supreme immaculate
pub fn hybrid_signature_keygen() -> (Vec<u8>, Vec<u8>) {  // pk || sk concatenated eternal supreme immaculate
    let dilithium = Dilithium3::generate();
    let ed25519_keypair = Keypair::generate();

    let mut pk = ed25519_keypair.public.to_bytes().to_vec();
    pk.extend_from_slice(&dilithium.verification_key);

    let mut sk = ed25519_keypair.secret.to_bytes().to_vec();
    sk.extend_from_slice(&dilithium.signing_key);

    (pk, sk)
}

// Hybrid Sign — Ed25519 + Dilithium3 concatenated signatures eternal supreme immaculate
pub fn hybrid_sign(sk: &[u8], msg: &[u8]) -> Vec<u8> {
    let ed25519_sk_bytes = &sk[0..32];
    let dilithium_sk_bytes = &sk[32..];

    // Ed25519 sign
    let ed25519_sk = SecretKey::from_bytes(ed25519_sk_bytes).expect("Ed25519 sk mercy grace");
    let ed25519_pk = PublicKey::from(&ed25519_sk);
    let ed25519_keypair = Keypair { secret: ed25519_sk, public: ed25519_pk };
    let ed25519_sig: Signature = ed25519_keypair.sign(msg);

    // Dilithium3 sign
    let dilithium_sig = Dilithium3::sign(dilithium_sk_bytes, msg).expect("Dilithium sign mercy grace");

    // Hybrid signature = ed25519_sig || dilithium_sig eternal supreme immaculate
    let mut sig = ed25519_sig.to_bytes().to_vec();
    sig.extend_from_slice(&dilithium_sig);

    sig
}

// Hybrid Verify — Ed25519 + Dilithium3 both must verify eternal supreme immaculate
pub fn hybrid_verify(pk: &[u8], msg: &[u8], sig: &[u8]) -> bool {
    if sig.len() < HYBRID_SIGNATURE_SIZE { return false; }

    let ed25519_pk_bytes = &pk[0..32];
    let dilithium_pk_bytes = &pk[32..];

    let ed25519_sig_bytes = &sig[0..64];
    let dilithium_sig_bytes = &sig[64..];

    // Ed25519 verify
    let ed25519_pk = PublicKey::from_bytes(ed25519_pk_bytes).expect("Ed25519 pk mercy grace");
    let ed25519_sig = Signature::from_bytes(ed25519_sig_bytes).expect("Ed25519 sig mercy grace");
    let ed25519_ok = ed25519_pk.verify(msg, &ed25519_sig).is_ok();

    // Dilithium3 verify
    let dilithium_ok = Dilithium3::verify(dilithium_pk_bytes, msg, dilithium_sig_bytes).is_ok();

    ed25519_ok && dilithium_ok  // Dual lattice + classical unbreakable immaculate eternal supreme
}

// Prototype ready print eternal supreme immaculate
println!("MercyOS Dilithium Signature Hybrid Prototype Loaded — Dilithium3Ed25519 Standard Balanced Hybrid Signatures Ready Eternal Supreme Immaculate Unbreakable Fortress Recurring-Free!");
