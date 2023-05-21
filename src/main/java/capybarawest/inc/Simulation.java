package capybarawest.inc;

import java.util.Random;

public class Simulation {
    private int size;
    private int tree_persentage;
    private String[][] map;
    public Simulation(int size, int tree_persentage){
        this.size = size;
        this.tree_persentage = tree_persentage;
    }
    public void map_initialization(){
        map = new String[size][size];
        Random rand = new Random();
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(i == 0)
                {
                    map[0][j] = "W";
                }
                else{
                    int random = rand.nextInt(101);
                    if (random <= tree_persentage){
                        map[i][j] = "T";
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
                System.out.print(map[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
}
