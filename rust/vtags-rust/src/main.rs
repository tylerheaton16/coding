extern crate glob;
use clap::{Parser, Subcommand};
use glob::glob;
use std::{collections::HashMap, path::PathBuf};
use sv_parser::{Locate, RefNode, SyntaxTree, parse_sv, unwrap_node};
use vtags_rust::print_identifier;

#[derive(Parser, Debug)]
#[command(version, about, long_about = "Vtags parsing of Verilog/System-Verilog")]
struct Cli {
    #[command(subcommand)]
    cmd: Commands,
}

#[derive(Subcommand, Debug, Clone)]
enum Commands {
    ReadRtl {
        #[arg(
            long,
            help = "Pattern to match SystemVerilog files (e.g., '*.sv' or 'src/**/*.sv')"
        )]
        path_dir: String,
    },
}

fn main() {
    let cli = Cli::parse();
    let mut rtl_files: Vec<PathBuf> = Vec::new();

    /*
    TODO: add defines to CLI
    Example: defines.insert("DEBUG".to_string(), "1".to_string());
    Implementation:
        #[arg(long, help = "Add `defines` file path")]
        defines: Vec<String>,
    */
    let defines = HashMap::new();
    // The list of include paths
    //TODO: add includes to CLI
    let includes: Vec<PathBuf> = Vec::new();

    //Glob all Verilog and System Verilog files
    // Parse
    match cli.cmd {
        Commands::ReadRtl { path_dir } => glob_files(path_dir, &mut rtl_files),
    };

    let collect_syntax_trees: Vec<SyntaxTree> = rtl_files
        .iter()
        .filter_map(|file| {
            let path = PathBuf::from(file);
            parse_sv(&path, &defines, &includes, false, false)
                .ok()
                .map(|(tree, _)| tree)
        })
        .collect();

    //NOTE: At this point in the code, ASTs have been created for each RTL file that was read in

    for syntax_tree in collect_syntax_trees {
        for node in &syntax_tree {
            if let RefNode::ModuleDeclarationAnsi(module) = node {
                print_identifier!(syntax_tree, module, ModuleIdentifier, "Module");

                for child_module in module.into_iter() {
                    if let RefNode::ModuleInstantiation(inst) = child_module {
                        print_identifier!(syntax_tree, inst, InstanceIdentifier, "Instance");

                        for ports in inst.into_iter() {
                            if let RefNode::PortIdentifier(ports) = ports {
                                print_identifier!(syntax_tree, ports, PortIdentifier, "Port");
                            }
                        }
                    }
                }
            }
        }
    }
}

fn glob_files(path: String, rtl_files: &mut Vec<PathBuf>) {
    //parse all files with .sv and .v suffixes
    for rtl_name in glob(&format!("{}/*.sv", path))
        .unwrap()
        .chain(glob(&format!("{}/*.v", path)).unwrap())
    {
        rtl_files.push(rtl_name.unwrap())
    }
}
