package capybarawest.inc;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Simulation {
    private int size;
    private int tree_persentage;
    private int bush_persentage;
    private int capybara_persentage;
    private int dog_persentage;
    private String[][] map;
    //Map<Integer, Capybara> capybaraMap = new HashMap<>();
    //Map<Integer, Dog> dogMap = new HashMap<>();
    Map<Integer, Tree> treeMap = new HashMap<>();
   // Map<Integer, Bush> bushMap = new HashMap<>();
    public Simulation(int size, int tree_persentage, int bush_persentage, int capybara_persentage, int dog_persentage){
        this.size = size;
        this.tree_persentage = tree_persentage;
        this.bush_persentage = bush_persentage;
        this.capybara_persentage = capybara_persentage;
        this.dog_persentage = dog_persentage;
    }
    public void map_initialization(){
        map = new String[size][size];
        int tree_counter = 0, bush_counter = 0, capybara_counter = 0, dog_counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    map[0][j] = "W";
                }
                else {
                    map[i][j] = "0";
                }
            }
        }
        public void setRandomlyObject(int amount_of_objects){
            Random rand = new Random();
            int randOX;
            int randOY;
            for(int i = 0; i < amount_of_objects; i++){
                randOX = rand.nextInt((size*size) + 1);
                randOY = rand.nextInt((size*size) + 1);
                if (map[randOX][randOY] != "0") {
                    map[randOX][randOY] = "typ obiektu";
                }
                else{
                    while(map[randOX][randOY] != "0")
                    {
                        randOX = rand.nextInt((size*size) + 1);
                        randOY = rand.nextInt((size*size) + 1);
                        //!!!!!! trzeba wpisac cos typu i ++;
                        // скорее всего нужно будет добавить отдельный класс мапа с полями координат и с отдельным функциями
                        //чтобы рандомно расставить объекты на карте + шоу мап
                    }
                }


            }
        }
    }
    /*public void map_initialization(){
        map = new String[size][size];
        Random rand = new Random();
        int tree_counter = 0, bush_counter = 0, capybara_counter = 0, dog_counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    map[0][j] = "W";
                } else {
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
    }
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
        System.out.println("Dupaaa");
    }
   */
}
