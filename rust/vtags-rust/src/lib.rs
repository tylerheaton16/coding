//! VTags Rust Library
//!
//! A collection of macros and utilities for VTags functionality.

// Re-export macros for external use

// Macro definitions module
pub mod macros {
    use sv_parser::{Locate, RefNode, unwrap_node};
    #[macro_export]
    macro_rules! print_identifier {
        ($syntax_tree:expr, $ref_node:expr, $id_type:tt, $print_type:expr) => {
            if let Some(identifier) = unwrap_node!($ref_node, $id_type) {
                if let Some(identifier_loc) = $crate::macros::get_identifier(identifier) {
                    if let Some(identifier_name) = $syntax_tree.get_str(&identifier_loc) {
                        println!("{}: {}", $print_type, identifier_name);
                    } else {
                        println!("Failed to get string for identifier");
                    }
                } else {
                    println!("Failed to get identifier location");
                }
            } else {
                println!("Failed to find {} in node", stringify!($id_type));
            }
        };
    }
    pub fn get_identifier(node: RefNode) -> Option<Locate> {
        // unwrap_node! can take multiple types
        match unwrap_node!(node, SimpleIdentifier, EscapedIdentifier) {
            Some(RefNode::SimpleIdentifier(x)) => Some(x.nodes.0),
            Some(RefNode::EscapedIdentifier(x)) => Some(x.nodes.0),
            _ => None,
        }
    }
}
