#![no_std]
#![feature(alloc_error_handler)] // Optional if needed for custom OOM

extern crate alloc;

// Internal low-level modules (private by default)
mod anomaly;
mod consensus;
mod falcon_fft;
mod falcon_gauss;
mod falcon_gauss_ky;
mod falcon_keygen;
mod falcon_verify;
mod fft;
mod lattice_entropy;
mod lattice_reduction;
mod sentinel;
mod global_scale;
mod swarm_scale;

// Public high-level interfaces
pub mod eternal_fusion;
pub mod falcon_sign;    // Public entry for Falcon signing
pub mod ml_dsa;
pub mod ml_kem;
pub mod ml_sphincs;
pub mod quantum_groove;

// Primary API — unified fusion is the recommended way to use MercyOS
pub use eternal_fusion::{
    MercyFusion,
    MercyScheme,
    MercyFusion::mercy_fusion_status as status,
};

// Optional: re-export common types if modules define shared (add as impl progresses)
