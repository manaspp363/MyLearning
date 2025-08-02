# 📘 Java Basics Notes

This document covers the core Java concepts for beginners and interview prep. It includes:

- ✅ Primitive Data Types  
- ✅ String, StringBuilder, StringBuffer (with memory explanation)  
- ✅ Wrapper Classes  
- ✅ Arrays (1D & 2D) with operations  

---

## 🔹 1. Primitive Data Types

Java has **8 primitive types**. They store values directly in memory (stack).

| Type      | Size    | Default | Use Case                    |
|-----------|---------|---------|-----------------------------|
| `byte`    | 1 byte  | 0       | Small numbers (–128 to 127) |
| `short`   | 2 bytes | 0       | Short-range integers        |
| `int`     | 4 bytes | 0       | Most common for numbers     |
| `long`    | 8 bytes | 0L      | Large numbers               |
| `float`   | 4 bytes | 0.0f    | Decimal values              |
| `double`  | 8 bytes | 0.0d    | High-precision decimals     |
| `char`    | 2 bytes | '\u0000'| Single characters like 'A'  |
| `boolean` | 1 bit   | false   | true/false                  |

> ☑️ Primitive types are **not objects** and cannot be used directly with collections.

---

## 🔹 2. String in Java

### 🔸 What is String?

- `String` is a **class in `java.lang` package**.
- Strings are **immutable** → once created, can't be changed.
- Internally, Strings are stored in a **String Pool** (in heap memory).

### 🔸 How String is stored

```java
String s1 = "Java";       // Stored in String Constant Pool
String s2 = "Java";       // Reuses same object as s1

String s3 = new String("Java");  // New object in heap memory
```

🔁 If you create with `new`, a new object is created even if value exists in pool.

### 🔸 Common String Operations

```java
String str = " Hello Java ";
str.length();                        // 11
str.charAt(0);                       // ' '
str.trim();                          // "Hello Java"
str.substring(1, 6);                 // "Hello"
str.indexOf("Java");                 // 6
str.toLowerCase();                   // " hello java "
str.toUpperCase();                   // " HELLO JAVA "
str.contains("Java");                // true
str.equals("hello");                 // false
str.equalsIgnoreCase("HELLO JAVA"); // true
str.replace("Java", "World");        // " Hello World "
```

---

## 🔹 3. StringBuilder vs StringBuffer

### 🔸 StringBuilder  
- Not thread-safe, but fast  
- Mutable: You can modify the content  
- Use when working in a single thread  

```java
StringBuilder sb = new StringBuilder("Hello");
sb.append(" Java");              // "Hello Java"
sb.insert(5, " World");          // "Hello World Java"
sb.delete(5, 11);                // "Hello Java"
sb.reverse();                    // "avaJ olleH"
```

### 🔸 StringBuffer  
- Thread-safe, slightly slower  
- Mutable and synchronized  
- Use in multi-threaded environments  

```java
StringBuffer sbf = new StringBuffer("Hi");
sbf.append(" Manas");
sbf.reverse();
```

---

## 🔹 4. Wrapper Classes

Wrapper classes are used to convert primitive types into objects.

| Primitive | Wrapper Class |
|-----------|----------------|
| `byte`    | `Byte`         |
| `short`   | `Short`        |
| `int`     | `Integer`      |
| `long`    | `Long`         |
| `float`   | `Float`        |
| `double`  | `Double`       |
| `char`    | `Character`    |
| `boolean` | `Boolean`      |

### 🔸 Why use Wrappers?

- Required for collections (`ArrayList`, `HashMap`, etc.)  
- Support `null` values (unlike primitives)  
- Utility methods (like `parseInt`, `valueOf`, etc.)

### 🔸 Autoboxing & Unboxing

```java
int a = 10;
Integer obj = a;   // Autoboxing
int b = obj;       // Unboxing
```

---

## 🔹 5. Arrays in Java

Arrays are used to store multiple values of the same type.  
They are fixed in size.

### 🔸 Declaration

```java
int[] arr = new int[5];           // default: [0,0,0,0,0]
int[] arr2 = {10, 20, 30};        // initialized
```

### 🔸 Access and Loop

```java
arr[0] = 5;                        // set value
int x = arr[2];                   // get value

// Normal for loop
for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}

// Enhanced for loop
for (int val : arr) {
    System.out.println(val);
}
```

### 🔸 Utility Methods

```java
import java.util.Arrays;

Arrays.sort(arr);                 // ascending sort
Arrays.toString(arr);             // print array
Arrays.binarySearch(arr, 20);     // returns index
```

### 🔸 2D Arrays (Matrix)

```java
int[][] matrix = new int[2][3];
matrix[0][1] = 10;
```

---

## ✅ Summary

- Use primitive types for performance  
- Use `StringBuilder` when you need to modify strings  
- Wrapper classes are essential for collections and null safety  
- Arrays are basic but limited; prefer `ArrayList` for flexibility  

---
📚 *Made with ❤️ by ```Manas Pandey``` while learning Java collections and core concepts.*
