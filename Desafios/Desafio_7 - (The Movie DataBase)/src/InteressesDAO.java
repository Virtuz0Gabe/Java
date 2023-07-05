import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class InteressesDAO {
    private ConectarSQL conectarSQL;

    public InteressesDAO (ConectarSQL conectarSQL){
        this.conectarSQL = conectarSQL;
    }

    public ArrayList<Integer> listaDeIteresses(Usuario usuario){
        ArrayList<Integer> response = new  ArrayList<>();
        try(Connection connection = conectarSQL.getConnection()){
            String query = "SELECT * FROM filmes_virtuozos.interesses WHERE id_usuario = " + usuario.getId();
            try(PreparedStatement statement = connection.prepareStatement(query)){
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    response.add(resultSet.getInt("id_filme"));
                }
            }
            conectarSQL.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }



}
