package capybarawest.inc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        int size;
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj rozmiar mapy(liczba wierszy i kolum): ");
        size = scan.nextInt();
        Simulation simulation = new Simulation(size);
        simulation.map_initialization();
        simulation.print_map();
=======
        int size;
        int liczba_drzew;
        int liczba_krzaków;
        int liczba_kapibar;
        int liczba_psów;
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj rozmiar mapy(liczba wierszy i liczba kolumn): ");
        size = scan.nextInt();
        System.out.println("Podaj ilość drzew: ");
        liczba_drzew = scan.nextInt();
        System.out.println("Podaj ilość krzaków: ");
        liczba_krzaków = scan.nextInt();
        System.out.println("Podaj ilość kapibar: ");
        liczba_kapibar = scan.nextInt();
        System.out.println("Podaj ilość psów: ");
        liczba_psów = scan.nextInt();
        Simulation symulacja = new Simulation(size, liczba_drzew, liczba_krzaków, liczba_kapibar, liczba_psów);
       symulacja.map_initialization();
       symulacja.umieśćDrzewa();
       symulacja.umieśćKrzaki();
       symulacja.umieśćKapibar();
       symulacja.umieśćPsów();
        symulacja.print_map();
>>>>>>> justyna_test
    }

}