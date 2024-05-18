# Пулы потоков

**Пулы потоков (thread pools) в Java — это механизм управления коллекцией потоков для выполнения задач. Они предоставляют способ повторного использования потоков и управления их созданием и уничтожением, что позволяет улучшить производительность и стабильность многопоточных приложений. Пулы потоков реализованы с помощью класса ThreadPoolExecutor, который находится в пакете java.util.concurrent.**

## Зачем использовать пулы потоков?
* Повторное использование потоков: Пулы потоков позволяют повторно использовать существующие потоки для выполнения новых задач, избегая затрат на создание и уничтожение потоков.
* Ограничение числа одновременно выполняемых потоков: Пулы потоков помогают ограничить количество одновременно выполняемых потоков, предотвращая перегрузку системы.
* Управление жизненным циклом потоков: Пулы потоков автоматически управляют жизненным циклом потоков, освобождая разработчика от необходимости явного управления созданием и завершением потоков.

## Основные реализации пула потоков
**В Java предоставляется несколько стандартных реализаций пулов потоков через фабричные методы класса Executors:**
* FixedThreadPool
* CachedThreadPool
* SingleThreadExecutor
* ScheduledThreadPool

## 1. FixedThreadPool
   **FixedThreadPool создает пул с фиксированным числом потоков. Если все потоки заняты, новые задачи будут ожидать, пока не освободится поток.**

## Пример использования:

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.submit(() -> {
                System.out.println("Task " + index + " executed by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}
```

## 2. CachedThreadPool
   **CachedThreadPool создает пул, который динамически создает новые потоки по мере необходимости, но переиспользует существующие потоки, если они доступны. Неиспользуемые потоки уничтожаются, если они остаются неактивными в течение 60 секунд.**

## Пример использования:

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.submit(() -> {
                System.out.println("Task " + index + " executed by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}
```

## 3. SingleThreadExecutor
   **SingleThreadExecutor создает пул с одним потоком. Все задачи выполняются последовательно в одном и том же потоке.**

## Пример использования:

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.submit(() -> {
                System.out.println("Task " + index + " executed by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}
```

## 4. ScheduledThreadPool
   **ScheduledThreadPool создает пул, который может планировать задачи для выполнения после задержки или периодически.**

## Пример использования:

```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExample {
public static void main(String[] args) {
ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        // Задача с задержкой 5 секунд
        executor.schedule(() -> {
            System.out.println("Task executed by " + Thread.currentThread().getName());
        }, 5, TimeUnit.SECONDS);

        // Периодическая задача
        executor.scheduleAtFixedRate(() -> {
            System.out.println("Periodic task executed by " + Thread.currentThread().getName());
        }, 0, 3, TimeUnit.SECONDS);

        // Остановка после некоторого времени для демонстрации
        executor.schedule(() -> {
            executor.shutdown();
        }, 20, TimeUnit.SECONDS);
    }
}
```

## Управление пулом потоков
**Пулы потоков могут управляться с помощью методов интерфейса ExecutorService:**

* shutdown(): Инициирует упорядоченное завершение работы пула. После вызова этого метода новые задачи не принимаются, но ранее отправленные задачи продолжают выполняться.
* shutdownNow(): Пытается немедленно остановить все запущенные задачи и возвращает список задач, ожидающих выполнения.
* awaitTermination(long timeout, TimeUnit unit): Блокирует выполнение до завершения всех задач или истечения времени ожидания.
## Настройка ThreadPoolExecutor
**Для более детальной настройки можно использовать непосредственно класс ThreadPoolExecutor, который позволяет задавать такие параметры, как:**

* Начальное и максимальное количество потоков.
* Время ожидания для неактивных потоков перед их завершением.
* Очередь задач (например, LinkedBlockingQueue).
## Пример использования:

```java
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutorExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
        2,  // corePoolSize
        4,  // maximumPoolSize
        60, // keepAliveTime
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(10) // queueCapacity
        );

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.submit(() -> {
                System.out.println("Task " + index + " executed by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}
```
## Основные преимущества использования пулов потоков
* Повышенная производительность: Пулы потоков минимизируют накладные расходы на создание и уничтожение потоков.
* Управляемая конкурентность: Пулы потоков помогают контролировать количество одновременно выполняемых задач, предотвращая перегрузку системы.
* Упрощенное управление потоками: Пулы потоков автоматически управляют жизненным циклом потоков, облегчая разработку многопоточных приложений.