# JDBC. Порядок взаимодействия с базой данных. Класс DriverManager. Интерфейс Connection

**JDBC (Java Database Connectivity) — это API в Java, который позволяет взаимодействовать с различными реляционными базами данных. Он предоставляет стандартный интерфейс для выполнения SQL-запросов и управления результатами.**

## Порядок взаимодействия с базой данных
**Взаимодействие с базой данных через JDBC обычно включает следующие шаги:**

1. Загрузка драйвера базы данных.
2. Установка соединения с базой данных.
3. Создание объекта для отправки SQL-запросов.
4. Выполнение SQL-запросов.
5. Обработка результатов.
6. Закрытие соединения. 

## Класс DriverManager
**DriverManager — это класс, который управляет набором драйверов базы данных. Он пытается выбрать подходящий драйвер для выполнения запросов к базе данных.**

### Основные методы:
* getConnection(String url): Устанавливает соединение с базой данных по указанному URL.
* getConnection(String url, String user, String password): Устанавливает соединение с базой данных по указанному URL, используя заданные имя пользователя и пароль.

## Интерфейс Connection
**Connection — это интерфейс, представляющий соединение с конкретной базой данных. Он предоставляет методы для работы с транзакциями, создания объектов для выполнения запросов и других операций с базой данных.**

### Основные методы:
* createStatement(): Создает объект Statement для выполнения SQL-запросов.
* prepareStatement(String sql): Создает объект PreparedStatement для выполнения параметризованных SQL-запросов.
* setAutoCommit(boolean autoCommit): Включает или отключает режим автоматической фиксации транзакций.
* commit(): Фиксирует все изменения, сделанные в текущей транзакции.
* rollback(): Откатывает все изменения, сделанные в текущей транзакции.
* close(): Закрывает соединение с базой данных.

## Пример использования JDBC
**Ниже приведен пример, демонстрирующий основные шаги взаимодействия с базой данных через JDBC.**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "root";
        String password = "password";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Загрузка драйвера базы данных (не требуется для JDBC 4.0 и выше)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Установка соединения с базой данных
            connection = DriverManager.getConnection(url, user, password);

            // Создание объекта для отправки SQL-запросов
            statement = connection.createStatement();

            // Выполнение SQL-запроса
            String sql = "SELECT id, name, email FROM users";
            resultSet = statement.executeQuery(sql);

            // Обработка результатов
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Закрытие ресурсов
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
```