fn main() {
    //This chart shoes that r has a longer lifetime than b, so it can't compile
    /* let r;                // ---------+-- 'a
                              //          |
        {                     //          |
            let x = 5;        // -+-- 'b  |
            r = &x;           //  |       |
        }                     // -+       |
                              //          |
        println!("r: {r}");   //          |
    */                        // ---------+
    //let string1 = String::from("abcd");
    //let string2 = "xyz";
    //let result = longest(string1.as_str(), string2);
    //println!("The longest string is {result}");

    //Exploring lifetimes
    //let string1 = String::from("long string is long");
    //let result;
    //{
    //    let string2 = String::from("xyz");
    //    //result = longest(string1.as_str(), string2.as_str());
    //    println!("the longest string is {result}");
    //}
    ////try again but this time in outer scope - should fail
    ////because string2 goes out of scope after the inner loop
    //println!("the longest string is {result}");

    let novel = String::from("Call me Ishmael. Some years ago....");
    let first_sentence = novel.split('.').next().unwrap();
    let i = ImportantExcerpt {
        part: first_sentence,
    };
}
fn longest<'a>(x: &'a str, y: &'a str) -> &'a str {
    if x.len() > y.len() { x } else { y }
}
//if we only returned the first parameter, we wouldn't need a lifetime
//for y
fn longest_dont_output_y<'a>(x: &'a str, y: &str) -> &'a str {
    x
}
//won't work because x or y don't have a lifetime
//fn longest_need_input_lifetimes<'a>(x: &str, y: &str) -> &'a str {
//    let result = String::from("really long string");
//    result.as_str()
//}
//what if we wanted to create a struct which doesn't own
//its value
struct ImportantExcerpt<'a> {
    part: &'a str,
}
