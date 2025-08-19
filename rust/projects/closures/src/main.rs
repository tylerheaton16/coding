use std::thread;
use std::time::Duration;

fn main() {
    //Closures are anonymous functions you can save in a variable
    //or pass as arguments
    let store = Inventory {
        shirts: vec![ShirtColor::Blue, ShirtColor::Red, ShirtColor::Blue],
    };

    let user_pref1 = Some(ShirtColor::Red);
    let giveaway1 = store.giveaway(user_pref1);
    println!(
        "The user with preference {:?} gets {:?}",
        user_pref1, giveaway1
    );

    let user_pref2 = None;
    let giveaway2 = store.giveaway(user_pref2);
    println!(
        "The user with preference {:?} gets {:?}",
        user_pref2, giveaway2
    );
    //let's actually specify the type for a closure
    //We don't really need to do this, but we can enforce its use
    //more if we do this
    let expensive_closure = |num: u32| -> u32 {
        println!("calculating slowly...");
        //thread::sleep(Duration::from_secs(2));
        num
    };

    let example_closure = |x| x;

    let s = example_closure(String::from("hello"));
    //let n = example_closure(5);
    // WARNING: The closure can't be for a u32 and a string. Errors

    let list = vec![1, 2, 3];
    println!("Before defining closure: {list:?}");

    let only_borrows = || println!("From closure: {list:?}");

    println!("before calling closure: {list:?}");
    only_borrows();
    println!("after calling closure: {list:?}");
    //NOTE: We are only borrowing in closures, so this actually
    //does not override list or end its lifetime

    let mut list = vec![1, 2, 3];
    println!("before calling closure: {list:?}");
    let mut borrows_mutably = || list.push(7);
    //can't do this because you are doing an immutable borrow
    //println!("in between calling closure: {list:?}");
    borrows_mutably();
    println!("after calling closure: {list:?}");

    //NOTE: If you want your closure to take ownership, you can use `move`

    let list = vec![1, 2, 3];
    println!("Before defining closure: {list:?}");

    thread::spawn(move || println!("From thread: {list:?}"))
        .join()
        .unwrap();
    let mut list = [
        Rectangle {
            width: 10,
            height: 1,
        },
        Rectangle {
            width: 3,
            height: 5,
        },
        Rectangle {
            width: 7,
            height: 12,
        },
    ];

    list.sort_by_key(|r| r.width);
    println!("{list:#?}");

    let mut sort_operations = vec![];
    let value = String::from("closure called");

    //list.sort_by_key(|r| {
    //    //Not allowed because we cannot move out of value
    //    sort_operations.push(value);
    //    r.width
    //});
    //println!("{list:#?}");
}

#[derive(Debug)]
struct Rectangle {
    width: u32,
    height: u32,
}

#[derive(Debug, PartialEq, Copy, Clone)]
enum ShirtColor {
    Red,
    Blue,
}

struct Inventory {
    shirts: Vec<ShirtColor>,
}

impl Inventory {
    fn giveaway(&self, user_preference: Option<ShirtColor>) -> ShirtColor {
        user_preference.unwrap_or_else(|| self.most_stocked())
    }

    fn most_stocked(&self) -> ShirtColor {
        let mut num_red = 0;
        let mut num_blue = 0;

        for color in &self.shirts {
            match color {
                ShirtColor::Red => num_red += 1,
                ShirtColor::Blue => num_blue += 1,
            }
        }
        if num_red > num_blue {
            ShirtColor::Red
        } else {
            ShirtColor::Blue
        }
    }
}
