import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Venda {
    
    private ArrayList<Produto> productList;
    private float totalValue;
    private String formaPag;

    // ============================================= GETTERS =============================================
    public ArrayList<Produto> getProductList() {
        return productList;
    }
    
    public float getTotalValue() {
        return totalValue;
    }

    public String getFormaPag() {
        return formaPag;
    }

    // ============================================= SETTERS =============================================
    public void setProductList (ArrayList<Produto> listaProdutos){
        this.productList = listaProdutos;
    }
    
    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public void setFormaPag(String formaPag) {
        this.formaPag = formaPag;
    }

    // ============================================= MÉTODOS =============================================
    public float calcularValorTotal(ArrayList<Produto> listaDeProdutos){
        float total = 0.0f;
        for (Produto produto : listaDeProdutos){
            total += produto.getValue() * produto.getQuantity();
        }
        return total;
    }

    public void showProductList (List<Produto> ListaDeProdutos){
        System.out.println("\n--------- Cardápio ---------");
        for (Produto produto : ListaDeProdutos){
            System.out.println(produto);
        }
    }

    public ArrayList<Produto> criaListaDeProdutos(){
        ArrayList<Produto> ListaDeProdutos = new ArrayList<Produto>();

        Produto Coca = new Produto(1, 0);
        Produto Guarana = new Produto(2, 0);
        Produto Hamburger = new Produto(3, 0);
        Produto Batata = new Produto(4, 0);
        Produto Agua = new Produto(5, 0);

        ListaDeProdutos.add(Coca);
        ListaDeProdutos.add(Guarana);
        ListaDeProdutos.add(Hamburger);
        ListaDeProdutos.add(Batata);
        ListaDeProdutos.add(Agua);

        return ListaDeProdutos;
    }

    public Venda addVenda (){
        ArrayList<Produto> listaDeProdutos = criaListaDeProdutos();
        Venda novaVenda = new Venda();
        Scanner scanner = new Scanner (System.in);
        int VendaAtiva = 1;

        // ADICIONANDO OS PRODUTOS
        while (VendaAtiva == 1){
            
            showProductList(listaDeProdutos);
            String msg = "\nDigite o Index do Produto a ser adicionado:";
            System.out.println(msg);

            String ValorDigitado = scanner.nextLine();

            while (!ValidaScannerNumber(ValorDigitado, msg, 1, 5)){
                ValorDigitado = scanner.nextLine();
            }

            int indexProduct = Integer.parseInt(ValorDigitado) - 1;
            
            msg = "\nDigite a quantidade que deseja comprar: ";
            System.out.println(msg);

            ValorDigitado = scanner.nextLine();
            while (!ValidaScannerNumber(ValorDigitado, msg, 1, 30)){
                ValorDigitado = scanner.nextLine();
            }
            int quantity = Integer.parseInt(ValorDigitado);
            
            listaDeProdutos.get(indexProduct).addQuantity(quantity);
            listaDeProdutos.set(indexProduct, listaDeProdutos.get(indexProduct));
            System.out.println("\nProduto comprado:");
            System.out.println(listaDeProdutos.get(indexProduct));
            
            msg = "\nO que fazer agora?\n[1] Adicionar outra venda\n[2] Finalizar Venda";
            System.out.println(msg);

            ValorDigitado = scanner.nextLine();
            while (!ValidaScannerNumber(ValorDigitado, msg, 1, 2)){
                ValorDigitado = scanner.nextLine();
            }
            VendaAtiva = Integer.parseInt(ValorDigitado);
        }
        
        System.out.println("Selecione a forma de pagamento:");
        System.out.println("[1] Débito");
        System.out.println("[2] Crédito");
        System.out.println("[3] PIX");
        System.out.println("[4] Dinheiro");

        String msg = "Selecione a forma de pagamento:\n[1] Débito\n[2] Crédito\n[3] PIX\n[4] Dinheiro";
        String ValorDigitado = scanner.nextLine();
            while (!ValidaScannerNumber(ValorDigitado, msg, 1, 30)){
                ValorDigitado = scanner.nextLine();
            }
        int FormaPag = Integer.parseInt(ValorDigitado);

        // ADICIONANDO A FORMA DE PAGAMENTO
        switch (FormaPag){
            case 1:
                novaVenda.setFormaPag("Débito");
                break;

            case 2:
                novaVenda.setFormaPag("Crédito");
                break;
            
            case 3:
                novaVenda.setFormaPag("PIX");
                break;

            case 4:
                novaVenda.setFormaPag("Dinheiro");
                break;
        }

        novaVenda.setTotalValue(calcularValorTotal(listaDeProdutos));
        novaVenda.setProductList(listaDeProdutos);
        return novaVenda;
    }


    public Boolean ValidaScannerNumber(String valorDigitado, String msg, int min, int max){
        int valor;
        try {
            valor = Integer.parseInt(valorDigitado);
                if (valor >= min && valor <= max) {
                    return true;
            } else {
                System.out.println("Valor inválido, tente novamente!\n");
                System.out.println(msg);
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido, tente novamente!\n");
            System.out.println(msg);
        }
        return false;
    }

    @Override
    public String toString() {
        return productList + "\n ==== Resumo da Venda ====\nValor total: R$" + totalValue + " Forma de Pagamento: " + formaPag + "\n";
    }

    

}
