import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class TaskList {

    ConectarSQL conectarSQL = new ConectarSQL();
    TarefaSQL tarefaSQL = new TarefaSQL(conectarSQL);
    Scanner scanner = new Scanner(System.in);

// ======================================== Métodos ========================================
    

// =================================== MÉTODOS PRINCIPAIS ===================================
    
    // -- [1] -------- ESTE MÉTODO ADICIONA UM NOVO OBJETO [Task] A LISTA DE TAREFAS [TaskList]
    public void addTask() {
        String TaskDescription;
        System.out.println("\nDigite a [DESCRIÇÃO] da tarefa: ");
        TaskDescription = scanner.nextLine();
        
        System.out.println("\nDigite a [DATA] prevista para esta tarefa: ");
        java.sql.Date taskDate = FormatadorDeData();
        
        tarefaSQL.adicionarTarefa(TaskDescription, taskDate);
    }
    
    // -- [2] -------- MÉTODO QUE ROMOVE UMA DETERMINADA TAREFA DA LISTA
    public boolean removeTask () {
        String msg = "\nQual tarefa você deseja Remover?\ndigite o ID da tarefa para remove-la ou [DIGITE 0] para cancelar";
        System.out.println(msg);
        while (!ValidaScannerNumber(scanner, msg)){
            scanner.next();
        }
        int removeTaskIndex = scanner.nextInt();
        
        if (removeTaskIndex == 0) {
            System.out.println("Remoção cancelada...");
            return true;
        }

        tarefaSQL.deletarTarefa(removeTaskIndex);
        return true;
    }

    // -- [3] -------- MÉTODO QUE EDITA UMA DETERMINADA TAREFA
    public void editTask () {
        String msg = "\nQual tarefa você deseja alterar?\ndigite o ID da tarefa para Alterar ou [DIGITE 0] para cancelar\n";
        System.out.println(msg);
        while (!ValidaScannerNumber(scanner, msg)){
            scanner.next();
        }
        int id = scanner.nextInt();
        String msg2 = "O que você deseja alterar na tarefa:\n[1] Descrição\n[2] Data";
        System.out.println(msg2);
        
        while (!ValidaScannerNumber(scanner, msg)){
            scanner.next();
        }
        int anwser = scanner.nextInt();
        scanner.nextLine();
        
        switch (anwser){
            case 1:
            System.out.println("Digite a nova descrição: ");
            String alteracao = scanner.nextLine();
            tarefaSQL.alterarTarefa(id, "descricao", alteracao);
            break;
            
            case 2:
            System.out.println("Digite a nova Data: ");
            Date data = FormatadorDeData();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            alteracao = dateFormat.format(data);
            tarefaSQL.alterarTarefa(id, "data", alteracao);
            break;
            }  
    }

    // -- [4] -------- MÉTODO QUE ALTERAR O STATUS DA TAREFA
    public void switchTaskStatus() {
        String msg = "\nQual tarefa você deseja alterar o Status?\ndigite o Index da tarefa para alterar o Status";
        System.out.println(msg);
        while (!ValidaScannerNumber(scanner, msg)){
            scanner.next();
        }
        int id = scanner.nextInt();
        tarefaSQL.alterarStatusDaTarefa(id);
    }

    // -- [5] -------- Pesquisar no Banco de dados
    public void pesquisa (){
        String msg = ("\nQual formato de pesquisa você deseja fazer?\n[1] Sintaxe SQL - \u001B[93mDEMANDA CONHECIMENTO DE SQL\u001B[0m\n[2] Por periodo de Data\n[3] Palavra-chave\n[4] Mostrar TUDO");
        System.out.println(msg);
        while (!ValidaScannerNumber(scanner, msg)){
            scanner.next();
        }
        int escolha = scanner.nextInt();
        switch (escolha){
            case 1:
                System.out.println("\nDigite a Sinxtaxe: ");
                scanner.nextLine();
                String sintaxe = scanner.nextLine();
                try {
                    tarefaSQL.pesquisaBanco(sintaxe);
                } catch (Exception e) {
                    System.out.println("\u001B[31mSintaxe incorreta, estude mais!\u001B[0m");
                    break;
                }
                break;

            case 2:
                System.out.println("Digite a data \u001B[36mINICIAL\u001B[0m: ");
                java.sql.Date dataInicial = FormatadorDeData();
                System.out.println("Digite a data \u001B[36mFINAL\u001B[0m:");
                java.sql.Date dataFinal = FormatadorDeData();
                String query = "SELECT * FROM tarefas WHERE data between '" + dataInicial + "' AND '" + dataFinal + "'";
                tarefaSQL.pesquisaBanco(query);
                break;
            
            case 3:
                System.out.println("\nDigite a \u001B[36mPalavra-Chave\u001B[0m");
                scanner.nextLine();
                String palavraChave = scanner.nextLine();
                query = "SELECT * FROM tarefas WHERE descricao LIKE '%" + palavraChave + "%';";
                System.out.println(query);
                tarefaSQL.pesquisaBanco(query);
                break;

            case 4:
                System.out.println("\nAqui está sua Lista de Tarefas Atualizada: ");
                System.out.println("Id |Status|    Data    |  Descrição");
                query = "SELECT * FROM tarefas";
                tarefaSQL.pesquisaBanco(query);
                break;

            default:
                System.out.println("\u001B[31mValor inválido!\u001B[0m\n");
        }
    }

    // -- [6] -------- Realizar a tarefa
    public void iniciarTarefa(){
        String msg = "Digite o Id da tarefa você irá trabalhar: ";
        System.out.println(msg);
        while (!ValidaScannerNumber(scanner, msg)){
            scanner.next();
        }
        int idTask = scanner.nextInt();
        AtomicBoolean runningTask = new AtomicBoolean(true);
        AtomicBoolean doingTask = new AtomicBoolean(true);
        AtomicInteger workedTime = new AtomicInteger(0);
        int escolha;
        System.out.println("Iniciando a tarefa");
        
        // ============= THREAD PARA EXECUTAR EM SEGUNDO PLANO =======================
        Thread threadLegal = new Thread(() -> {
            while (doingTask.get()){
                long threeSecondsTime = System.currentTimeMillis();
                while (runningTask.get()){                
                    long currentTime = System.currentTimeMillis();
                    if ((currentTime - threeSecondsTime) > 1000){                        
                        workedTime.set(1 + workedTime.get());
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        int horas = workedTime.get()/3600;
                        int minutos = workedTime.get()/60;
                        int segundos = workedTime.get()%60;
                        String tempoFormatado = String.format("%02d:%02d:%02d", horas, minutos, segundos);
                        System.out.println("\nTempo trabalhado: [\u001B[36m" + tempoFormatado + "\u001B[0m]");
                        System.out.println("Pressione:\n[1] Pausar\n[2] Encerrar");
                        threeSecondsTime = System.currentTimeMillis();
                    }
                }
            }
        });
        threadLegal.start();
        // =====================================================================

        while (doingTask.get()){
            if (runningTask.get()){
                escolha = scanner.nextInt();
                scanner.nextLine();
                if (escolha == 1){
                    runningTask.set(false);
                    System.out.println("\u001B[33m     Programa pausado...\u001B[0m");
                }
                if (escolha == 2){
                    runningTask.set(false);
                    doingTask.set(false);
                }
            }else{
                System.out.println("\n[1] Continuar\n[2] Finalizar");
                escolha = scanner.nextInt();
                scanner.nextLine();
                if (escolha == 1){
                    runningTask.set(true);
                }
                if(escolha == 2){
                    doingTask.set(false);
                }
            }
        }

        int horas = workedTime.get()/3600;
        int minutos = workedTime.get()/60;
        int segundos = workedTime.get()%60;
        String tempoFormatado = String.format("%02d:%02d:%02d", horas, minutos, segundos);
        tarefaSQL.alterarTarefa(idTask, "tempo_de_trabalho", String.valueOf(workedTime.get()));
        System.out.println("\n\u001B[92mVocê trabalhou pelo tempo de " + tempoFormatado + " na tarefa...\u001B[0m");
    }

    // -- [7] -------- EXPORTAR DADOS DO BANCO
    public void exportarDadosDoBanco (){
        tarefaSQL.exportarDadosDoBanco();
    }

    // -- [8] -------- IMPORTAR DADOS PARA O BANCO
    public void importarDadosParaBanco(){
        tarefaSQL.importarDadosParaBanco();
    }

    // =================================== MÉTODOS ADICIONAIS ===================================

    // METODO QUE FAZ A FORMATAÇÃO DA DATA EM DATE, IRÁ RETORNAR SOMENTE QUANDO O FORMATO FOR VÁLIDO
    public java.sql.Date FormatadorDeData(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        boolean dataValida = false;
        String StringDate;
        Date formatedDate = null;
        java.sql.Date sqlDate = null;

        while (!dataValida) {
            System.out.println("A data informada deve estar no formato [dd/MM/yyyy]");
            StringDate = scanner.nextLine();

            try {
                formatedDate = formatter.parse(StringDate);
                sqlDate = new java.sql.Date(formatedDate.getTime());
                dataValida = true;
            } catch (ParseException exception){
                System.out.println("Formato de data inválido. Por favor, insira uma data válida.");
            }
        }
        return sqlDate;
    }

    public Boolean ValidaScannerNumber(Scanner answer, String msg){
        if (answer.hasNextInt()){
            return true;
        }else{
            System.out.println("Valor inválido, tente novamente!\n");
            System.out.println(msg);
            return false;
        }
    }
}
