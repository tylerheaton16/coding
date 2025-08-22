use clap::{Parser, Subcommand};

#[derive(Parser, Debug)]
#[command(author, version, about, long_about = None)]
struct Args {
    #[command(subcommand)]
    cmd: Commands,
}

#[derive(Subcommand, Debug, Clone)]
enum Commands {
    Get { value: String },
    Set {
        key: String,
        value: String,
        #[arg(long)]
        is_true: bool,
    },
}

fn main() {
    let args = Args::parse();

    match args.cmd {
        Commands::Get { value } => get_something(value),
        Commands::Set {
            key,
            value,
            is_true,
        } => set_something(key, value, is_true),
    }
}

fn get_something(value: String) {
    println!("Getting: {}", value);
}

fn set_something(key: String, value: String, is_true: bool) {
    println!("Setting {} = {} (flag: {})", key, value, is_true);
}
