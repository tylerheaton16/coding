use std::collections::HashMap;

use std::fmt::Result;
use std::io::Result as IoResult;
use std::{cmp::Ordering, io}; // can use {} to import more than 1
use std::io::{self, Write}; // same as std::io, std::io::Write
use std::collections::*; //brings everything in with *

//NOTE: Can use the `as <result>` words to change how its imported
//fn function1() -> Result {
//    // --snip--
//}
//
//fn function2() -> IoResult<()> {
//    // --snip--
//}

fn main() {
    let mut map = HashMap::new();
    map.insert(1, 2);
}
