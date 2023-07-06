
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class TarefaSQL {
    private ConectarSQL conectarSQL;

    public TarefaSQL(ConectarSQL conectarSQL){
        this.conectarSQL = conectarSQL;
    }

    // - [1] -- ADICIONAR TAREFA
    public void adicionarTarefa(String descricao, Date data){
        try (Connection connection = conectarSQL.getConnection()){
            String query = "INSERT INTO tarefas (descricao, data, status) VALUES (?, ?, ?)"; // ?, ? são pq vou passar os parâmetros mais adiante no setString() e setDATE()
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, descricao);
                statement.setDate(2, data);
                statement.setInt(3, 0);
                statement.executeUpdate();
                System.out.println("\u001B[32mTarefa adicionada com sucesso ao banco de dados!\u001B[0m\n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    
    
    // - [2] -- DELETAR TAREFA
    public void deletarTarefa(int id){
        try (Connection connection = conectarSQL.getConnection()){
            String query = "DELETE FROM tarefas WHERE id = " + id; 
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.executeUpdate();
                System.out.println("\u001B[32mTarefa removida com sucesso do banco de dados!\u001B[0m\n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    
    // - [3] -- ALTERAR TAREFA
    public void alterarTarefa (int id, String campo, String alteracao){
        try (Connection connection = conectarSQL.getConnection()){
            String query = "UPDATE tarefas SET " + campo + " = '" + alteracao + "' WHERE id =" + id;
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.executeUpdate();
                System.out.println("\u001B[32mTarefa alterada com sucesso no banco de dados!\u001B[0m\n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    // - [4] -- ALTERAR STATUS DA TAREFA
    public boolean alterarStatusDaTarefa(int id){
        try (Connection connection = conectarSQL.getConnection()){
            String query = "SELECT * FROM tarefas WHERE id = " + id;
            try(PreparedStatement statement = connection.prepareStatement(query)){
                    ResultSet resultSet = statement.executeQuery();
                    int status = 0;
                    if (resultSet.next()){
                        status = resultSet.getInt("status");
                    }else{
                        System.out.println("Tarefa não encontrada!");
                        return true;
                    }
                    String newStatus;
                    if (status == 1){
                        newStatus = "0";
                    }else{
                        newStatus = "1";
                    }
                    alterarTarefa(id, "status", newStatus);
                    System.out.println("\u001B[32mStatus alterado com sucesso!\u001B[0m\n");
                }
            }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    // - [5] -- FAZER PESQUISA
    public void pesquisaBanco (String pesquisa) {
        try (Connection connection = conectarSQL.getConnection()){
            pesquisa = pesquisa + " ORDER BY data";
            try(PreparedStatement statement = connection.prepareStatement(pesquisa)){
                ResultSet resultSet = statement.executeQuery();
                System.out.println("---------------------------------------------------");
                while(resultSet.next()){
                    int status = resultSet.getInt("status");
                    String showStatus = " ";
                    if (status == 1){
                        showStatus = "X";
                    }
                    int id = resultSet.getInt("id");
                    String descricao = resultSet.getString("descricao");
                    Date data = resultSet.getDate("data");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String dataFormatada = formatter.format(data);
                    System.out.println(id + " |  [" + showStatus + "]" + "  | " + dataFormatada + " | " + descricao + "\n---------------------------------------------------");
                }   
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    
}





