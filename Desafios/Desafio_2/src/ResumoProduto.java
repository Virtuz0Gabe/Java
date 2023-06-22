import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class ResumoProduto {

    //private ArrayList<Venda> historicoDeVendas;
    
    public ResumoProduto(ArrayList<Venda> historicoDeVenda) {
        HashMap<String, Float> mapa = new HashMap<>();

        for (Venda venda : historicoDeVenda){
            for (Produto produto : venda.getProductList()){
                float valorDoProduto = produto.getValue() * produto.getQuantity();
                
                if (mapa.containsKey(produto.getDescription())){
                    float valorExistente = mapa.get(produto.getDescription());
                    float valorAcumulado = valorExistente + valorDoProduto;
                    mapa.put(produto.getDescription(), valorAcumulado);
                }else{
                    mapa.put(produto.getDescription(), valorDoProduto);
                }
            }
        }

        TreeMap<String, Float> mapaOrdenado = new TreeMap<>(new Comparator<String>() {
            
            public int compare(String chave_1, String chave_2) {
                Float valor_1 = mapa.get(chave_1);
                Float valor_2 = mapa.get(chave_2);
                return Float.compare(valor_2, valor_1);
            }
        });
        mapaOrdenado.putAll(mapa);

        System.out.println("\n\n============== Resumo por Produto ==============\n");
        System.out.println("\u001B[34mProduto  \u001B[0m| \u001B[32mValor Total");
        for (Map.Entry<String, Float> resumo : mapaOrdenado.entrySet()) {
            String produto = resumo.getKey();
            Float valorTotal = resumo.getValue();

            if (valorTotal > 0){
                System.out.println("\u001B[34m" + produto + "  \u001B[0m| Valor Total: \u001B[32mR$ " + valorTotal + "\u001B[0m");
            }

        } 
    }
}
