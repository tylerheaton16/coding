use rand::Rng;
use std::cmp::Ordering;
use std::io;

fn main() {
    println!("Guess the number!");

    let secret_number = rand::thread_rng().gen_range(1..=100);

    /*
    loop {
        println!("Please input your guess.");

        let mut guess = String::new();

        /*
           io is in standard library and can be used for IO features like prompting a user
        */
        io::stdin()
            .read_line(&mut guess)
            .expect("Failed to read line");

        //guess needs to be of type i32, so we modify `guess` to be below
        let guess: u32 = match guess.trim().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };
        println!("You guessed: {guess}");

        match guess.cmp(&secret_number) {
            Ordering::Less => println!("Too small!"),
            Ordering::Greater => println!("Too big!"),
            Ordering::Equal => {
                println!("You win!");
                break; //ends the `loop` we made and the program since it is the last executable
            }
        }
    }
    */
    /*
        In chapter 9, we are talking about making a type to enforce a requirement
        Why would we want to write an if statement to keep our guess between 1 and 100
        when we could enforce that
    */

    loop {
        println!("Please input your guess.");

        let mut guess = String::new();

        io::stdin()
            .read_line(&mut guess)
            .expect("Failed to read line");

        //guess needs to be of type i32, so we modify `guess` to be below
        let guess: i32 = match guess.trim().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };

        let mut guess = Guess::new(guess);
        let my_value = guess.value;
        println!("my value is {my_value}");
    }
}

pub struct Guess {
    value: i32,
}

impl Guess {
    pub fn new(value: i32) -> Guess {
        if value < 1 || value > 100 {
            panic!("Guess value must be between 1 and 100, got {value}");
        }
        Guess { value }
    }
    pub fn value(&self) -> i32 {
        self.value
    }
}
