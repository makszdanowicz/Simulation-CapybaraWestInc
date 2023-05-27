package capybarawest.inc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int size;
        int tree_percentage;
        int bush_persentage;
        int capybara_persentage;
        int dog_persentage;
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj rozmiar mapy(liczba wierszy i liczba kolumn): ");
        size = scan.nextInt();
        System.out.println("Podaj ilość drzew(w procentach od 0 do 100): ");
        tree_percentage = scan.nextInt();
        System.out.println("Podaj ilość krzaków(w procentach od 0 do " + (100-tree_percentage) + "): ");
        bush_persentage = scan.nextInt();
        System.out.println("Podaj ilość kapibar(w procentach od 0 do " + (100 - tree_percentage - bush_persentage) + "): ");
        capybara_persentage = scan.nextInt();
        System.out.println("Podaj ilość psów(w procentach od 0 do " + (100 - tree_percentage - bush_persentage - capybara_persentage) + "): ");
        dog_persentage = scan.nextInt();
        Simulation symulacja = new Simulation(size, tree_percentage, bush_persentage, capybara_persentage, dog_persentage);
        //Main object = new Main(size);
        symulacja.map_initialization();
        symulacja.print_map();
    }

}