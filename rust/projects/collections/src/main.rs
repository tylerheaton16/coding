use std::collections::HashMap;

fn main() {
    let v_example: Vec<i32> = Vec::new();
    let v = vec![1, 2, 3, 4, 5];
    //let does_not_exist = &v[3];
    let does_not_exist = v.get(3);

    match does_not_exist {
        Some(third) => println!("The third value is {third}"),
        None => println!("There is no 3rd value"),
    }

    if let Some(third) = does_not_exist {
        println!("Third value exists {third}");
    }

    //immutable borrow
    //let first = &v[0];
    //mutable borrow
    //v.push(6);
    let v = vec![100, 32, 57];
    for i in &v {
        println!("{i}");
    }

    let mut g = vec![100, 32, 57];
    for i in &mut g {
        *i += 50;
    }

    let row = vec![
        SpreadsheetCell::Int(3),
        SpreadsheetCell::Float(10.12),
        SpreadsheetCell::Text(String::from("blue")),
    ];

    let mut _s = String::new(); // create a new string
    let data = "initial contents";
    let s = data.to_string();
    let s = "initial contents".to_string(); //turns &str to String type
    let s = String::from("initial contents"); // string literal turns into a String
    // NOTE: .to_string() and String::from do the same thing

    //strings are UTF-8 encoded, so we can include properly any encoded data
    let hello = String::from("السلام عليكم");
    let hello = String::from("Dobrý den");
    let hello = String::from("Hello");
    let hello = String::from("שלום");
    let hello = String::from("नमस्ते");
    let hello = String::from("こんにちは");
    let hello = String::from("안녕하세요");
    let hello = String::from("你好");
    let hello = String::from("Olá");
    let hello = String::from("Здравствуйте");
    let hello = String::from("Hola");

    let mut s = String::from("foo");
    s.push_str("bar");
    println!("s is {s}");
    // NOTE: That push_str takes a string slice (&str) because we don't want to take ownership

    let mut s1 = String::from("foo");
    let s2 = "bar";
    s1.push_str(s2);
    // This works because we did not take ownership of s2 - we just borrowed its value
    println!("s2 is {s2}");

    let mut s = String::from("lo");
    s.push('l');

    // NOTE: We can also just use the concatenation operator
    let s1 = String::from("Hello, ");
    let s2 = String::from("world!");
    let s3 = s1 + &s2; //You MUST do String + &str. You can't do &str + &str because of the signature of +
    //println!("s1 is {s1}"); // can't do this because s1 was moved.
    println!("s2 is {s2}");
    println!("s3 is {s3}");

    //concatenation gets to be annoying, so why not just use the `format!` macro. It's like println! but
    //it returns a String type

    let s1 = String::from("tic");
    let s2 = String::from("tac");
    let s3 = String::from("toe");
    let s = format!("{s1}-{s2}-{s3}");

    //Indexing strings
    let s1 = String::from("hi");
    //let h = s1[0]; // can't do this in rust

    let hello = "Здравствуйте";
    //let answer = &hello[0]; // still can't do
    //If you really need to get the index of a string, you can use string slices

    let s = &hello[0..4]; // s is a &str which contains the first 4 bytes of a String
    println!("s is {s}");

    //We can also iterate on a string.

    for c in "3A".chars() {
        println!("{c}");
    }
    for c in "3A".bytes() {
        println!("{c}");
    }

    //Lets look at hash maps now (key value pairs)
    let mut scores = HashMap::new();
    scores.insert(String::from("Blue"), 10);
    scores.insert(String::from("Yellow"), 50);

    //lets access a hash map
    let team_name = String::from("Blue");
    //Below will "get" the borrowed key. Then, we will use .copied() to turn it into
    //Option<i32> instead of Option<&i32>. Then, unwrap gets the i32 out of Option
    let score = scores.get(&team_name).copied().unwrap_or(0);

    //Print the key value pair in scores
    for (key, value) in &scores {
        println!("{key}: {value}");
    }
    // NOTE: If you use a type that implements the Copy trait (i.e. i32), then it will be copied into the hashmap
    // if you use one that doesn't, it loses ownership being sent into the hash map

    let field_name = String::from("Favorite color");
    let field_value = String::from("Blue");

    let mut map = HashMap::new();
    map.insert(field_name, field_value); // takes ownership of field_name and field_value
    /*
    map.insert(&field_name, &field_value); //Note we can use &, but the values must always
    be valid for the "lifetime" of the HashMap
    */
    // field_name and field_value are invalid at this point, try using them and
    // see what compiler error you get!

    //Lets override values in a hash map
    let mut scores = HashMap::new();

    scores.insert(String::from("Blue"), 10);
    scores.insert(String::from("Blue"), 25);
    println!("{scores:?}");
    //lets override only if it doesn't have a value

    let mut scores = HashMap::new();
    scores.insert(String::from("Blue"), 10);

    scores.entry(String::from("Yellow")).or_insert(50);
    scores.entry(String::from("Blue")).or_insert(50);
    //Note that BLUE keeps the value 10. and does not get 50
    println!("{scores:?}");

    //let's update a value based on an old value

    let text = "hello world wonderful world";

    let mut map = HashMap::new();

    for word in text.split_whitespace() {
        let count = map.entry(word).or_insert(0);
        *count += 1; //we grab the location in the hash table and increment its value in the key/value pair
    }

    println!("{map:?}");


}

enum SpreadsheetCell {
    Int(i32),
    Float(f64),
    Text(String),
}
