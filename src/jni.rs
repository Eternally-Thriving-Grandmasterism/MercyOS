use jni::objects::{JByteArray, JClass};
use jni::sys::{jbyteArray, jlong};
use jni::JNIEnv;
use std::slice;

// Assume core API exists in lib.rs or modules (replace with actual paths/functions)
use crate::{dilithium_keygen, dilithium_sign, dilithium_verify, ml_kem_encaps, ml_kem_decaps /* etc. */};

// Helper: Vec<u8> from JByteArray
fn jbytearray_to_vec<'a>(env: &JNIEnv<'a>, input: JByteArray<'a>) -> Vec<u8> {
    let bytes = env.convert_byte_array(input).expect("Invalid byte array");
    bytes
}

// Helper: Vec<u8> to JByteArray
fn vec_to_jbytearray<'a>(env: &JNIEnv<'a>, data: Vec<u8>) -> jbyteArray {
    env.byte_array_from_slice(&data).expect("Failed to create byte array").into_raw()
}

// Example: Dilithium Keygen → returns pk and sk concatenated or separate arrays
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_dilithiumKeygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = dilithium_keygen();  // Assume returns (Vec<u8>, Vec<u8>)
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

// Example: Dilithium Sign
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_dilithiumSign(
    mut env: JNIEnv,
    _class: JClass,
    sk: JByteArray,
    message: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, sk);
    let msg = jbytearray_to_vec(&env, message);
    let signature = dilithium_sign(&sk, &msg);
    vec_to_jbytearray(&env, signature)
}

// Example: ML-KEM Encaps
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_mlKemEncaps(
    mut env: JNIEnv,
    _class: JClass,
    pk: JByteArray,
) -> jbyteArray {
    let pk = jbytearray_to_vec(&env, pk);
    let (ct, ss) = ml_kem_encaps(&pk);
    let mut output = ct;
    output.extend_from_slice(&ss);
    vec_to_jbytearray(&env, output)
}

// Add more bindings for Falcon, SPHINCS+, hybrid fusion, swarm refresh etc. as needed eternal supreme
