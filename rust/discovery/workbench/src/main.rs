fn main() {
    /*
    all

    tests that every element passes a predicate
    */

    let v = vec![1, -1];
    let vi = v.iter().all(|&v| v > 0);
    println!("vi is {vi}");

    let a = vec![1,2,3];
    let b = vec![4,5,6];

    let v = vec![vec![1,2,3], vec![4,5,6]];
    let _v_ref = vec![a, b];

    let test: Vec<Vec<i32>> = v.iter().map(|x| x.iter().map(|y| y * 2).collect()).collect();
    println!("test is {test:#?}");
    println!("v is {v:?}");

    let v_get = v.get(0).unwrap();
}
