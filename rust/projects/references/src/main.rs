fn main() {
    let s1 = String::from("hello");
    let len = calculate_length(&s1);
    println!("The length of '{s1}' is {len}.");

    let mut s = String::from("hello");
    change(&mut s);

    let r1 = &mut s;
    //let r2 = &mut s; //can't do because we are already borrowing s in r1
    println!("{r1}");
    //println!("{r1}, {r2}");

    //dangling references
    let reference_to_nothing = dangle();
}

fn calculate_length(s: &String) -> usize { // Note: s is a refernce to a String
    s.len()
} //s will go out of scope here. But, s does not have ownership because it is just a reference, so s1 is still kept

fn change(some_string: &mut String) {
    some_string.push_str(", world");
}

fn dangle() -> &String {
    let s = String::from("hello");

    s //&s can't do &s as s goes out of scope when `dangle` ends. So, you can't pass a reference to it
}
