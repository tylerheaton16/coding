use std::error::Error;
use std::fs;
use std::fs::File;
use std::io::ErrorKind;
use std::io::{self, Read};
use std::net::IpAddr;

fn main() {
    /*
    When calling a panic, the code beings "unwinding" - walks up the stack, cleans the data
    from each function it encounters

    You can also just "abort" and end the program without cleaning.
    */
    /*
    Add to Cargo.toml to set abort status for a profile. Below aborts if in release mode
    [profile.release]
    panic = 'abort'

    */
    //panic!("Caveman: I sad - crash out now");
    let v = vec![1, 2, 3];
    //v[99];
    let greeting_file_result = File::open("hello.txt");

    /*
    let greeting_file = match greeting_file_result {
        Ok(file) => file,
        Err(error) => panic!("Problem opening the file: {error}"),
    };
    */

    //If the file doesn't exist, why don't we just make it? let's do that
    let greeting_file = match greeting_file_result {
        Ok(file) => file,
        Err(error) => match error.kind() {
            ErrorKind::NotFound => match File::create("hello.txt") {
                Ok(fc) => fc,
                Err(e) => panic!("Problem creating the file: {e:?}"),
            },
            _ => {
                panic!("Problem opening the file: {error:?}");
            }
        },
    };
    //This is the same as above but using closures - will learn in chapter 13
    let greeting_file_using_colsures = File::open("hello.txt").unwrap_or_else(|error| {
        if error.kind() == ErrorKind::NotFound {
            File::create("hello.txt").unwrap_or_else(|error| {
                panic!("Problem creating the file: {error:?}");
            })
        } else {
            panic!("Problem opening the file: {error:?}");
        }
    });

    //We can use the unwrap function to check if it is valid, if not panic!
    //Won't error because the above created hello.txt
    let greeting_file = File::open("hello.txt").unwrap();

    //The expect function lets us choose the panic!
    let greeting_file = File::open("hello.txt").expect("hello.txt should be included");

    //Can't do this unless we change how we return. We CAN do this in a function though.
    //let greeting_file = File::open("hello.txt")?;

    //IpAddr will always be correct. So, expect will never error. But what if someone
    //sent us the IP address? Expect helps us understand the expectation that our IP
    //address has to be correct
    let home: IpAddr = "127.0.0.1"
        .parse()
        .expect("Hardcoded IP address should be valid");

}

fn read_username_from_file() -> Result<String, io::Error> {
    let username_file_result = File::open("hello.txt");

    let mut username_file = match username_file_result {
        Ok(file) => file,
        Err(e) => return Err(e),
    };

    let mut username = String::new();

    match username_file.read_to_string(&mut username) {
        Ok(_) => Ok(username),
        Err(e) => Err(e),
    }
}
// NOTE: Error propogation like this is so useful, that rust as created a easy way to write this
// We use the `?` operator
fn read_username_from_file_easy_way() -> Result<String, io::Error> {
    let mut username_file = File::open("hello.txt")?;
    let mut username = String::new();
    username_file.read_to_string(&mut username)?;
    Ok(username)
}
/*
NOTE: This is saying we can create an implementation of our own error function and then use `?`

For example, we could change the read_username_from_file function in Listing 9-7 to
return a custom error type named OurError that we define.
If we also define impl From<io::Error> for OurError to construct an instance of OurError
from an io::Error, then the ? operator calls in the body of read_username_from_file will
call from and convert the error types without needing to add any more code to the function.

impl From<io::Error> for OurError // This is saying
*/

//Note we can make this even shorter
fn read_username_from_file_easy_way_even_shorter() -> Result<String, io::Error> {
    let mut username = String::new();

    File::open("hellur.txt")?.read_to_string(&mut username)?;

    Ok(username)
}

//Now, let's just make this un-godly simple. Let's use the `fs` library - filesystem manipulation
fn read_username_from_file_ungodly_simple() -> Result<String, io::Error> {
    // This just opens the file, creates a new String, reads the contents,
    //and puts it into that String which is returned by the function
    fs::read_to_string("hello.txt")
}
fn last_char_of_first_line(text: &str) -> Option<char> {
    /*
       The `?` is similar to when it is called on the Result<T,E> type.
       If the value is None, none will be returned early from the function.
       If the value is Some, then the value in Some will be the resultant

    NOTE: Why the hell would we want to continue to .chars().last() when there
    isn't a character in the first place. The `?` solves this.
    */
    text.lines().next()?.chars().last()
}

//We can use `?` on Result or Option. How do we go in between though?

/*
// NOTE: This will convert our main function to return a type

    fn main() -> Result<(), Box<dyn Error>> {
        let greeting_file = File::open("hello.txt")?;

        Ok(())
    }
*/
