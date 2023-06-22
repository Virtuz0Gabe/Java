import java.util.ArrayList;


public class PlaysList {

    private ArrayList<Musica> musicList;
    private String description;
    private int duration;


    public PlaysList (ArrayList<Musica> musicList, String description){
        this.musicList = musicList;
        this.description = description;
        
        int duration = 0;
        for (Musica musica : musicList){
            duration += musica.getDuration();
        }
        this.duration = duration;
    }
       


    public ArrayList<Musica> getMusicList() {
        return musicList;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }


    // public void showMusics (){
    //     for (Musica music : musicList){
    //             System.out.println(music);
    //         }
    // }

    // ================================ MÉTODOS ADICIONAIS ================================
    @Override
    public String toString(){
        return  "Descrição: \u001B[35m" + description + "\n\u001B[0mDuração: \u001B[36m" + duration/60 + " minutos e " + duration%60 + " segundos \u001B[0m\nMúsicas:\n" + musicList + "\n";
    }







}
