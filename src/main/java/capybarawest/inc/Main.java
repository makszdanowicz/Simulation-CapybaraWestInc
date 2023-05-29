package capybarawest.inc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int size;
        int liczba_drzew;
        int liczba_krzaków;
        int liczba_kapibar;
        int liczba_psów;
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj rozmiar mapy(liczba wierszy i liczba kolumn): ");
        size = scan.nextInt();
        System.out.println("Podaj ilosc drzew(mozesz stworzyc do " + ((size-1)*(size)) + " obiektow): ");
        liczba_drzew = scan.nextInt();
        System.out.println("Podaj ilosc krzaków(mozesz stworzyc do " + ((size-1)*(size)-liczba_drzew) + " obiektow): ");
        liczba_krzaków = scan.nextInt();
        System.out.println("Podaj ilosc kapibar(mozesz stworzyc do " + ((size-1)*(size)-liczba_drzew-liczba_krzaków) + " obiektow): ");
        liczba_kapibar = scan.nextInt();
        System.out.println("Podaj ilosc psow(mozesz stworzyc do " + ((size-1)*(size)-liczba_drzew-liczba_krzaków-liczba_kapibar) + " obiektow): ");
        liczba_psów = scan.nextInt();
        Simulation symulacja = new Simulation(size, liczba_drzew, liczba_krzaków, liczba_kapibar, liczba_psów);
        symulacja.map_initialization();
        symulacja.umieśćDrzewa();
        symulacja.umieśćKrzaki();
        symulacja.umieśćKapibar();
        symulacja.umieśćPsów();
        symulacja.print_map();
    }

}