//! src/error.rs - MercyOS Unified Error Types v1.0.0
//! no_std compatible, Copy + Debug for zero overhead

#![no_std]

#[derive(Copy, Clone, Debug, PartialEq, Eq)]
pub enum MercyError {
    InvalidScheme,
    NoSchemeSelected,
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
            MercyError::InvalidScheme => "Selected scheme is invalid or unsupported",
            MercyError::NoSchemeSelected => "No active scheme instantiated (e.g., KEM for signing)",
            MercyError::KeyGenerationFailed => "Key generation failed (e.g., sampling rejection or norm bound)",
            MercyError::SigningFailed => "Signing failed (e.g., norm too large or sampling error)",
            MercyError::VerificationFailed => "Signature verification explicitly failed",
            MercyError::EncapsulationFailed => "KEM encapsulation failed",
            MercyError::DecapsulationFailed => "KEM decapsulation failed",
            MercyError::InvalidInput => "Invalid input parameters (length, format)",
            MercyError::InternalError => "Internal cryptographic primitive error",
        }
    }
}
