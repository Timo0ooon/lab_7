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

// Синхронизатор, ограничивает доступ к какому-то ресурсу. Он принимает аргумент, в котором надо указать количество потоков
public class SemaphoreExample {
    public static void main(String[] args) {
        Semaphore callBox = new Semaphore(2);
        Thread thread = new Person("Dima", callBox);
        Thread thread1 = new Person("Dima1", callBox);
        Thread thread2 = new Person("Dima2", callBox);
        Thread thread3 = new Person("Dima3", callBox);

    }
}

class Person extends Thread {
    String name;
    private Semaphore callBox;
    public Person(String name, Semaphore callBox) {
        this.name = name;
        this.callBox = callBox;
        this.start();
    }
    @Override
    public void run() {
        System.out.println(this.name + " waiting");
        try {

            callBox.acquire(); // threadCount++
            System.out.println(this.name + " using phone");
            sleep(2000);
            System.out.println(this.name + " finished");

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            callBox.release(); // threadCount--
        }
    }
}

```

## CountDownLatch
**CountDownLatch позволяет одному или нескольким потокам ждать завершения операций в других потоках.**

### Пример

```java
import MultiThreading.lesson6.CounterExample;

import java.util.concurrent.CountDownLatch;

// Потоки блокируются пока не выполнится определенное количество действий
public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        Salesman salesman = new Salesman();
        salesman.openMarket();
        salesman.turnOnTheLights();
        salesman.goToTheCashier();

        new Person("Dima", salesman.countDownLatch);
        new Person("Nikita", salesman.countDownLatch);
        new Person("Ilya", salesman.countDownLatch);
        new Person("Zaur", salesman.countDownLatch);
        new Person("Jeka", salesman.countDownLatch);
        new Person("Vlad", salesman.countDownLatch);
    }
}


class Salesman {
    public final CountDownLatch countDownLatch = new CountDownLatch(3); // count = 3

    public void openMarket() throws InterruptedException {
        System.out.println("Market opened");
        Thread.sleep(1000);
        countDownLatch.countDown(); // count--
    }

    public void turnOnTheLights() throws InterruptedException {
        System.out.println("Salesman turns on the lights");
        Thread.sleep(1000);
        countDownLatch.countDown(); // count--
    }

    public void goToTheCashier() throws InterruptedException {
        System.out.println("Salesman goes to the cashier");
        Thread.sleep(1000);
        countDownLatch.countDown(); // count--
    }

}


class Person extends Thread {
    public String name;
    public CountDownLatch countDownLatch;

    Person(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
        this.start();
    }

    @Override
    public void run() {
        try {
            countDownLatch.await(); // while (count != 0)
            System.out.println(this.name + " go shopping");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
