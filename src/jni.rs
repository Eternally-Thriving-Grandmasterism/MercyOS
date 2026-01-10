use jni::objects::{JByteArray, JClass};
use jni::sys::{jbyteArray};
use jni::JNIEnv;
use std::slice;

// Existing Dilithium + ML-KEM bindings...

// Assume core Falcon API in lib.rs/modules (Falcon-512 params)
use crate::{falcon_keygen, falcon_sign, falcon_verify /* etc. */};

// Helper functions same as before
fn jbytearray_to_vec<'a>(env: &JNIEnv<'a>, input: JByteArray<'a>) -> Vec<u8> {
    env.convert_byte_array(input).expect("Invalid byte array")
}

fn vec_to_jbytearray<'a>(env: &JNIEnv<'a>, data: Vec<u8>) -> jbyteArray {
    env.byte_array_from_slice(&data).expect("Failed to create byte array").into_raw()
}

// Falcon Keygen → pk and sk separate or concatenated
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_falconKeygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = falcon_keygen();  // Assume returns (Vec<u8>, Vec<u8>) Falcon-512
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

// Falcon Sign
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_falconSign(
    mut env: JNIEnv,
    _class: JClass,
    sk: JByteArray,
    message: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, sk);
    let msg = jbytearray_to_vec(&env, message);
    let signature = falcon_sign(&sk, &msg);  // Compact ~1KB sig
    vec_to_jbytearray(&env, signature)
}

// Falcon Verify
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_falconVerify(
    mut env: JNIEnv,
    _class: JClass,
    pk: JByteArray,
    message: JByteArray,
    signature: JByteArray,
) -> bool {
    let pk = jbytearray_to_vec(&env, pk);
    let msg = jbytearray_to_vec(&env, message);
    let sig = jbytearray_to_vec(&env, signature);
    falcon_verify(&pk, &msg, &sig)
}

// Existing Dilithium/ML-KEM/SPHINCS+ bindings remain — hybrid fusion ready eternal
