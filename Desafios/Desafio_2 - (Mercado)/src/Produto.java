public class Produto {
    
    private int Id;
    private float value;
    private int quantity;
    private String description;


    public Produto(int Id, int quantity) {

        switch (Id){
            case 1:
                this.description = "Coca-Cola Lata - 350ml";
                this.value = 4.00f;
                break;

            case 2:
                this.description = "Guarana - 600ml";
                this.value = 5.99f;
                break;

            case 3:
                this.description = "Hamburger de caixa - Sadia";
                this.value = 12.50f;
                break;

            case 4:
                this.description = "Salgadinho Batata - Lays";
                this.value = 10.00f;
                break;

            case 5:
                this.description = "Água Sem Gás";
                this.value = 4.99f;
                break;

            default:
            System.out.println("Valor inváido");
        }
        this.quantity = quantity;
        this.Id = Id;
    }

    // ============================================= GETTERS =============================================
    public int getId() {
        return Id;
    }

    public String getDescription() {
        return description;
    }

    public float getValue() {
        return value;
    }

    public float getQuantity() {
        return quantity;
    }

    // ============================================= SETTERS =============================================
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity){
        this.quantity += quantity;
    }

    // ============================================= Override =============================================
    @Override
    public String toString() {
        return "[" + Id +"] " + description + " | R$ " + value; 
    }
}
