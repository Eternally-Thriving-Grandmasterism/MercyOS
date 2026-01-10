//! src/error.rs - MercyOS Error Handling v1.0.0
//! no_std compatible, Copy for zero overhead

#![no_std]

#[derive(Copy, Clone, Debug, PartialEq, Eq)]
pub enum MercyError {
    InvalidScheme,
    KeyGenerationFailed,
    SigningFailed,
    VerificationFailed,
    EncapsulationFailed,
    DecapsulationFailed,
    InvalidInput,
    InternalError,
}

impl MercyError {
    pub fn as_str(&self) -> &'static str {
        match self {
            MercyError::InvalidScheme => "Invalid or unsupported MercyScheme",
            MercyError::KeyGenerationFailed => "Key generation failed (e.g., sampling rejection)",
            MercyError::SigningFailed => "Signing failed (e.g., norm too large)",
            MercyError::VerificationFailed => "Verification explicitly failed",
            MercyError::EncapsulationFailed => "KEM encapsulation failed",
            MercyError::DecapsulationFailed => "KEM decapsulation failed",
            MercyError::InvalidInput => "Invalid input parameters",
            MercyError::InternalError => "Internal cryptographic error",
        }
    }
}
