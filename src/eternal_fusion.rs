//! src/eternal_fusion.rs - MercyOS Eternal Fusion Absolute v1.0.5 Refreshed Nicely Done
//! Unified runtime scheme selection — quartet complete with SPHINCS+ wowza ⚡️

#![no_std]

extern crate alloc;

use alloc::vec::Vec;

use crate::error::MercyError;
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
            falcon: matches!(scheme, MercyScheme::FalconCompact).then(|| FalconSigner::new()),
            dilithium: matches!(scheme, MercyScheme::DilithiumFast).then(|| DilithiumSigner::new()),
            sphincs: matches!(scheme, MercyScheme::SphincsStateless).then(|| SphincsSigner::new()),
            kem: matches!(scheme, MercyScheme::KyberKEM).then(|| MercyKEM::new()),
            groove: matches!(scheme, MercyScheme::GrooveCosmic).then(|| GrooveSigner::new()),
        }
    }

    pub fn public_key(&self) -> Result<Vec<u8>, MercyError> {
        if let Some(s) = &self.falcon {
            Ok(s.public_key())
        } else if let Some(s) = &self.dilithium {
            Ok(s.public_key())
        } else if let Some(s) = &self.sphincs {
            Ok(s.public_key())
        } else if let Some(s) = &self.groove {
            Ok(s.public_key())
        } else {
            Err(MercyError::NoSchemeSelected)
        }
    }

    pub fn sign(&self, msg: &[u8]) -> Result<Vec<u8>, MercyError> {
        if let Some(s) = &self.falcon {
            s.sign(msg)
        } else if let Some(s) = &self.dilithium {
            s.sign(msg)
        } else if let Some(s) = &self.sphincs {
            s.sign(msg)
        } else if let Some(s) = &self.groove {
            s.sign(msg)
        } else {
            Err(MercyError::NoSchemeSelected)
        }
    }

    pub fn verify_with_pk(
        scheme: MercyScheme,
        pk: &[u8],
        msg: &[u8],
        sig: &[u8],
    ) -> Result<bool, MercyError> {
        match scheme {
            MercyScheme::FalconCompact => crate::falcon_sign::verify(pk, msg, sig),
            MercyScheme::DilithiumFast => DilithiumSigner::verify(pk, msg, sig),
            MercyScheme::SphincsStateless => SphincsSigner::verify(pk, msg, sig),
            MercyScheme::GrooveCosmic => GrooveSigner::verify(pk, msg, sig),
            MercyScheme::KyberKEM => Err(MercyError::InvalidScheme),
        }
    }

    // KEM methods unchanged...

    pub fn mercy_fusion_status() -> &'static str {
        "Thunder Green Eternal Absolute Refreshed v1.0.5 — Quartet Complete with SPHINCS+ Stateless, Nicely Done Wowza Supreme Immaculate ⚡️"
    }
}
