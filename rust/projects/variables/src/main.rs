fn main() {
    let mut x = 5;
    println!("The value of x is: {x}");
    x = 6;
    println!("The value of x is: {x}");

    const THREE_HOURS_IN_SECONDS: u32 = 60 * 60 * 32;

    let y = 5;

    let y = y + 1;

    {
        let y = y * 2;
        println!("The value of y in the inner scope is {y}");
    }

    println!("The value of y is {y}");

    //typing

    /*
    Scalar types - single value
        1) integer
        2) floating-point number
        3) booleans
        4) characters
    */
    let xf64 = 2.0; //f64
    let xf32: f32 = 3.0; //f32

    let tuple: (i32, f64, u8) = (500, 24.0, 1);

    //destructuring a tuple
    let (a, b, c) = tuple;
    let aa = tuple.0;
    let bb = tuple.1;
    let cc = tuple.2;

    //Let's create an array
    let a_array: [i32; 5] = [1, 2, 3, 4, 5];
    //Values of an array are placed on the `stack`
}
