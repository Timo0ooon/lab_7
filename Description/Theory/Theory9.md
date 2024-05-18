# Интерфейсы Statement, PreparedStatement, ResultSet, RowSet

## 1. Интерфейс Statement
**Statement используется для выполнения статических SQL-запросов без параметров. Он представляет простой способ выполнения запроса к базе данных.**

### Пример использования:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "root";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String sql = "SELECT id, name, email FROM users";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
```
## 2. Интерфейс PreparedStatement
**PreparedStatement используется для выполнения параметризованных SQL-запросов. Он представляет более эффективный способ выполнения запросов с использованием параметров.**

### Пример использования:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "root";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, email FROM users WHERE id = ?")) {

            preparedStatement.setInt(1, 1); // Установка параметра
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
```
## 3. Интерфейс ResultSet
**ResultSet представляет результат запроса к базе данных. Он предоставляет методы для перебора и извлечения данных из результирующего набора.**

### Пример использования:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetExample {
public static void main(String[] args) {
String url = "jdbc:mysql://localhost:3306/mydatabase";
String user = "root";
String password = "password";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String sql = "SELECT id, name, email FROM users";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
```
## 4. Интерфейс RowSet
**RowSet представляет собой расширение ResultSet, которое может быть использовано для хранения и обработки набора данных в памяти. Он обеспечивает более гибкую работу с результатами запросов.**

### Пример использования:

```java
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class RowSetExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "username";
        String password = "password";

        try {
            RowSetFactory rowSetFactory = RowSetProvider.newFactory();
            CachedRowSet rowSet = rowSetFactory.createCachedRowSet();

            rowSet.setUrl(url);
            rowSet.setUsername(user);
            rowSet.setPassword(password);
            rowSet.setCommand("SELECT * FROM mytable");
            rowSet.execute();

            while (rowSet.next()) {
                // Обработка результата
                System.out.println(rowSet.getString("column1"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
```

**Эти примеры демонстрируют основные способы использования интерфейсов JDBC для работы с базами данных в Java.**