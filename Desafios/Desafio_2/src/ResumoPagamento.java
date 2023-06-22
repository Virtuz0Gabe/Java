import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ResumoPagamento {
    
    //private ArrayList<Venda> historicoDeVendas;

    public ResumoPagamento(ArrayList<Venda> historicoDeVendas) {
        
        HashMap<String, Float> mapa = new HashMap<>();

        for (Venda venda : historicoDeVendas){
            String formaPag = venda.getFormaPag();
            float valorTotal = venda.getTotalValue();

            if (mapa.containsKey(formaPag)){
                float valorExistente = mapa.get(formaPag);
                float valorAcumulado = valorExistente + valorTotal;
                mapa.put(formaPag, valorAcumulado);
            }else{
                mapa.put(formaPag, valorTotal);
            }
        }

        TreeMap<String, Float> mapaOrdenado = new TreeMap<>(new Comparator<String>() {
            
            public int compare(String chave_1, String chave_2) {
                Float valor_1 = mapa.get(chave_1);
                Float valor_2 = mapa.get(chave_2);
                return Float.compare(valor_1, valor_2);
            }
        });
        mapaOrdenado.putAll(mapa);

        System.out.println("\n\n============== Resumo por Forma de Pagamento ==============\n");
        System.out.println("\u001B[34mForma De Pagamento  \u001B[0m| \u001B[32mValor Total");
        for (Map.Entry<String, Float> resumo : mapaOrdenado.entrySet()) {
            String formaDepagamento = resumo.getKey();
            Float valorTotal = resumo.getValue();

            if (valorTotal > 0){
                System.out.println("\u001B[34m" + formaDepagamento + "  \u001B[0m| Valor Total: \u001B[32mR$ " + valorTotal + "\u001B[0m");
            }
        }
    }
}
