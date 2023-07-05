public class Usuario {
    int id;
    String apelido;

    
    public Usuario(int id,String apelido) {
        this.id = id;
        this.apelido = apelido;
    }



    // =========================== GETTERS ===========================
    public int getId() {
        return id;
    }
    public String getApelido() {
        return apelido;
    }

    // =========================== SETTERS ===========================
    public void setId(int id) {
        this.id = id;
    }
    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    


    
}
