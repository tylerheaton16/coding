fn main() {
    let number = 7;

    if number < 5 {
        println!("condition was true");
    } else {
        println!("condition was false");
    }

    if number % 4 == 0 {
        println!("number is divisible by 4");
    } else if number % 3 == 0 {
        println!("number is divisible by 3");
    } else if number % 2 == 0 {
        println!("number is divisible by 2");
    } else {
        println!("number is not divisible by 4, 3, or 2");
    }

    let condition = true;

    let number = if condition { 5 } else { 6 };
    println!("The value of a number is: {number}");

    //looping
    let mut count = 0;
    'counting_up: loop {
        println!("count = {count}");
        let mut remaining = 10;

        loop {
            println!("remaining = {remaining}");
            if remaining == 9 {
                break;
            }
            if count == 2 {
                break 'counting_up;
            }
            remaining -= 1;
        }

        count += 1;
    }
    println!("End count = {count}");

    let mut number = 3;

    while number != 0 {
        println!("{number}!");

        number -= 1;
    }

    println!("LIFTOFF!!!");

    //For loop use
    let a = [10, 20, 30, 40, 50];
    let mut index = 0;

    //note this is `error prone` if we changed the size of a
    //So, lets follow some better syntax "for x in y"
    while index < 5 {
        println!("the value is: {}", a[index]);

        index += 1;
    }

    //Better implementation which doesn't created run-time code to do above
    for element in a {
        println!("The value is: {element}");
    }

    //Let's reverse the LIFTOFF code a little to be safe and work backwards

    for num in (1..4).rev() {
        println!("{num}");
    }
    println!("LIFTOFF!");

}
