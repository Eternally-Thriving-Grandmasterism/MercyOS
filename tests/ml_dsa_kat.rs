#[cfg(test)]
mod tests {
    use std::fs::File;
    use std::io::{BufRead, BufReader};
    use hex;
    use mercyos::ml_dsa::{DilithiumSigner}; // Adjust when module ready

    #[test]
    fn dilithium_kat() {
        let path = "tests/kat/PQCsignKAT_Dilithium2.rsp"; // Or Dilithium3/5 — adjust for target level
        let file = File::open(path).expect("KAT file missing — download PQCsignKAT_Dilithium2.rsp and place in tests/kat/");
        let reader = BufReader::new(file);

        let mut count: Option<u32> = None;
        let mut msg: Vec<u8> = Vec::new();
        let mut pk: Vec<u8> = Vec::new();
        let mut sk: Vec<u8> = Vec::new();
        let mut sm: Vec<u8> = Vec::new();

        for line in reader.lines() {
            let line = line.unwrap();
            let trimmed = line.trim();
            if trimmed.is_empty() || trimmed.starts_with('#') {
                if !pk.is_empty() {
                    // Process completed vector
                    let sig_len = sm.len() - msg.len();
                    let sig = &sm[..sig_len];
                    let appended_msg = &sm[sig_len..];

                    assert_eq!(msg, appended_msg, "Signed message append mismatch for count {}", count.unwrap());

                    // Mandatory verification
                    assert!(DilithiumSigner::verify(&pk, &msg, sig),
                        "Verification failed for count {}", count.unwrap());

                    // Optional: sign match when deterministic
                    // let generated_sig = DilithiumSigner::sign(&sk, &msg);
                    // assert_eq!(generated_sig, sig, "Sign mismatch for count {}", count.unwrap());

                    // Reset
                    msg.clear();
                    pk.clear();
                    sk.clear();
                    sm.clear();
                }
                continue;
            }

            if let Some(eq_pos) = trimmed.find(" = ") {
                let key = &trimmed[..eq_pos];
                let value = &trimmed[eq_pos + 3..];

                match key {
                    "count" => count = Some(value.parse().unwrap()),
                    "msg" => msg = hex::decode(value).unwrap(),
                    "pk" => pk = hex::decode(value).unwrap(),
                    "sk" => sk = hex::decode(value).unwrap(),
                    "sm" => sm = hex::decode(value).unwrap(),
                    _ => {},
                }
            }
        }

        println!("ML-DSA/Dilithium KAT complete — parser aligned eternal ⚡️");
    }
}
