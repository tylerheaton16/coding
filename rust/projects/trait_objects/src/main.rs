fn main() {
    /*
    In other languages, you would implement a draw function as such
    Class Component {
        def draw(...) ...
    }

    Class Button extends Component

    val a = new Button
    a.draw

    However, in rust, we don't have inheritance. How would we do this?
    */

    // NOTE: We explore how to define a trait for common behavior here
    let screen = Screen {
        components: vec![
            Box::new(SelectBox {
                width: 75,
                height: 10,
                options: vec![
                    String::from("Yes"),
                    String::from("Maybe"),
                    String::from("No"),
                ],
            }),
            Box::new(Button {
                width: 50,
                height: 10,
                label: String::from("OK"),
            }),
        ],
    };

    screen.run();
}

//Let's create a trait
pub trait Draw {
    fn draw(&self);
}

//Let's make that trait object now!
pub struct Screen {
    // NOTE: This is saying that this is a vector of type Box<dyn Draw>, which is a trait object
    //It is a stand in for any type inside Box that implements the Draw trait
    pub components: Vec<Box<dyn Draw>>, // We have now made a `trait object`
}

pub struct Button {
    pub width: u32,
    pub height: u32,
    pub label: String,
}
//let's implement Button with the Draw trait
impl Draw for Button {
    fn draw(&self) {
        // code to draw a button
    }
}

//Let's implement it now

impl Screen {
    pub fn run(&self) {
        for component in self.components.iter() {
            component.draw();
        }
    }
}

//Let's say a new user wanted to create a new struct called `SelectBox`
struct SelectBox {
    width: u32,
    height: u32,
    options: Vec<String>,
}

impl Draw for SelectBox {
    fn draw(&self) {
        // code to actually draw a select box
    }
}
/*
NOTE: We could have done the above using something we already learned. If we just used generics
then we could have done the same thing. HOWEVER, they are ALL bound to `type Screen`
If we use a trait object, a Screen instance could hold a Vec<t>, Box<Button>, Box<TextField> etc

pub struct Screen<T: Draw> {
    pub components: Vec<T>,
}

impl<T> Screen<T>
where
    T: Draw,
{
    pub fn run(&self) {
        for component in self.components.iter() {
            component.draw();
        }
    }
}
*/
