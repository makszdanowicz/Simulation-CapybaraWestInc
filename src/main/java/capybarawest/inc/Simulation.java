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
    public Simulation(int size, int liczba_drzew, int liczba_krzaków, int liczba_kapibar, int liczba_psów){
        this.size = size;
        this.liczba_drzew = liczba_drzew;
        this.liczba_krzaków = liczba_krzaków;
        this.liczba_kapibar = liczba_kapibar;
        this.liczba_psów = liczba_psów;
    }
    public void map_initialization(){
        map = new String[size][size];
        Random rand = new Random();
        int tree_counter = 0, bush_counter = 0, capybara_counter = 0, dog_counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    map[0][j] = "W";
                }
                else if (i == 1){
                    for (int k = 0; k < liczba_drzew; k++){
                        int x = (int)(Math.random() * size);
                        int y = (int)(Math.random() * size);
                        map[x][y] = "T";
                    }
                }
            }
        }
    }
                /*else {
                    int random = rand.nextInt(101);
                    if (random <= tree_persentage) {
                        map[i][j] = "T";
                        tree_counter++;
                    } else {
                        map[i][j] = "0";
                    }
                }
            }
        }
            for (int i = 1; i < size; i++){
                for(int j = 0; j < size; j++){
                    if(map[i][j] == "0"){
                        int random1 = rand.nextInt(101);
                        if(random1 <= bush_persentage){
                            map[i][j] = "B";
                            bush_counter++;
                        }
                        else{
                            map[i][j] = "0";
                        }
                    }
                }
            }
        for (int i = 1; i < size; i++){
            for(int j = 0; j < size; j++){
                if(map[i][j] == "0"){
                    int random2= rand.nextInt(101);
                    if(random2 <= capybara_persentage){
                        map[i][j] = "C";
                        capybara_counter++;
                    }
                    else{
                        map[i][j] = "0";
                    }
                }
            }
        }
        for (int i = 1; i < size; i++){
            for(int j = 0; j < size; j++){
                if(map[i][j] == "0"){
                    int random3= rand.nextInt(101);
                    if(random3 <= capybara_persentage){
                        map[i][j] = "D";
                        dog_counter++;
                    }
                    else{
                        map[i][j] = "0";
                    }
                }
            }
        }
        System.out.println("Wygenerowane: ilosc drzew - " + tree_counter);
        System.out.println("ilosc krzakow - " + bush_counter);
        System.out.println("ilosc kapibar - " + capybara_counter);
        System.out.println("ilosc psow - " + dog_counter);
    }*/
    public void print_map(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(map[i][j] == "W"){
                    System.out.print(ColorClass.BLUE_BOLD + map[i][j] + " ");
                }
                else if(map[i][j] == "T"){
                    System.out.print(ColorClass.GREEN_BOLD + map[i][j] + " ");
                }
                else if (map[i][j] == "B"){
                    System.out.print(ColorClass.GREENBUSH_BOLD + map[i][j] + " ");
                }
                else if (map[i][j] == "C"){
                    System.out.print(ColorClass.ORANGE_BOLD + map[i][j] + " ");
                }
                else if (map[i][j] == "D"){
                    System.out.print(ColorClass.WHITE_BOLD + map[i][j] + " ");
                }
                else{
                    System.out.print(ColorClass.BLACK_BOLD + map[i][j] + " ");
                }
                //System.out.print(map[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
}
