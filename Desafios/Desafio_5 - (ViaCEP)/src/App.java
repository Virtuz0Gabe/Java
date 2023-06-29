import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running){
            System.out.println("\nDigite o CEP ou o ENDEREÇO: ");
            String entrada = scanner.nextLine();
            BuscaCep.verificaEntrada(entrada);

            //96820170
            // viacep.com.br/ws/RS/Porto Alegre/Domingos/json/
            // RS,Santa Cruz do Sul,Pedreira
            // RS,Porto Alegre,Domingos
            // RS,Porto Alegre,Domingos Jose
            // RS,Porto Alegre,Domingos+Jose
        }
        scanner.close();
    }
}
