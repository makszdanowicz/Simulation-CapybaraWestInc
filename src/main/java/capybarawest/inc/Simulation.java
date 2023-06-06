package capybarawest.inc;
import java.util.HashMap;
import java.util.Map;

public class Simulation {
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
                    map[0][j] = "W";
                } else map[i][j] = "0";
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
            } while (map[x][y] == "T");
            map[x][y] = "T";
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
            } while (map[x][y] == "T" || map[x][y] == "B");
            map[x][y] = "B";
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
            } while (map[x][y] == "T" || map[x][y] == "B" || map[x][y] == "C");
            map[x][y] = "C";
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
            } while (map[x][y] == "T" || map[x][y] == "B" || map[x][y] == "C" || map[x][y] == "D" );
            map[x][y] = "D";
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

    public void stworz_symulacje(){
        for(int i = 1; i < rozmiar; i++)
        {
            for (int j = 0; j < rozmiar; j++)
            {
                //Dzialanie symulacji
                for (int a = 1; a < rozmiar; a++)
                {
                    for (int b = 0; b < rozmiar; b++)
                    {
                        if (map[a][b] == "C" || map[a][b] == "D") //sprawdzanie warunkow dla kapibary i psa
                        {
                            if ((a-1) > 0)
                            {
                                if (map[a-1][b] == "T")
                                {
                                    if(map[a][b] == "C")
                                    {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(5);//5 - bo drzewo
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()){
                                            if (drzewo.koordynata_ox == (a-1) && drzewo.koordynata_oy == b){
                                                drzewo.getDamage(5);
                                                if(drzewo.hp <= 0)
                                                {
                                                    int klucz_drzewa = -5555;
                                                    for(Map.Entry<Integer, Tree> entry: treeMap.entrySet())
                                                    {
                                                        if(entry.getValue().equals(drzewo))
                                                        {
                                                             klucz_drzewa = entry.getKey();
                                                        }
                                                    }
                                                    System.out.println(klucz_drzewa); //!!!!TRZEBA POTEM USUNAC
                                                    treeMap.remove(klucz_drzewa);
                                                    map[a-1][b] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    else if(map[a][b] == "D")
                                    {
                                        for(Dog pies : dogMap.values())
                                        {
                                            if(pies.koordynata_ox == a && pies.koordynata_oy == b)
                                            {
                                                pies.eat(5);
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()){
                                            if (drzewo.koordynata_ox == (a-1) && drzewo.koordynata_oy == b){
                                                drzewo.getDamage(5);
                                                if(drzewo.hp <= 0)
                                                {
                                                    treeMap.remove(drzewo);
                                                    map[a-1][b] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }

                                }
                                else if(map[a-1][b] == "B")
                                {
                                    if(map[a][b] == "C") {
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()){
                                            if (krzak.koordynata_ox == (a-1) && krzak.koordynata_oy == b){
                                                krzak.getDamage(3);
                                                if(krzak.hp <= 0)
                                                {
                                                    bushMap.remove(krzak);
                                                    map[a-1][b] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    else if(map[a][b] == "D")
                                    {
                                        for(Dog pies : dogMap.values()) {
                                            if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                                pies.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()){
                                            if (krzak.koordynata_ox == (a-1) && krzak.koordynata_oy == b){
                                                krzak.getDamage(3);
                                                if(krzak.hp <= 0)
                                                {
                                                    bushMap.remove(krzak);
                                                    map[a-1][b] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            else if ((a+1) < rozmiar)
                            {
                                if (map[a+1][b] == "T")
                                {
                                    if(map[a][b] == "C")
                                    {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(5);//5 - bo drzewo
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()){
                                            if (drzewo.koordynata_ox == (a+1) && drzewo.koordynata_oy == b){
                                                drzewo.getDamage(5);
                                                if(drzewo.hp <= 0)
                                                {
                                                    treeMap.remove(drzewo);
                                                    map[a+1][b] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    else if(map[a][b] == "D")
                                    {
                                        for(Dog pies : dogMap.values())
                                        {
                                            if(pies.koordynata_ox == a && pies.koordynata_oy == b)
                                            {
                                                pies.eat(5);
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()){
                                            if (drzewo.koordynata_ox == (a+1) && drzewo.koordynata_oy == b){
                                                drzewo.getDamage(5);
                                                if(drzewo.hp <= 0)
                                                {
                                                    treeMap.remove(drzewo);
                                                    map[a+1][b] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                                else if(map[a+1][b] == "B")
                                {
                                    if(map[a][b] == "C")
                                    {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()){
                                            if (krzak.koordynata_ox == (a+1) && krzak.koordynata_oy == b){
                                                krzak.getDamage(3);
                                                if(krzak.hp <= 0)
                                                {
                                                    bushMap.remove(krzak);
                                                    map[a+1][b] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    else if(map[a][b] == "D")
                                    {
                                        for(Dog pies : dogMap.values())
                                        {
                                            if(pies.koordynata_ox == a && pies.koordynata_oy == b)
                                            {
                                                pies.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()){
                                            if (krzak.koordynata_ox == (a+1) && krzak.koordynata_oy == b){
                                                krzak.getDamage(3);
                                                if(krzak.hp <= 0)
                                                {
                                                    bushMap.remove(krzak);
                                                    map[a+1][b] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            else if ((b-1) >= 0)
                            {
                                if (map[a][b-1] == "T")
                                {
                                    if(map[a][b] == "C")
                                    {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(5);//5 - bo drzewo
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()){
                                            if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == (b-1)){
                                                drzewo.getDamage(5);
                                                if(drzewo.hp <= 0)
                                                {
                                                    treeMap.remove(drzewo);
                                                    map[a][b-1] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    else if(map[a][b] == "D")
                                    {
                                        for(Dog pies : dogMap.values())
                                        {
                                            if(pies.koordynata_ox == a && pies.koordynata_oy == b)
                                            {
                                                pies.eat(5);
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()){
                                            if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == (b-1)){
                                                drzewo.getDamage(5);
                                                if(drzewo.hp <= 0)
                                                {
                                                    treeMap.remove(drzewo);
                                                    map[a][b-1] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                                else if(map[a][b-1] == "B")
                                {
                                    if(map[a][b] == "C")
                                    {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()){
                                            if (krzak.koordynata_ox == a && krzak.koordynata_oy == (b-1)){
                                                krzak.getDamage(3);
                                                if(krzak.hp <= 0)
                                                {
                                                    bushMap.remove(krzak);
                                                    map[a][b-1] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    else if(map[a][b] == "D")
                                    {
                                        for(Dog pies : dogMap.values())
                                        {
                                            if(pies.koordynata_ox == a && pies.koordynata_oy == b)
                                            {
                                                pies.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()){
                                            if (krzak.koordynata_ox == a && krzak.koordynata_oy == (b-1)){
                                                krzak.getDamage(3);
                                                if(krzak.hp <= 0)
                                                {
                                                    bushMap.remove(krzak);
                                                    map[a][b-1] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            else if ((b+1) < rozmiar)
                            {
                                if (map[a][b+1] == "T")
                                {
                                    if(map[a][b] == "C")
                                    {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(5);//5 - bo drzewo
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()){
                                            if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == (b+1)){
                                                drzewo.getDamage(5);
                                                if(drzewo.hp <= 0)
                                                {
                                                    treeMap.remove(drzewo);
                                                    map[a][b+1] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    else if(map[a][b] == "D")
                                    {
                                        for(Dog pies : dogMap.values())
                                        {
                                            if(pies.koordynata_ox == a && pies.koordynata_oy == b)
                                            {
                                                pies.eat(5);
                                                break;
                                            }
                                        }
                                        for (Tree drzewo : treeMap.values()){
                                            if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == (b+1)){
                                                drzewo.getDamage(5);
                                                if(drzewo.hp <= 0)
                                                {
                                                    treeMap.remove(drzewo);
                                                    map[a][b+1] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                                else if(map[a][b+1] == "B")
                                {
                                    if(map[a][b] == "C")
                                    {
                                        //sprawdzanie,jesli kapibara(nasze value z kolekcji capybaraMap) ma koordynaty x = a(pozycja sprawdzonej kapibary)
                                        //y = b, to ta kapibara je owoce z drzewa
                                        for (Capybara kapibara : capybaraMap.values()) {
                                            if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                                kapibara.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()){
                                            if (krzak.koordynata_ox == a && krzak.koordynata_oy == (b+1)){
                                                krzak.getDamage(3);
                                                if(krzak.hp <= 0)
                                                {
                                                    bushMap.remove(krzak);
                                                    map[a][b+1] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    else if(map[a][b] == "D")
                                    {
                                        for(Dog pies : dogMap.values())
                                        {
                                            if(pies.koordynata_ox == a && pies.koordynata_oy == b)
                                            {
                                                pies.eat(3);
                                                break;
                                            }
                                        }
                                        for (Bush krzak : bushMap.values()){
                                            if (krzak.koordynata_ox == a && krzak.koordynata_oy == (b+1)){
                                                krzak.getDamage(3);
                                                if(krzak.hp <= 0)
                                                {
                                                    bushMap.remove(krzak);
                                                    map[a][b+1] = "0";
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                map[i][j] = "W";
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
}

