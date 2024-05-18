# Модификатор volatile. Атомарные типы данных и операции.

## Модификатор volatile
**Модификатор volatile в Java используется для переменных, доступ к которым может быть одновременно осуществлен несколькими потоками. Он указывает, что значение переменной должно быть всегда читано из основной памяти, а не из кеша потока.**

### Основные особенности volatile:
* Гарантирует видимость изменений переменной для всех потоков.
* Не обеспечивает атомарность операций.


### Пример использования volatile:

```java
public class VolatileExample {
    private volatile boolean running = true;

    public void start() {
        new Thread(() -> {
            while (running) {
                System.out.println("Running...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Stopped.");
        }).start();
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) {
        VolatileExample example = new VolatileExample();
        example.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        example.stop();
    }
}
```
## Атомарные типы данных и операции
**Атомарные типы данных и операции обеспечивают гарантированную атомарность (непрерывность) выполнения операций. Пакет java.util.concurrent.atomic предоставляет несколько атомарных классов, таких как AtomicInteger, AtomicLong, AtomicBoolean, и AtomicReference.**

### Основные атомарные классы:
* AtomicInteger
* AtomicLong
* AtomicBoolean
* AtomicReference

### Пример использования атомарных типов данных:

### AtomicInteger:

```java
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {
    private AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet();
    }

    public int getCounter() {
        return counter.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerExample example = new AtomicIntegerExample();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final count: " + example.getCounter());
    }
}
```

### AtomicLong:

```java
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongExample {
private AtomicLong counter = new AtomicLong(0);

    public void increment() {
        counter.incrementAndGet();
    }

    public long getCounter() {
        return counter.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicLongExample example = new AtomicLongExample();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final count: " + example.getCounter());
    }
}
```
### AtomicBoolean:

```java
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanExample {
    private AtomicBoolean flag = new AtomicBoolean(true);

    public void stop() {
        flag.set(false);
    }

    public boolean isRunning() {
        return flag.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicBooleanExample example = new AtomicBooleanExample();

        Thread thread = new Thread(() -> {
            while (example.isRunning()) {
                System.out.println("Running...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Stopped.");
        });

        thread.start();

        Thread.sleep(2000);
        example.stop();
        thread.join();
    }
}
```

### AtomicReference:

```java
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample {
    private AtomicReference<String> message = new AtomicReference<>("Hello");

    public void updateMessage(String newMessage) {
        message.set(newMessage);
    }

    public String getMessage() {
        return message.get();
    }

    public static void main(String[] args) {
        AtomicReferenceExample example = new AtomicReferenceExample();
        System.out.println("Initial message: " + example.getMessage());

        example.updateMessage("Goodbye");
        System.out.println("Updated message: " + example.getMessage());
    }
}
```
### Основные моменты:
* Модификатор volatile: Гарантирует видимость изменений переменной для всех потоков, но не обеспечивает атомарность операций.
* Атомарные классы (AtomicInteger, AtomicLong, AtomicBoolean, AtomicReference): Обеспечивают атомарность операций и потокобезопасность для базовых операций над примитивными и ссылочными типами данных.