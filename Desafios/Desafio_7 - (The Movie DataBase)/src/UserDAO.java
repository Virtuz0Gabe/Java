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


    public ArrayList<String> mostrarUsuariosDisponiveis() {
        ArrayList<String> userList = new ArrayList<>();
        try (Connection connection = conectarSQL.getConnection()){
            String query = "SELECT * FROM filmes_virtuozos.usuarios";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                ResultSet resultSet = statement.executeQuery();
                System.out.println("\n ======== Quem está assistindo? ========");
                
                int index = 1;
                while(resultSet.next()){
                    String apelido = resultSet.getString("apelido_usuario");
                    int id = resultSet.getInt("id_usuario");
                    System.out.println("[" + index + "] " + apelido);
                    
                    String idString = Integer.toString(id);
                    userList.add(idString+","+apelido);
                    index ++;
                }
            }
            conectarSQL.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    } 


    
}
