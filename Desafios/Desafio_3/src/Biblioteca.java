
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Biblioteca {
    
    private ArrayList<Musica> musicList;
    private ArrayList<PlaysList> list_OF_PlaysList;


    public Biblioteca (){
        musicList = new ArrayList<>();
        list_OF_PlaysList = new ArrayList<>();
    }



    // ================================ GETTERS ================================

    public Musica getMusic (String title){
        for (Musica music : musicList){
            if (title.equals(music.getTittle())){
                return music;
            }
        }
        System.out.println("\u001B[31mMúsica não encontrada");
        return null;    
    }

    public int getPlaysListSize (){
        return list_OF_PlaysList.size();
    }

    public int getMusicSize () {
        return musicList.size();
    }


    // ================================ MÉTODOS DE MOSTRAGEM ================================
    public void showMusicList () {
        if (musicList.size() == 0){
            System.out.println("     \u001B[31m ==== Biblioteca Vazia ====\n\u001B[0m");
        }
        for (Musica music : musicList){
            System.out.println(music);
        }
        System.out.println("==============================================");
    }

    public ArrayList<Musica> fixChoiceMusicList (ArrayList<Musica> ListaParaAdd, ArrayList<Musica> ListaQueContem){
        for (Musica music : ListaQueContem){
                if (ListaParaAdd.contains(music)){
                    ListaParaAdd.remove(music); 
                }
        }
        return ListaParaAdd;
    }
    

    public void showChoiceMusicList (ArrayList<Musica> ListaParaAdd){
        int id = 1;
        for (Musica music : ListaParaAdd){
            System.out.println("[" + id + "]" + " | " + music);
            id ++;
        }
    }

    public void showChoicePlayList (ArrayList<PlaysList> ListaParaAdd){
        int id = 1;
        for (PlaysList playsList : ListaParaAdd){
            System.out.println("[" + id + "]" + " | " + playsList);
            id ++;
        }
    }

    public void showPlayLists (){
        for (PlaysList playsList : list_OF_PlaysList){
            System.out.println(playsList);
        }
        System.out.println("==============================================");

    }



    public ArrayList<Musica> mergePlaysList (PlaysList playsList_1, PlaysList playsList_2){
        ArrayList<Musica> listaDeMusicas = new ArrayList<>(playsList_2.getMusicList());
        
        for (Musica musica : playsList_1.getMusicList()){
            if (!listaDeMusicas.contains(musica)){
                listaDeMusicas.add(musica);
            }
        }

        return listaDeMusicas;
    }



    // ================================ MÉTODOS DE ADIÇÃO ================================

    public void addMusic (){
        System.out.println("======= Criando uma nova música ======= \n");
        System.out.println("Digite o título da música: ");
        Scanner scanner = new Scanner(System.in);
        String tittle = scanner.nextLine();
        System.out.println("\nDigite o nome do Artista, caso não saiba Digite [Desconhecido]:");
        String artistName = scanner.nextLine();
        String msg = "\nDigite a duração da musica EM SEGUNDOS, lembre-se 60s = 1 minuto";
        System.out.println(msg);
        while (!ValidaScannerNumber(scanner, msg)){
            scanner.next();
        }
        int musicDuration = scanner.nextInt();
        // para transformar segundos em minutos basta dividir por 60

        Musica newMusic = new Musica(tittle, artistName, musicDuration);
        musicList.add(newMusic);
        System.out.println("Sua Música foi criada com Sucesso:");
        System.out.println("\u001B[32m" + newMusic + "\u001B[0m\n");
    }


    public void addPlayList (){
    
        Scanner scanner = new Scanner(System.in);
        String description;
        //int duration = 0;
        ArrayList<Musica> musicList_Playlist = new ArrayList<>();
        ArrayList<Musica> musicListToAdd = new ArrayList<>(musicList);

        // NOME DA PLAYLIST
        boolean choosingMusic = true;
        System.out.println("Digite o nome ou alguma descrição para a sua PlayList: ");
        description = scanner.nextLine();
       
        // MÚSICAS DA PLAYLIST
        while (choosingMusic){

            String msg = ("\nEscolha alguma música para adiconar a \u001B[36m" + description + "\u001B[0m ou digite 0 para CANCELAR/FINALIZAR");
            System.out.println(msg);
            showChoiceMusicList(musicListToAdd);
            
            String valorDigitado = scanner.nextLine();
            
            while (!ValidaScannerNumberTunado(valorDigitado, msg, 1, musicListToAdd.size())){
                valorDigitado = scanner.nextLine();
            }
            int choice = Integer.parseInt(valorDigitado) - 1;
            Musica musicToAdd = musicListToAdd.get(choice);

            musicList_Playlist.add(musicToAdd);
            System.out.println("A música \u001B[36m"+ musicToAdd.getTittle() + "\u001B[0m foi adicionada com sucesso!");
            System.out.println("PlayList atualiada: ");
            for (Musica music : musicList_Playlist){
                System.out.println(music);
            }
            musicListToAdd = fixChoiceMusicList(musicListToAdd, musicList_Playlist);
            if (musicListToAdd.size() > 0){

                msg = "\nCotinuar adicionando? \n[1] Sim\n[2] Não";
                System.out.println(msg);
                valorDigitado = scanner.nextLine();
                while (!ValidaScannerNumberTunado(valorDigitado, msg, 1, 2)){
                    valorDigitado = scanner.nextLine();
                }
                int continuar = Integer.parseInt(valorDigitado);
                if (continuar == 2){
                    choosingMusic = false;
                }
            }else{
                choosingMusic = false;
                System.out.println("\nTodas as músicas já foram adicionadas\n");
            }
        }
        // FAZ A CRIAÇÃO E ADICIONA NA LIBMASTER
        PlaysList newPlaysList = new PlaysList(musicList_Playlist, description);
        list_OF_PlaysList.add(newPlaysList);
        System.out.println("\n\u001B[32mSua PlayList foi criada com Sucesso:");
        System.out.println(newPlaysList + "\u001B[0m\n");
    }


    public void juntarPlayLists () {
        Scanner scanner = new Scanner(System.in);
        ArrayList<PlaysList> newPlayList = new ArrayList<>();
        ArrayList<PlaysList> playListToAdd = new ArrayList<>(list_OF_PlaysList);
        ArrayList<Musica> listaDeMusicas = new ArrayList<>();
        String description;
        
        if (list_OF_PlaysList.size() >= 2){
            System.out.println("Dê uma descrição para sua nova playList: ");
            description = scanner.nextLine();
            
            // ESCOLHENDO A PRIMEIRA PLAYLIST =============
            String msg = "\nSelecione as Playlist que você deseja juntar";
            System.out.println(msg);

            showChoicePlayList(playListToAdd);
            String valorDigitado = scanner.nextLine();

            while (!ValidaScannerNumberTunado(valorDigitado, msg, 1, playListToAdd.size())){
                valorDigitado = scanner.nextLine();
            }
            int choice = Integer.parseInt(valorDigitado) - 1;
            PlaysList playsList_1 = playListToAdd.get(choice);

            // ESCOLHENDO A SEGUNDA PLAYLIST =============
            System.out.println("\n=========================================");
            System.out.println("Selecione outra PlayList para adicionar:\n");
            
            playListToAdd.remove(choice);
            showChoicePlayList(playListToAdd);
            valorDigitado = scanner.nextLine();

            while (!ValidaScannerNumberTunado(valorDigitado, msg, 1, playListToAdd.size())){
                valorDigitado = scanner.nextLine();
            }
            choice = Integer.parseInt(valorDigitado) - 1;
            PlaysList playsList_2 = playListToAdd.get(choice);
            
            listaDeMusicas = mergePlaysList(playsList_1, playsList_2);
                
            PlaysList mergedPlaysList = new PlaysList(listaDeMusicas, description);
            list_OF_PlaysList.add(mergedPlaysList);
            System.out.println("\n\u001B[32mSua PlayList foi criada com Sucesso:");
            System.out.println(mergedPlaysList + "\u001B[0m\n");
            
        }else{
            System.out.println("\u001B[31mVocê não possui PlayList suficiente para juntar.\u001B[0m");
        }
    }


    // ================================ MÉTODOS DE REPRODUÇÃO ================================

    public void reproduzirMusicas () {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Musica> ListaDeMusicasParaReproduzir = new ArrayList<>(musicList);
    
        String msg = "Escolha a forma de reprodução:\n[1] Ordem original\n[2] Ordem aleatória";
        System.out.println(msg);
        String valorDigitado = scanner.nextLine();
        while (!ValidaScannerNumberTunado(valorDigitado, msg, 1, 2)){
            valorDigitado = scanner.nextLine();
        }
        int choice = Integer.parseInt(valorDigitado);
        
        if (choice == 2){
            Collections.shuffle(ListaDeMusicasParaReproduzir);
        }
        Musica musicaAtual = ListaDeMusicasParaReproduzir.get(0);
        ListaDeMusicasParaReproduzir.remove(0);
        System.out.println("\n\n  ======  Music Play   ======  ");
        System.out.println("Reproduzindo: \u001B[34m" + musicaAtual.getTittle());
        System.out.println("\u001B[0mTempo Restante: \u001B[34m" + (int) (musicaAtual.getDuration() / 60) + ":" + (int)(musicaAtual.getDuration()%60));
        System.out.println("\n  ==== < | :: | > =====  \n");
        System.out.println("\u001B[35mPróxmas Músicas\u001B[0m");
        for (Musica music : ListaDeMusicasParaReproduzir){
        System.out.println(music);
        }
        System.out.println("===================================\n");
    }


    public void reproduzirPlayList () {
        Scanner scanner = new Scanner(System.in);
        String msg = "\nQual PlayList Você deseja reproduzir: ";
        System.out.println(msg);
        showChoicePlayList(list_OF_PlaysList);
        
        String valorDigitado = scanner.nextLine();
        while (!ValidaScannerNumberTunado(valorDigitado, msg, 1, list_OF_PlaysList.size())){
            valorDigitado = scanner.nextLine();
        }
        int choice = Integer.parseInt(valorDigitado) - 1;
        PlaysList temp = list_OF_PlaysList.get(choice);
        ArrayList<Musica> ListaDeMusicasParaReproduzir = new ArrayList<>(temp.getMusicList());
       
        msg = "Escolha a forma de reprodução:\n[1] Ordem original\n[2] Ordem aleatória";
        System.out.println(msg);
        valorDigitado = scanner.nextLine();
        while (!ValidaScannerNumberTunado(valorDigitado, msg, 1, 2)){
            valorDigitado = scanner.nextLine();
        }
        choice = Integer.parseInt(valorDigitado);
        
        if (choice == 2){
            Collections.shuffle(ListaDeMusicasParaReproduzir);
        }
        int minutos = temp.getDuration() / 60;
        int segundos = temp.getDuration() % 60;
        Musica musicaAtual = ListaDeMusicasParaReproduzir.get(0);
        ListaDeMusicasParaReproduzir.remove(0);
        System.out.println("\n  ======  Music Play   ======  ");
        System.out.println("Reproduzindo: \u001B[34m" + musicaAtual.getTittle());
        System.out.println("\u001B[0mTempo Restante: \u001B[34m" + (int) (musicaAtual.getDuration() / 60) + ":" + (int)(musicaAtual.getDuration()%60));
        System.out.println("\u001B[0mDuração Total de PlayList: \u001B[34m" + minutos + " Minutos e " + segundos + " segundos\u001B[0m" );
        System.out.println("\n  ==== < | :: | > =====  \n");
        System.out.println("\u001B[35mPróxmas Músicas\u001B[0m");
        for (Musica music : ListaDeMusicasParaReproduzir){
            System.out.println(music);
        }
        System.out.println("===================================\n");
    }

    // ================================ MÉTODOS ADICIONAIS ================================
    public Boolean ValidaScannerNumber(Scanner answer, String msg){
        if (answer.hasNextInt()){
            return true;
        }else{
            System.out.println("Valor inválido, tente novamente!\n");
            System.out.println(msg);
            return false;
        }
    }

    public Boolean ValidaScannerNumberTunado(String valorDigitado, String msg, int min, int max){
        int valor;
        try {
            valor = Integer.parseInt(valorDigitado);
                if (valor >= min && valor <= max) {
                    return true;
            } else {
                System.out.println("Valor inválido, tente novamente!\n");
                System.out.println(msg);
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido, tente novamente!\n");
            System.out.println(msg);
        }
        return false;
    }

    public void musicaExtra (){
        Musica musicaEx1 = new Musica(1);
        musicList.add(musicaEx1);
        Musica musicaEx2 = new Musica(2);
        musicList.add(musicaEx2);
        Musica musicaEx3 = new Musica(3);
        musicList.add(musicaEx3);
        Musica musicaEx4 = new Musica(4);
        musicList.add(musicaEx4);
        Musica musicaEx5 = new Musica(5);
        musicList.add(musicaEx5);

    }
}
