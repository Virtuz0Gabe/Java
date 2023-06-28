import java.util.Scanner;
public class app {
    public static void main(String[] args) {

        TaskList taskList = new TaskList();
        Scanner scanner = new Scanner(System.in);
        int options = 0;
        
        System.out.println("\nSeja bem vindo(a) a sua Lista de Tarefas: \n");
        while (options != 6){
            System.out.println("\n[1] Criar uma Nova Tarefa");
            System.out.println("[2] Remover uma Tarefa");
            System.out.println("[3] Editar uma Tarefa");
            System.out.println("[4] Editar Status de um Tarefa");
            System.out.println("[5] Fazer pesquisa");
            System.out.println("[6] Encerrar");

            
            if (scanner.hasNextInt()){
                options = scanner.nextInt();

                switch (options){
                    case 1:
                    taskList.addTask();
                    break;
                    
                    case 2:
                    taskList.removeTask();
                    break;
                    
                    case 3:
                    taskList.editTask();
                    break;
                    
                    case 4:
                    taskList.switchTaskStatus();
                    break;

                    case 5:
                    taskList.pesquisa();
                    break;
                    
                    case 6:
                    System.exit(0);
                    break;
                }

            }else {
                System.out.println("Digite um valor inteiro, não inveta moda!");
                scanner.next();
            }
        }
        scanner.close();
    }
}
