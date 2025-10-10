
# Object-Oriented Programming Concepts

---

## 🧬 Inheritance (Is-a Relationship)

Inheritance is an OOP concept where one class inherits properties and behaviors from another class.

- **Parent Class**: Defines common attributes and methods.
- **Child Class**: Inherits from the parent and can add its own features or override parent methods.

### ✅ Use
- Promotes code reusability
- Supports polymorphism
- Improves maintainability
- Enables better organization

### 📌 When to Use
- When common behavior needs to be shared across multiple classes
- When polymorphic behavior is required

### 💻 Example
```java
// Parent class
JPARepository {}

// Child class
UserRepository extends JPARepository {
    findByName();
    findByEmail();
    
    @Query
    CustomFind();
}
```

---

## 🔀 Polymorphism

Polymorphism means "many forms." It allows methods or objects to take multiple forms.

- **Compile-time (Method Overloading)**: Same method name, different parameters
- **Run-time (Method Overriding / Dynamic Dispatch)**: Same method name, different logic

### ✅ Why Use It
- Flexibility: Code works with generalized types
- Code Reusability: Shared logic in parent classes, specialized behavior in children
- Decoupling: Reduces dependency on specific child classes
- Supports OOP principles: Inheritance + Polymorphism → Polymorphic behavior

### 📌 When to Use
- When multiple types of objects share a common parent
- When you want to treat different objects uniformly but allow their own behavior

### 💡 Example
User roles in your system: `User`, `Agent`, `Dealer`

#### 🧪 Compile-Time Polymorphism
In `UserService`, role-based technology is used. Multiple `login` methods are created with different parameters for `Agent`, `Dealer`, and `User`.

- Used for verification
- Used for access control

#### ⏱️ Run-Time Polymorphism
(Implementation details can be added here.)

---

## 🧩 Abstraction

Abstraction focuses on hiding internal processes and showing only essential features to the user.

- Achieved through abstract classes and interfaces
- Focuses on what the object should do, not how it does it

### ✅ Why Use It
- Hides complexity
- Encourages modularity
- Promotes code reusability and scalability
- Supports polymorphism

### 📌 When to Use
- To simplify complexity
- To promote code reusability

### 📍 Where to Use
- Create an `add` method in the service layer and call it from the controller
- Avoid rewriting the entire method → improves reusability

---

## 🔒 Encapsulation

Encapsulation bundles data into a single unit (object) and hides direct access to that data.

- Access is controlled through getters and setters
- Fields are usually marked `private`, and public methods provide controlled access

### ✅ Why Use It
- Data hiding
- Maintains security and control

### 📌 When to Use
- Always for entity classes
- Any class where direct access to fields should be restricted
- Especially useful for user accounts, financial data, settings, etc.

### 💻 Example
```java
@Entity
class User {
    private String name;
    private String email;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

---

## 🔗 Association

Association is a relationship between two or more classes where one class interacts with another.

### 🧬 Types of Association
- `@OneToMany`
- `@ManyToOne`
- `@OneToOne`
- `@ManyToMany`

### ✅ Why Use It
- Models real-world relationships between objects
- Promotes modularity and reusability
- Keeps classes loosely coupled

### 📍 Where to Use
- Widely used in entity classes
- Example: `Model` table and `Year` table are separate but associated

---

## 🧱 Composition

Composition is a strong form of association where one class owns another class.

- Represents a “part-of” relationship
- If the owner object is destroyed, the owned object also ceases to exist

### ✅ Why Use It
- Represents ownership relationships
- Promotes modularity

### 📍 Where to Use
- Example: `Car` table is associated with `Brand` table → part-of relationship
```
