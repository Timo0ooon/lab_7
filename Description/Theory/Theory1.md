# Многопоточность. Класс Thread, интерфейс Runnable. Модификатор synchronized.

**Многопоточность позволяет программам выполнять несколько задач одновременно, что повышает производительность и эффективность. В Java многопоточность можно реализовать несколькими способами, основными из которых являются использование класса Thread и интерфейса Runnable. Также для синхронизации доступа к общим ресурсам используется модификатор synchronized.**

# Класс Thread
**Класс Thread в Java представляет собой поток исполнения. Для создания и запуска нового потока можно создать класс, наследующий Thread, и переопределить метод run().**

### Пример:

```java
public class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
            try {
                Thread.sleep(1000); // Пауза на 1 секунду
                } 
            catch (InterruptedException ignored) {}
        }
    }
}

    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();
        
        thread1.start();
        thread2.start();
    }
}
```
# Интерфейс Runnable
**Интерфейс Runnable является функциональным интерфейсом, который содержит единственный метод run(). Для создания потока с использованием Runnable нужно реализовать этот интерфейс и передать объект класса, реализующего Runnable, в конструктор класса Thread.**

### Пример:
```java
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
            try {
                Thread.sleep(1000); // Пауза на 1 секунду
                } 
            catch (InterruptedException ignored) {}
        }
    }
}

    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyRunnable());
        Thread thread2 = new Thread(new MyRunnable());
        
        thread1.start();
        thread2.start();
    }
}
```
# Модификатор synchronized
**Модификатор synchronized используется для синхронизации потоков. Он предотвращает одновременный доступ к критическим секциям кода несколькими потоками, что позволяет избежать проблем с параллельным доступом к общим ресурсам.**

### Пример:

```java
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class MyRunnable implements Runnable {
    private Counter counter;

    public MyRunnable(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread thread1 = new Thread(new MyRunnable(counter));
        Thread thread2 = new Thread(new MyRunnable(counter));
        
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count: " + counter.getCount());
    }
}
```
## Основные моменты:
1. Класс Thread: Для создания потока можно наследовать Thread и переопределить метод run().
2. Интерфейс Runnable: Реализуйте Runnable и передайте его в конструктор Thread.
3. Модификатор synchronized: Используется для синхронизации методов или блоков кода, чтобы предотвратить одновременный доступ к ним несколькими потоками.<br/>

***Этот механизм позволяет эффективно управлять многопоточностью и синхронизацией в Java, обеспечивая безопасное выполнение параллельных задач.***