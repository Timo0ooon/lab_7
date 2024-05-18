# Методы wait(), notify() класса Object, интерфейсы Lock и Condition.

## Методы wait(), notify(), notifyAll()

**Эти методы позволяют управлять потоками, ожидающими выполнения определенных условий. Они вызываются на объектах, используемых в синхронизированных блоках или методах.**

* wait(): Ставит текущий поток в состояние ожидания до тех пор, пока другой поток не вызовет notify() или notifyAll() на том же объекте.
* notify(): Пробуждает один из потоков, ожидающих на том же объекте.
* notifyAll(): Пробуждает все потоки, ожидающие на том же объекте.

### Пример:

```java
class SharedResource {
    private boolean condition = false;

    public synchronized void waitForCondition() {
        while (!condition) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Condition met, proceeding");
    }

    public synchronized void setCondition(boolean condition) {
        this.condition = condition;
        notifyAll(); // Notify all waiting threads
    }
}

public class WaitNotifyExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Runnable waitingTask = () -> {
            System.out.println("Waiting for condition");
            resource.waitForCondition();
            System.out.println("Condition met, task completed");
        };

        Runnable notifyingTask = () -> {
            try {
                Thread.sleep(2000); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Setting condition to true");
            resource.setCondition(true);
        };

        Thread thread1 = new Thread(waitingTask);
        Thread thread2 = new Thread(notifyingTask);

        thread1.start();
        thread2.start();
    }
}
```

## Интерфейсы Lock и Condition
**Интерфейс Lock предоставляет более гибкий механизм синхронизации, чем встроенные синхронизированные методы и блоки. Интерфейс Condition используется для управления потоками, ожидающими выполнения условий в рамках Lock.**

### Интерфейс Lock
**Основные методы:**
* lock(): Захватывает замок.
* unlock(): Освобождает замок.
* tryLock(): Пытается захватить замок без ожидания.
* lockInterruptibly(): Захватывает замок, может быть прерван.

### Пример:
```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}

public class LockExample {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final count: " + counter.getCount());
    }
}
```

### Интерфейс Condition
**Основные методы:**
* await(): Переход в режим ожидания.
* signal(): Пробуждение одного ожидающего потока.
* signalAll(): Пробуждение всех ожидающих потоков.

### Пример:
```java
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedResource {
    private boolean condition = false;
    private final Lock lock = new ReentrantLock();
    private final Condition conditionMet = lock.newCondition();

    public void waitForCondition() {
        lock.lock();
        try {
            while (!condition) {
                try {
                    conditionMet.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Condition met, proceeding");
        } finally {
            lock.unlock();
        }
    }

    public void setCondition(boolean condition) {
        lock.lock();
        try {
            this.condition = condition;
            conditionMet.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

public class ConditionExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Runnable waitingTask = () -> {
            System.out.println("Waiting for condition");
            resource.waitForCondition();
            System.out.println("Condition met, task completed");
        };

        Runnable notifyingTask = () -> {
            try {
                Thread.sleep(2000); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Setting condition to true");
            resource.setCondition(true);
        };

        Thread thread1 = new Thread(waitingTask);
        Thread thread2 = new Thread(notifyingTask);

        thread1.start();
        thread2.start();
    }
}
```
### Основные моменты:
1. Методы wait(), notify(), notifyAll(): Используются для управления потоками, ожидающими выполнения условий.
2. Интерфейс Lock: Предоставляет гибкие методы для захвата и освобождения замков.
3. Интерфейс Condition: Управляет потоками, ожидающими выполнения условий в рамках Lock.<br>

**Эти механизмы позволяют создавать более сложные и гибкие системы синхронизации в Java.**