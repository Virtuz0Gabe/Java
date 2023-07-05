import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FilmeDAO {
    private ConectarSQL conectarSQL;

    public FilmeDAO (ConectarSQL conectarSQL){
        this.conectarSQL = conectarSQL;
    }


    // === MÉTODOS DE VERIFICAÇÃO DE DADOS ======================================================
    public boolean filmeExistenteNoBanco (Filme filme){
        try(Connection connection = conectarSQL.getConnection()) {
            String query = "SELECT * FROM filmes_virtuozos.filmes WHERE id_filme =" + filme.getId();
            try(PreparedStatement statement = connection.prepareStatement(query)){
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()){
                    System.out.println("Filme não encontrado");
                    return false;
                }
            }
            conectarSQL.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean filmeExistenteNosRecomendados (Filme filme, Usuario usuario){
        try (Connection connection = conectarSQL.getConnection()) {
            String query = "SELECT * FROM filmes_virtuozos.interesses WHERE id_usuario =" + usuario.getId() + " AND id_filme = " + filme.getId();
            try(PreparedStatement statement = connection.prepareStatement(query)){
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()){
                    System.out.println("Filme não encontrado");
                    return false;
                }
            }
            conectarSQL.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    // === MÉTODOS DE MANIPULAÇÃO AO BANCO DE DADOS ======================================================
    public void adicionarFilmeNoBanco (Filme filme) {
        try (Connection connection = conectarSQL.getConnection()) {
            String query = "INSERT INTO filmes_virtuozos.filmes (id_filme, titulo, data_lancamento, sinopse, quantidade_de_votos, media_de_votos) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1, filme.getId());
                statement.setString(2, filme.getTitle());
                statement.setDate(3, filme.getReleaseDate());
                statement.setString(4, filme.getOverview());
                statement.setInt(5, filme.getVoteCount());
                statement.setFloat(6, filme.getVoteAverage());
                statement.executeUpdate();
            }
            conectarSQL.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public void adicionarFilmeNosRecomendados (Filme filme, Usuario usuario) {
        try (Connection connection = conectarSQL.getConnection()) {
            String query = "INSERT INTO filmes_virtuozos.interesses (id_filme, id_usuario) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1, filme.getId());
                statement.setInt(2, usuario.getId());
                statement.executeUpdate();
            }
            conectarSQL.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    // === MÉTODOS DE MANIPULAÇÃO AO BANCO DE DADOS ======================================================
    
    public void mostrarFilme (int idFilme){
        try(Connection connection = conectarSQL.getConnection()){
            String query = "SELECT * FROM filmes_virtuozos.filmes WHERE id_filme = " + idFilme;
            try(PreparedStatement statement = connection.prepareStatement(query)){
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                String titulo = resultSet.getString("titulo");
                java.sql.Date dataDeLancamento = resultSet.getDate("data_lancamento");
                String sinopse = resultSet.getString("sinopse");
                float media_de_votos = resultSet.getFloat("media_de_votos");
                int quantidade_de_votos = resultSet.getInt("quantidade_de_votos");

                System.out.println("\n\n\u001B[34mTítulo: \u001B[0m" + titulo + 
                "\n\u001B[34mData de Lançamento: \u001B[0m" + dataDeLancamento +
                "\n\n\u001B[96mMédia de Votos: \u001B[0m" + media_de_votos +
                "\n\u001B[96mQuantidade de Votos: \u001B[0m" + quantidade_de_votos +
                "\n\n\u001B[94mSinópse: \u001B[0m" + sinopse);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }





}
