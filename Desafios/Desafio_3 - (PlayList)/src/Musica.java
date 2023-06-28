

public class Musica {
    
    private String title;
    private String artist;
    private int duration;
    
    public Musica(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    public Musica (int x){
        switch (x){
            case 1:
                this.title = "Vendetta";
                this.artist = "Vírtuz";
                this.duration = 200;
                break;
            
            case 2:
                this.title = "Rap do Minecraft";
                this.artist = "Criança";
                this.duration = 180;
                break;

            case 3:
                this.title = "Metamorphosis";
                this.artist = "Vírtuz";
                this.duration = 143;
                break;

            case 4:
                this.title = "Forever Young";
                this.artist = "Euro Beat";
                this.duration = 377;
                break;

            case 5:
                this.title = "Gurenge";
                this.artist = "Lisa";
                this.duration = 143;
                break;
        }
    }


    public String getTittle() {
        return title;
    }
    public void setTittle(String tittle) {
        this.title = tittle;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }


    // ================================ MÉTODOS ADICIONAIS ================================
    @Override
    public String toString(){
        return title + "  | " + artist + " | \u001B[36m" + duration + "\u001B[0m";
    }
}
