use std::{fs::File, io::{BufRead, BufReader}};
use std::io;

fn main() -> io::Result<()> {
    let file = File::open("src/puzzle-input.txt")?;
    let reader = BufReader::new(file);

    //Create 2 vecs to do our comparison on
    let mut v1: Vec<String> = Vec::new();
    let mut v2: Vec<String> = Vec::new();
    let mut v3: Vec<i32> = Vec::new();

    for line_result in reader.lines() {
        let line = line_result?; // Handle potential errors for each line
        let line_vec: Vec<&str> = line.split(" ").collect();
        v1.push(line_vec[0].to_string());
        v2.push(line_vec[1].to_string());
        // Further parsing logic for 'line' can go here
        //println!("line_vec is {line_vec:?}");
    }

    //Now v1 and v2 contain their elements. Let's sort them
    v1.sort();
    v2.sort();

    let v1_iter = v1.iter();
    let v2_iter = v2.iter();

    for (v1, v2) in v1_iter.zip(v2_iter) {
        //fill out
        v3.push(get_difference_and_store(v1, v2));
    }

    println!("the sum of v3 is {}", v3.iter().sum::<i32>());

    get_similarity_score(v1, v2);


    Ok(())
}

//Part 1
fn get_difference_and_store(a: &str, b: &str) -> i32 {
    let a_int: i32 = a.parse().unwrap_or(0);
    let b_int: i32 = b.parse().unwrap_or(0);

    (a_int - b_int).abs()
}

//Part 2
fn get_similarity_score(v1: Vec<String>, v2: Vec<String>) {
    let mut similarity_score = 0;
    let mut num_repeated;

    for n in &v1 {
        num_repeated = 0;

        for m in &v2 {
              if n == m {
                  num_repeated += 1;
              }
        }
        similarity_score += n.parse().unwrap_or(0) * num_repeated;
    }

    println!("similarity_score is {similarity_score}");
}
