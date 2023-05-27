package capybarawest.inc;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Simulation {
    private int size;
    private int liczba_drzew;
    private int liczba_krzaków;
    private int liczba_kapibar;
    private int liczba_psów;
    private String[][] map;
    //Map<Integer, Capybara> capybaraMap = new HashMap<>();
    //Map<Integer, Dog> dogMap = new HashMap<>();
    Map<Integer, Tree> treeMap = new HashMap<>();

    // Map<Integer, Bush> bushMap = new HashMap<>();
    public Simulation(int size, int liczba_drzew, int liczba_krzaków, int liczba_kapibar, int liczba_psów) {
        map = new String[size][size];
        this.size = size;
        this.liczba_drzew = liczba_drzew;
        this.liczba_krzaków = liczba_krzaków;
        this.liczba_kapibar = liczba_kapibar;
        this.liczba_psów = liczba_psów;
        int max_obiekty = (size * (size - 1)); // maksymalna liczba obiektów na mapie (bez pierwszego wiersza)
        int suma_obiekty = liczba_drzew + liczba_krzaków + liczba_kapibar + liczba_psów; // suma liczby obiektów

        if (suma_obiekty > max_obiekty) {
            System.out.println("Błąd: Liczba obiektów przekracza dostępną ilość miejsc na mapie.");
            System.out.println("Spróbuj ponownie z mniejszą liczbą obiektów lub większą mapą.");
            System.exit(0);
        }
    }

    public void map_initialization() {
        map = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    map[0][j] = "W";
                } else map[i][j] = "0";
            }
        }
    }

    public void umieśćDrzewa() {
        for (int k = 0; k < liczba_drzew; k++) {
            int x, y;
            do{
                x = (int) (Math.random() * (size - 1)) + 1;
                y = (int) (Math.random() * size);
            } while (map[x][y] == "T");
            map[x][y] = "T";
        }
    }
    public void umieśćKrzaki(){
        for(int k = 0; k < liczba_krzaków; k++){
            int x,y;
            do{
                x = (int)(Math.random() * (size - 1)) + 1;
                y = (int)(Math.random() * size);
            } while (map[x][y] == "T" || map[x][y] == "B");
            map[x][y] = "B";
        }
    }
    public void umieśćKapibar(){
        for(int k = 0; k < liczba_kapibar; k++){
            int x,y;
            do{
                x = (int)(Math.random() * (size - 1)) + 1;
                y = (int)(Math.random() * size);
            } while (map[x][y] == "T" || map[x][y] == "B" || map[x][y] == "C");
            map[x][y] = "C";
        }
    }
    public void umieśćPsów(){
        for(int k = 0; k < liczba_psów; k++){
            int x,y;
            do{
                x = (int)(Math.random() * (size - 1)) + 1;
                y = (int)(Math.random() * size);
            } while (map[x][y] == "T" || map[x][y] == "B" || map[x][y] == "C" || map[x][y] == "D" );
            map[x][y] = "D";
        }
    }
    public void print_map() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == "W") {
                    System.out.print(ColorClass.BLUE_BOLD + map[i][j] + " ");
                } else if (map[i][j] == "T") {
                    System.out.print(ColorClass.GREEN_BOLD + map[i][j] + " ");
                } else if (map[i][j] == "B") {
                    System.out.print(ColorClass.GREENBUSH_BOLD + map[i][j] + " ");
                } else if (map[i][j] == "C") {
                    System.out.print(ColorClass.ORANGE_BOLD + map[i][j] + " ");
                } else if (map[i][j] == "D") {
                    System.out.print(ColorClass.WHITE_BOLD + map[i][j] + " ");
                } else {
                    System.out.print(ColorClass.BLACK_BOLD + map[i][j] + " ");
                }
            }
            System.out.println(" ");
        }
    }
}

