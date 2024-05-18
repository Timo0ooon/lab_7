# Интерфейсы Executor, ExecutorService, Callable, Future

**Интерфейсы Executor, ExecutorService, Callable и Future из пакета java.util.concurrent предоставляют мощные инструменты для управления и выполнения задач в многопоточных приложениях. Рассмотрим каждый из этих интерфейсов подробнее.**

## 1. Интерфейс Executor
**Executor — это простой интерфейс для выполнения задач в отдельном потоке. Он определяет метод execute(Runnable command), который принимает объект Runnable и выполняет его асинхронно.**

## Пример использования:

```java
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorExample {
    public static void main(String[] args) {
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            System.out.println("Task executed by " + Thread.currentThread().getName());
        });
    }
}
```
## 2. Интерфейс ExecutorService
**ExecutorService — это расширение интерфейса Executor, которое добавляет методы для управления жизненным циклом исполнительного сервиса и для выполнения задач, возвращающих результат. Важные методы включают:**
* submit(Runnable task): Выполняет задачу Runnable и возвращает объект Future, представляющий результат задачи.
* submit(Callable<T> task): Выполняет задачу Callable и возвращает объект Future, представляющий результат задачи.
* shutdown(): Инициирует упорядоченное завершение работы, при котором ранее отправленные задачи выполняются, но новые задачи не принимаются.
* shutdownNow(): Пытается остановить все запущенные задачи и возвращает список задач, ожидающих выполнения.

## Пример использования:

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            System.out.println("Task 1 executed by " + Thread.currentThread().getName());
        });

        executorService.submit(() -> {
            System.out.println("Task 2 executed by " + Thread.currentThread().getName());
        });

        executorService.shutdown();
    }
}
```

## 3. Интерфейс Callable
**Callable — это интерфейс, представляющий задачу, которая возвращает результат и может выбрасывать исключение. Он определяет метод call(), который должен быть реализован для выполнения задачи.**

## Пример использования:

```java
import java.util.concurrent.Callable;

public class CallableExample {
    public static void main(String[] args) {
        Callable<String> task = () -> {
            Thread.sleep(1000);
            return "Task executed by " + Thread.currentThread().getName();
        };

        try {
            System.out.println(task.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
## 4. Интерфейс Future
**Future — это интерфейс, представляющий результат асинхронной операции. Он предоставляет методы для проверки статуса задачи, получения результата и отмены выполнения.**
* isDone(): Возвращает true, если задача завершена.
* get(): Блокирует выполнение до завершения задачи и возвращает результат.
* get(long timeout, TimeUnit unit): Блокирует выполнение до завершения задачи или до истечения заданного времени ожидания, возвращает результат.
* cancel(boolean mayInterruptIfRunning): Пытается отменить выполнение задачи.

## Пример использования:

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FutureExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<String> task = () -> {
            Thread.sleep(1000);
            return "Task executed by " + Thread.currentThread().getName();
        };

        Future<String> future = executorService.submit(task);

        try {
            System.out.println("Result: " + future.get(2, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
```
## Основные моменты:
* Executor: Определяет метод для асинхронного выполнения задач.
* ExecutorService: Расширяет Executor, добавляя методы для управления жизненным циклом исполнительного сервиса и выполнения задач, возвращающих результат.
* Callable: Интерфейс для задач, возвращающих результат и выбрасывающих исключение.
* Future: Интерфейс для получения результатов многопоточных операций и управления выполнением задач.