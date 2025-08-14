fn main() {
    let _s_literal = "hello"; //string literal; however, it is immutable and cannot change. Not too helpful as a string
    //let s = String::from("hello");
    let mut s = String::from("hello");

    // :: allows us to namespace this `from` function under the `String` type
    s.push_str(", world!"); // push_str() appends a literal to String
    println!("{s}");

    // Let's say that we want to duplicate the value on the heap - we can use clone()
    let s2 = s.clone();
    println!("s = {s}, s2 = {s2}");

    // NOTE: String literals cannot be mutated even with `mut`

    takes_ownership(s);
    //let s2 = s.clone(); // doesn't work because s is now out of scope

    let x = 5;
    makes_copy(x);

    let s3 = gives_ownership();
    let s4 = takes_and_gives_back(s3);
    println!("{s4}");
}

fn takes_ownership(some_string: String) {
    // some_string comes into scope
    println!("{some_string}");
} // Here, some_string goes out of scope and `drop` is called. The backing
// memory is freed.

fn makes_copy(some_integer: i32) {
    // some_integer comes into scope
    println!("{some_integer}");
} // Here, some_integer goes out of scope. Nothing special happens.

fn gives_ownership() -> String {
    // gives_ownership will move its
    // return value into the function
    // that calls it

    let some_string = String::from("heaven or hell"); // some_string comes into scope

    some_string // some_string is returned and
    // moves out to the calling
    // function
}

// This function takes a String and returns one
fn takes_and_gives_back(a_string: String) -> String {
    // a_string comes into
    // scope

    a_string // a_string is returned and moves out to the calling function
}
