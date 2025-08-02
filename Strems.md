# ☕ Java Stream API 

## 🧵 What is a Stream in Java?

Java Streams, introduced in **Java 8**, provide a functional and declarative way to process collections of data.

- A **Stream** is a sequence of elements supporting **pipeline operations**.
- It is **not a data structure** but a view of data from sources like collections, arrays, or I/O channels.
- Streams allow operations like **filtering**, **mapping**, **sorting**, and **reducing**.
- They support **lazy evaluation**, **parallel execution**, and **chaining** of operations.

### 🔑 Key Characteristics
- **Non-mutating**: Original data remains unchanged.
- **Lazy execution**: Intermediate operations are evaluated only when a terminal operation is invoked.
- **Composable**: Multiple operations can be chained together.
- **Parallelizable**: Streams can be processed concurrently.

---

## 🔄 Most Used Intermediate Operations

Intermediate operations return a stream and are **chainable**.

### 1. `map()`
Transforms each element using a function.
```java
Stream<R> map(Function<T, R> mapper)
```

### 2. `filter()`
Filters elements based on a predicate.
```java
Stream<T> filter(Predicate<T> predicate)
```

### 3. `sorted()`
Sorts elements in natural or custom order.
```java
Stream<T> sorted()
Stream<T> sorted(Comparator<T> comparator)
```

### 4. `flatMap()`
Flattens nested structures into a single stream.
```java
Stream<R> flatMap(Function<T, Stream<R>> mapper)
```

### 5. `distinct()`
Removes duplicate elements.
```java
Stream<T> distinct()
```

### 6. `peek()`
Performs an action on each element without modifying it.
```java
Stream<T> peek(Consumer<T> action)
```

---

## 🏁 Most Used Terminal Operations

Terminal operations **end the stream pipeline** and return a result.

### 1. `collect()`
Collects elements into a collection or result.
```java
<R, A> R collect(Collector<T, A, R> collector)
```

### 2. `forEach()`
Performs an action for each element.
```java
void forEach(Consumer<T> action)
```

### 3. `reduce()`
Combines elements into a single result.
```java
T reduce(T identity, BinaryOperator<T> accumulator)
Optional<T> reduce(BinaryOperator<T> accumulator)
```

### 4. `count()`
Returns the number of elements.
```java
long count()
```

### 5. `findFirst()`
Returns the first element, if present.
```java
Optional<T> findFirst()
```

### 6. `allMatch()` / `anyMatch()`
Checks if all or any elements match a predicate.
```java
boolean allMatch(Predicate<T> predicate)
boolean anyMatch(Predicate<T> predicate)
```

---

## 📌 Summary

- Streams simplify data processing with **functional-style operations**.
- Use **intermediate operations** to transform and filter data.
- Use **terminal operations** to produce results.
- Ideal for **clean**, **concise**, and **parallelizable** code.

---
