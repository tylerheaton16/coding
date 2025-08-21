fn main() {
    //WARNING: These problems always end up being the bane of my existence. Let's give it a go
    /*
    Find the number of times "XMAS" is in the puzzle. Vertical, horizontal, diagonally, and backwards

    A struct could be useful here?
    Gonna need to do some digging into how to approach problems like this

    1) Reminder, we can create a impl From for MyStruct. This can create a "conversion"
        in this problem, we can convert a &str to some fields in a struct
    2) Try to do this without using crates to make your life easier. Found some youtube
        explanations where people leverage iter(), map, flat_map, closures, and structs/impls to solve this.
    */
    part_one(String::from("tyler").as_str());
}

fn part_one(input: &str) -> Option<u32> {
    //To do this, they say make a Vec<Vec<u8>> - a vector of a vector of a byte. The byte will be the letter
    let test = "MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX";

    //NOTE: You MUST specify the type. When you use 2 collects, it is so generic it doesn't know how to return
    let a: Vec<Vec<u8>> = test.lines().map(|row| row.bytes().collect()).collect();
    let b = test.lines();
    let c = "MS";
    //Messing with how to do string manipulation with vecs, strings, getting byte values, etc
    let d: Vec<_> = c.lines().map(|x| x.bytes()).collect();
    let e: Vec<Vec<_>> = c.lines().map(|x| x.bytes().collect()).collect();
    let f = vec![1, 2, 3];
    //let words = vec!["alpha", "beta", "gamma"];
    //let merged: String = words.iter().flat_map(|s| s.chars()).collect();
    //println!("merged is {merged:?}");

    //println!("f is {f:?}");
    //println!("a is {a:#?}");
    //println!("b is {b:#?}");
    //println!("d is {d:?}");
    //println!("e is {e:?}");

    let grid = Grid::from(test);
    let mut count = 0;

    /*
        Here is the challenge for me. I need to iterate on an iterate over an iterate ...etc. Think about it
        Iterate over the rows and look for an X.
        We can look at grid.rows to know how many rows there are

       flat_map is for flattening nested iterables. For example:

       vec![vec![1,2], vec![3,4]].flat_map() → [1,2,3,4]
       ["ab", "cd"].flat_map(|s| s.chars()) → ['a','b','c','d']
    */

        let j = (0..grid.rows)
            .flat_map(|row| (0..grid.cols).map(move |col| (row, col)))
            .filter(|&(row, col)| grid.bytes[row][col] == b'X')
            .for_each(|(row,col)| count += grid.xmas_count(row, col));

    Some(count as u32)
}

//Let's create this "grid" that we are talking about. That way, it will be of
//type Grid. So let grid: <Grid> will be the case
struct Grid {
    bytes: Vec<Vec<u8>>,
    rows: usize, //amount of rows in outer vector
    cols: usize, //amount of chars on every line
}

impl From<&str> for Grid {
    fn from(input: &str) -> Grid {
        //We have now implemented a from function for Grid. We can take a string slice
        //and use it to create our vector of bytes, how many rows, and how many cols
        let bytes: Vec<Vec<u8>> = input.lines().map(|row| row.bytes().collect()).collect();
        let rows = bytes.len();
        let cols = bytes.first().map_or(0, |row| row.len());

        Grid { bytes, rows, cols }
    }
}
