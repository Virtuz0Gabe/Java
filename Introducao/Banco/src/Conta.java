import javax.management.relation.RelationSupport;

public class Conta {
    private float saldo;
    public String firstName;
    public String lastName;
    public int Age;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void getSaldo() {
        System.out.println("Saldo atual: R$ " + this.saldo);
    }

    public void withdraw (int Valor) {
        if (this.saldo < Valor){
            System.out.println("Valor Inválido");
        }else{
            this.saldo -= Valor;
        }
    }

    public void deposit (int Valor) {
        if (Valor < 0){
            System.out.println("Valor Inválido");
        }else{
            this.saldo += Valor;
        }
    }
}
