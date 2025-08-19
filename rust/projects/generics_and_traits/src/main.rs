use std::fmt::{Debug, Display};

fn main() {
    let number_list = vec![34, 50, 25, 100, 65];

    let largest = largest_generic(&number_list);

    let integer = Point { x: 5, y: 10 };
    let float = Point { x: 1.0, y: 1.0 };
    // let mixed = Point {x: 5, y:1.0} // will not work because we mixed types on x and y
    let mixed = Point_double_generic { x: 5, y: 1.0 }; // this works because we made a new generic

    let p = Point { x: 5, y: 10 };
    println!("p.x = {}", p.x());
}

fn largest(list: &[i32]) -> &i32 {
    let mut largest = &list[0];

    for item in list {
        if item > largest {
            largest = item;
        }
    }

    largest
}

//Read this is largest_generic is a function with is generic over some type `T` (largest_generic<T>)
//We can only compare 2 things (item > largest) if T implements the PartialOrd trait (T: std::Cmp::PartialOrd)
fn largest_generic<T: std::cmp::PartialOrd>(list: &[T]) -> &T {
    let mut largest = &list[0];

    for item in list {
        if item > largest {
            largest = item;
        }
    }

    largest
}

//Let's make some structs with generics
struct Point<T> {
    x: T,
    y: T,
}
//We can also use generics in impl's on structs/enums
impl<T> Point<T> {
    fn x(&self) -> &T {
        &self.x
    }
}

struct Point_double_generic<T, U> {
    x: T,
    y: U,
}

//Talking about traits now. We use them to define a set of behaviors necessary to
//accomplish a purpose
pub trait Summary {
    //fn summarize(&self) -> String;

    //We can also provide a "default" behavior for a trait
    //fn summarize(&self) -> String {
    //    String::from("(Read more...)")
    //}
    fn summarize_author(&self) -> String;

    fn summarize(&self) -> String {
        format!("(Read more from {}...)", self.summarize_author())
    }
}

pub struct NewsArticle {
    pub headline: String,
    pub location: String,
    pub author: String,
    pub content: String,
}

impl Summary for NewsArticle {
    fn summarize(&self) -> String {
        format!("{}, by {} ({})", self.headline, self.author, self.location)
    }
    fn summarize_author(&self) -> String {
        format!("@{}", self.author)
    }
}

pub struct SocialPost {
    pub username: String,
    pub content: String,
    pub reply: bool,
    pub repost: bool,
}

impl Summary for SocialPost {
    fn summarize(&self) -> String {
        format!("{}: {}", self.username, self.content)
    }
    fn summarize_author(&self) -> String {
        format!("@{}", self.username)
    }
}

//Let's look at traits as parameters
//We are saying that the notify function will be passed something which implements Summary
//Syntactic sugar for notify<T: Summary>(item: &T)
//We create trait bounds. Saying T must be of type Summary and then our item is that type
pub fn notify(item: &impl Summary) {
    println!("Breaking news! {}", item.summarize());
}

//We can do this with 2 traits
pub fn notify_implements_summary(item1: &impl Summary, item2: &impl Summary) {
    //This allows item1 and item2 to be any type that implements summary
}
pub fn notify_forced_type<T: Summary>(item1: &T, item2: &T) {
    //This forces item1 and item2 to be the same type as well as implement Summary
}
//Can implement multiple trait bounds
pub fn notify_mult_trait_bounds(item: &(impl Summary + Display)) {
    //the parameter must implement Summary and Display
}
pub fn notify_works_with_generics<T: Summary + Display>(item: &T) {
    //we can use a generic T which must implement Summary and Display
}
//This can get very verbose. So, rust has created a simpler syntax to solve this
fn some_function<T: Display + Clone, U: Clone + Debug>(t: &T, u: &U) -> i32 {
    //The function is difficult to read. Some generic Type which has 2 parameters which implement
    //different traits called T and U and returns an i32.
    //lets make this more readable
    5 //here so no compiler error
}
fn some_function_more_readable<T, U>(t: &T, u: &U) -> i32
where
    T: Display + Clone,
    U: Clone + Debug,
{
    5 //here so no compiler error
}

//We can also return types that implement traits
// NOTE: returns_summarizable returns `SocialPost` which implements the type Summary
// WARNING: Can only return a single type. Can't return 2 types
fn returns_summarizable() -> impl Summary {
    SocialPost {
        username: String::from("horse_ebooks"),
        content: String::from("of course, as you probably already know, people"),
        reply: false,
        repost: false,
    }
}

//Finally we can use trait bounds to conditionally implement methods
//We won't implement the method, but we can say this method MUSTE follow this
struct Pair<T> {
    //Pair has the type T. X and Y are of type T
    x: T,
    y: T,
}

//This creates an instance of type `Self`. let test<Self> = Pair::new(x,y)
impl<T> Pair<T> {
    fn new(x: T, y: T) -> Self {
        Self { x, y }
    }
}

//This is called a blanket implementation
//e.g., the STD library implements ToString trait on any type that implements the Display trait
//impl<T: Display> ToString for T {}
impl<T: Display + PartialOrd> Pair<T> {
    fn cmp_display(&self) {
        if self.x >= self.y {
            println!("The largest member is x = {}", self.x);
        } else {
            println!("The largest member is y = {}", self.y);
        }
    }
}
