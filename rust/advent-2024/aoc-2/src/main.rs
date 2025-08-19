use std::io;
use std::{
    fs::File,
    io::{BufRead, BufReader},
};

fn main() -> io::Result<()> {
    let file = File::open("src/puzzle-input.txt")?;
    let reader = BufReader::new(file);

    //Create 2 vecs to do our comparison on
    let mut line_vec: Vec<String> = Vec::new();

    for line_result in reader.lines() {
        let line = line_result?; // Handle potential errors for each line
        line_vec.push(line);
    }

    let result_i32: Vec<Vec<i32>> = line_vec
        .iter()
        .map(|line| {
            line.split_whitespace()
                .map(|num| num.parse::<i32>().unwrap())
                .collect()
        })
        .collect();

    //println!("result_i32 is {result_i32:?}");

    let report = Report { report: result_i32 };
    println!("Num reports safe for part 1 without problem dampener: {}", report.is_safe_count());

    //Part 2
    let good_rows = report.get_good_rows();
    println!("Rows fixed by problem dampener {good_rows:?}");

    //part 2 rows
    println!("Safe reports after using problem dampener: {}", good_rows + report.is_safe_count());

    Ok(())
}

/*
Part 1
1) can only increase (i+1 > i)
2) can only decrease (i+1 < i)
3) difference must be <=3 (i+1 - i) <= 3

Part 2
Can remove 1 level. still must increment or decrement and max diff of 3
*/

struct Report {
    report: Vec<Vec<i32>>,
}

impl Report {
    //Want to implement the functions to check a condition of a Vec
    fn is_safe_without_dampener(&self, levels: &[i32]) -> bool {
    if levels.len() < 2 {
        return true;
    }

    let mut increasing = None;

    for window in levels.windows(2) {
        let diff = window[1] - window[0];

        if diff.abs() < 1 || diff.abs() > 3 {
            return false;
        }

        match increasing {
            None => increasing = Some(diff > 0),
            Some(true) if diff <= 0 => return false,
            Some(false) if diff >= 0 => return false,
            _ => {}
        }
    }

    true
}
    fn is_incrementing_safetly(&self) -> (Vec<bool>, Vec<bool>) {
        let all_incrementing: Vec<bool> = self
            .report
            .iter()
            .map(|w| w.windows(2).all(|v| v[1] > v[0]))
            .collect();
        let incrementing_by_three: Vec<bool> = self
            .report
            .iter()
            .map(|w| w.windows(2).all(|v| v[1] - v[0] <= 3))
            .collect();

        (all_incrementing, incrementing_by_three)
    }

    fn is_decrementing_safetly(&self) -> (Vec<bool>, Vec<bool>) {
        let all_decrementing: Vec<bool> = self
            .report
            .iter()
            .map(|w| w.windows(2).all(|v| v[1] < v[0]))
            .collect();
        let decrementing_by_three: Vec<bool> = self
            .report
            .iter()
            .map(|w| w.windows(2).all(|v| v[0] - v[1] <= 3))
            .collect();

        (all_decrementing, decrementing_by_three)
    }

    fn is_safe_count(&self) -> i32 {
        let mut count = 0;
        let (is_inc, inc_three) = self.is_incrementing_safetly();
        let (is_dec, dec_three) = self.is_decrementing_safetly();

        for (i, k) in is_inc.iter().zip(inc_three.iter()) {
            if i & k {
                count += 1
            };
        }
        for (i, k) in is_dec.iter().zip(dec_three.iter()) {
            if i & k {
                count += 1
            };
        }

        count
    }

    fn get_good_rows(&self) -> i32 {
        let (is_inc, inc_three) = self.is_incrementing_safetly();
        let (is_dec, dec_three) = self.is_decrementing_safetly();

        //make a Vec that ands the inc and ands the dec then ors them
        let inc_vec: Vec<bool> = is_inc
            .iter()
            .zip(inc_three.iter())
            .map(|(x, y)| x & y)
            .collect();
        let dec_vec: Vec<bool> = is_dec
            .iter()
            .zip(dec_three.iter())
            .map(|(x, y)| x & y)
            .collect();

        let comb_vec: Vec<bool> = inc_vec
            .iter()
            .zip(dec_vec.iter())
            .map(|(x, y)| x | y)
            .collect();

        //let filtered_bad: = self.report.iter().zip(comb_vec.iter()).filter(|(_, &keep)| keep).map(|(value, _)| value).collect();
        let failed: Vec<&Vec<i32>> = self
            .report
            .iter()
            .zip(comb_vec.iter())
            .filter(|(_, keep)| !*keep) // Note the ! to get false values
            .map(|(vec, _)| vec)
            .collect();

        //This is annoying. But, since we were referencing a vector, we couldn't clone a new one.
        //So, we needed to derefence the vectors to clone them which is done here
        let failed_clone: Vec<Vec<i32>> = failed.iter().map(|v| (*v).clone()).collect();
        let mut count = 0;

        for v in failed_clone {
            for i in 0..v.len() {
                //NOTE: Cloning here was the trick I was looking for. If every time we enter this loop
                //we clone doing let mut v = v.clone(). Then we can just reset the vector to the original
                //and continue solving the problem
                //Originally, we couldn't clone because v was a reference. Had to fix that with "failed_clone" above
                let mut v = v.clone();  // Clone the original for each test
                v.remove(i);
                //println!("v with index {} removed is now {:?}", i, v);
                if self.is_safe_without_dampener(&v) {
                    count += 1;
                    break; // Found a valid removal for this row, no need to try more
                }
            }
        }

        count

        /*
        Now we want to look at a Vec<i32> and remove a column and check if it is now valid.
        We need to do that for the length of the row.

        If the row is 6 long, we need to check it 6 times
        */

        //Practice vector manipulation here. We want to use valid_reports to pull out invalid reports to check on
        /*
        if element.1 (bool) == false, then make new vec with element.0 of result_i32

         Using zip for element-wise filtering:

         let mask = vec![true, false, true, false];
         let data = vec![1, 2, 3, 4];

         let filtered: Vec<i32> = data.into_iter()
             .zip(mask.iter())
             .filter(|(_, keep)| *keep)
             .map(|(value, _)| value)
             .collect();
         // Result: [1, 3]

        */
    }
}
