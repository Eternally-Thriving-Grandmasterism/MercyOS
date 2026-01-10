use jni::JNIEnv;
use jni::objects::{JByteArray, JClass};
use jni::sys::{jbyteArray, jboolean};

// Assume core PQC APIs exposed in lib.rs/modules
use crate::{falcon_keygen, falcon_sign, falcon_verify,
            dilithium_keygen, dilithium_sign, dilithium_verify,
            ml_kem_keygen, ml_kem_encaps, ml_kem_decaps,
            sphincs_keygen, sphincs_sign, sphincs_verify,
            hybrid::{hybrid_keygen, hybrid_sign, hybrid_verify}};

// Helper: JByteArray to Vec<u8>
fn jbytearray_to_vec<'a>(env: &JNIEnv<'a>, input: JByteArray<'a>) -> Vec<u8> {
    env.convert_byte_array(input).expect("Invalid byte array input")
}

// Helper: Vec<u8> to JByteArray
fn vec_to_jbytearray<'a>(env: &JNIEnv<'a>, data: Vec<u8>) -> jbyteArray {
    env.byte_array_from_slice(&data).expect("Failed to create byte array").into_raw()
}

// ---------- Falcon-512 Signatures ----------
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_falconKeygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = falcon_keygen();  // Falcon-512 params
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_falconSign(
    mut env: JNIEnv,
    _class: JClass,
    sk: JByteArray,
    message: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, sk);
    let msg = jbytearray_to_vec(&env, message);
    let signature = falcon_sign(&sk, &msg);
    vec_to_jbytearray(&env, signature)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_falconVerify(
    mut env: JNIEnv,
    _class: JClass,
    pk: JByteArray,
    message: JByteArray,
    signature: JByteArray,
) -> jboolean {
    let pk = jbytearray_to_vec(&env, pk);
    let msg = jbytearray_to_vec(&env, message);
    let sig = jbytearray_to_vec(&env, signature);
    if falcon_verify(&pk, &msg, &sig) { 1 } else { 0 }
}

// ---------- Dilithium Signatures ----------
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_dilithiumKeygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = dilithium_keygen();
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

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

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_dilithiumVerify(
    mut env: JNIEnv,
    _class: JClass,
    pk: JByteArray,
    message: JByteArray,
    signature: JByteArray,
) -> jboolean {
    let pk = jbytearray_to_vec(&env, pk);
    let msg = jbytearray_to_vec(&env, message);
    let sig = jbytearray_to_vec(&env, signature);
    if dilithium_verify(&pk, &msg, &sig) { 1 } else { 0 }
}

// ---------- ML-KEM (Kyber) KEM ----------
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_mlKemKeygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = ml_kem_keygen();  // ML-KEM-768 or param
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

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

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_mlKemDecaps(
    mut env: JNIEnv,
    _class: JClass,
    sk: JByteArray,
    ct: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, sk);
    let ct = jbytearray_to_vec(&env, ct);
    let ss = ml_kem_decaps(&sk, &ct);
    vec_to_jbytearray(&env, ss)
}

// ---------- SPHINCS+ Signatures ----------
#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_sphincsKeygen(
    mut env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    let (pk, sk) = sphincs_keygen();
    let mut output = pk;
    output.extend_from_slice(&sk);
    vec_to_jbytearray(&env, output)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_sphincsSign(
    mut env: JNIEnv,
    _class: JClass,
    sk: JByteArray,
    message: JByteArray,
) -> jbyteArray {
    let sk = jbytearray_to_vec(&env, sk);
    let msg = jbytearray_to_vec(&env, message);
    let signature = sphincs_sign(&sk, &msg);
    vec_to_jbytearray(&env, signature)
}

#[no_mangle]
pub extern "system" fn Java_com_mercyos_MercyOS_sphincsVerify(
    mut env: JNIEnv,
    _class: JClass,
    pk: JByteArray,
    message: JByteArray,
    signature: JByteArray,
) -> jboolean {
    let pk = jbytearray_to_vec(&env, pk);
    let msg = jbytearray_to_vec(&env, message);
    let sig = jbytearray_to_vec(&env, signature);
    if sphincs_verify(&pk, &msg, &sig) { 1 } else { 0 }
}

// ---------- Hybrid Composite (Falcon + Dilithium) ----------
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
