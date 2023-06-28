import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class ConectarSQL {
    private final String url = "jdbc:mysql://localhost:3306/tarefas";
    private final String user = "root";
    private final String password = "gabrielwerner1";
    private Connection connection;
    

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void close() throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }
}