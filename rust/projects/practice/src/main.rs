fn main() {
    let c = f2c(72.0);
    let f = c2f(0.0);
    println!("Degrees in Celsius is: {c}");
    println!("Degrees in Fahrenheight is: {f}");
    let fib = fibonacci(6);
    println!("fib sequence is {fib}");
    sing_twelve_days_of_christmas();
}

//Fahrenheight to Celsius
fn f2c(f: f64) -> f64 {
    let c: f64 = (5.0 / 9.0) * (f - 32.0);
    c
}
//Celsius to Fahrenheight
fn c2f(c: f64) -> f64 {
    let f: f64 = (c * 9.0 / 5.0) + 32.0;
    f
}

//Designing the Fibonacci sequence
/*f(n) = f(n-1) + f(n-2) */

fn fibonacci(n: i32) -> i32 {
    if n == 0 {
        0
    } else if n == 1 {
        1
    } else {
        fibonacci(n - 1) + fibonacci(n - 2)
    }
}
/*
lets say f(5) = f(4) + f(3)
f(4) = f(3) + f(2)
f(3) = f(2) + f(1)
f(2) = f(1) + f(0)
f(1) = 1
f(0) = 0
*/

//Sing the twelve days of Christmas taking advantage of the repetition of the lyrics

//fn sing(lyrics: )
fn sing_twelve_days_of_christmas() {
    let day = [
        "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth",
        "tenth", "eleventh", "twelth",
    ];
    let lyrics = [
        "a partridge in a pear tree",
        "two turtle doves,",
        "three french hens,",
        "four calling birds",
        "five golden rings",
        "six geese a-laying",
        "seven swans a-swimming",
        "eight maids a-milking",
        "nine ladies dancing",
        "ten lords a-leaping",
        "eleven pipers piping",
        "twelve drummers drumming",
    ];

    let mut n = 0;
    let mut m = 1;
    while n < 12 {
        println!("On the {} day of Christmas", day[n]);
        println!("my true love gave to me");
        while m > 0 {
            println!("{}", lyrics[m-1]);
            m -= 1;
        }

        n += 1;
        m = n + 1;
    }
}
