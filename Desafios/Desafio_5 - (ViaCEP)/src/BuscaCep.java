import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;

public class BuscaCep {

    static String urlInicial = "http://viacep.com.br/ws/";
    static HashMap<String, ArrayList<Endereco>> cache = new HashMap<>();

    public static boolean verificaEntrada (String entrada) {

        String chave = entrada.replace(" ", "");
        if (cache.containsKey(chave)){
            ArrayList<Endereco> enderecos = cache.get(chave);
            System.out.println("\n\u001B[92mPesquisa encontrada: \u001B[0m");
            if (enderecos.size() == 1){
                System.out.println(enderecos.get(0));
            }else{
                System.out.println(String.format("%-15s %-15s %-25s %s", "CEP", "Bairro", "Logradouro", "Complemento"));
                for (Endereco endereco : enderecos){
                    mostrarEspecifico(endereco);
                }
            }
            return true;
        }

        if (entrada.matches("\\d{8}")){
            buscandoPorCEP(entrada);
            return true;
        }
        if (entrada.matches("[A-Z]{2},\\s*[A-Za-z\\s]+,\\s*[A-Za-z\\s+]+")){
            buscandoPorEspecifico(entrada);
            return true;
        }
        System.out.println("\u001B[31m   Formato inválido! \u001B[0m");
        return false;
    }

    public static void buscandoPorEspecifico(String entrada) {
        String chave = entrada.replace(" ", "");
        String[] parte = entrada.split(",");
        String uf = parte[0];
        String cidade = parte[1];
        String logradouro = parte[2];

        cidade = cidade.replace(" ", "%20");
        logradouro = logradouro.replace(" ", "%20");
        String urlCompleta = urlInicial + uf + "/" + cidade + "/" + logradouro + "/json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlCompleta))
                .GET() // padronics mas eu quis por
                .build(); // encerramento da cosntrução do nosso obj, ele é imutável e agr está pronto para ser usado
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {                
                String jsonResponse = response.body();
                Gson gson = new Gson();
                TypeToken<List<Endereco>> typeToken = new TypeToken<List<Endereco>>() {};
                ArrayList<Endereco> enderecos = gson.fromJson(jsonResponse, typeToken.getType());
                if (enderecos.size() ==0){
                    System.out.println("\u001B[31m    Nada encontrado\u001B[0m");
                }else{
                    System.out.println("\n");
                    cache.put(chave, enderecos);
                    System.out.println(String.format("%-15s %-15s %-25s %s", "CEP", "Bairro", "Logradouro", "Complemento"));
                    for (Endereco endereco : enderecos) {
                        mostrarEspecifico(endereco);
                    }
                }
            } else {
                System.out.println("Erro na requisição: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 


    public static void buscandoPorCEP(String entrada) {
        String urlCompleta = urlInicial + entrada + "/json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlCompleta))
                .GET() // padronics mas eu quis por
                .build(); // encerramento da cosntrução do nosso obj, ele é imutável e agr está pronto para ser usado
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                Gson gson = new Gson();
                try{
                    Endereco endereco = gson.fromJson(jsonResponse, Endereco.class);
                    ArrayList<Endereco> enderecos = new ArrayList<>();
                    enderecos.add(endereco);
                    if (enderecos.size() ==0 ){
                        System.out.println("\u001B[31m    Nada encontrado\u001B[0m");
                    }else{
                        System.out.println(endereco);
                        cache.put(entrada, enderecos);
                    }
                }catch (JsonSyntaxException e) {
                    System.out.println("deu pal na desserialização do JSON: " + e.getMessage());
                }
            } else {
                System.out.println("Erro na requisição: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void mostrarEspecifico(Endereco endereco){
        System.out.println(String.format("%-15s %-15s %-25s %s", endereco.getCep(), endereco.getBairro(), endereco.getLogradouro(), endereco.getComplemento()));
    }

}
