# 📦 Java Generics

---

## 🔍 What Are Generics?

Generics allow us to create classes, methods, and interfaces that work with different data types while ensuring type safety. They help catch type-related errors at compile time, making code more reliable and reusable.

---

## ✅ Why Use Generics?

- **Type Safety**: Errors are caught during compile time instead of runtime.
- **Code Reusability**: The same class or method can handle different data types.
- **Eliminates Typecasting**: No need to manually cast objects when using collections.
- **Cleaner Code**: Reduces redundancy and improves readability.

---

## 📍 Where Are Generics Used?

- In the **Collection Framework** — classes like `List`, `Map`, `Set`, and `Queue`
- In frameworks like **Spring**, wildcards (`<?>`) are often used in APIs (e.g., `@ResponseBody`) to accept any data type

---

## 🌟 Pros of Generics

- Type-safe code — prevents `ClassCastException`
- Reusable and modular code
- Seamless integration with Java Collections
- Compile-time type checking avoids runtime issues

---

## 🧪 Example: Using Wildcards

```java
import java.util.*;

public class GenericsExample {
    static void printList(List<?> list) {
        for (Object obj : list) {
            System.out.print(obj + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Integer> intList = List.of(1, 2, 3);
        List<String> strList = List.of("A", "B", "C");

        printList(intList);
        printList(strList);
    }
}
```

Here, `<?>` is a wildcard, meaning the method can accept a list of any type.

---

## 📦 Generic Class Example

```java
class Box<T> { // T is a type parameter
    private T value;

    public void set(T value) { this.value = value; }
    public T get() { return value; }
}
```

Usage:

```java
Box<Integer> intBox = new Box<>();
intBox.set(10);

Box<String> strBox = new Box<>();
strBox.set("Hello");
```

---

## 🧰 Generic Method Example

```java
class Util {
    // Generic method with type <T>
    public static <T> void printArray(T[] array) {
        for (T item : array) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
```

---

## 🔐 Type Safety

Type safety ensures that the data used in code is of the correct type. It prevents errors like using a string where a number is expected.

### ✅ Why Use Type Safety

- Catches errors at compile time
- Makes code more reliable and predictable
- Reduces the need for typecasting
- Works hand-in-hand with Generics

### 📍 Where It's Used

- In Generics (e.g., `List<String>`, `Map<Integer, String>`)
- In method parameters and return types
- Common in collections, data models, and utility classes

### 🕒 When to Use

- When working with data collections or reusable methods
- To prevent accidental type mismatches
- For early error detection during compilation

### 💻 Code Example

❌ Without Type Safety (Old Java - Before Generics)

```java
List list = new ArrayList(); // Raw type - no type checking
list.add("Hello");
list.add(123); // Works, but risky!

for (Object item : list) {
    String str = (String) item; // ⚠️ Causes ClassCastException at runtime
    System.out.println(str);
}
```

✅ With Type Safety (Using Generics)

```java
List<String> list = new ArrayList<>(); // Type-safe list
list.add("Hello");
// list.add(123); // ❌ Compile-time error

for (String str : list) {
    System.out.println(str); // No casting needed
}
```

---

## 📏 Bounded Types

Bounded types restrict what kind of data can be used. They allow methods or classes to accept only specific types.

### ✅ Why Use Bounded Types

- To limit allowed data types
- To use methods or fields available only in a specific class/interface
- To prevent invalid data types

### 📍 Where They're Used

- In generic classes, methods, or interfaces
- When operations depend on a certain class (e.g., `Number`)

---

### 🟩 1. Upper Bound (`extends`)

Accepts the specified type or any of its subclasses.

```java
public static <T extends Number> double square(T num) {
    return num.doubleValue() * num.doubleValue();
}
```

Since `T extends Number`, we can safely call `num.doubleValue()`.

---

### 🟥 2. Lower Bound (`super`)

Accepts the specified type or any of its superclasses.

```java
public static void addNumbers(List<? super Integer> list) {
    list.add(10);
    list.add(20);
    System.out.println(list);
}
```

This works because `Integer` is a subclass of `Number`, and `Object` is a superclass of both.

---

## ❓ Wildcards (`?`)

A wildcard (`?`) in Java Generics represents an unknown type. It allows methods or classes to work with different types without specifying the exact type.

### ✅ Why Use Wildcards

- To make methods or classes more flexible
- To work with collections of unknown or multiple types
- To reuse code without knowing the exact type
- Common in API responses (`List<?>`, `ResponseBody<?>`)

### 📏 Types of Wildcards

- **Unbounded**: `<?>`
- **Upper Bound**: `<? extends Type>` → Read-only
- **Lower Bound**: `<? super Type>` → Write-only
