# Multithreading in Java
**Multithreading** refers to the process of executing **multiple threads concurrently** within a single program.

- To improve **CPU utilization**  
- To perform **multiple tasks simultaneously**
  
Used when multiple operations must happen in parallel, for example:
- Sending multiple notifications (Email, SMS)
- Handling multiple client requests
- Performing background tasks while UI remains responsive
  
## Code
```java
class MyTask extends Thread { 
    private String taskName; 
    
    MyTask(String name) { 
        this.taskName = name; 
    }

    @Override
    public void run() { 
        for (int i = 1; i <= 5; i++) { 
            System.out.println(taskName + " running step " + i); 
            try { 
                Thread.sleep(500); // simulate work 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        } 
    } 
}

public class MultiThreadExample {
    public static void main(String[] args) {
        MyTask task1 = new MyTask("Email Service");
        MyTask task2 = new MyTask("Report Generator");

        task1.start(); // starts new thread
        task2.start(); // starts another thread

        System.out.println("Main thread continues...");
    }
}
````

---

## Thread Lifecycle

| State                 | Description                            | Example                     |
| --------------------- | -------------------------------------- | --------------------------- |
| **New**               | Thread is created but not yet started  | `Thread t1 = new Thread();` |
| **Runnable**          | Ready to run, waiting for CPU time     | `t1.start();`               |
| **Running**           | Currently executing the `run()` method | `run();`                    |
| **Blocked / Waiting** | Waiting for resource or another thread | `sleep(), wait(), notify()` |
| **Terminated**        | Finished execution                     | After `run()` completes     |

---

## 🔒 Synchronization

Synchronization ensures that **only one thread** can access a shared resource at a time, preventing **data inconsistency** and **race conditions**.

When multiple threads modify shared data, results can become unpredictable. Synchronization keeps operations **atomic**.

#### 1. Synchronized Method

Locks the **entire method** for a single thread.

```java
synchronized void printDocument(String doc) {
    for (int i = 1; i <= 3; i++) {
        System.out.println(Thread.currentThread().getName() + " printing " + doc + " page " + i);
    }
}
```

#### 2. Synchronized Block

Locks **only a specific section** of code.

```java
public void printDocument(String doc) {
    synchronized(this) {
        for (int i = 1; i <= 3; i++) {
            System.out.println(Thread.currentThread().getName() + " printing " + doc + " page " + i);
        }
    }
}
```

#### Thread Coordination Methods

* `wait()`
* `notify()`
* `notifyAll()`

---

## Deadlock

**Deadlock** occurs when two or more threads are waiting for each other’s locks indefinitely.

### Example Scenario

Thread A holds Lock1 and waits for Lock2,
Thread B holds Lock2 and waits for Lock1 → both are **stuck forever**.

### Prevention

* Maintain consistent **lock ordering**
* Use **`tryLock()`** or **timeout-based locking**
* Prefer **ReentrantLock** for more control

---

## Futures

A **Future** represents an asynchronous task whose result will be available **later**.

* To perform background (non-blocking) tasks
* To execute multiple tasks in parallel and collect results later
* To allow the main thread to continue working while background tasks execute

In an e-commerce app:
* Order is received → Future returns a token immediately
* Order confirmation is processed asynchronously and completed later

---

## 🔁 ReentrantLock

`ReentrantLock` gives explicit control over locking — similar to `synchronized`, but more **flexible and powerful**.

### Use Cases
* Thread can **re-enter** the same lock (recursive)
* Supports **fair (FIFO)** locks
* Has methods like:

  * `lock()`
  * `unlock()`
  * `tryLock()`
  * `lockInterruptibly()`

### ✅ Advantages

* Prevents **deadlocks** using time-based `tryLock()`
* Provides better control over lock acquisition and release

---

## ⚙️ Concurrency Control

Concurrency ensures that shared data remains **consistent and atomic**, even when accessed by multiple threads.

Without concurrency control, threads may:

* Cause **race conditions**
* **Overwrite** each other’s updates
* Lead to **deadlocks** or inconsistent state
  
We can use:

* `synchronized` methods / blocks
* `ReentrantLock`
* `Atomic` classes
* `Semaphore`
* `volatile` keyword

### Benefits

* Maintains **data integrity**
* Enables **safe parallel execution**
* Prevents **race conditions** and **deadlocks**

---

## Semaphores

A **Semaphore** controls how many threads can access a specific block of code or resource at once.

### Example
```java
Semaphore semaphore = new Semaphore(3); // allow up to 3 threads
```

### Types
* **Binary Semaphore** → Only one permit (like a lock)
* **Counting Semaphore** → Multiple permits

### Concept: Bounded Resource

A **bounded resource** has a **fixed capacity**, e.g.:
* Database connections
* File handles
* Thread pools

Semaphore acts as a **permit counter**:
* Threads **acquire()** before using the resource
* Threads **release()** after finishing
* If no permit is available → thread **waits**

### Use of Semaphores
* Prevent **resource exhaustion**
* Control **concurrent access**
* Avoid **race conditions**

### 📍 Common Uses
| Resource             | Example                 |
| -------------------- | ----------------------- |
| Database connections | Max 10 at a time        |
| API rate limit       | Only 5 concurrent calls |
| Shared buffer        | Only 3 items in queue   |
| Thread pool          | Limited active threads  |

### Methods
* `acquire()`
* `release()`
* `tryAcquire()`
* `availablePermits()`
* `drainPermits()`

### Coordination
* `wait()`, `notify()`, `notifyAll()`
* `signal()`

---

````markdown
---

## Thread Pool
A **Thread Pool** is a collection of pre-created threads that are **ready to execute tasks**.  
Instead of creating a new thread for every task, you **submit tasks to the pool**, and available threads pick them up.

Managed by the **`ExecutorService`** in Java.

###ThreadPool
- **Performance** → Avoids the overhead of creating/destroying threads for each task  
- **Resource Management** → Limits the number of concurrent threads (prevents CPU/memory exhaustion)  
- **Scalability** → Efficiently handles many short-lived or long-running tasks  
- **Task Management** → Provides easy APIs for scheduling, submitting, and managing tasks

### When to Use
- When your application has **many concurrent tasks**
- When creating new threads for every operation is **expensive**
- For **server-side** apps (web servers, DB queries, background jobs)
- When you need **controlled concurrency** or **scheduled execution**

### Example
```java
import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " is running on " + Thread.currentThread().getName());
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                System.out.println("Task " + taskId + " finished");
            });
        }

        // Shutdown the pool gracefully
        executor.shutdown();
    }
}
````

### Summary

| Concept             | Description                                             |
| ------------------- | ------------------------------------------------------- |
| **ThreadPool**      | Pre-created threads reused for multiple tasks           |
| **ExecutorService** | Main API for managing thread pools                      |
| **Benefits**        | Improves performance, scalability, and resource control |
| **Types**           | Fixed, Cached, Single, Scheduled                        |

---

## ExecutorService
`ExecutorService` is an interface in `java.util.concurrent` that manages threads for you.
It **abstracts thread creation and task management** — you simply submit tasks, and it executes them using threads from a pool.

* Simplifies **thread management**
* Supports **thread pooling** and **reuse**
* Allows **graceful shutdown**
* Provides **Future** support for asynchronous tasks
* Enables **task scheduling** (run after delay or periodically)

### Key Methods

| Method                   | Description                                                     | Example                                               |
| ------------------------ | --------------------------------------------------------------- | ----------------------------------------------------- |
| `execute(Runnable task)` | Submits a task for execution (no result)                        | `executor.execute(() -> System.out.println("Task"));` |
| `submit(Runnable task)`  | Submits a task and returns a `Future<?>`                        | `Future<?> f = executor.submit(() -> doWork());`      |
| `shutdown()`             | Gracefully stops accepting new tasks and finishes existing ones | `executor.shutdown();`                                |
| `shutdownNow()`          | Attempts to stop all running tasks immediately                  | `executor.shutdownNow();`                             |

### ExecutorService + Future Example
```java
ExecutorService executor = Executors.newFixedThreadPool(2);

Future<String> result = executor.submit(() -> {
    Thread.sleep(2000);
    return "Task Completed!";
});

try {
    System.out.println("Result: " + result.get()); // waits for completion
} catch (Exception e) {
    e.printStackTrace();
}

executor.shutdown();
```

---

## Summary of Key Multithreading Concepts
| Concept             | Purpose                                   | Key Classes / Methods                |
| ------------------- | ----------------------------------------- | ------------------------------------ |
| **Multithreading**  | Run multiple tasks concurrently           | `Thread`, `Runnable`                 |
| **Synchronization** | Control thread access to shared resources | `synchronized`, `wait()`, `notify()` |
| **Deadlock**        | Avoid circular waiting                    | `tryLock()`, lock ordering           |
| **Future**          | Handle async tasks and results            | `ExecutorService`, `Future`          |
| **ReentrantLock**   | Explicit and flexible locking             | `lock()`, `unlock()`, `tryLock()`    |
| **Semaphore**       | Limit concurrent access                   | `acquire()`, `release()`             |
| **Thread Pool**     | Reuse pre-created threads                 | `Executors`, `ExecutorService`       |
| **ExecutorService** | Manage and control thread execution       | `submit()`, `shutdown()`             |
