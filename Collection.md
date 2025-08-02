

### ☕️ Java Collections Notes


# Java Collections Overview

This guide covers the core of Java Collections Framework including:

- 🔹 Collection API & `Collection` vs `Collections`
- 🔹 List, Set, Map, Queue
- 🔹 Common implementations: ArrayList, HashSet, HashMap, TreeSet, etc.
- 🔹 Most-used operations

---

## 🧠 1. What is the Java Collection Framework?

Java Collection Framework (JCF) provides **ready-to-use classes & interfaces** to
store, access, and manipulate groups of data (objects).

### 🔸 Core Interfaces
- `Collection` (parent of List, Set, Queue)
- `Map` (stores key-value pairs)

---

## 📦 2. Collection vs Collections vs Collection API

| Term         | Description |
|--------------|-------------|
| `Collection` | Interface (root of List, Set, Queue)  
| `Collections`| Utility class with static methods (`sort()`, `reverse()`, etc.)  
| Collection API | The whole framework that includes interfaces, classes, and
                   algorithms for data structures like List, Set, Map, Queue  

---

## 📃 3. List Interface

- **Ordered**, allows **duplicates**
- Access via **index**
- Implementations: `ArrayList`, `LinkedList`

### 🔹 ArrayList

- Backed by **dynamic array**
- Fast access (get), slow insertion/removal (in middle)

```java
List<String> list = new ArrayList<>();
list.add("Apple");
list.add("Banana");
list.get(0);             // "Apple"
list.set(1, "Mango");
list.remove("Apple");
list.contains("Mango");  // true

### 🔹 LinkedList

* Backed by **doubly-linked list**
* Fast insertion/removal, slow random access

```java
List<Integer> link = new LinkedList<>();
link.add(10);
link.addFirst(5);    // only in LinkedList
link.remove(0);
```

---

## 🔢 4. Set Interface

* **No duplicates**, unordered or sorted based on implementation
* Implementations: `HashSet`, `LinkedHashSet`, `TreeSet`

### 🔹 HashSet

* **No order**, uses hash table
* Allows one `null` value

```java
Set<String> set = new HashSet<>();
set.add("A");
set.add("B");
set.add("A");         // ignored
set.contains("B");    // true
```

### 🔹 LinkedHashSet

* **Maintains insertion order**

```java
Set<String> lhs = new LinkedHashSet<>();
lhs.add("X");
lhs.add("Y");     // Order: X → Y
```

### 🔹 TreeSet

* **Sorted** in natural order
* No `null` allowed

```java
Set<Integer> ts = new TreeSet<>();
ts.add(30);
ts.add(10);
ts.add(20);  // Sorted: 10, 20, 30
```

---

## 🗺️ 5. Map Interface

* Stores **key-value pairs**
* No duplicate keys
* Implementations: `HashMap`, `Hashtable`

### 🔹 HashMap

* Allows one `null` key, many `null` values
* Not synchronized (not thread-safe)

```java
Map<Integer, String> map = new HashMap<>();
map.put(1, "One");
map.put(2, "Two");
map.get(1);            // "One"
map.containsKey(2);    // true
map.remove(1);
```

### 🔹 Hashtable

* **Thread-safe**
* No `null` key or value allowed

```java
Map<String, String> ht = new Hashtable<>();
ht.put("name", "Manas");
// ht.put(null, "value"); ❌ throws NullPointerException
```

---

## 🔁 6. Queue Interface

* Used for **FIFO** (First-In-First-Out)
* Implementations: `Deque`, `PriorityQueue`

### 🔹 Deque (Double-Ended Queue)

* Add/remove from both ends
* Implemented by `ArrayDeque`

```java
Deque<Integer> dq = new ArrayDeque<>();
dq.addFirst(1);
dq.addLast(2);
dq.removeFirst();  // 1
dq.removeLast();   // 2
```

### 🔹 PriorityQueue

* Elements are ordered based on **natural ordering** or custom comparator

```java
Queue<Integer> pq = new PriorityQueue<>();
pq.add(30);
pq.add(10);
pq.add(20);      // Poll returns 10 (smallest)

pq.poll();       // 10
```

---

## 🔧 7. Collections Utility Class

Class: `java.util.Collections`
Used for operations like:

```java
Collections.sort(list);
Collections.reverse(list);
Collections.max(list);
Collections.min(list);
Collections.frequency(list, "A");
```

---

## 📝 Summary

| Interface | Key Points                            |
| --------- | ------------------------------------- |
| `List`    | Ordered, duplicates allowed           |
| `Set`     | Unordered/Sorted, no duplicates       |
| `Map`     | Key-value, unique keys                |
| `Queue`   | FIFO, special operations at head/tail |

---

📚 *Prepared by ```Manas Pandey``` for learning and revision.
