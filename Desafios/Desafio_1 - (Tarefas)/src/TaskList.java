import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class TaskList {

    ArrayList<Task> TaskList = new ArrayList<Task>();
    
    // ======================================== Métodos ========================================
    
    // MÉTODO QUE MOSTRA TODAS AS TAREFAS ORDENADAS PELA DATA E STATUS DE CONCLUSÃO
    public void showTaskList(){
        System.out.println("Index |  Status  |    Data    |  Descrição");

        if(TaskList.size() > 0){
            Collections.sort(TaskList, new Comparator<Task>() {
                @Override
                public int compare(Task task_1, Task task_2) {
                    return task_1.getDate().compareTo(task_2.getDate());
                }
            });

            int index = 1;
            for (Task task : TaskList){
                if (task.getStatus() == "Ativa"){
                    System.out.println("  " + index + "   | " + task); 
                    index ++;
                }
            }
            for (Task task : TaskList) {
                if (task.getStatus() == "Feita"){
                    System.out.println("  \u001B[32m" + index + "   | " + task + "\u001B[0m");
                    index ++;
                }
            }
             
        }else{
            System.out.println("\n          ## Lista Vazia ## \n");
        }
        System.out.println("========================================\n");
    }

    // =================================== MÉTODOS PRINCIPAIS ===================================
    
    // -- [1] -------- ESTE MÉTODO ADICIONA UM NOVO OBJETO [Task] A LISTA DE TAREFAS [TaskList]
    public void addTask() {
        Scanner scanner = new Scanner(System.in);
        String TaskDescription;
        
        System.out.println("\nDigite a [DESCRIÇÃO] da tarefa: ");
        TaskDescription = scanner.nextLine();
        
        System.out.println("\nDigite a [DATA] prevista para esta tarefa: ");
        Date TaskDate = FormatadorDeData();
        
        Task newTask = new Task(TaskDescription, TaskDate); 
        TaskList.add(newTask);
    }
    
    // -- [2] -------- MÉTODO QUE ROMOVE UMA DETERMINADA TAREFA DA LISTA
    public void removeTask () {
        Scanner scanner = new Scanner(System.in);
        if (TaskList.size() == 0){
            System.out.println("\nNão há tarefas na sua lista");
        }else{
            String msg = "\nQual tarefa você deseja Remover?\ndigite o Index da tarefa para remove-la ou [DIGITE 0] para cancelar";
            System.out.println(msg);

            while (!ValidaScannerNumber(scanner, msg)){
                scanner.next();
            }
            int removeTaskIndex = scanner.nextInt();
            
            if (removeTaskIndex == 0) {
                System.out.println("Remoção cancelada...");
            }else if (removeTaskIndex > TaskList.size()){
                System.out.println("Index inválido!\nCertifique-se de passar um valor dentro de um intervalo existente");
                removeTask();
            }else if (removeTaskIndex != 0){
                TaskList.remove(removeTaskIndex - 1);
            }
        }
    }

    // -- [3] -------- MÉTODO QUE EDITA UMA DETERMINADA TAREFA
    public void editTask () {
        Scanner scanner = new Scanner(System.in);
        if (TaskList.size() == 0){
            System.out.println("\nNão há tarefas na sua lista");
        }else{
            String msg = "\nQual tarefa você deseja alterar?\ndigite o Index da tarefa para Alterar ou [DIGITE 0] para cancelar\n";
            System.out.println(msg);
            
            while (!ValidaScannerNumber(scanner, msg)){
                scanner.next();
            }

            int editTaskIndex = scanner.nextInt() - 1;
            
            if (editTaskIndex != -1 && editTaskIndex <= (TaskList.size() - 1)){
                Task taskEdited = TaskList.get(editTaskIndex);
                System.out.println("Tarefa escolhida:\n" + taskEdited);
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
                    String newDescription = scanner.nextLine();
                    System.out.println("A descrição de " + taskEdited.getDescription() + " foi alterada para " + newDescription);
                    taskEdited.setDescription(newDescription);
                    break;
                    
                    case 2:
                    System.out.println("Digite a nova Data: ");
                    Date newDate = FormatadorDeData();
                    System.out.println("A data de " + taskEdited.getDescription() + " foi alterada para " + newDate);
                    taskEdited.setDate(newDate);
                    break;
                }
            }else if (editTaskIndex == -1){
                System.out.println("Edição cancelada...");
                
            }else if (editTaskIndex > TaskList.size() + 1){
                System.out.println("Index inválido!\nCertifique-se de passar um valor dentro de um intervalo existente");
                editTask();
            }
        }
    }

    // -- [4] -------- MÉTODO QUE ALTERAR O STATUS DA TAREFA
        public void switchTaskStatus() {
            Scanner scanner = new Scanner(System.in);
            if (TaskList.size() == 0){
                System.out.println("\nNão há tarefas na sua lista");
            }else {
                String msg = "\nQual tarefa você deseja alterar o Status?\ndigite o Index da tarefa para alterar o Status ou [DIGITE 0] para cancelar\n";
                System.out.println(msg);

                while (!ValidaScannerNumber(scanner, msg)){
                    scanner.next();
                }

                int editTaskStatusIndex = scanner.nextInt() - 1;
                
                if (editTaskStatusIndex != -1 && editTaskStatusIndex <= (TaskList.size() - 1)){
                    Task taskEdited = TaskList.get(editTaskStatusIndex);
                    System.out.println("Tarefa escolhida:\n" + taskEdited);
                    String newStatus = "";
                    
                    switch (taskEdited.getStatus()) {
                        case "Ativa":
                        newStatus = "Feita";
                        break;
                        
                        case "Feita":
                        newStatus = "Ativa";
                        break;
                    }
                    taskEdited.setStatus(newStatus);
                }
            }
        }

    // =================================== MÉTODOS ADICIONAIS ===================================

    // METODO QUE FAZ A FORMATAÇÃO DA DATA EM DATE, IRÁ RETORNAR SOMENTE QUANDO O FORMATO FOR VÁLIDO
    public Date FormatadorDeData(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Scanner scanner = new Scanner(System.in);
        boolean dataValida = false;
        String StringDate;
        Date TaskDate = null;

        while (!dataValida) {
            System.out.println("A data informada deve estar no formato [dd/MM/yyyy]");
            StringDate = scanner.nextLine();

            try {
                TaskDate = formatter.parse(StringDate);
                dataValida = true;
            } catch (ParseException exception){
                System.out.println("Formato de data inválido. Por favor, insira uma data válida.");
            }
        }
        return TaskDate;
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
