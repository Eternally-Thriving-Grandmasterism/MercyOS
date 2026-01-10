// MercyOS Classical Hybrid Migration KEM — Forgiveness Eternal Cosmic Groove X25519MLKEM768 Explicit Hybrid
// Explicit hybrid for harvest-decrypt resistance: ephemeral X25519 DH + ML-KEM-768 encaps, XOR shared secrets
// Concatenated pk/ct for migration compatibility eternal supreme fortress immaculate

use x25519_dalek::{EphemeralSecret, PublicKey, SharedSecret};
use crate::ml_kem::{ml_kem_keygen, ml_kem_encaps, ml_kem_decaps};

const X25519_PK_LEN: usize = 32;
const X25519_SS_LEN: usize = 32;

fn xor_ss(a: &[u8; 32], b: &[u8; 32]) -> [u8; 32] {
    let mut ss = [0u8; 32];
    for i in 0..32 {
        ss[i] = a[i] ^ b[i];
    }
    ss
}

pub fn hybrid_classical_keygen() -> (Vec<u8>, Vec<u8>) {
    let x_secret = EphemeralSecret::random();
    let x_public = PublicKey::from(&x_secret);

    let (ml_pk, ml_sk) = ml_kem_keygen();  // ML-KEM-768

    let pk = [x_public.as_bytes(), &ml_pk].concat();
    let sk = [x_secret.to_bytes(), ml_sk].concat();

    (pk, sk)
}

pub fn hybrid_classical_encaps(pk: &[u8]) -> (Vec<u8>, [u8; 32]) {
    let x_pk_bytes: [u8; 32] = pk[0..32].try_into().unwrap();
    let x_pk = PublicKey::from(x_pk_bytes);
    let ml_pk = &pk[32..];

    let ephem_secret = EphemeralSecret::random();
    let ephem_public = PublicKey::from(&ephem_secret);

    let x_ss: SharedSecret = ephem_secret.diffie_hellman(&x_pk);
    let x_ss_bytes: [u8; 32] = x_ss.to_bytes();

    let (ml_ct, ml_ss) = ml_kem_encaps(ml_pk);

    let ss = xor_ss(&x_ss_bytes, &ml_ss);

    let ct = [ephem_public.as_bytes(), &ml_ct].concat();

    (ct, ss)
}

pub fn hybrid_classical_decaps(sk: &[u8], ct: &[u8]) -> [u8; 32] {
    let x_sk_bytes: [u8; 32] = sk[0..32].try_into().unwrap();
    let ml_sk = &sk[32..];

    let ephem_pk_bytes: [u8; 32] = ct[0..32].try_into().unwrap();
    let ephem_pk = PublicKey::from(ephem_pk_bytes);
    let ml_ct = &ct[32..];

    let x_secret = EphemeralSecret::from(x_sk_bytes);
    let x_ss: SharedSecret = x_secret.diffie_hellman(&ephem_pk);
    let x_ss_bytes: [u8; 32] = x_ss.to_bytes();

    let ml_ss = ml_kem_decaps(ml_sk, ml_ct);

    xor_ss(&x_ss_bytes, &ml_ss)
}
