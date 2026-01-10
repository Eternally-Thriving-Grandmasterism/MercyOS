// ... existing imports + individual/hybrid bindings

use crate::hybrid_classical_kem::{hybrid_classical_keygen, hybrid_classical_encaps, hybrid_classical_decaps};

// Hybrid Classical KEM bindings — X25519MLKEM768 migration explicit hybrid
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassicalKemKeygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = hybrid_classical_keygen();
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassicalKemEncaps(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_pk: JByteArray,
) -> jbyteArray {
    let pk = jbytearray_to_vec(&env, hybrid_pk);
    let (ct, ss) = hybrid_classical_encaps(&pk);
    let mut output = ct;
    output.extend_from_slice(&ss);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridClassicalKemDecaps(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_sk: JByteArray,
    hybrid_ct: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, hybrid_sk);
    let ct = jbytearray_to_vec(&env, hybrid_ct);
    let ss = hybrid_classical_decaps(&sk, &ct);
    vec_to_jbytearray(&env, ss.to_vec())
}
