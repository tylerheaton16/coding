extern crate glob;
use clap::{Parser, Subcommand};
use glob::glob;
use std::{collections::HashMap, path::PathBuf};
use sv_parser::{RefNode, SyntaxTree, parse_sv, unwrap_node};
use vtags_rust::{get_id, print_identifier};

#[derive(Parser, Debug)]
#[command(version, about, long_about = "Vtags parsing of Verilog/System-Verilog")]
struct Cli {
    #[command(subcommand)]
    cmd: Commands,
}

#[derive(Subcommand, Debug, Clone)]
enum Commands {
    ReadRtl {
        #[arg(long, help = "Pattern to match SystemVerilog files (e.g., '*.sv' or 'src/**/*.sv')")]
        path_dir: String,
    },
}

/*
   A module needs ...
        1) Module name
        2) Ports
            a. port name
            b. port direction
            c. port width
        3) Signals
            a. signal name
            b. signal width
        4) Instance
            a. instance name
            b. module the instance is
            c. port connections -- HashMap<String, String>, // port_name -> signal_name

*/
#[derive(Default, Debug, Clone)]
#[allow(dead_code)]
struct ModuleStruct {
    // Module identification
    module_name: Option<String>,

    // Module interface
    ports: Option<Vec<Port>>,

    // Internal signals
    internal_signals: Option<Vec<Signal>>,

    // Child instances
    instances: Option<Vec<Instance>>,

    // Signal connectivity mapping
    signal_connections: Option<HashMap<String, Vec<Connection>>>,
}

#[derive(Default, Debug, Clone)]
#[allow(dead_code)]
struct Port {
    name: Option<String>,
    direction: Option<PortDirection>, // Input, Output, Inout
    width: Option<u32>,               // bit width
}

#[derive(Default, Debug, Clone)]
#[allow(dead_code)]
struct Signal {
    name: String,
    width: Option<u32>,
}

#[derive(Default, Debug, Clone)]
#[allow(dead_code)]
struct Instance {
    instance_name: Option<String>,
    module_type: Option<String>,
    port_connections: Option<HashMap<String, String>>, // port_name -> signal_name
}

#[derive(Default, Debug, Clone)]
#[allow(dead_code)]
struct Connection {
    connection_type: ConnectionType,
    target: String, // port name or instance.port
}

#[derive(Default, Debug, Copy, Clone)]
#[allow(dead_code)]
enum PortDirection {
    #[default]
    None,
    Input,
    Output,
    Inout,
    Ref,
}

#[derive(Default, Debug, Clone)]
#[allow(dead_code)]
enum ConnectionType {
    #[default]
    None,
    ToPort,             // connects to module port (upward)
    ToInstance(String), // connects to instance (downward), String is instance name
}

fn main() {
    //Sets up CLI for clap
    let cli = Cli::parse();
    let mut rtl_files: Vec<PathBuf> = Vec::new();

    //Create vector to traverse for signal connection
    let mut module_struct: HashMap<String, ModuleStruct> = HashMap::new();
    let mut instances_struct: Vec<Instance> = Vec::new();
    let mut port_struct: Vec<Port> = Vec::new();

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

    /*
       BUG: Currently, this treats a single file as a syntax tree,
       so if there are multiple modules in a file it only sees one.
       Solution: I prefer to handle everything as individual files.
       Write rust parser to separate all modules into individual files
       where the filename matches the module name
    */
    let collect_syntax_trees: Vec<SyntaxTree> = rtl_files
        .iter()
        .filter_map(|file| {
            let path = PathBuf::from(file);
            parse_sv(&path, &defines, &includes, false, false).ok().map(|(tree, _)| tree)
        })
        .collect();

    //NOTE: At this point in the code, ASTs have been created for each RTL file that was read in

    //let mut mod_s = ModuleStruct::default();

    for syntax_tree in collect_syntax_trees {
        let mut mod_s = ModuleStruct::default();

        for node in &syntax_tree {
            if let RefNode::ModuleDeclarationAnsi(module) = node {
                print_identifier!(syntax_tree, module, ModuleIdentifier, "Module");
                mod_s.module_name = get_id!(syntax_tree, module, ModuleIdentifier);

                for module_info in module {
                    let mut port = Port { name: None, direction: None, width: Some(1)};

                    // Look for ANSI port declarations
                    if let RefNode::AnsiPortDeclaration(port_decl) = module_info {
                        for ports in port_decl {
                            for ports_internal_attr in ports {
                                if let RefNode::PortIdentifier(port_id) = ports_internal_attr {
                                    port.name = get_id!(syntax_tree, port_id, PortIdentifier);
                                }
                                if let RefNode::PortDirection(port_direction) = ports_internal_attr {
                                    let port_direction = match port_direction {
                                        sv_parser::PortDirection::Input(_) => Some(PortDirection::Input),
                                        sv_parser::PortDirection::Output(_) => Some(PortDirection::Output),
                                        sv_parser::PortDirection::Inout(_) => Some(PortDirection::Inout),
                                        sv_parser::PortDirection::Ref(_) => Some(PortDirection::Ref),
                                    };
                                    port.direction = port_direction;
                                }
                                if let RefNode::NetPortHeader(net_header) = ports_internal_attr {
                                    for header_child in net_header {
                                        if let RefNode::PackedDimension(packed_d_enum) = header_child {
                                            match packed_d_enum {
                                                sv_parser::PackedDimension::Range(range) => {
                                                    let mut msb_val = None;
                                                    let mut lsb_val = None;
                                                    let mut number_count = 0;

                                                    // Extract MSB and LSB from the range
                                                    for range_child in range.into_iter() {
                                                        if let RefNode::ConstantExpression(expr) = range_child {
                                                            for expr_child in expr {
                                                                if let RefNode::ConstantPrimary(primary) = expr_child {
                                                                    for primary_child in primary {
                                                                        if let RefNode::IntegralNumber(number) = primary_child {
                                                                            for number_child in number {
                                                                                if let RefNode::Locate(loc) = number_child {
                                                                                    if let Some(num_str) = syntax_tree.get_str(loc) {
                                                                                        if let Ok(num) = num_str.parse::<u32>() {
                                                                                            //When we iterate on the Integral number. We get an MSB and an LSB. We must compute the width here
                                                                                            if number_count == 0 {
                                                                                                msb_val = Some(num);
                                                                                            } else if number_count == 1 {
                                                                                                lsb_val = Some(num);
                                                                                            }
                                                                                            number_count += 1;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    if let (Some(msb), Some(lsb)) = (msb_val, lsb_val) {
                                                        let width = (msb as i32 - lsb as i32 + 1).unsigned_abs();
                                                        port.width = Some(width);
                                                        println!("Calculated width: {} from [{}:{}]", width, msb, lsb);
                                                    }
                                                }
                                                sv_parser::PackedDimension::UnsizedDimension(_) => {
                                                    port.width = Some(1); // single bit
                                                }
                                            };
                                        }
                                    }
                                }
                            }
                        }
                        port_struct.push(port);
                    }
                    // Extract module instances
                    if let RefNode::ModuleInstantiation(inst) = module_info {
                        //print_identifier!(syntax_tree, inst, InstanceIdentifier, "Instance");
                        //print_identifier!(syntax_tree, inst, InterfaceInstantiation, "Instance");
                        let mut instance = Instance {
                            instance_name: get_id!(syntax_tree, inst, InstanceIdentifier),
                            module_type: get_id!(syntax_tree, inst, ModuleIdentifier),
                            port_connections: Some(HashMap::new()),
                        };

                        for ports in inst.into_iter() {
                            if let RefNode::NamedPortConnection(port) = ports {
                                //print_identifier!(syntax_tree, port, PortIdentifier, "Port Connections");
                                for child in port.into_iter() {
                                    if let RefNode::Expression(expr) = child {
                                        // Look for HierarchicalIdentifier in the expression
                                        for expr_child in expr.into_iter() {
                                            if let RefNode::HierarchicalIdentifier(hier_id) = expr_child {
                                                let port_name = get_id!(syntax_tree, port, PortIdentifier);

                                                let signal_name = if hier_id.nodes.1.is_empty() {
                                                    // Simple identifier case: just "clk"
                                                    // nodes.1 = []
                                                    // nodes.2 = "clk"
                                                    //print_identifier!(syntax_tree, &hier_id.nodes.2, SimpleIdentifier, "Single match");
                                                    get_id!(syntax_tree, &hier_id.nodes.2, SimpleIdentifier)
                                                } else {
                                                    // Hierarchical identifier case: "bus_inst.slave"
                                                    // nodes.1 = [("bus_inst", _, ".")]
                                                    // nodes.2 = "slave"
                                                    let mut parts = Vec::new();

                                                    // Get intermediate parts
                                                    for (identifier, _, _) in &hier_id.nodes.1 {
                                                        if let Some(part1) = get_id!(syntax_tree, identifier, SimpleIdentifier) {
                                                            //print_identifier!(syntax_tree, identifier, SimpleIdentifier, "hier match");
                                                            parts.push(part1);
                                                        }
                                                    }

                                                    // Get final part
                                                    if let Some(part2) = get_id!(syntax_tree, &hier_id.nodes.2, SimpleIdentifier) {
                                                        //print_identifier!(syntax_tree, &hier_id.nodes.2, SimpleIdentifier, "hier2 match");
                                                        parts.push(part2);
                                                    }

                                                    Some(parts.join("."))
                                                };
                                                //
                                                if let (Some(port), Some(signal)) = (port_name, signal_name) {
                                                    //instance.port_connections.as_mut().unwrap().insert(port, signal);
                                                    instance.port_connections.as_mut().unwrap().insert(port, signal);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        instances_struct.push(instance);
                    }
                }
                mod_s.instances = Some(instances_struct.clone());
                mod_s.ports = Some(port_struct.clone());
                //We must clear the structs that way the next module does not reuse any information
                instances_struct.clear();
                port_struct.clear();
            }
            //TODO: Uncomment below for all Nonansi type modules. Copy paste above. Refactor in future
            // else if let RefNode::ModuleDeclarationNonansi(module) = node {...}
        }
        module_struct.insert(mod_s.module_name.clone().unwrap_or(String::from("None Module")), mod_s.clone());
    }
    println!("ModuleStruct so far is {module_struct:#?}");
}

fn glob_files(path: String, rtl_files: &mut Vec<PathBuf>) {
    //parse all files with .sv and .v suffixes
    for rtl_name in glob(&format!("{}/*.sv", path)).unwrap().chain(glob(&format!("{}/*.v", path)).unwrap()) {
        rtl_files.push(rtl_name.unwrap())
    }
}
