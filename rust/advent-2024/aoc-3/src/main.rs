use regex::Regex;
use std::fs;
use std::io;

fn main() -> io::Result<()> {
    /*
    scan the memory for uncorrected multiply commands
    Add result of all valid multiplies
    2*4 + 5*5 + 11*8 + 8*5 = 161
    */

    //let file = File::open("src/puzzle-input.txt")?;
    let file = fs::read_to_string("src/puzzle-input.txt")?;
    /*
    NOTE: In a regex, you can create "capture groups".
    This is why there are parentheses around \d{1,3}. This allows us to
    do .get(1) and .get(2)
    .get(0) would be the entire match
    */
    let mut result = 0;
    let re = Regex::new(r"mul\((\d{1,3}),(\d{1,3})\)").unwrap();
    let find_mul: Vec<_> = re.find_iter(&file).map(|m| m.as_str()).collect();

    for e in find_mul {
        let text = re.captures(e).unwrap();
        //This was ridiculous, but apparently it is common
        let n1: i32 = text.get(1).unwrap().as_str().parse().unwrap();
        let n2: i32 = text.get(2).unwrap().as_str().parse().unwrap();

        result += n1 * n2
    }
    println!("result is: {result}");

    /*
       Part 2
        Technically, any mul commands between a don't and a do or don't and don't should just be ignored
    mul...do...mul...do...mul...don't...mul...don't...mul...do
    */
    let mut remove = false;
    let mut result2 = 0;

    let re = Regex::new(r"mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)").unwrap();
    let find_mul: Vec<_> = re.find_iter(&file).map(|m| m.as_str()).collect();
    println!("find_mul is {find_mul:?}");

    for e in find_mul {
        //need to check if it is do, don't, or a mul.

        match e {
            "don't()" => remove = true,
            "do()" => remove = false,
            _ => {
                let text = re.captures(e).unwrap();
                let n1: i32 = text.get(1).unwrap().as_str().parse().unwrap();
                let n2: i32 = text.get(2).unwrap().as_str().parse().unwrap();
                if remove {
                    result2 += 0;
                } else {
                    result2 += n1 * n2
                }
            }
        }
        /*
        If you don't want to use `match` we can go about doing this a less verbose way
        In the future, try `if let` syntax.
        */
    }

    println!("result2 is: {result2}");

    Ok(())
}
