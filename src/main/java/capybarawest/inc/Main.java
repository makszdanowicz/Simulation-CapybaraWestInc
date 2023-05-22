package capybarawest.inc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello simulation!");
        int size;
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj rozmiar mapy(liczba wierszy i kolum): ");
        size = scan.nextInt();
        Simulation simulation = new Simulation(size);
        simulation.map_initialization();
        simulation.print_map();
    }

}