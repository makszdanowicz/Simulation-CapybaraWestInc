package capybarawest.inc;

import java.util.Scanner;

public class Main {
    /* private String[][] map;
    private int size;

    public Main(int size){
        this.size = size;
    }
    public void map_initialization(){
        map = new String[size][size];
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(i == 0)
                {
                    map[0][j] = "W";
                }
                else{
                    map[i][j] = "0";
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
     */
    public static void main(String[] args) {
        int size;
        int tree_percentage;
        int bush_persentage;
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj rozmiar mapy(liczba wierszy i liczba kolumn): ");
        size = scan.nextInt();
        System.out.println("Podaj ilość drzew(w procentach od 0 do 100): ");
        tree_percentage = scan.nextInt();
        System.out.println("Podaj ilość krzaków(w procentach od 0 do " + (100-tree_percentage) + "): ");
        bush_persentage = scan.nextInt();
        Simulation symulacja = new Simulation(size, tree_percentage, bush_persentage);
        //Main object = new Main(size);
        symulacja.map_initialization();
        symulacja.print_map();
    }

}