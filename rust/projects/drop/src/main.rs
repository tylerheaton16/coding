fn main() {

    let c = CustomSmartPointer {
        data: String::from("my stuff"),
    };

    let d = CustomSmartPointer {
        data: String::from("other stuff"),
    };

    println!("CustomSmartPointer created");

    //NOTE: If you want to drop something before the Drop is handled for you, you must
    //use the `std::mem::drop` function

    let c = CustomSmartPointer {
        data: String::from("some data"),
    };
    println!("CustomSmartPointer created.");
    //c.drop(); // can't do this because we aren't allowed to run our own drop
    drop(c); //This is std::mem::drop in the prelude
    println!("CustomSmartPointer dropped before the end of main");
}

struct CustomSmartPointer {
    data: String,
}

impl Drop for CustomSmartPointer {
    fn drop(&mut self) {
        println!("Dropping CustomSmartPointer with data {}!",self.data);
    }
}
