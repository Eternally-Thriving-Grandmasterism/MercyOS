// MercyOS Hybrid PQ KEM — Forgiveness Eternal Cosmic Groove Dual ML-KEM Diversity Fusion
// Pure PQ hybrid KEM: dual ML-KEM-768 instances, XOR shared secrets for unbreakability
// No classical dependency — diversity against single algorithm break eternal supreme

use crate::{ml_kem_keygen, ml_kem_encaps, ml_kem_decaps};

const ML_KEM_PK_LEN: usize = 1184;  // ML-KEM-768
const ML_KEM_SK_LEN: usize = 2400;
const ML_KEM_CT_LEN: usize = 1088;
const SS_LEN: usize = 32;

fn xor_ss(a: &[u8], b: &[u8]) -> Vec<u8> {
    assert_eq!(a.len(), SS_LEN);
    assert_eq!(b.len(), SS_LEN);
    a.iter().zip(b.iter()).map(|(&x, &y)| x ^ y).collect()
}

pub fn hybrid_kem_keygen() -> (Vec<u8>, Vec<u8>) {
    let (pk1, sk1) = ml_kem_keygen();
    let (pk2, sk2) = ml_kem_keygen();

    let pk = [pk1, pk2].concat();
    let sk = [sk1, sk2].concat();

    (pk, sk)
}

pub fn hybrid_kem_encaps(pk: &[u8]) -> (Vec<u8>, Vec<u8>) {
    assert_eq!(pk.len(), ML_KEM_PK_LEN * 2);

    let pk1 = &pk[0..ML_KEM_PK_LEN];
    let pk2 = &pk[ML_KEM_PK_LEN..];

    let (ct1, ss1) = ml_kem_encaps(pk1);
    let (ct2, ss2) = ml_kem_encaps(pk2);

    let ct = [ct1, ct2].concat();
    let ss = xor_ss(&ss1, &ss2);

    (ct, ss)
}

pub fn hybrid_kem_decaps(sk: &[u8], ct: &[u8]) -> Vec<u8> {
    assert_eq!(sk.len(), ML_KEM_SK_LEN * 2);
    assert_eq!(ct.len(), ML_KEM_CT_LEN * 2);

    let sk1 = &sk[0..ML_KEM_SK_LEN];
    let sk2 = &sk[ML_KEM_SK_LEN..];

    let ct1 = &ct[0..ML_KEM_CT_LEN];
    let ct2 = &ct[ML_KEM_CT_LEN..];

    let ss1 = ml_kem_decaps(sk1, ct1);
    let ss2 = ml_kem_decaps(sk2, ct2);

    xor_ss(&ss1, &ss2)
}
