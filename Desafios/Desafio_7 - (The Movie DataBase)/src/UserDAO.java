import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    private ConectarSQL conectarSQL;

    public UserDAO (ConectarSQL conectarSQL){
        this.conectarSQL = conectarSQL;
    }

    public ArrayList<Usuario> mostrarUsuariosDisponiveis() {
        ArrayList<Usuario> userList = new ArrayList<>();
        try (Connection connection = conectarSQL.getConnection()){
            String query = "SELECT * FROM filmes_virtuozos.usuarios";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                ResultSet resultSet = statement.executeQuery();

                while(resultSet.next()){
                    String apelido = resultSet.getString("apelido_usuario");
                    int id = resultSet.getInt("id_usuario");
                    Usuario usuario = new Usuario(id, apelido);
                    userList.add(usuario);
                }
            }
            conectarSQL.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    }
}
