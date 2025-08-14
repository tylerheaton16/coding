use rand::Rng;
use std::cmp::Ordering;
use std::io;

fn main() {
    println!("Guess the number!");

    let secret_number = rand::thread_rng().gen_range(1..=100);

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
}
