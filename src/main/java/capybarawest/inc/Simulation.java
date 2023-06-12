package capybarawest.inc;
import java.util.*;

public class Simulation {
    //CONST
    private static final String WATER = "W";
    private static final String TREE = "T";
    private static final String BUSH = "B";
    private static final String DOG = "D";
    private static final String CAPYBARA = "C";
    private static final String EMPTY_FIELD = ".";

    private int rozmiar;
    private int liczba_drzew;
    private int liczba_krzakow;
    private int liczba_kapibar;
    private int liczba_psow;
    private String[][] map;
    Map<Integer, Tree> treeMap = new HashMap<>();

    Map<Integer, Bush> bushMap = new HashMap<>();
    Map<Integer, Capybara> capybaraMap = new HashMap<>();
    Map<Integer, Dog> dogMap = new HashMap<>();

    public Simulation(int rozmiar, int liczba_drzew, int liczba_krzakow, int liczba_kapibar, int liczba_psow) {
        map = new String[rozmiar][rozmiar];
        this.rozmiar = rozmiar;
        this.liczba_drzew = liczba_drzew;
        this.liczba_krzakow = liczba_krzakow;
        this.liczba_kapibar = liczba_kapibar;
        this.liczba_psow = liczba_psow;
        int max_obiekty = (rozmiar * (rozmiar - 1)); // maksymalna liczba obiektów na mapie (bez pierwszego wiersza)
        int suma_obiekty = liczba_drzew + liczba_krzakow + liczba_kapibar + liczba_psow; // suma liczby obiektów

        if (suma_obiekty > max_obiekty) {
            System.out.println("Błąd: Liczba obiektów przekracza dostępną ilość miejsc na mapie.");
            System.out.println("Spróbuj ponownie z mniejszą liczbą obiektów lub większą mapą.");
            System.exit(0);
        }
    }

    public void map_initialization() {
        map = new String[rozmiar][rozmiar];
        for (int i = 0; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {
                if (i == 0) {
                    map[0][j] = WATER;
                } else map[i][j] = EMPTY_FIELD;
            }
        }
    }

    public void umiescDrzewa() {
        int indeks_drzewa = 0;
        for (int k = 0; k < liczba_drzew; k++) {
            int x, y;
            do{
                x = (int) (Math.random() * (rozmiar - 1)) + 1;
                y = (int) (Math.random() * rozmiar);
            } while (TREE.equals(map[x][y]));
            map[x][y] = TREE;
            Tree tree = new Tree(50, x, y);
            indeks_drzewa++;
            treeMap.put(indeks_drzewa, tree);
        }
        for (Map.Entry<Integer, Tree> entry : treeMap.entrySet()){
            Integer key = entry.getKey();
            Tree value = entry.getValue();
            System.out.println("Drzewo nr " + key + ":" + value);
        }
    }

    public void umiescKrzaki(){
        int indeks_krzaka = 0;
        for(int k = 0; k < liczba_krzakow; k++){
            int x,y;
            do{
                x = (int)(Math.random() * (rozmiar - 1)) + 1;
                y = (int)(Math.random() * rozmiar);
            } while (TREE.equals(map[x][y]) || BUSH.equals(map[x][y]));
            map[x][y] = BUSH;
            Bush krzak = new Bush(30, x, y);
            indeks_krzaka++;
            bushMap.put(indeks_krzaka, krzak);
        }
        for (Map.Entry<Integer, Bush> entry : bushMap.entrySet()){
            Integer key = entry.getKey();
            Bush value = entry.getValue();
            System.out.println("Krzak nr " + key + ":" + value);
        }
    }

    public void umiescKapibar(){
        int indeks_kapibar = 0;
        for(int k = 0; k < liczba_kapibar; k++){
            int x,y;
            do{
                x = (int)(Math.random() * (rozmiar - 1)) + 1;
                y = (int)(Math.random() * rozmiar);
            } while (TREE.equals(map[x][y]) || BUSH.equals(map[x][y]) || CAPYBARA.equals(map[x][y]));
            map[x][y] = CAPYBARA;
            Capybara kapibara = new Capybara(100, x, y, 3, 5);
            indeks_kapibar++;
            capybaraMap.put(indeks_kapibar, kapibara);
        }
        for(Map.Entry<Integer, Capybara> entry : capybaraMap.entrySet()){
            Integer key = entry.getKey();
            Capybara value = entry.getValue();
            System.out.println("Kapibara nr " + key + ":" + value);
        }
    }

    public void umiescPsow(){
        int indeks_psow = 0;
        for(int k = 0; k < liczba_psow; k++){
            int x,y;
            do{
                x = (int)(Math.random() * (rozmiar - 1)) + 1;
                y = (int)(Math.random() * rozmiar);
            } while (TREE.equals(map[x][y]) || BUSH.equals(map[x][y]) || CAPYBARA.equals(map[x][y]) || DOG.equals(map[x][y]));
            map[x][y] = DOG;
            Dog pies = new Dog(100, x, y, 1, 5);
            indeks_psow++;
            dogMap.put(indeks_psow, pies);
        }
        for(Map.Entry<Integer, Dog> entry : dogMap.entrySet()){
            Integer key = entry.getKey();
            Dog value = entry.getValue();
            System.out.println("Pies nr " + key + ":" + value);
        }
    }

    public void stworz_symulacje() {
        for (int i = 1; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {

                //Dzialanie symulacji
                for (int a = 1; a < rozmiar; a++) {
                    for (int b = 0; b < rozmiar; b++) {
                        if (Objects.equals(map[a][b], "C") || Objects.equals(map[a][b], "D")) {
                            //SPRAWDZAMY CZY JEST ROSLINA KOLO ZWIERZA
                            if ((a - 1) > 0) {
                                if (Objects.equals(map[a - 1][b], "T")) {
                                    if (Objects.equals(map[a][b], "C")) {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(5);//5 - bo drzewo
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()) {
                                            if (drzewo.koordynata_ox == (a - 1) && drzewo.koordynata_oy == b) {
                                                drzewo.getDamage(5);
                                                if (drzewo.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Tree> entry = iterator.next();
                                                        if (entry.getValue().equals(drzewo)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Tree> entry: treeMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(drzewo))
                                                        {
                                                             int klucz_drzewa = entry.getKey();
                                                             System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                             treeMap.remove(klucz_drzewa);
                                                        }
                                                    }

                                                     */
                                                    //System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                    //treeMap.remove(klucz_drzewa);
                                                    map[a - 1][b] = ".";
                                                }
                                                break;//CZY TO NAM TRZEBA
                                            }
                                        }
                                    } else if (Objects.equals(map[a][b], "D")) {
                                        for (Dog pies : dogMap.values()) {
                                            if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                                pies.eat(5);
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()) {
                                            if (drzewo.koordynata_ox == (a - 1) && drzewo.koordynata_oy == b) {
                                                drzewo.getDamage(5);
                                                if (drzewo.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Tree> entry = iterator.next();
                                                        if (entry.getValue().equals(drzewo)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Tree> entry: treeMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(drzewo))
                                                        {
                                                            int klucz_drzewa = entry.getKey();
                                                            System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                            treeMap.remove(klucz_drzewa);
                                                        }
                                                    }

                                                     */
                                                    //System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                    //treeMap.remove(klucz_drzewa);
                                                    map[a - 1][b] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    }

                                } else if (Objects.equals(map[a - 1][b], "B")) {
                                    if (Objects.equals(map[a][b], "C")) {
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()) {
                                            if (krzak.koordynata_ox == (a - 1) && krzak.koordynata_oy == b) {
                                                krzak.getDamage(3);
                                                if (krzak.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Bush> entry = iterator.next();
                                                        if (entry.getValue().equals(krzak)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Bush> entry : bushMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(krzak))
                                                        {
                                                            int klucz_krzaka = entry.getKey();
                                                            System.out.println(klucz_krzaka);
                                                            bushMap.remove(klucz_krzaka);
                                                        }
                                                    }

                                                     */
                                                    //bushMap.remove(krzak); ---usunac?????
                                                    map[a - 1][b] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    } else if (Objects.equals(map[a][b], "D")) {
                                        for (Dog pies : dogMap.values()) {
                                            if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                                pies.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()) {
                                            if (krzak.koordynata_ox == (a - 1) && krzak.koordynata_oy == b) {
                                                krzak.getDamage(3);
                                                if (krzak.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Bush> entry = iterator.next();
                                                        if (entry.getValue().equals(krzak)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Bush> entry : bushMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(krzak))
                                                        {
                                                            int klucz_krzaka = entry.getKey();
                                                            System.out.println(klucz_krzaka);
                                                            bushMap.remove(klucz_krzaka);
                                                        }
                                                    }

                                                     */
                                                    //bushMap.remove(krzak); ---usunac?????
                                                    map[a - 1][b] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else if (a < rozmiar - 1) {
                                if (Objects.equals(map[a + 1][b], "T")) {
                                    if (Objects.equals(map[a][b], "C")) {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(5);//5 - bo drzewo
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()) {
                                            if (drzewo.koordynata_ox == (a + 1) && drzewo.koordynata_oy == b) {
                                                drzewo.getDamage(5);
                                                if (drzewo.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Tree> entry = iterator.next();
                                                        if (entry.getValue().equals(drzewo)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Tree> entry: treeMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(drzewo))
                                                        {
                                                            int klucz_drzewa = entry.getKey();
                                                            System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                            treeMap.remove(klucz_drzewa);
                                                        }
                                                    }

                                                     */
                                                    //System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                    //treeMap.remove(klucz_drzewa);
                                                    map[a + 1][b] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    } else if (Objects.equals(map[a][b], "D")) {
                                        for (Dog pies : dogMap.values()) {
                                            if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                                pies.eat(5);
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()) {
                                            if (drzewo.koordynata_ox == (a + 1) && drzewo.koordynata_oy == b) {
                                                drzewo.getDamage(5);
                                                if (drzewo.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Tree> entry = iterator.next();
                                                        if (entry.getValue().equals(drzewo)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Tree> entry: treeMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(drzewo))
                                                        {
                                                            int klucz_drzewa = entry.getKey();
                                                            System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                            treeMap.remove(klucz_drzewa);
                                                        }
                                                    }

                                                     */
                                                    //System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                    //treeMap.remove(klucz_drzewa);
                                                    map[a + 1][b] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                } else if (Objects.equals(map[a + 1][b], "B")) {
                                    if (Objects.equals(map[a][b], "C")) {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()) {
                                            if (krzak.koordynata_ox == (a + 1) && krzak.koordynata_oy == b) {
                                                krzak.getDamage(3);
                                                if (krzak.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Bush> entry = iterator.next();
                                                        if (entry.getValue().equals(krzak)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Bush> entry : bushMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(krzak))
                                                        {
                                                            int klucz_krzaka = entry.getKey();
                                                            System.out.println(klucz_krzaka);
                                                            bushMap.remove(klucz_krzaka);
                                                        }
                                                    }

                                                     */
                                                    //bushMap.remove(krzak); ---usunac?????
                                                    map[a + 1][b] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    } else if (Objects.equals(map[a][b], "D")) {
                                        for (Dog pies : dogMap.values()) {
                                            if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                                pies.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()) {
                                            if (krzak.koordynata_ox == (a + 1) && krzak.koordynata_oy == b) {
                                                krzak.getDamage(3);
                                                if (krzak.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Bush> entry = iterator.next();
                                                        if (entry.getValue().equals(krzak)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Bush> entry : bushMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(krzak))
                                                        {
                                                            int klucz_krzaka = entry.getKey();
                                                            System.out.println(klucz_krzaka);
                                                            bushMap.remove(klucz_krzaka);
                                                        }
                                                    }

                                                     */
                                                    //bushMap.remove(krzak); ---usunac?????
                                                    map[a + 1][b] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else if ((b - 1) >= 0) {
                                if (Objects.equals(map[a][b - 1], "T")) {
                                    if (Objects.equals(map[a][b], "C")) {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(5);//5 - bo drzewo
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()) {
                                            if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == b - 1) {
                                                drzewo.getDamage(5);
                                                if (drzewo.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Tree> entry = iterator.next();
                                                        if (entry.getValue().equals(drzewo)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Tree> entry: treeMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(drzewo))
                                                        {
                                                            int klucz_drzewa = entry.getKey();
                                                            System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                            treeMap.remove(klucz_drzewa);
                                                        }
                                                    }

                                                     */
                                                    //System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                    //treeMap.remove(klucz_drzewa);
                                                    map[a][b - 1] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    } else if (Objects.equals(map[a][b], "D")) {
                                        for (Dog pies : dogMap.values()) {
                                            if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                                pies.eat(5);
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()) {
                                            if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == (b - 1)) {
                                                drzewo.getDamage(5);
                                                if (drzewo.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Tree> entry = iterator.next();
                                                        if (entry.getValue().equals(drzewo)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Tree> entry: treeMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(drzewo))
                                                        {
                                                            int klucz_drzewa = entry.getKey();
                                                            System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                            treeMap.remove(klucz_drzewa);
                                                        }
                                                    }

                                                     */
                                                    //System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                    //treeMap.remove(klucz_drzewa);
                                                    map[a][b - 1] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                } else if (Objects.equals(map[a][b - 1], "B")) {
                                    if (Objects.equals(map[a][b], "C")) {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()) {
                                            if (krzak.koordynata_ox == a && krzak.koordynata_oy == (b - 1)) {
                                                krzak.getDamage(3);
                                                if (krzak.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Bush> entry = iterator.next();
                                                        if (entry.getValue().equals(krzak)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Bush> entry : bushMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(krzak))
                                                        {
                                                            int klucz_krzaka = entry.getKey();
                                                            System.out.println(klucz_krzaka);
                                                            bushMap.remove(klucz_krzaka);
                                                        }
                                                    }

                                                     */
                                                    //bushMap.remove(krzak); ---usunac?????
                                                    map[a][b - 1] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    } else if (Objects.equals(map[a][b], "D")) {
                                        for (Dog pies : dogMap.values()) {
                                            if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                                pies.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()) {
                                            if (krzak.koordynata_ox == a && krzak.koordynata_oy == (b - 1)) {
                                                krzak.getDamage(3);
                                                if (krzak.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Bush> entry = iterator.next();
                                                        if (entry.getValue().equals(krzak)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Bush> entry : bushMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(krzak))
                                                        {
                                                            int klucz_krzaka = entry.getKey();
                                                            System.out.println(klucz_krzaka);
                                                            bushMap.remove(klucz_krzaka);
                                                        }
                                                    }

                                                     */
                                                    //bushMap.remove(krzak); ---usunac?????
                                                    map[a][b - 1] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else if ((b + 1) < rozmiar) {
                                if (Objects.equals(map[a][b + 1], "T")) {
                                    if (map[a][b] == "C") {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(5);//5 - bo drzewo
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()) {
                                            if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == (b + 1)) {
                                                drzewo.getDamage(5);
                                                if (drzewo.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Tree> entry = iterator.next();
                                                        if (entry.getValue().equals(drzewo)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Tree> entry: treeMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(drzewo))
                                                        {
                                                            int klucz_drzewa = entry.getKey();
                                                            System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                            treeMap.remove(klucz_drzewa);
                                                        }
                                                    }

                                                     */
                                                    //System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                    //treeMap.remove(klucz_drzewa);
                                                    map[a][b + 1] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    } else if (Objects.equals(map[a][b], "D")) {
                                        for (Dog pies : dogMap.values()) {
                                            if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                                pies.eat(5);
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()) {
                                            if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == (b + 1)) {
                                                drzewo.getDamage(5);
                                                if (drzewo.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Tree> entry = iterator.next();
                                                        if (entry.getValue().equals(drzewo)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Tree> entry: treeMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(drzewo))
                                                        {
                                                            int klucz_drzewa = entry.getKey();
                                                            System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                            treeMap.remove(klucz_drzewa);
                                                        }
                                                    }
                                                     */
                                                    //System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                    //treeMap.remove(klucz_drzewa);
                                                    map[a][b + 1] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                } else if (Objects.equals(map[a][b + 1], "B")) {
                                    if (Objects.equals(map[a][b], "C")) {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()) {
                                            if (krzak.koordynata_ox == a && krzak.koordynata_oy == (b + 1)) {
                                                krzak.getDamage(3);
                                                if (krzak.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Bush> entry = iterator.next();
                                                        if (entry.getValue().equals(krzak)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Bush> entry : bushMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(krzak))
                                                        {
                                                            int klucz_krzaka = entry.getKey();
                                                            System.out.println(klucz_krzaka);
                                                            bushMap.remove(klucz_krzaka);
                                                        }
                                                    }

                                                     */
                                                    //bushMap.remove(krzak); ---usunac?????
                                                    map[a][b + 1] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    } else if (Objects.equals(map[a][b], "D")) {
                                        for (Dog pies : dogMap.values()) {
                                            if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                                pies.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()) {
                                            if (krzak.koordynata_ox == a && krzak.koordynata_oy == (b + 1)) {
                                                krzak.getDamage(3);
                                                if (krzak.hp <= 0) {
                                                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                    Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<Integer, Bush> entry = iterator.next();
                                                        if (entry.getValue().equals(krzak)) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                    /*
                                                    for(Map.Entry<Integer, Bush> entry : bushMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(krzak))
                                                        {
                                                            int klucz_krzaka = entry.getKey();
                                                            System.out.println(klucz_krzaka);
                                                            bushMap.remove(klucz_krzaka);
                                                        }
                                                    }

                                                     */
                                                    //bushMap.remove(krzak); ---usunac?????
                                                    map[a][b + 1] = ".";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            //JESLI NIE MA ZADNYCH ROSLIN KOLO ZWIERZA, TO RUSZA SIE
                            else if (map[a - 1][b] != "T" && map[a - 1][b] != "B" &&
                                    map[a + 1][b] != "T" && map[a + 1][b] != "B" &&
                                    map[a][b - 1] != "T" && map[a][b - 1] != "B" &&
                                    map[a][b + 1] != "T" && map[a][b + 1] != "B") {
                                Random random = new Random();
                                String[] kierunki = {"gora", "dol", "lewo", "prawo"};
                                String losowy_kierunek = kierunki[random.nextInt(kierunki.length)];
                                //RUCH KAPIBAR
                                if (Objects.equals(map[a][b], "C")) {
                                    Capybara kapibara1 = new Capybara(); //referencja do komurki w pamieci, przechowujacej obiekt kapibara
                                    for (Capybara kapibara : capybaraMap.values()) {
                                        if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                            kapibara1 = kapibara;
                                            break;
                                        }
                                    }
                                        if (Objects.equals(losowy_kierunek, "gora")) {
                                            if (Objects.equals(map[a - 1][b], ".")) {
                                                map[a - 1][b] = "C";
                                                kapibara1.koordynata_ox = a - 1;
                                                map[a][b] = ".";
                                            } else if (Objects.equals(map[a - 1][b], "D")) {
                                                //TIKAJ NAXYJ
                                            }
                                        } else if (Objects.equals(losowy_kierunek, "dol")) {
                                            if (Objects.equals(map[a + 1][b], ".")) {
                                                map[a + 1][b] = "C";
                                                kapibara1.koordynata_ox = a + 1;
                                                map[a][b] = ".";
                                            } else if (Objects.equals(map[a + 1][b], "D")) {
                                                //TIKAJ NAXYJ
                                            }
                                        } else if (Objects.equals(losowy_kierunek, "prawo")) {
                                            if (Objects.equals(map[a][b + 1], ".")) {
                                                map[a][b + 1] = "C";
                                                kapibara1.koordynata_oy = b + 1;
                                                map[a][b] = ".";
                                            } else if (Objects.equals(map[a][b + 1], "D")) {
                                                //TIKAJ NAXYJ
                                            }
                                        } else if (Objects.equals(losowy_kierunek, "lewo")) {
                                            if (Objects.equals(map[a][b - 1], ".")) {
                                                map[a][b - 1] = "C";
                                                kapibara1.koordynata_oy = b - 1;
                                                map[a][b] = ".";
                                            } else if (Objects.equals(map[a][b - 1], "D")) {
                                                //TIKAJ NAXYJ
                                            }
                                        }


                                }
                                //RUCH PSOW
                                else if (Objects.equals(map[a][b], "D")) {
                                    Dog pies1 = new Dog();
                                    for (Dog pies : dogMap.values()) {
                                        if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                            pies1 = pies;
                                            break;
                                        }
                                    }
                                        if (Objects.equals(losowy_kierunek, "gora")) {
                                            if (Objects.equals(map[a - 1][b], ".")) {
                                                map[a - 1][b] = "D";
                                                pies1.koordynata_ox = a - 1;
                                                map[a][b] = ".";
                                            } else if (Objects.equals(map[a - 1][b], "C")) {
                                                //IBASH NAXYJ
                                            }
                                        } else if (Objects.equals(losowy_kierunek, "dol")) {
                                            if (Objects.equals(map[a + 1][b], ".")) {
                                                map[a + 1][b] = "D";
                                                pies1.koordynata_ox = a + 1;
                                                map[a][b] = ".";
                                            } else if (Objects.equals(map[a + 1][b], "C")) {
                                                //IBASH NAXYJ
                                            }
                                        } else if (Objects.equals(losowy_kierunek, "prawo")) {
                                            if (Objects.equals(map[a][b + 1], ".")) {
                                                map[a][b + 1] = "D";
                                                pies1.koordynata_oy = b + 1;
                                                map[a][b] = ".";
                                            } else if (Objects.equals(map[a][b + 1], "C")) {
                                                //IBASH NAXYJ
                                            }
                                        } else if (Objects.equals(losowy_kierunek, "lewo")) {
                                            if (Objects.equals(map[a][b - 1], ".")) {
                                                map[a][b - 1] = "D";
                                                pies1.koordynata_oy = b - 1;
                                                map[a][b] = ".";
                                            } else if (Objects.equals(map[a][b - 1], "C")) {
                                                //IBASH NAXYJ
                                            }
                                        }
                                }
                            }
                        }
                    }
                }

                 if(!("C".equals(map[i][j]) || "D".equals(map[i][j])))
                 {
                    map[i][j] = "W";
                 }
            }
            System.out.println(" ");

            //Etapowe wyswietlenie mapy
            for (int k = 0; k < rozmiar; k++) {
                for (int n = 0; n < rozmiar; n++) {
                    if (map[k][n] == "W") {
                        System.out.print(ColorClass.BLUE_BOLD + map[k][n] + " ");
                    } else if (map[k][n] == "T") {
                        System.out.print(ColorClass.GREEN_BOLD + map[k][n] + " ");
                    } else if (map[k][n] == "B") {
                        System.out.print(ColorClass.GREENBUSH_BOLD + map[k][n] + " ");
                    } else if (map[k][n] == "C") {
                        System.out.print(ColorClass.ORANGE_BOLD + map[k][n] + " ");
                    } else if (map[k][n] == "D") {
                        System.out.print(ColorClass.WHITE_BOLD + map[k][n] + " ");
                    } else {
                        System.out.print(ColorClass.BLACK_BOLD + map[k][n] + " ");
                    }
                }
                System.out.println(" ");
            }
            System.out.println(" ");
        }
    }

    public void wyswietl_mape() {
        for (int i = 0; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {
                if (map[i][j] == "W") {
                    System.out.print(ColorClass.BLUE_BOLD + map[i][j] + " ");
                } else if (map[i][j] == "T") {
                    System.out.print(ColorClass.GREEN_BOLD + map[i][j] + " ");
                } else if (map[i][j] == "B") {
                    System.out.print(ColorClass.GREENBUSH_BOLD + map[i][j] + " ");
                } else if (map[i][j] == "C") {
                    System.out.print(ColorClass.ORANGE_BOLD + map[i][j] + " ");
                } else if (map[i][j] == "D") {
                    System.out.print(ColorClass.WHITE_BOLD + map[i][j] + " ");
                } else {
                    System.out.print(ColorClass.BLACK_BOLD + map[i][j] + " ");
                }
            }
            System.out.println(" ");
        }
    }

    public void wypisz_wyniki(){
        //Wypisanie elementow kolekcji treeMap
        System.out.println("Drzewa po symulacji:");
        for(Map.Entry<Integer, Tree> entry : treeMap.entrySet()){
            Integer key = entry.getKey();
            Tree value = entry.getValue();
            System.out.println(key + ": " + value);
        }

        System.out.println("Krzaki po symulacji:");
        for(Map.Entry<Integer, Bush> entry : bushMap.entrySet())
        {
            Integer key = entry.getKey();
            Bush value = entry.getValue();
            System.out.println(key + ": " + value);
        }

        System.out.println("Kapibary po symulacji:");
        for(Map.Entry<Integer, Capybara> entry : capybaraMap.entrySet())
        {
            Integer key = entry.getKey();
            Capybara value = entry.getValue();
            System.out.println(key + ": " + value);
        }

        System.out.println("Psy po symulacji:");
        for(Map.Entry<Integer, Dog> entry : dogMap.entrySet())
        {
            Integer key = entry.getKey();
            Dog value = entry.getValue();
            System.out.println(key + ": " + value);
        }


    }
}

