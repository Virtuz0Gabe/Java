public class Banco {
    public static void main(String[] args) {
        
        Conta conta = new Conta();
        System.out.println("Criando uma nova conta...");
        conta.firstName = "Gabriel";
        conta.lastName = "Werner";

        System.out.println("\n");
        System.out.println(conta.getFullName());
        System.out.println("Tirando Extrato:");
        conta.getSaldo();

        System.out.println("\n");
        System.out.println("Depositando um valor de R$ 120,00");
        conta.deposit(120);
        conta.getSaldo();

        System.out.println("\n");
        System.out.println("Tentando Retirar uma valor maior que o saldo:");
        conta.withdraw(1000);

        System.out.println("\n");
        System.out.println("Retirando o valor de R$ 40,00");
        conta.withdraw(40);
        conta.getSaldo();

    }
}
