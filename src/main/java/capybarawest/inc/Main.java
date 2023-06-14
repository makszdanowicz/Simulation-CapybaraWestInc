package capybarawest.inc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rozmiar;
        int liczba_drzew;
        int liczba_krzakow;
        int liczba_kapibar;
        int liczba_psow;
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj rozmiar mapy(liczba wierszy i liczba kolumn): ");
        rozmiar = scan.nextInt();
        System.out.println("Podaj ilosc drzew(mozesz stworzyc do " + ((rozmiar-1)*(rozmiar)) + " obiektow): ");
        liczba_drzew = scan.nextInt();
        System.out.println("Podaj ilosc krzakow(mozesz stworzyc do " + ((rozmiar-1)*(rozmiar)-liczba_drzew) + " obiektow): ");
        liczba_krzakow = scan.nextInt();
        System.out.println("Podaj ilosc kapibar(mozesz stworzyc do " + ((rozmiar-1)*(rozmiar)-liczba_drzew-liczba_krzakow) + " obiektow): ");
        liczba_kapibar = scan.nextInt();
        System.out.println("Podaj ilosc psow(mozesz stworzyc do " + ((rozmiar-1)*(rozmiar)-liczba_drzew-liczba_krzakow-liczba_kapibar) + " obiektow): ");
        liczba_psow = scan.nextInt();
        Simulation symulacja = new Simulation(rozmiar, liczba_drzew, liczba_krzakow, liczba_kapibar, liczba_psow);
        symulacja.map_initialization();
        symulacja.umiescDrzewa();
        symulacja.umiescKrzaki();
        symulacja.umiescKapibar();
        symulacja.umiescPsow();
        symulacja.wyswietl_mape();
        symulacja.stworz_symulacje();
        symulacja.wyswietl_mape();
        symulacja.wypisz_wyniki();
    }
}