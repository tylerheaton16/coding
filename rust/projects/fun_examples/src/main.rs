use std::fmt;

#[derive(Debug, Clone)]
enum Element {
    Fire,
    Water,
    Earth,
    Air,
}

#[derive(Debug, Clone)]
struct Spell {
    name: String,
    element: Element,
    power: u32,
    cost: u32,
}

#[derive(Debug)]
struct Wizard {
    name: String,
    mana: u32,
    spells: Vec<Spell>,
}

trait Castable {
    fn cast(&mut self, spell_name: &str) -> Result<String, String>;
    fn learn_spell(&mut self, spell: Spell);
}

impl Castable for Wizard {
    fn cast(&mut self, spell_name: &str) -> Result<String, String> {
        if let Some(spell) = self.spells.iter().find(|s| s.name == spell_name) {
            if self.mana >= spell.cost {
                self.mana -= spell.cost;
                Ok(format!("{} casts {}! Deals {} damage", self.name, spell.name, spell.power))
            } else {
                Err("Not enough mana!".to_string())
            }
        } else {
            Err("Spell not known!".to_string())
        }
    }

    fn learn_spell(&mut self, spell: Spell) {
        self.spells.push(spell);
    }
}

#[derive(Debug)]
enum Vehicle {
    Car { brand: String, speed: u32 },
    Bike { type_name: String, gears: u8 },
    Plane { model: String, altitude: u32 },
}

trait Drivable {
    fn start_engine(&self) -> String;
    fn get_info(&self) -> String;
}

impl Drivable for Vehicle {
    fn start_engine(&self) -> String {
        match self {
            Vehicle::Car { brand, .. } => format!("{} engine roars to life!", brand),
            Vehicle::Bike { type_name, .. } => format!("Pedaling the {} bike!", type_name),
            Vehicle::Plane { model, .. } => format!("{} engines spinning up!", model),
        }
    }

    fn get_info(&self) -> String {
        match self {
            Vehicle::Car { brand, speed } => format!("{} car, top speed: {} mph", brand, speed),
            Vehicle::Bike { type_name, gears } => format!("{} bike with {} gears", type_name, gears),
            Vehicle::Plane { model, altitude } => format!("{} plane, max altitude: {} ft", model, altitude),
        }
    }
}

#[derive(Debug)]
struct GameCharacter {
    name: String,
    health: i32,
    level: u32,
}

impl fmt::Display for GameCharacter {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "{} (Level {}, HP: {})", self.name, self.level, self.health)
    }
}

#[derive(Debug)]
enum PlayerAction {
    Attack(u32),
    Heal(u32),
    Defend,
    UseItem(String),
}

trait Combatable {
    fn take_damage(&mut self, damage: u32);
    fn heal(&mut self, amount: u32);
    fn is_alive(&self) -> bool;
}

impl Combatable for GameCharacter {
    fn take_damage(&mut self, damage: u32) {
        self.health -= damage as i32;
        if self.health < 0 {
            self.health = 0;
        }
    }

    fn heal(&mut self, amount: u32) {
        self.health += amount as i32;
        if self.health > 100 {
            self.health = 100;
        }
    }

    fn is_alive(&self) -> bool {
        self.health > 0
    }
}

#[derive(Debug)]
enum Color {
    Red,
    Green,
    Blue,
    Custom(u8, u8, u8),
}

impl Color {
    fn to_rgb(&self) -> (u8, u8, u8) {
        match self {
            Color::Red => (255, 0, 0),
            Color::Green => (0, 255, 0),
            Color::Blue => (0, 0, 255),
            Color::Custom(r, g, b) => (*r, *g, *b),
        }
    }
}

struct Shape {
    name: String,
    color: Color,
}

impl Shape {
    fn new(name: &str, color: Color) -> Self {
        Shape {
            name: name.to_string(),
            color,
        }
    }

    fn describe(&self) -> String {
        let (r, g, b) = self.color.to_rgb();
        format!("{} shape with color RGB({}, {}, {})", self.name, r, g, b)
    }
}

fn main() {
    println!("=== Wizard Example ===");
    let mut gandalf = Wizard {
        name: "Gandalf".to_string(),
        mana: 100,
        spells: vec![],
    };

    let fireball = Spell {
        name: "Fireball".to_string(),
        element: Element::Fire,
        power: 50,
        cost: 30,
    };

    gandalf.learn_spell(fireball);
    
    match gandalf.cast("Fireball") {
        Ok(msg) => println!("{}", msg),
        Err(err) => println!("Error: {}", err),
    }

    println!("\n=== Vehicle Example ===");
    let vehicles = vec![
        Vehicle::Car { brand: "Tesla".to_string(), speed: 200 },
        Vehicle::Bike { type_name: "Mountain".to_string(), gears: 21 },
        Vehicle::Plane { model: "Boeing 747".to_string(), altitude: 40000 },
    ];

    for vehicle in &vehicles {
        println!("{}", vehicle.start_engine());
        println!("{}", vehicle.get_info());
        println!();
    }

    println!("=== Combat Example ===");
    let mut hero = GameCharacter {
        name: "Hero".to_string(),
        health: 100,
        level: 5,
    };

    println!("Before combat: {}", hero);
    
    let actions = vec![
        PlayerAction::Attack(25),
        PlayerAction::Heal(15),
        PlayerAction::Defend,
        PlayerAction::UseItem("Potion".to_string()),
    ];

    for action in actions {
        match action {
            PlayerAction::Attack(damage) => {
                println!("Hero attacks for {} damage!", damage);
                hero.take_damage(damage);
            },
            PlayerAction::Heal(amount) => {
                println!("Hero heals for {} HP!", amount);
                hero.heal(amount);
            },
            PlayerAction::Defend => println!("Hero defends!"),
            PlayerAction::UseItem(item) => println!("Hero uses {}!", item),
        }
        println!("Current status: {}", hero);
    }

    println!("\n=== Shape and Color Example ===");
    let shapes = vec![
        Shape::new("Circle", Color::Red),
        Shape::new("Square", Color::Blue),
        Shape::new("Triangle", Color::Custom(128, 64, 192)),
    ];

    for shape in shapes {
        println!("{}", shape.describe());
    }
}