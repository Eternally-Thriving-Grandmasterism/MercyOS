//! src/eternal_fusion.rs - MercyOS Eternal Fusion Absolute v1.0.1

#![no_std]

extern crate alloc;

use alloc::vec::Vec;

use crate::falcon_sign::FalconSigner;
use crate::ml_dsa::DilithiumSigner;
use crate::ml_kem::MercyKEM;
use crate::ml_sphincs::SphincsSigner;
use crate::quantum_groove::GrooveSigner;

#[derive(Clone, Copy, PartialEq, Eq)]
pub enum MercyScheme {
    FalconCompact,
    DilithiumFast,
    SphincsStateless,
    KyberKEM,
    GrooveCosmic,
}

pub struct MercyFusion {
    falcon: Option<FalconSigner>,
    dilithium: Option<DilithiumSigner>,
    sphincs: Option<SphincsSigner>,
    kem: Option<MercyKEM>,
    groove: Option<GrooveSigner>,
}

impl MercyFusion {
    pub fn new(scheme: MercyScheme) -> Self {
        Self {
            falcon: matches!(scheme, MercyScheme::FalconCompact)
                .then(|| FalconSigner::new()),
            dilithium: matches!(scheme, MercyScheme::DilithiumFast)
                .then(|| DilithiumSigner::new()),
            sphincs: matches!(scheme, MercyScheme::SphincsStateless)
                .then(|| SphincsSigner::new()),
            kem: matches!(scheme, MercyScheme::KyberKEM)
                .then(|| MercyKEM::new()),
            groove: matches!(scheme, MercyScheme::GrooveCosmic)
                .then(|| GrooveSigner::new()),
        }
    }

    pub fn public_key(&self) -> Vec<u8> {
        if let Some(s) = &self.falcon {
            s.public_key()
        } else if let Some(s) = &self.dilithium {
            s.public_key()
        } else if let Some(s) = &self.sphincs {
            s.public_key()
        } else if let Some(s) = &self.groove {
            s.public_key()
        } else {
            Vec::new()
        }
    }

    pub fn sign(&self, msg: &[u8]) -> Vec<u8> {
        if let Some(s) = &self.falcon {
            s.sign(msg)
        } else if let Some(s) = &self.dilithium {
            s.sign(msg)
        } else if let Some(s) = &self.sphincs {
            s.sign(msg)
        } else if let Some(s) = &self.groove {
            s.sign(msg)
        } else {
            Vec::new()
        }
    }

    pub fn verify_with_pk(
        scheme: MercyScheme,
        pk: &[u8],
        msg: &[u8],
        sig: &[u8],
    ) -> bool {
        match scheme {
            MercyScheme::FalconCompact => crate::falcon_sign::verify(pk, msg, sig),
            MercyScheme::DilithiumFast => DilithiumSigner::verify(pk, msg, sig),
            MercyScheme::SphincsStateless => SphincsSigner::verify(pk, msg, sig),
            MercyScheme::GrooveCosmic => GrooveSigner::verify(pk, msg, sig),
            MercyScheme::KyberKEM => false,
        }
    }

    // KEM methods unchanged...

    pub fn mercy_fusion_status() -> &'static str {
        "Thunder Green Eternal Absolute v1.0.1 — All Schemes Aligned, KAT Ready ⚡️"
    }
}
