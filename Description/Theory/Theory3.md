# Классы-синхронизаторы из пакета java.util.concurrent.

**Пакет java.util.concurrent в Java предоставляет множество классов для управления многопоточностью и синхронизацией. Эти классы-синхронизаторы позволяют упрощать реализацию сложных параллельных алгоритмов и обеспечивают высокую производительность и надежность.**

## Основные классы-синхронизаторы:
* Semaphore
* CountDownLatch
* Exchanger


## Semaphore 
**Semaphore управляет доступом к ресурсу с использованием счетчика разрешений. Потоки могут запрашивать и освобождать разрешения.**

### Пример

```java
import java.util.concurrent.Semaphore;

class SharedResource {
    private final Semaphore semaphore;

    public SharedResource(int permits) {
        semaphore = new Semaphore(permits);
    }

    public void accessResource() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " accessed the resource.");
            Thread.sleep(2000); // Simulate resource access
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " released the resource.");
            semaphore.release();
        }
    }
}
```

## CountDownLatch
**CountDownLatch позволяет одному или нескольким потокам ждать завершения операций в других потоках.**

### Пример

```java
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) {
        final int TASK_COUNT = 3;
        CountDownLatch latch = new CountDownLatch(TASK_COUNT);

        for (int i = 0; i < TASK_COUNT; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " is running.");
                    Thread.sleep(1000); // Simulate task
                    System.out.println(Thread.currentThread().getName() + " finished.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        try {
            latch.await();
            System.out.println("All tasks are finished.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

## Exchanger

**Exchanger позволяет двум потокам обмениваться данными друг с другом.**

### Пример
```java
import java.util.concurrent.Exchanger;

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                String data = "Data from Thread 1";
                System.out.println("Thread 1 is exchanging: " + data);
                String receivedData = exchanger.exchange(data);
                System.out.println("Thread 1 received: " + receivedData);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                String data = "Data from Thread 2";
                System.out.println("Thread 2 is exchanging: " + data);
                String receivedData = exchanger.exchange(data);
                System.out.println("Thread 2 received: " + receivedData);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
```

### Основные моменты:
* Semaphore: Управляет доступом к ресурсам с использованием счетчика разрешений.
* CountDownLatch: Позволяет потокам ожидать завершения операций других потоков.
* Exchanger: Позволяет двум потокам обмениваться данными.