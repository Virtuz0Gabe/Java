import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("\n =========== Music-Play Joia Legal ===========");
        System.out.println("  Título    |    Artista    |    Duração\n");
        Scanner scanner = new Scanner(System.in);
        Biblioteca libMaster = new Biblioteca();
        libMaster.musicaExtra();
        libMaster.showMusicList();
        System.out.println("\n");

        boolean appAtivo = true;

        while (appAtivo) {
            System.out.println("[0] Finalizar o Programa");
            System.out.println("[1] Adicionar Música");
            System.out.println("[2] Criar PlayList");
            System.out.println("[3] Juntar PlayList");
            System.out.println("[4] Reproduzir");

            String escolha = scanner.nextLine();
            switch (escolha){
                case "0":
                    System.exit(0);
                    break;

                case "1":
                    libMaster.addMusic();
                    break;
                
                case "2":
                    libMaster.addPlayList();
                    break;
                
                case "3":
                    libMaster.juntarPlayLists();
                    break;
                
                case "4":

                    System.out.println("\nReproduzir...\n[1] Músicas\n[2] uma Playlist");
                    escolha = scanner.nextLine();
                    switch (escolha){
                        case "1":
                            if (libMaster.getMusicSize() > 0){
                            libMaster.reproduzirMusicas();
                            }else{
                                System.out.println("\u001B[31mVocê não possui Músicas Para reproduzir\u001B[0m");
                            }
                            break;

                        case "2":
                            if (libMaster.getPlaysListSize() > 0){
                            libMaster.reproduzirPlayList();
                            }else{
                                System.out.println("\u001B[31mVocê não possui PlayList Para reproduzir\u001B[0m");
                            }
                    }
                    break;
                    

                default:
                    System.out.println("\n\u001B[31mEntrada Inválida\u001B[0m");
            }
            System.out.println("\n\nSua Biblioteca Atualizada\n");
            System.out.println(" === Lista de Músicas === ");
            System.out.println(" Título  |  Autor  | duração");
            libMaster.showMusicList();
            System.out.println("\n === Lista de PlayLists ===");
            
            libMaster.showPlayLists();
            System.out.println("\n\n");
        }

    }

    
}
