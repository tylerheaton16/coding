fn main() {
    let mut user1 = User {
        active: true,
        username: String::from("username"),
        email: String::from("someemail@examples.com"),
        sign_in_count: 1,
    };

    user1.email = String::from("anotheremail@exmaples.com");

    //NOTE: This is a lot of work just to get most of user1 into user2. Better way to do it below
    //let user2 = User {
    //    active: user1.active,
    //    username: user1.username,
    //    email: String::from("another@example.com"),
    //    sign_in_count: user1.sign_in_count,
    //};
    let user2 = User {
        email: String::from("anotheremail@gmail.com"),
        ..user1
    };

    let _testa = user1.email; // can do because user1.email wasn't moved in user2
    //let _testb = user1.username; //can't do because user1.username was moved into user2

    let _user3 = build_user(String::from("user2@gmail.com"), String::from("user22"));
    let _black = Color(0, 0, 0);
    let origin = Point(0, 0, 0);

    let Point(x,y,z) = origin;
    println!("{x}");

    let subject = AlwaysEqual; //assigning a fieldless struct to subject
}

struct User {
    active: bool,
    username: String,
    email: String,
    sign_in_count: u64,
}

fn build_user(email: String, username: String) -> User {
    User {
        active: true,
        username,
        email,
        sign_in_count: 1,
    }
}
struct Color(i32, i32, i32);
struct Point(i32, i32, i32);

struct AlwaysEqual;
