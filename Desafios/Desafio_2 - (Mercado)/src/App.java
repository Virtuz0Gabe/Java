import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("\n\n\n===== | Sistema de Vendas | =====");
        Scanner scanner = new Scanner(System.in);

        ArrayList<Venda> historicoDeVenda = new ArrayList<Venda>();
        boolean Expediente = true;
        final Date InicioDasVendas = new Date();

        while (Expediente){

            String msg = "Options:\n[1] iniciar uma venda\n[2] Encerrar Expediente";
            System.out.println(msg);
            while (!ValidaScannerNumber(scanner, msg)){
                scanner.next();
            }
            int response = scanner.nextInt();
            
            switch (response){
                case 1:
                    Venda newVenda = new Venda();
                    newVenda = newVenda.addVenda();
                    historicoDeVenda.add(newVenda);
                    break;
                
                case 2:
                    Expediente = false;
                    Date dataFinal = new Date(); 
                    System.out.println("O expediente iniciado em " + ArrumaData(InicioDasVendas) + " foi encerrado às " + ArrumaData(dataFinal));
                    ResumoPagamento resumoPagamento = new ResumoPagamento(historicoDeVenda);
                    ResumoProduto resumoProduto = new ResumoProduto(historicoDeVenda);
                    break;

                default:
                    System.out.println("Valor inválido");
            }
        }
    }

    public static Boolean ValidaScannerNumber(Scanner answer, String msg){
        if (answer.hasNextInt()){
            return true;
        }else{
            System.out.println("Valor inválido, tente novamente!\n");
            System.out.println(msg);
        }
        return false;
    }

    public static String ArrumaData(Date data){
        String beautyDate;
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
        beautyDate = formater.format(data);
        return beautyDate;
    }
}
