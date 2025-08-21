use::macros::my_vec;
fn main() {
    /*
    Macros in rust are either declarative macros or procedural macros
    Macros are a way of writing code - "metaprogramming"
    */

    //Declarative macros with macro_rules!

    let _v: Vec<u32> = my_vec![1, 2, 3]; //made a vec using the vec! macro
}
