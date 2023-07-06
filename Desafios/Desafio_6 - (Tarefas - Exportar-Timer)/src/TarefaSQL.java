import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
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

    // - [6] -- REALIZAR TAREFA
    //Para esta funcionalidade foi usado o método já existente, alterar tarefa
    // no caso, para alterar o valor de: "temp_ de_trabalho" no banco de dados

    // - [7] -- EXPORTAR DADOS DO BANCO 
    public void exportarDadosDoBanco () {
        try (Connection connection = conectarSQL.getConnection()){
            String query = "SELECT * FROM tarefas";
            try(PreparedStatement statement = connection.prepareStatement(query);
                ResultSet  resultSet = statement.executeQuery();
                FileWriter fileWriter = new FileWriter("src\\Dados\\exported_data.csv");
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.builder()
                    .setHeader("Descrição", "Data de Previsão", "Concluído", "Tempo de Trabalho")
                    .build());
                ){
                while (resultSet.next()) {
                    String descricao = resultSet.getString("descricao");
                    Date data = resultSet.getDate("data");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String dataFormatada = formatter.format(data);
                    
                    int status = resultSet.getInt("status");
                    String showStatus = "Não";
                    if (status == 1){
                        showStatus = "Sim";
                    }
                    int tempo_de_trabalho_total = resultSet.getInt("tempo_de_trabalho");
                    int minutos = tempo_de_trabalho_total / 60;
                    int segundos = tempo_de_trabalho_total % 60;
                    String tempo_de_trabalho = String.format("%02d:%02d", minutos, segundos);
                    csvPrinter.printRecord(descricao, dataFormatada, showStatus, tempo_de_trabalho);
                }
                csvPrinter.flush();
                System.out.println("\n\u001B[92mAs informações do Banco de dados foram exportadas para um arquivo .csv chamado \u001B[94m[exported_data.csv]\u001B[92m \nAbra a Pasta [Dados] para ter acesso ao arquivo!\u001B[0m");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // - [8] -- IMPORTAR DADOS PARA O BANCO
    public void importarDadosParaBanco () {
        File file = new File("src\\Dados\\import_data.csv");
        if (file.length() == 0){
            System.out.println("\n\u001B[91mO arquivo de importação está Vazio!");
            System.out.println("Por favor Verifique você colocou o arquivo corretamente na pasta \u001B[94m[DADOS] \u001B[91mem \u001B[94msrc \u001B[91mdo projeto\u001B[0m");
            return;
        }
        String query = "INSERT INTO tarefas (descricao, data, status, tempo_de_trabalho) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = conectarSQL.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            FileReader fileReader = new FileReader("src\\Dados\\import_data.csv");
            BufferedReader buff = new BufferedReader(fileReader)){
        
                String line;
                while ((line = buff.readLine()) != null) {
                    if (line.equals("Descrição,Data de Previsão,Concluído,Tempo de Trabalho")){
                        line = buff.readLine();
                    }
                    String [] info = line.split(",");
                    // ESTRUTURA DO ARQUIVO .csv
                    // Descrição, Data de Previsão, Concluído, Tempo de Trabalho
                    
                    // TRATAMENTO DE INFORMAÇÕES
                    String descricao = info[0];
                    java.sql.Date sqlDate;
                    int status;
                    int tempo_de_trabalho;
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    
                    // == DATA ==
                    try{
                        java.util.Date dataUtil = dateFormat.parse(info[1]);
                        sqlDate = new java.sql.Date(dataUtil.getTime());
                    } catch (ParseException e) {
                        System.out.println("\n\u001B[91mO formato de Data do arquivo não segue um padrão válido!\u001B[0m");
                        e.printStackTrace();
                        return;
                    }
                    // == STATUS ==
                    if (info[2].equalsIgnoreCase("Sim")){
                        status = 1;
                    }else{
                        status = 2;
                    }
                    // == TEMPO DE TRABALHO ==
                    try {
                        String[] time = info[3].split(":");
                        int minutos = Integer.parseInt(time[0]) * 60;
                        int segundos = Integer.parseInt(time[1]);
                        tempo_de_trabalho = minutos + segundos;
                    } catch (NumberFormatException e ) {
                        System.out.println("\n\u001B[91mO formato de Tempo de Trabalho no arquivo não segue um padrão válido!\u001B[0m");
                        e.printStackTrace();
                        return;
                    }
                    statement.setString(1, descricao);
                    statement.setDate(2, sqlDate);
                    statement.setInt(3, status);
                    statement.setInt(4, tempo_de_trabalho);
                    statement.executeUpdate();
                }
            System.out.println("\u001B[32mDados adicionados com sucesso ao Banco de Dados!\u001B[m");
            FileWriter fileWrite = new FileWriter("src\\Dados\\import_data.csv");
            fileWrite.write("");
            fileWrite.close();

        } catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }
}





