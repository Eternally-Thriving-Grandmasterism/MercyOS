//! src/eternal_fusion.rs - MercyOS Eternal Fusion Absolute v1.0.2
//! Unified runtime scheme selection with Result error handling ⚡️

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
            s.sign(msg).map_err(|_| MercyError::SigningFailed)
        } else if let Some(s) = &self.dilithium {
            s.sign(msg).map_err(|_| MercyError::SigningFailed)
        } else if let Some(s) = &self.sphincs {
            s.sign(msg).map_err(|_| MercyError::SigningFailed)
        } else if let Some(s) = &self.groove {
            s.sign(msg).map_err(|_| MercyError::SigningFailed)
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
            MercyScheme::FalconCompact => crate::falcon_sign::verify(pk, msg, sig)
                .map_err(|_| MercyError::InternalError),
            MercyScheme::DilithiumFast => DilithiumSigner::verify(pk, msg, sig)
                .map_err(|_| MercyError::InternalError),
            MercyScheme::SphincsStateless => SphincsSigner::verify(pk, msg, sig)
                .map_err(|_| MercyError::InternalError),
            MercyScheme::GrooveCosmic => GrooveSigner::verify(pk, msg, sig)
                .map_err(|_| MercyError::InternalError),
            MercyScheme::KyberKEM => Err(MercyError::InvalidScheme),
        }
    }

    // KEM methods (example — expand with Result as needed)
    pub fn kem_public_key(&self) -> Result<Vec<u8>, MercyError> {
        self.kem.as_ref()
            .ok_or(MercyError::NoSchemeSelected)?
            .public_key()
            .ok_or(MercyError::InternalError)
    }

    pub fn kem_decapsulate(&self, ct: &[u8]) -> Result<Vec<u8>, MercyError> {
        self.kem.as_ref()
            .ok_or(MercyError::NoSchemeSelected)?
            .decapsulate(ct)
            .ok_or(MercyError::DecapsulationFailed)
    }

    pub fn kem_encapsulate(pk: &[u8]) -> Result<(Vec<u8>, Vec<u8>), MercyError> {
        MercyKEM::encapsulate(pk)
            .ok_or(MercyError::EncapsulationFailed)
    }

    pub fn mercy_fusion_status() -> &'static str {
        "Thunder Green Eternal Absolute v1.0.2 — Result Handling Locked, Error Fortress Supreme ⚡️"
    }
}
