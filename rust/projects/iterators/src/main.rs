fn main() {
    //iterators are "lazy" meaning they have no effect until you call methods
    // that consume the iterator to use it up
    let v1 = vec![1, 2, 3];
    let v1_iter = v1.iter();

    for i in v1_iter {
        println!("Got: {i}");
    }

    //Iterators get rid of boiler plate code that allows us to use
    //simple for loops with i indexes and getting sizes

    let v1: Vec<i32> = vec![1, 2, 3];
    //NOTE: map doesn't actually do anything here. We don't consume
    //so, we get a warning in the compiler. We must handle this
    //This is good code practice - we must consume the iterator
    v1.iter().map(|x| x + 1);

    let v2: Vec<_> = v1.iter().map(|x| x + 1).collect();
    assert_eq!(v2, vec![2, 3, 4]);
}
#[derive(PartialEq, Debug)]
struct Shoe {
    size: u32,
    style: String,
}

fn shoes_in_size(shoes: Vec<Shoe>, shoe_size: u32) -> Vec<Shoe> {
    shoes.into_iter().filter(|s| s.size == shoe_size).collect()
}
#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn filters_by_size() {
        let shoes = vec![
            Shoe {
                size: 10,
                style: String::from("sneaker"),
            },
            Shoe {
                size: 13,
                style: String::from("sandal"),
            },
            Shoe {
                size: 10,
                style: String::from("boot"),
            },
        ];

        let in_my_size = shoes_in_size(shoes, 10);

        assert_eq!(
            in_my_size,
            vec![
                Shoe {
                    size: 10,
                    style: String::from("sneaker")
                },
                Shoe {
                    size: 10,
                    style: String::from("boot")
                },
            ]
        );
    }
}
/*
//pub trait in STD library
 pub trait Iterator {
    type Item;

    fn next(&mut self) -> Option<Self::Item>;

    // methods with default implementations elided
}

*/
