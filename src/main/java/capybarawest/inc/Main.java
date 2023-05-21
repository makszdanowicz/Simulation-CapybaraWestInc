package capybarawest.inc;

import java.util.Scanner;

public class Main {
    private String[][] map;
    private int size;

    public Main(int size){
        this.size = size;
    }
    public void map_initialization(){
        map = new String[size][size];
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                map[i][j] = "0";
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
    public static void main(String[] args) {
        int size;
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj rozmiar mapy(liczba wierszy i liczba kolumn): ");
        size = scan.nextInt();
        Main object = new Main(size);
        object.map_initialization();
        object.print_map();
    }

}