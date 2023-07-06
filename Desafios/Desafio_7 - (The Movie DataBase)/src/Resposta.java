import java.util.ArrayList;
// ESSE OBJETO É NESCESSÁRIO POIS A RESPOSTA DA API VEM EM PÁGINAS, OU SEJA NÃO VEM A RESPOSTA BRUTA (QUE É A LISTA DE FILMES EM JSON)
public class Resposta {
    ArrayList<Filme> results;

    public ArrayList<Filme> getResult() {
        return results;
    }
    public void setResult(ArrayList<Filme> result) {
        this.results = result;
    } 
}


