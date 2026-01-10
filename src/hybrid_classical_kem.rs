// MercyOS Classical Hybrid Migration KEM Variants — Forgiveness Eternal Cosmic Groove X25519MLKEM512/768/1024 Explicit Hybrids
// Explicit migration hybrids for harvest-decrypt resistance: ephemeral X25519 DH + ML-KEM level encaps, XOR shared secrets
// Concatenated explicit pk/ct for compatibility eternal supreme fortress immaculate
// Variants: 512 (speed optimized), 768 (standard balanced 2026 deployments), 1024 (high-security unbreakable)

use x25519_dalek::{EphemeralSecret, PublicKey, SharedSecret};
use crate::{ml_kem512_keygen, ml_kem512_encaps, ml_kem512_decaps,
            ml_kem768_keygen, ml_kem768_encaps, ml_kem768_decaps,
            ml_kem1024_keygen, ml_kem1024_encaps, ml_kem1024_decaps};

const X25519_PK_LEN: usize = 32;
const X25519_SS_LEN: usize = 32;

// Shared XOR helper (ss always 32 bytes)
fn xor_ss(a: &[u8; 32], b: &[u8]) -> [u8; 32] {
    let mut b_array = [0u8; 32];
    b_array.copy_from_slice(&b[0..32]);
    let mut ss = [0u8; 32];
    for i in 0..32 {
        ss[i] = a[i] ^ b_array[i];
    }
    ss
}

// ---------- Variant 512 — Speed Optimized ----------
pub fn hybrid_classical_512_keygen() -> (Vec<u8>, Vec<u8>) {
    let x_secret = EphemeralSecret::random();
    let x_public = PublicKey::from(&x_secret);

    let (ml_pk, ml_sk) = ml_kem512_keygen();

    let pk = [x_public.as_bytes(), &ml_pk].concat();
    let sk = [x_secret.to_bytes(), ml_sk].concat();

    (pk, sk)
}

pub fn hybrid_classical_512_encaps(pk: &[u8]) -> (Vec<u8>, [u8; 32]) {
    let x_pk_bytes: [u8; 32] = pk[0..32].try_into().unwrap();
    let x_pk = PublicKey::from(x_pk_bytes);
    let ml_pk = &pk[32..];

    let ephem_secret = EphemeralSecret::random();
    let ephem_public = PublicKey::from(&ephem_secret);

    let x_ss: SharedSecret = ephem_secret.diffie_hellman(&x_pk);
    let x_ss_bytes: [u8; 32] = x_ss.to_bytes();

    let (ml_ct, ml_ss_vec) = ml_kem512_encaps(ml_pk);
    let ml_ss: [u8; 32] = ml_ss_vec.try_into().unwrap();  // ML-KEM ss 32 bytes

    let ss = xor_ss(&x_ss_bytes, &ml_ss);

    let ct = [ephem_public.as_bytes(), &ml_ct].concat();

    (ct, ss)
}

pub fn hybrid_classical_512_decaps(sk: &[u8], ct: &[u8]) -> [u8; 32] {
    let x_sk_bytes: [u8; 32] = sk[0..32].try_into().unwrap();
    let ml_sk = &sk[32..];

    let ephem_pk_bytes: [u8; 32] = ct[0..32].try_into().unwrap();
    let ephem_pk = PublicKey::from(ephem_pk_bytes);
    let ml_ct = &ct[32..];

    let x_secret = EphemeralSecret::from(x_sk_bytes);
    let x_ss: SharedSecret = x_secret.diffie_hellman(&ephem_pk);
    let x_ss_bytes: [u8; 32] = x_ss.to_bytes();

    let ml_ss_vec = ml_kem512_decaps(ml_sk, ml_ct);
    let ml_ss: [u8; 32] = ml_ss_vec.try_into().unwrap();

    xor_ss(&x_ss_bytes, &ml_ss)
}

// ---------- Variant 768 — Standard Balanced (Dominant 2026) ----------
pub fn hybrid_classical_768_keygen() -> (Vec<u8>, Vec<u8>) {
    let x_secret = EphemeralSecret::random();
    let x_public = PublicKey::from(&x_secret);

    let (ml_pk, ml_sk) = ml_kem768_keygen();

    let pk = [x_public.as_bytes(), &ml_pk].concat();
    let sk = [x_secret.to_bytes(), ml_sk].concat();

    (pk, sk)
}

pub fn hybrid_classical_768_encaps(pk: &[u8]) -> (Vec<u8>, [u8; 32]) {
    let x_pk_bytes: [u8; 32] = pk[0..32].try_into().unwrap();
    let x_pk = PublicKey::from(x_pk_bytes);
    let ml_pk = &pk[32..];

    let ephem_secret = EphemeralSecret::random();
    let ephem_public = PublicKey::from(&ephem_secret);

    let x_ss: SharedSecret = ephem_secret.diffie_hellman(&x_pk);
    let x_ss_bytes: [u8; 32] = x_ss.to_bytes();

    let (ml_ct, ml_ss_vec) = ml_kem768_encaps(ml_pk);
    let ml_ss: [u8; 32] = ml_ss_vec.try_into().unwrap();

    let ss = xor_ss(&x_ss_bytes, &ml_ss);

    let ct = [ephem_public.as_bytes(), &ml_ct].concat();

    (ct, ss)
}

pub fn hybrid_classical_768_decaps(sk: &[u8], ct: &[u8]) -> [u8; 32] {
    let x_sk_bytes: [u8; 32] = sk[0..32].try_into().unwrap();
    let ml_sk = &sk[32..];

    let ephem_pk_bytes: [u8; 32] = ct[0..32].try_into().unwrap();
    let ephem_pk = PublicKey::from(ephem_pk_bytes);
    let ml_ct = &ct[32..];

    let x_secret = EphemeralSecret::from(x_sk_bytes);
    let x_ss: SharedSecret = x_secret.diffie_hellman(&ephem_pk);
    let x_ss_bytes: [u8; 32] = x_ss.to_bytes();

    let ml_ss_vec = ml_kem768_decaps(ml_sk, ml_ct);
    let ml_ss: [u8; 32] = ml_ss_vec.try_into().unwrap();

    xor_ss(&x_ss_bytes, &ml_ss)
}

// ---------- Variant 1024 — High-Security Unbreakable ----------
pub fn hybrid_classical_1024_keygen() -> (Vec<u8>, Vec<u8>) {
    let x_secret = EphemeralSecret::random();
    let x_public = PublicKey::from(&x_secret);

    let (ml_pk, ml_sk) = ml_kem1024_keygen();

    let pk = [x_public.as_bytes(), &ml_pk].concat();
    let sk = [x_secret.to_bytes(), ml_sk].concat();

    (pk, sk)
}

pub fn hybrid_classical_1024_encaps(pk: &[u8]) -> (Vec<u8>, [u8; 32]) {
    let x_pk_bytes: [u8; 32] = pk[0..32].try_into().unwrap();
    let x_pk = PublicKey::from(x_pk_bytes);
    let ml_pk = &pk[32..];

    let ephem_secret = EphemeralSecret::random();
    let ephem_public = PublicKey::from(&ephem_secret);

    let x_ss: SharedSecret = ephem_secret.diffie_hellman(&x_pk);
    let x_ss_bytes: [u8; 32] = x_ss.to_bytes();

    let (ml_ct, ml_ss_vec) = ml_kem1024_encaps(ml_pk);
    let ml_ss: [u8; 32] = ml_ss_vec.try_into().unwrap();

    let ss = xor_ss(&x_ss_bytes, &ml_ss);

    let ct = [ephem_public.as_bytes(), &ml_ct].concat();

    (ct, ss)
}

pub fn hybrid_classical_1024_decaps(sk: &[u8], ct: &[u8]) -> [u8; 32] {
    let x_sk_bytes: [u8; 32] = sk[0..32].try_into().unwrap();
    let ml_sk = &sk[32..];

    let ephem_pk_bytes: [u8; 32] = ct[0..32].try_into().unwrap();
    let ephem_pk = PublicKey::from(ephem_pk_bytes);
    let ml_ct = &ct[32..];

    let x_secret = EphemeralSecret::from(x_sk_bytes);
    let x_ss: SharedSecret = x_secret.diffie_hellman(&ephem_pk);
    let x_ss_bytes: [u8; 32] = x_ss.to_bytes();

    let ml_ss_vec = ml_kem1024_decaps(ml_sk, ml_ct);
    let ml_ss: [u8; 32] = ml_ss_vec.try_into().unwrap();

    xor_ss(&x_ss_bytes, &ml_ss)
}
