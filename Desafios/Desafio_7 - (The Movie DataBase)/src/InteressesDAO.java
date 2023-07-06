import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class InteressesDAO {
    private ConectarSQL conectarSQL;

    public InteressesDAO (ConectarSQL conectarSQL){
        this.conectarSQL = conectarSQL;
    }

    public ArrayList<Filme> listaDeIteresses(Usuario usuario){
        ArrayList<Filme> response = new  ArrayList<>();
        try(Connection connection = conectarSQL.getConnection()){
            String query = "SELECT f.* FROM filmes_virtuozos.filmes f INNER JOIN filmes_virtuozos.interesses i ON f.id_filme = i.id_filme WHERE id_usuario = ?";
            try(PreparedStatement statement = connection.prepareStatement(query)){
               statement.setInt(1, usuario.getId());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    String titulo = resultSet.getString("titulo");
                    java.sql.Date dataDeLancamento = resultSet.getDate("data_lancamento");
                    String sinopse = resultSet.getString("sinopse");
                    float media_de_votos = resultSet.getFloat("media_de_votos");
                    int quantidade_de_votos = resultSet.getInt("quantidade_de_votos");

                    Filme filme = new Filme(titulo, dataDeLancamento, sinopse, media_de_votos, quantidade_de_votos);
                    response.add(filme);
                }
            }
            conectarSQL.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
