public class Endereco {
    
    String logradouro;
    String bairro;
    String localidade;
    String uf;
    
    String cep;
    String complemento;


    // ==================================================== GETTERS ====================================================
    public String getCep() {
        return cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }

// ==================================================== SETTERS ====================================================
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
    
    public void setUf(String uf) {
        this.uf = uf;
    }


// ==================================================== PERSONALIZADOS ====================================================    

    @Override
    public String toString(){
        return "logradouro: " + logradouro + " |  bairro: " + bairro + " |  localidade: " + localidade;
    }
}
