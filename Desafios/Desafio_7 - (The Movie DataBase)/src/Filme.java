//import java.sql.Date;

import java.sql.Date;

public class Filme {
    private int id;
    private String title;
    private java.sql.Date release_date;
    private String overview;
    private float vote_average;
    private int vote_count;

    private String backdroPath;

    
    public Filme(String title, Date release_date, String overview, float vote_average, int vote_count) {
        this.title = title;
        this.release_date = release_date;
        this.overview = overview;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }


    public String toString(){
        return "\n\n\u001B[34mTítulo: \u001B[0m" + title + 
        "\n\u001B[34mData de Lançamento: \u001B[0m" + release_date +
        "\n\n\u001B[96mPorcentagem de Votos: \u001B[0m" + vote_average +
        "\n\u001B[96mQuantidade de Votos: \u001B[0m" + vote_count +
        "\n\n\u001B[94mSinópse: \u001B[0m" + overview;
    }


    // =========================== GETTERS ===========================
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public java.sql.Date getReleaseDate() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public float getVoteAverage() {
        return vote_average;
    }

    public int getVoteCount() {
        return vote_count;
    }

    public String getBackdroPath() {
        return backdroPath;
    }


    // =========================== GETTERS ===========================
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(java.sql.Date releaseDate) {
        this.release_date = releaseDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setVoteAverage(float voteAverage) {
        this.vote_average = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.vote_count = voteCount;
    }

    public void setBackdroPath(String backdroPath) {
        this.backdroPath = backdroPath;
    }

    
    



}

// EXEMPLO DE COMO FICA O JSON RETORNADO [OBS: ESTÁ SENDO CONSIDERADO O RETORNO PELA TRADUÇÃO PARA pt-BR]
// {"adult":false,
//"backdroPath":"/27u4kBGGOQLqizEudJAOWMkvhip.jpg",
//"genre_ids":[28,80,53],
//"id":385687,
//"original_language":"en",
//"original_title":"Fast X",
//"overview":"O fim da estrada está chegando. Velozes & Furiosos 10, é o décimo filme da franquia Velozes & Furiosos, lança os capítulos finais de uma grande saga, uma das mais famosas e populares do cinema, agora com sua terceira década e ainda mais forte. Com o mesmo elenco e personagens principais de quando começou. Ao longo de muitas missões e lutando contra todos os obstáculos impossíveis, Dom Toretto (Vin Diesel) e sua família foram mais espertos, mais furiosos e mais rápidos do que todos os inimigos em seu caminho. Agora, eles enfrentam o seu oponente mais letal: uma ameaça terrível das sombras do passado, alimentada por sede de vingança e determinada a destruir esta família.",
//"popularity":4156.935,
//"poster_path":"/wDWAA5QApz5L5BKfFaaj8HJCAQM.jpg",
//"releaseDate":"2023-05-17",
//"title":"Velozes & Furiosos 10",
//"video":false,
//"voteAverage":7.3,
//"voteCount":2346}