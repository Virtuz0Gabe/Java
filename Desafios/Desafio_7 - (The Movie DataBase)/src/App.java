import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        
        Sessao sessao = new Sessao();
        Scanner scanner =new Scanner(System.in);
        boolean runningApp = true;
        boolean runningSession;
        System.out.println("\n\u001B[95m == Seja Bem Vindo ao Filmes Virtuozos == \u001B[0m\n");
        
        while (runningApp){
            sessao.Login();
            runningSession = true;
            while (runningSession){
                String escolha;
                System.out.println("\u001B[95m\nO que você deseja fazer?\u001B[0m");
                System.out.println("[1] Ver catálogo de filmes ");
                System.out.println("[2] Ver os recomendados para você");
                System.out.println("[3] Encerrar Sessão como \u001B[94m" + sessao.getUsuarioApelido() + "\u001B[0m");
                escolha = scanner.nextLine();
                switch (escolha){
                    case "1":
                        sessao.mostrarCatalogo();
                        break;

                    case "2":
                        sessao.mostrarInteresses();
                        break;

                    case "3":
                        runningSession = false;
                        System.out.println("\n\u001B[92mSaindo...\n\u001B[0m");
                        break;

                    default:
                        System.out.println("\n\u001B[91mValor       Inválido!");
                }
            }
        }
        scanner.close();
    }
}
