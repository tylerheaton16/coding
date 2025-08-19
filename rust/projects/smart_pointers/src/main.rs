use crate::List::{Cons, Nil};
use std::ops::Deref;
fn main() {
    //The most simple smart pointer is a `box`
    //Allow you to store data on the Heap instead of the Stack

    let b = Box::new(5);
    //b has the value of a Box that points to the value 5 on the heap
    println!("b = {b}");

    let list = Cons(1, Box::new(Cons(2, Box::new(Cons(3, Box::new(Nil))))));

    let x = 5;
    let y = &x;
    assert_eq!(5, x);
    assert_eq!(5, *y); //we dereference the pointer to get the value of y

    let x = 5;
    //This is a copied value of x in a Box on the heap
    let y = Box::new(x); // this is like the same way to dereference y above

    assert_eq!(5, x);
    assert_eq!(5, *y); //we dereference the pointer to get the value of y

    let x = 5;
    let y = MyBox::new(x); //we create our own Box of x

    assert_eq!(5, x);
    assert_eq!(5, *y); //we dereference the pointer to get the value of y

    //NOTE: Deref coercion converts a reference to a type that implements the Deref trait
    //into a reference to another Type
    let m = MyBox::new(String::from("Rust"));
    hello(&m);
}

enum List {
    Cons(i32, Box<List>),
    Nil,
}

fn hello(name: &str) {
    println!("Hello, {name}!");
}

//Let's make our own box called MyBox
struct MyBox<T>(T);

impl<T> MyBox<T> {
    fn new(x: T) -> MyBox<T> {
        MyBox(x)
    }
}
impl<T> Deref for MyBox<T> {
    type Target = T;

    fn deref(&self) -> &Self::Target {
        &self.0
    }
}
