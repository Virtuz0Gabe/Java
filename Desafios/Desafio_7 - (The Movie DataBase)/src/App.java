import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        
        Sessao sessao = new Sessao();
        Scanner scanner =new Scanner(System.in);
        boolean runningApp = true;
        boolean runningSession;
        System.out.println("\n == Seja Bem Vindo ao Filmes Virtuozos ==\n");
        
        while (runningApp){
            sessao.Login();
            runningSession = true;
            while (runningSession){
                String escolha;
                System.out.println("\u001B[95m\nO que você deseja fazer?\u001B[0m");
                System.out.println("[1] Ver catálogo de filmes ");
                System.out.println("[2] Ver os recomendados para você");
                escolha = scanner.nextLine();
                switch (escolha){
                    case "1":
                        sessao.buscarCatalogo();
                        break;
                }
                // ações do APP quando Logado!
                // CATÁLOGO!
                // MINHA LISTA DE INTERESSES
            }
        }

        scanner.close();
    }
}
