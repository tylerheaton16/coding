fn main() {
    let width1 = 30;
    let height1 = 50;
    let rect1 = (30, 50);
    let size = 2;
    let rect2 = Rectangle {
        width: dbg!(30 * size), // can use the dbg! macro to help us print these out separately
        height: 50,
    };

    println!(
        "The area of the rectangle is {} square pixels.",
        area(width1, height1)
    );

    println!(
        "The area of the rectangle is {} square pixels.",
        area_tuple(rect1)
    );

    println!(
        "The area of the rectangle is {} square pixels.",
        area_struct(&rect2) // NOTE: Want to borrow rect2, NOT take ownership
    );
    println!("rect2 is {rect2:#?}");

    println!(
        "The area of the rectangle is {} square pixels.",
        rect2.area()
    );

    let rect4 = Rectangle {
        width: 30,
        height: 50,
    };
    let rect5 = Rectangle {
        width: 10,
        height: 40,
    };
    let rect6 = Rectangle {
        width: 60,
        height: 45,
    };

    println!("Can rect4 hold rect5? {}", rect4.can_hold(&rect5));
    println!("Can rect4 hold rect6? {}", rect4.can_hold(&rect6));

    let square = Rectangle::square(3); // create a `square` using a new instance
}

fn area(width: u32, height: u32) -> u32 {
    width * height
}

fn area_tuple(dimensions: (u32, u32)) -> u32 {
    dimensions.0 * dimensions.1
}

fn area_struct(rectangle: &Rectangle) -> u32 {
    rectangle.width * rectangle.height
}

#[derive(Debug)]
struct Rectangle {
    width: u32,
    height: u32,
}
// let's choose to create an implementation of the area function for `Rectanlge`
impl Rectangle {
    fn area(&self) -> u32 {
        self.width * self.height
    }

    fn can_hold(&self, other: &Rectangle) -> bool {
        self.width > other.width && self.height > other.height
    }

    fn square(size: u32) -> Self {
        Self {
            width: size,
            height: size,
        }
    }
}
