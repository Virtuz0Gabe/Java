public class Filme {
    private String id;
    private String title;
    private String release_date;
    private String overview;

    private String vote_average;
    private String vote_count;

    private String backdrop_path;


    public String toString(){
        return "\n\n\u001B[96mTítulo: \u001B[0m" + title + "\n\u001B[96mData de Lançamento: \u001B[0m" + release_date + "\n\n\u001B[94mSinópse: \u001B[0m" + overview;
    }

}


// {"adult":false,
//"backdrop_path":"/27u4kBGGOQLqizEudJAOWMkvhip.jpg",
//"genre_ids":[28,80,53],
//"id":385687,
//"original_language":"en",
//"original_title":"Fast X",
//"overview":"O fim da estrada está chegando. Velozes & Furiosos 10, é o décimo filme da franquia Velozes & Furiosos, lança os capítulos finais de uma grande saga, uma das mais famosas e populares do cinema, agora com sua terceira década e ainda mais forte. Com o mesmo elenco e personagens principais de quando começou. Ao longo de muitas missões e lutando contra todos os obstáculos impossíveis, Dom Toretto (Vin Diesel) e sua família foram mais espertos, mais furiosos e mais rápidos do que todos os inimigos em seu caminho. Agora, eles enfrentam o seu oponente mais letal: uma ameaça terrível das sombras do passado, alimentada por sede de vingança e determinada a destruir esta família.",
//"popularity":4156.935,
//"poster_path":"/wDWAA5QApz5L5BKfFaaj8HJCAQM.jpg",
//"release_date":"2023-05-17",
//"title":"Velozes & Furiosos 10",
//"video":false,
//"vote_average":7.3,
//"vote_count":2346}