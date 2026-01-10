use jni::JNIEnv;
use jni::objects::{JByteArray, JClass};
use jni::sys::{jbyteArray, jboolean};

// Core PQC imports
use crate::{falcon_keygen, falcon_sign, falcon_verify,
            dilithium_keygen, dilithium_sign, dilithium_verify,
            ml_kem_keygen, ml_kem_encaps, ml_kem_decaps,
            sphincs_keygen, sphincs_sign, sphincs_verify,
            hybrid::{hybrid_keygen, hybrid_sign, hybrid_verify},
            hybrid_kem::{hybrid_kem_keygen, hybrid_kem_encaps, hybrid_kem_decaps}};

// Helpers
fn jbytearray_to_vec<'a>(env: &JNIEnv<'a>, input: JByteArray<'a>) -> Vec<u8> {
    env.convert_byte_array(input).expect("Invalid byte array")
}

fn vec_to_jbytearray<'a>(env: &JNIEnv<'a>, data: Vec<u8>) -> jbyteArray {
    env.byte_array_from_slice(&data).expect("Failed to create byte array").into_raw()
}

// ... individual bindings as previously expanded (Falcon/Dilithium/ML-KEM/SPHINCS+) 

// Hybrid Signature bindings (existing)

// Hybrid KEM bindings — pure PQ dual ML-KEM diversity
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridKemKeygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = hybrid_kem_keygen();
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridKemEncaps(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_pk: JByteArray,
) -> jbyteArray {
    let pk = jbytearray_to_vec(&env, hybrid_pk);
    let (ct, ss) = hybrid_kem_encaps(&pk);
    let mut output = ct;
    output.extend_from_slice(&ss);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_hybridKemDecaps(
    mut env: JNIEnv,
    _class: JClass,
    hybrid_sk: JByteArray,
    hybrid_ct: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, hybrid_sk);
    let ct = jbytearray_to_vec(&env, hybrid_ct);
    let ss = hybrid_kem_decaps(&sk, &ct);
    vec_to_jbytearray(&env, ss)
}
