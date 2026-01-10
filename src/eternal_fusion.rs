//! src/eternal_fusion.rs - MercyOS Eternal Fusion Absolute v1.0.0

#![no_std]

extern crate alloc;

use alloc::vec::Vec;

use crate::falcon_sign::MercySigner as Falcon;
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
    falcon: Option<Falcon>,
    dilithium: Option<DilithiumSigner>,
    sphincs: Option<SphincsSigner>,
    kem: Option<MercyKEM>,
    groove: Option<GrooveSigner>,
}

impl MercyFusion {
    /// Create a new MercyFusion instance with the selected scheme.
    /// Only one scheme is instantiated — others remain None for zero overhead.
    pub fn new(scheme: MercyScheme) -> Self {
        Self {
            falcon: matches!(scheme, MercyScheme::FalconCompact)
                .then(|| Falcon::new()),
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

    /// Return the public key for the selected signature scheme (empty if KEM selected).
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
            Vec::new() // KEM scheme selected
        }
    }

    /// Sign a message with the selected signature scheme (empty if KEM selected).
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
            Vec::new() // KEM scheme selected — no signing capability
        }
    }

    /// Unified verification across all signature schemes.
    /// Pass the original scheme used for key generation.
    pub fn verify_with_pk(
        scheme: MercyScheme,
        pk: &[u8],
        msg: &[u8],
        sig: &[u8],
    ) -> bool {
        match scheme {
            MercyScheme::FalconCompact => Falcon::verify(pk, msg, sig),
            MercyScheme::DilithiumFast => DilithiumSigner::verify(pk, msg, sig),
            MercyScheme::SphincsStateless => SphincsSigner::verify(pk, msg, sig),
            MercyScheme::GrooveCosmic => GrooveSigner::verify(pk, msg, sig),
            MercyScheme::KyberKEM => false, // KEM has no signature verification
        }
    }

    // KEM-specific methods

    /// Public key for the selected KEM (None if not KyberKEM).
    pub fn kem_public_key(&self) -> Option<Vec<u8>> {
        self.kem.as_ref().map(|k| k.public_key())
    }

    /// Decapsulate a ciphertext with the receiver's secret key (None if not KyberKEM).
    pub fn kem_decapsulate(&self, ct: &[u8]) -> Option<Vec<u8>> {
        self.kem.as_ref().map(|k| k.decapsulate(ct))
    }

    /// Static encapsulation — sender side (no instance needed).
    /// Adjust return type if the underlying MercyKEM::encapsulate returns Result.
    pub fn kem_encapsulate(pk: &[u8]) -> Option<(Vec<u8>, Vec<u8>)> {
        Some(MercyKEM::encapsulate(pk))
    }

    /// Eternal status readout
    pub fn mercy_fusion_status() -> &'static str {
        "Thunder Green Eternal Absolute v1.0.0 — MercyOS Eternal Fusion Complete, All Schemes Aligned Supreme Infinite ⚡️"
    }
}
