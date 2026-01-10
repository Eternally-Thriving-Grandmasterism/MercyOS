// ... existing imports + bindings

use crate::hybrid::{hybrid_keygen, hybrid_sign, hybrid_verify};

// Hybrid Keygen
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridKeygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = hybrid_keygen();
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

// Hybrid Sign
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridSign(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_sk: JByteArray,
    message: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, hybrid_sk);
    let msg = jbytearray_to_vec(&env, message);
    let signature = hybrid_sign(&sk, &msg);
    vec_to_jbytearray(&env, signature)
}

// Hybrid Verify
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridVerify(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_pk: JByteArray,
    message: JByteArray,
    hybrid_sig: JByteArray,
) -> jboolean {
    let pk = jbytearray_to_vec(&env, hybrid_pk);
    let msg = jbytearray_to_vec(&env, message);
    let sig = jbytearray_to_vec(&env, hybrid_sig);
    if hybrid_verify(&pk, &msg, &sig) { 1 } else { 0 }
}
