// ... existing imports + individual/hybrid bindings

use crate::hybrid_classical_kem::{hybrid_classical_512_keygen, hybrid_classical_512_encaps, hybrid_classical_512_decaps,
                                 hybrid_classical_768_keygen, hybrid_classical_768_encaps, hybrid_classical_768_decaps,
                                 hybrid_classical_1024_keygen, hybrid_classical_1024_encaps, hybrid_classical_1024_decaps};

// Hybrid Classical KEM 512 bindings — Speed optimized
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassical512Keygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = hybrid_classical_512_keygen();
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassical512Encaps(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_pk: JByteArray,
) -> jbyteArray {
    let pk = jbytearray_to_vec(&env, hybrid_pk);
    let (ct, ss) = hybrid_classical_512_encaps(&pk);
    let mut output = ct;
    output.extend_from_slice(&ss);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassical512Decaps(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_sk: JByteArray,
    hybrid_ct: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, hybrid_sk);
    let ct = jbytearray_to_vec(&env, hybrid_ct);
    let ss = hybrid_classical_512_decaps(&sk, &ct);
    vec_to_jbytearray(&env, ss.to_vec())
}

// Hybrid Classical KEM 768 bindings — Standard balanced (dominant 2026)
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassical768Keygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = hybrid_classical_768_keygen();
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassical768Encaps(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_pk: JByteArray,
) -> jbyteArray {
    let pk = jbytearray_to_vec(&env, hybrid_pk);
    let (ct, ss) = hybrid_classical_768_encaps(&pk);
    let mut output = ct;
    output.extend_from_slice(&ss);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassical768Decaps(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_sk: JByteArray,
    hybrid_ct: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, hybrid_sk);
    let ct = jbytearray_to_vec(&env, hybrid_ct);
    let ss = hybrid_classical_768_decaps(&sk, &ct);
    vec_to_jbytearray(&env, ss.to_vec())
}

// Hybrid Classical KEM 1024 bindings — High-security unbreakable
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassical1024Keygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = hybrid_classical_1024_keygen();
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassical1024Encaps(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_pk: JByteArray,
) -> jbyteArray {
    let pk = jbytearray_to_vec(&env, hybrid_pk);
    let (ct, ss) = hybrid_classical_1024_encaps(&pk);
    let mut output = ct;
    output.extend_from_slice(&ss);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassical1024Decaps(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_sk: JByteArray,
    hybrid_ct: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, hybrid_sk);
    let ct = jbytearray_to_vec(&env, hybrid_ct);
    let ss = hybrid_classical_1024_decaps(&sk, &ct);
    vec_to_jbytearray(&env, ss.to_vec())
}
