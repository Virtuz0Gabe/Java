import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;

public class Sessao {

    //private HashMap<String, ArrayList<Filme>> cache = new HashMap<>();
    private int idUsuarioLogado;
    private String apelidoUsuarioLogado;
    Scanner scanner = new Scanner(System.in);
    ConectarSQL conectarSQL = new ConectarSQL();
    UserDAO userDAO = new UserDAO(conectarSQL);


    // =================================== MÉTODOS PRINCIPAIS ===================================
    public void Login (){
        ArrayList<String> idList = userDAO.mostrarUsuariosDisponiveis();
        String msg = "Escolha um usuário para Logar";
        System.out.println(msg);
        while (!ValidaScannerNumber(scanner, msg)){
            scanner.next();
        }
        int index = scanner.nextInt() - 1;
        String usuario = idList.get(index);
        String dados[] = usuario.split(",");
        this.idUsuarioLogado = Integer.parseInt(dados[0]);
        this.apelidoUsuarioLogado = dados[1];
        System.out.println("Seja bem vindo " + apelidoUsuarioLogado);
    }

    public void buscarCatalogo (){
        String url = "https://api.themoviedb.org/3/discover/movie?language=pt-BR&api_key=61cbb745059bfb58e7c56ab5fce49653";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200){
                String jsonResponse = response.body();
                Gson gson = new Gson();
                Resposta resposta = gson.fromJson(jsonResponse, Resposta.class);
                for (Filme filme : resposta.getResult()){
                    System.out.println(filme);
                    String msg = "\n[1] Adicionar a Lista de Desejos\n[2] Próximo filme =>";
                    while (!ValidaScannerNumber(scanner, msg)){
                        scanner.next();
                    }
                    String escolha = scanner.nextInt();
                    if (escolha.equals(1)){
                        
                    }
                }
            
            }
        } catch (Exception e){
            e.printStackTrace();
        }
            
    }


    // =================================== MÉTODOS ADICIONAIS ===================================
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
