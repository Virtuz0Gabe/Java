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

        if (cache.containsKey(entrada)){
            ArrayList<Endereco> enderecos = cache.get(entrada);
            System.out.println("Pesquisa encontrada: ");
            for (Endereco endereco : enderecos){
                System.out.println(endereco);
            }
            return true;
        }

        if (entrada.matches("\\d{8}")){
            buscandoPorCEP(entrada);
            return true;
        }
        if (entrada.matches("[A-Z]{2},\\b[A-Za-z\\s]+\\b,\\b[A-Za-z\\s+]+\\b")){
            buscandoPorEspecifico(entrada);
            return true;
        }
        return false;
    }

    public static void buscandoPorEspecifico(String entrada) {

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

                System.out.println("\n");
                cache.put(entrada, enderecos);
                for (Endereco endereco : enderecos) {
                    System.out.println(endereco);
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
                    System.out.println(endereco);
                    ArrayList<Endereco> enderecos = new ArrayList<>();
                    enderecos.add(endereco);
                    cache.put(entrada, enderecos);

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
}
