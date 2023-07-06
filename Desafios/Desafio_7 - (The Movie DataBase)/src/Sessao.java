import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Sessao {

    Scanner scanner = new Scanner(System.in);
    ConectarSQL conectarSQL = new ConectarSQL();
    private UserDAO userDAO = new UserDAO(conectarSQL);
    private FilmeDAO filmeDAO = new FilmeDAO(conectarSQL);
    private InteressesDAO interessesDAO = new InteressesDAO(conectarSQL);
    private Usuario usuario;

    // =========================== GETTERS ===========================
    public String getUsuarioApelido(){
        return usuario.getApelido();
    }

    // =================================== MÉTODOS PRINCIPAIS ===================================
    public void Login (){
        ArrayList<Usuario> userList = userDAO.mostrarUsuariosDisponiveis();
        int index = 1;
        for (Usuario usuario : userList){
            System.out.println("[" + index + "] " + usuario.getApelido());
            index ++;
        }
        String msg = "Escolha um usuário para Logar";
        System.out.println(msg);
        while (!ValidaScannerNumber(scanner, msg)){
            scanner.next();
        }
        int escolha = scanner.nextInt() - 1;
        this.usuario = (userList.get(escolha));
        System.out.println("\nSeja bem vindo \u001B[95m" + usuario.getApelido() + "\u001B[0m");
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
                Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
                Resposta resposta = gson.fromJson(jsonResponse, Resposta.class);
                for (Filme filme : resposta.getResult()){
                    // AS DUAS LINHAS SEGUINTES LIMPAM O TERMINAL.
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    System.out.println(filme);
                    String msg = "\n[1] Adicionar a Lista de Desejos\n[2] Próximo filme =>\n[3] Sair";
                    System.out.println("\n\n" + msg);
                    while (!ValidaScannerNumber(scanner, msg)){
                        scanner.next();
                    }
                    int escolha = scanner.nextInt();
                    if (escolha == 1){
                        adicionarFilmeNoBanco(filme);
                    }
                    if (escolha == 3){
                        return;
                    }
                }  
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void adicionarFilmeNoBanco (Filme filme) {
        if (!filmeDAO.filmeExistenteNoBanco(filme)){
            filmeDAO.adicionarFilmeNoBanco(filme);
        }
        if (!filmeDAO.filmeExistenteNosRecomendados(filme, usuario)){
            filmeDAO.adicionarFilmeNosRecomendados(filme, usuario);
            System.out.println("\n\u001B[92m       Filme Adicionado a sua Lista de Interesses!");
        }else{
            System.out.println("\n\u001B[94m       Você já possui este filme em sua Lista de Interesses!");
        }
    }

    public void mostrarInteresses(){
        ArrayList<Filme> listaDeInteresse = interessesDAO.listaDeIteresses(usuario);
        System.out.println("\n\u001B[95m       Sua Lista de Interesses: \u001B[0m");
        for (Filme filme : listaDeInteresse){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println(filme);
            String msg = "       \n\u001B[95mOque deseja fazer?\u001B[0m\n[1] Próximo Filme da Lista =>\n[2] Voltar ao Menu <=\u001B[0m";
            System.out.println(msg);
            while (!ValidaScannerNumber(scanner, msg)){
               scanner.next();
            }
            int escolha = scanner.nextInt();
            if (escolha == 2){
                return;
            }
        }   
        System.out.println("\n\u001B[92m       Sua Lista de Interresses chegou ao fim!\u001B[0m");
    }

    // =================================== MÉTODOS ADICIONAIS ===================================
    public Boolean ValidaScannerNumber(Scanner answer, String msg){
        if (answer.hasNextInt()){
            return true;
        }else{
            System.out.println("\u001B[91mValor inválido, tente novamente!\n");
            System.out.println(msg);
            return false;
        }
    }
}
