package capybarawest.inc;

import java.util.Random;

public class Map {
    private int size;
    private String[][] map;

    /*
    public void map_initialization(){
        map = new String[size][size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    map[0][j] = "W";
                } else {
                    int random = rand.nextInt(101);
                    if (random <= tree_persentage) {
                        map[i][j] = "T";

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
                    }
                    else{
                        map[i][j] = "0";
                    }
                }
            }
        }
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
                    System.out.print(ColorClass.PURPLE_BOLD + map[i][j] + " ");
                }
                else{
                    System.out.print(ColorClass.WHITE + map[i][j] + " ");
                }
                //System.out.print(map[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

     */
}
