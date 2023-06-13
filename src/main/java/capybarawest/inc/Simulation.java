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

    //Global
    private static int licznik_zniszczonych_drzew = 0;
    private static int licznik_zniszczonych_krzakow = 0;
    private static int licznik_atakowanych_kapibar = 0;
    private static int indeks_roslin = 0;

    private int rozmiar;
    private int liczba_drzew;
    private int liczba_krzakow;
    private int liczba_kapibar;
    private int liczba_psow;
    private String[][] map;
    Map<Integer, Plants> plantsMap = new HashMap<>();
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
        for (int k = 0; k < liczba_drzew; k++) {
            int x, y;
            do{
                x = (int) (Math.random() * (rozmiar - 1)) + 1;
                y = (int) (Math.random() * rozmiar);
            } while (TREE.equals(map[x][y]));
            map[x][y] = TREE;
            Tree tree = new Tree(50, x, y);
            indeks_roslin++;
            plantsMap.put(indeks_roslin, tree);
        }

        for (Map.Entry<Integer, Plants> entry : plantsMap.entrySet()){
            Integer key = entry.getKey();
            Plants value = entry.getValue();
            System.out.println("Drzewo nr " + key + ":" + value);
        }
    }

    public void umiescKrzaki(){
        for(int k = 0; k < liczba_krzakow; k++){
            int x,y;
            do{
                x = (int)(Math.random() * (rozmiar - 1)) + 1;
                y = (int)(Math.random() * rozmiar);
            } while (TREE.equals(map[x][y]) || BUSH.equals(map[x][y]));
            map[x][y] = BUSH;
            Bush krzak = new Bush(30, x, y);
            indeks_roslin++;
            plantsMap.put(indeks_roslin, krzak);
        }

        for (Map.Entry<Integer, Plants> entry : plantsMap.entrySet()){
            Integer key = entry.getKey();
            Plants value = entry.getValue();
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

                //Dzialanie symulacji(sprawdzanie warunkow dla kapibary lub psa)
                for (int a = 1; a < rozmiar; a++) {
                    for (int b = 0; b < rozmiar; b++) {
                        String sasiad;
                        if(CAPYBARA.equals(map[a][b])) {
                            sasiad = sprawdz_sasiadow(a, b);
                            if("roslina gora".equals(sasiad)){
                                eat(a,b,a-1,b);
                            }
                            else if("roslina dol".equals(sasiad)){
                                eat(a,b,a+1,b);
                            }
                            else if("roslina lewo".equals(sasiad)){
                                eat(a,b,a,b-1);
                            }
                            else if("roslina prawo".equals(sasiad)){
                                eat(a,b,a,b+1);
                            }
                            else if("pies gora".equals(sasiad) || "pies dol".equals(sasiad) || "pies lewo".equals(sasiad) || "pies prawo".equals(sasiad)){
                                //metoda uciekaj
                            }
                            else if("nic gora".equals(sasiad) || "nic dol".equals(sasiad) || "nic lewo".equals(sasiad) || "nic prawo".equals(sasiad)){
                                move(a,b);
                            }
                        }
                        else if(DOG.equals(map[a][b])){
                            sasiad = sprawdz_sasiadow(a,b);
                            if("roslina gora".equals(sasiad)){
                                eat(a,b,a-1,b);
                            }
                            else if("roslina dol".equals(sasiad)){
                                eat(a,b,a+1,b);
                            }
                            else if("roslina lewo".equals(sasiad)){
                                eat(a,b,a,b-1);
                            }
                            else if("roslina prawo".equals(sasiad)){
                                eat(a,b,a,b+1);
                            }
                            else if("kapibara gora".equals(sasiad) || "kapibara dol".equals(sasiad) || "kapibara lewo".equals(sasiad) || "kapibara prawo".equals(sasiad)){
                                //metoda atack
                            }
                            else if("nic gora".equals(sasiad) || "nic dol".equals(sasiad) || "nic lewo".equals(sasiad) || "nic prawo".equals(sasiad)){
                               move(a,b);
                            }
                        }
                    }
                }
            }
        }
    }
    public String sprawdz_sasiadow_gora(int x, int y){
        if((x-1)>0) {
            if (TREE.equals(map[x - 1][y]) || BUSH.equals(map[x - 1][y])) return "roslina";
            else if (DOG.equals(map[x - 1][y])) return "pies";
            else if (CAPYBARA.equals(map[x - 1][y])) return "kapibara";
            else if (EMPTY_FIELD.equals(map[x - 1][y])) return "nic";
        }
        return "NIE MOZNA SPRAWDZIC SASIEDA NA GORZE";
    }
    public String sprawdz_sasiadow_dol(int x, int y){
        if((x+1)<(rozmiar-1)) {
            if (TREE.equals(map[x + 1][y]) || BUSH.equals(map[x + 1][y])) return "roslina";
            else if (DOG.equals(map[x + 1][y])) return "pies";
            else if (CAPYBARA.equals(map[x + 1][y])) return "kapibara";
            else if (EMPTY_FIELD.equals(map[x + 1][y])) return "nic";
        }
        return "NIE MOZNA SPRAWDZIC SASIEDA NA DOLU";
    }
    public String sprawdz_sasiadow_lewo(int x, int y){
        if((y-1)>=0) {
            if (TREE.equals(map[x][y - 1]) || BUSH.equals(map[x][y - 1])) return "roslina";
            else if (DOG.equals(map[x][y - 1])) return "pies";
            else if (CAPYBARA.equals(map[x][y - 1])) return "kapibara";
            else if (EMPTY_FIELD.equals(map[x][y - 1])) return "nic";
        }
        return "NIE MOZNA SPRAWDZIC SASIEDA Z LEWEJ STRONY";
    }
    public String sprawdz_sasiadow_prawo(int x, int y){
        if((y+1)<=(rozmiar-1)) {
            if (TREE.equals(map[x][y + 1]) || BUSH.equals(map[x][y + 1])) return "roslina";
            else if (DOG.equals(map[x][y + 1])) return "pies";
            else if (CAPYBARA.equals(map[x][y + 1])) return "kapibara";
            else if (EMPTY_FIELD.equals(map[x][y + 1])) return "nic";
        }
        return "NIE MOZNA SPRAWDZIC SASIEDA Z PRAWEJ STRONY";
    }
    public String  sprawdz_sasiadow(int x, int y){
        String  gora,dol,lewo,prawo;
        gora = sprawdz_sasiadow_gora(x, y);
        dol = sprawdz_sasiadow_dol(x, y);
        lewo = sprawdz_sasiadow_lewo(x, y);
        prawo = sprawdz_sasiadow_prawo(x, y);
        if("roslina".equals(gora)) return "roslina gora";
        else if("roslina".equals(dol)) return "roslina dol";
        else if("roslina".equals(lewo)) return "roslina lewo";
        else if("roslina".equals(prawo)) return "roslina prawo";
        else if(CAPYBARA.equals(map[x][y])) {
            if ("pies".equals(gora)) return "pies gora";
            else if ("pies".equals(dol)) return "pies dol";
            else if ("pies".equals(lewo)) return "pies lewo";
            else if ("pies".equals(prawo)) return "pies prawo";
            else if ("nic".equals(gora)) return "nic gora";
            else if ("nic".equals(dol)) return "nic dol";
            else if ("nic".equals(lewo)) return "nic lewo";
            else if ("nic".equals(prawo)) return "nic prawo";
        }
        else if(DOG.equals(map[x][y])){
            if ("kapibara".equals(gora)) return "kapibara gora";
            else if ("kapibara".equals(dol)) return "kapibara dol";
            else if ("kapibara".equals(lewo)) return "kapibara lewo";
            else if ("kapibara".equals(prawo)) return "kapibara prawo";
            else if ("nic".equals(gora)) return "nic gora";
            else if ("nic".equals(dol)) return "nic dol";
            else if ("nic".equals(lewo)) return "nic lewo";
            else if ("nic".equals(prawo)) return "nic prawo";
        }
        return "COS ZEPSULO";
    }

    public void eat(int x, int y, int koordynataOX_roslina, int koordynataOY_roslina) {
        //Zmiana zdrowia dla rosliny
        for(Plants plant : plantsMap.values())
        {
            if(plant.koordynata_ox == koordynataOX_roslina && plant.koordynata_oy == koordynataOY_roslina)
            {
                //!!!!!!!!!!!!!!!!!!UUUUWAAGGGAAAAAAAAAAAAAAA TU TRZEBA ZMIENIC
                plant.getDamage();
                if (plant.hp <= 0) {
                    licznik_zniszczonych_drzew++;
                    //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                    Iterator<Map.Entry<Integer, Plants>> iterator = plantsMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<Integer, Plants> entry = iterator.next();
                        if (entry.getValue().equals(plant)) {
                            iterator.remove();
                        }
                    }
                    map[koordynataOX_roslina][koordynataOY_roslina] = ".";
                }
                break;
            }
        }
        //Zmiana zdrowia dla kapibary
        if(CAPYBARA.equals(map[x][y]))
        {
            for (Capybara kapibara : capybaraMap.values()) {
                if (kapibara.koordynata_ox == x && kapibara.koordynata_oy == y) {
                    kapibara.eat(5);//5 - bo drzewo
                    break;
                }
            }
        }
        //Zmiana zdrowia dla psa
        else if(DOG.equals(map[x][y]))
        {
            for (Dog pies : dogMap.values()) {
                if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                    pies.eat(3);
                    break;
                }
            }
        }
    }

    public void move(int x, int y) {
        Random random = new Random();
        String[] kierunki = {"gora", "dol", "lewo", "prawo"};
        String losowy_kierunek = kierunki[random.nextInt(kierunki.length)];
        if ("gora".equals(losowy_kierunek)) {
            if (x - 1 > 0) {
                if (EMPTY_FIELD.equals(map[x - 1][y])) {
                    if (CAPYBARA.equals(map[x][y])) {
                        for (Capybara kapibara : capybaraMap.values()) {
                            if (kapibara.koordynata_ox == x && kapibara.koordynata_oy == y) {
                                map[x-1][y] = CAPYBARA;
                                kapibara.koordynata_ox = (x-1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                    else if (DOG.equals(map[x][y])) {
                        for (Dog pies : dogMap.values()) {
                            if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                                map[x-1][y] = DOG;
                                pies.koordynata_ox = (x-1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                }
                else
                {
                    for(int i = 0; i < 4; i++)
                    {
                        losowy_kierunek = kierunki[random.nextInt(kierunki.length)];
                        if(!"gora".equals(losowy_kierunek)) //not equals to gora
                        {
                            break;
                        }

                    }
                    if(!"gora".equals(losowy_kierunek)){
                        move(x,y);
                    }
                }
            }
        }
        else if ("dol".equals(losowy_kierunek)) {
            if (x + 1 < (rozmiar-1)) {
                if (EMPTY_FIELD.equals(map[x+1][y])) {
                    if (CAPYBARA.equals(map[x][y])) {
                        for (Capybara kapibara : capybaraMap.values()) {
                            if (kapibara.koordynata_ox == x && kapibara.koordynata_oy == y) {
                                map[x+1][y] = CAPYBARA;
                                kapibara.koordynata_ox = (x+1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                    else if (DOG.equals(map[x][y])) {
                        for (Dog pies : dogMap.values()) {
                            if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                                map[x+1][y] = DOG;
                                pies.koordynata_ox = (x+1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                }
                else
                {
                    for(int i = 0; i < 4; i++)
                    {
                        losowy_kierunek = kierunki[random.nextInt(kierunki.length)];
                        if(!"dol".equals(losowy_kierunek)) //not equals to dol
                        {
                            break;
                        }

                    }
                    if(!"dol".equals(losowy_kierunek)){
                        move(x,y);
                    }
                }
            }
        }
        else if ("lewo".equals(losowy_kierunek)) {
            if (y - 1 >= 0) {
                if (EMPTY_FIELD.equals(map[x][y-1])) {
                    if (CAPYBARA.equals(map[x][y])) {
                        for (Capybara kapibara : capybaraMap.values()) {
                            if (kapibara.koordynata_ox == x && kapibara.koordynata_oy == y) {
                                map[x][y-1] = CAPYBARA;
                                kapibara.koordynata_oy = (y-1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                    else if (DOG.equals(map[x][y])) {
                        for (Dog pies : dogMap.values()) {
                            if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                                map[x][y-1] = DOG;
                                pies.koordynata_oy = (y-1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                }
                else
                {
                    for(int i = 0; i < 4; i++)
                    {
                        losowy_kierunek = kierunki[random.nextInt(kierunki.length)];
                        if(!"lewo".equals(losowy_kierunek)) //not equals to dol
                        {
                            break;
                        }

                    }
                    if(!"lewo".equals(losowy_kierunek)){
                        move(x,y);
                    }
                }
            }
        }
        else if ("prawo".equals(losowy_kierunek)) {
            if (y + 1 <= (rozmiar-1)) {
                if (EMPTY_FIELD.equals(map[x][y+1])) {
                    if (CAPYBARA.equals(map[x][y])) {
                        for (Capybara kapibara : capybaraMap.values()) {
                            if (kapibara.koordynata_ox == x && kapibara.koordynata_oy == y) {
                                map[x][y+1] = CAPYBARA;
                                kapibara.koordynata_oy = (y+1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                    else if (DOG.equals(map[x][y])) {
                        for (Dog pies : dogMap.values()) {
                            if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                                map[x][y+1] = DOG;
                                pies.koordynata_oy = (y+1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                }
                else
                {
                    for(int i = 0; i < 4; i++)
                    {
                        losowy_kierunek = kierunki[random.nextInt(kierunki.length)];
                        if(!"prawo".equals(losowy_kierunek)) //not equals to dol
                        {
                            break;
                        }

                    }
                    if(!"prawo".equals(losowy_kierunek)){
                        move(x,y);
                    }
                }
            }
        }
    }
    /*public int sprawdz_sasiadow(int x, int y){
        //0-nic
        //1-drzewo lub krzak
        //2-pies
        //3-kapibara
            if((x-1)>0){
                if(TREE.equals(map[x-1][y]) || BUSH.equals(map[x-1][y])) return 1;
                //else if(DOG.equals(map[x-1][y])) return 2;
                //else if(CAPYBARA.equals(map[x-1][y])) return 3;
                //else if(EMPTY_FIELD.equals(map[x-1][y])) return 0;
            }
            else if((x+1)<(rozmiar-1)){
                if(TREE.equals(map[x+1][y]) || BUSH.equals(map[x+1][y])) return 1;
                //else if(DOG.equals(map[x+1][y])) return 2;
                //else if(CAPYBARA.equals(map[x+1][y])) return 3;
                //else if(EMPTY_FIELD.equals(map[x+1][y])) return 0;
            }
            else if((y-1)>=0){
                if(TREE.equals(map[x][y-1]) || BUSH.equals(map[x][y-1])) return 1;
                //else if(DOG.equals(map[x][y-1])) return 2;
                //else if(CAPYBARA.equals(map[x][y-1])) return 3;
                //else if(EMPTY_FIELD.equals(map[x][y-1])) return 0;
            }
            else if((y+1)<(rozmiar-1)){
                if(TREE.equals(map[x][y+1]) || BUSH.equals(map[x][y+1])) return 1;
                //else if(DOG.equals(map[x][y+1])) return 2;
                //else if(CAPYBARA.equals(map[x][y+1])) return 3;
                //else if(EMPTY_FIELD.equals(map[x][y+1])) return 0;
            }

        return -1111111111;
    }
    */

/*
    public void stworz_symulacje() {
        //Przechodzenie na kolejne etapy symulacji
        for (int i = 1; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {

                //Dzialanie symulacji(sprawdzanie warunkow)
                for (int a = 1; a < rozmiar; a++) {
                    for (int b = 0; b < rozmiar; b++) {


                        //SPRAWDZAMY CZY JEST ROSLINA KOLO ZWIERZA


                        //Sprawdzenie dla kapibar
                        if (Objects.equals(map[a][b], "C")) {
                            //Czy jest roslina na gorze
                            if (a - 1 > 0) {
                                if (Objects.equals(map[a - 1][b], "T")) {
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
                                                licznik_zniszczonych_drzew++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Tree> entry = iterator.next();
                                                    if (entry.getValue().equals(drzewo)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a - 1][b] = ".";
                                            }
                                            break;
                                        }
                                    }
                                } else if (Objects.equals(map[a - 1][b], "B")) {
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
                                                licznik_zniszczonych_krzakow++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Bush> entry = iterator.next();
                                                    if (entry.getValue().equals(krzak)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a - 1][b] = ".";
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            //Czy jest roslina na dole
                            else if (a < rozmiar - 1) {
                                if (Objects.equals(map[a + 1][b], "T")) {
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
                                                licznik_zniszczonych_drzew++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Tree> entry = iterator.next();
                                                    if (entry.getValue().equals(drzewo)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a + 1][b] = ".";
                                            }
                                            break;
                                        }
                                    }
                                } else if (Objects.equals(map[a + 1][b], "B")) {
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
                                                licznik_zniszczonych_krzakow++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Bush> entry = iterator.next();
                                                    if (entry.getValue().equals(krzak)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a + 1][b] = ".";
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            //Czy jest roslina z lewej strony
                            else if (b - 1 >= 0) {
                                if (Objects.equals(map[a][b - 1], "T")) {
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
                                                licznik_zniszczonych_drzew++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Tree> entry = iterator.next();
                                                    if (entry.getValue().equals(drzewo)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a][b - 1] = ".";
                                            }
                                            break;
                                        }
                                    }
                                } else if (Objects.equals(map[a][b - 1], "B")) {
                                    for (Capybara kapibara : capybaraMap.values()) {
                                        if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                            kapibara.eat(3);
                                            break;
                                        }
                                    }
                                    for (Bush krzak : bushMap.values()) {
                                        if (krzak.koordynata_ox == a && krzak.koordynata_oy == b - 1) {
                                            krzak.getDamage(3);
                                            if (krzak.hp <= 0) {
                                                licznik_zniszczonych_krzakow++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Bush> entry = iterator.next();
                                                    if (entry.getValue().equals(krzak)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a][b - 1] = ".";
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            //Czy jest roslina z prawej strony
                            else if (b + 1 < rozmiar) {
                                if (Objects.equals(map[a][b + 1], "T")) {
                                    for (Capybara kapibara : capybaraMap.values()) {
                                        if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                            kapibara.eat(5);//5 - bo drzewo
                                            break;
                                        }
                                    }
                                    for (Tree drzewo : treeMap.values()) {
                                        if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == b + 1) {
                                            drzewo.getDamage(5);
                                            if (drzewo.hp <= 0) {
                                                licznik_zniszczonych_drzew++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Tree> entry = iterator.next();
                                                    if (entry.getValue().equals(drzewo)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a][b + 1] = ".";
                                            }
                                            break;
                                        }
                                    }
                                } else if (Objects.equals(map[a][b + 1], "B")) {
                                    for (Capybara kapibara : capybaraMap.values()) {
                                        if (kapibara.koordynata_ox == a && kapibara.koordynata_oy == b) {
                                            kapibara.eat(3);
                                            break;
                                        }
                                    }
                                    for (Bush krzak : bushMap.values()) {
                                        if (krzak.koordynata_ox == a && krzak.koordynata_oy == b + 1) {
                                            krzak.getDamage(3);
                                            if (krzak.hp <= 0) {
                                                licznik_zniszczonych_krzakow++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Bush> entry = iterator.next();
                                                    if (entry.getValue().equals(krzak)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a][b + 1] = ".";
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }


                        //Sprawdzenie dla psow
                        else if (Objects.equals(map[a][b], "D")) {
                            //Czy jest roslina na gorze
                            if (a - 1 > 0) {
                                if (Objects.equals(map[a - 1][b], "T")) {
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
                                                licznik_zniszczonych_drzew++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Tree> entry = iterator.next();
                                                    if (entry.getValue().equals(drzewo)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a - 1][b] = ".";
                                            }
                                            break;
                                        }
                                    }
                                } else if (Objects.equals(map[a - 1][b], "B")) {
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
                                                licznik_zniszczonych_krzakow++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Bush> entry = iterator.next();
                                                    if (entry.getValue().equals(krzak)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a - 1][b] = ".";
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            //Czy jest roslina na dole
                            else if (a < rozmiar - 1) {
                                if (Objects.equals(map[a + 1][b], "T")) {
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
                                                licznik_zniszczonych_drzew++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Tree> entry = iterator.next();
                                                    if (entry.getValue().equals(drzewo)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a + 1][b] = ".";
                                            }
                                            break;
                                        }
                                    }
                                } else if (Objects.equals(map[a + 1][b], "B")) {
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
                                                licznik_zniszczonych_krzakow++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Bush> entry = iterator.next();
                                                    if (entry.getValue().equals(krzak)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a + 1][b] = ".";
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            //Czy jest roslina z lewej strony
                            else if (b - 1 >= 0) {
                                if (Objects.equals(map[a][b - 1], "T")) {
                                    for (Dog pies : dogMap.values()) {
                                        if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                            pies.eat(5);
                                            break;
                                        }
                                    }
                                    for (Tree drzewo : treeMap.values()) {
                                        if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == b - 1) {
                                            drzewo.getDamage(5);
                                            if (drzewo.hp <= 0) {
                                                licznik_zniszczonych_drzew++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Tree> entry = iterator.next();
                                                    if (entry.getValue().equals(drzewo)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a][b - 1] = ".";
                                            }
                                            break;
                                        }
                                    }
                                } else if (Objects.equals(map[a][b - 1], "B")) {
                                    for (Dog pies : dogMap.values()) {
                                        if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                            pies.eat(3);
                                            break;
                                        }
                                    }
                                    for (Bush krzak : bushMap.values()) {
                                        if (krzak.koordynata_ox == a && krzak.koordynata_oy == b - 1) {
                                            krzak.getDamage(3);
                                            if (krzak.hp <= 0) {
                                                licznik_zniszczonych_krzakow++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Bush> entry = iterator.next();
                                                    if (entry.getValue().equals(krzak)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a][b - 1] = ".";
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            //Czy jest roslina z prawej strony
                            else if (b + 1 < rozmiar) {
                                if (Objects.equals(map[a][b + 1], "T")) {
                                    for (Dog pies : dogMap.values()) {
                                        if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                            pies.eat(5);
                                            break;
                                        }
                                    }
                                    for (Tree drzewo : treeMap.values()) {
                                        if (drzewo.koordynata_ox == a && drzewo.koordynata_oy == b + 1) {
                                            drzewo.getDamage(5);
                                            if (drzewo.hp <= 0) {
                                                licznik_zniszczonych_drzew++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Tree>> iterator = treeMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Tree> entry = iterator.next();
                                                    if (entry.getValue().equals(drzewo)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a][b + 1] = ".";
                                            }
                                            break;
                                        }
                                    }
                                } else if (Objects.equals(map[a][b + 1], "B")) {
                                    for (Dog pies : dogMap.values()) {
                                        if (pies.koordynata_ox == a && pies.koordynata_oy == b) {
                                            pies.eat(3);
                                            break;
                                        }
                                    }
                                    for (Bush krzak : bushMap.values()) {
                                        if (krzak.koordynata_ox == a && krzak.koordynata_oy == b + 1) {
                                            krzak.getDamage(3);
                                            if (krzak.hp <= 0) {
                                                licznik_zniszczonych_krzakow++;
                                                //bezpieczne usuwanie elementow podczas iteracji bezposrednio przy pomocy iteratora
                                                Iterator<Map.Entry<Integer, Bush>> iterator = bushMap.entrySet().iterator();
                                                while (iterator.hasNext()) {
                                                    Map.Entry<Integer, Bush> entry = iterator.next();
                                                    if (entry.getValue().equals(krzak)) {
                                                        iterator.remove();
                                                    }
                                                }
                                                map[a][b + 1] = ".";
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }


                        //JESLI NIE MA ZADNYCH ROSLIN KOLO ZWIERZA, TO RUSZA SIE
                        else if (a - 1 > 0 && a + 1 < rozmiar && b + 1 < rozmiar && b - 1 > 0)
                        {
                            if (map[a - 1][b] != "T" && map[a - 1][b] != "B" &&
                                    map[a + 1][b] != "T" && map[a + 1][b] != "B" &&
                                    map[a][b - 1] != "T" && map[a][b - 1] != "B" &&
                                    map[a][b + 1] != "T" && map[a][b + 1] != "B")
                            {
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
                                        }
                                        else if (Objects.equals(map[a - 1][b], "D")) {
                                            kapibara1.getDamage(50);
                                            if(kapibara1.hp <= 0)
                                            {
                                                licznik_atakowanych_kapibar++;
                                            }
                                            Random random_kapibar = new Random();
                                            String[] kierunki_kapibar = {"gora", "dol", "lewo", "prawo"};
                                            String losowy_kierunek_kapibar = kierunki_kapibar[random.nextInt(kierunki_kapibar.length)];
                                            switch (losowy_kierunek_kapibar) {
                                                case "gora":
                                                    if (a - 2 > 0) {
                                                        if (Objects.equals(map[a - 2][b], ".")) {
                                                            map[a - 2][b] = "C";
                                                            kapibara1.koordynata_ox = a - 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "dol":
                                                    if (a + 2 < rozmiar) {
                                                        if (Objects.equals(map[a + 2][b], ".")) {
                                                            map[a + 2][b] = "C";
                                                            kapibara1.koordynata_ox = a + 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "prawo":
                                                    if (b + 2 < rozmiar) {
                                                        if (Objects.equals(map[a][b + 2], ".")) {
                                                            map[a][b + 2] = "C";
                                                            kapibara1.koordynata_oy = b + 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "lewo":
                                                    if (b - 2 > 0) {
                                                        if (Objects.equals(map[a][b - 2], ".")) {
                                                            map[a][b - 2] = "C";
                                                            kapibara1.koordynata_oy = b - 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                            }
                                        }
                                    }
                                    else if (Objects.equals(losowy_kierunek, "dol")) {
                                        if (Objects.equals(map[a + 1][b], ".")) {
                                            map[a + 1][b] = "C";
                                            kapibara1.koordynata_ox = a + 1;
                                            map[a][b] = ".";
                                        }
                                        else if (Objects.equals(map[a + 1][b], "D")) {
                                            kapibara1.getDamage(50);
                                            if(kapibara1.hp <= 0)
                                            {
                                                licznik_atakowanych_kapibar++;
                                            }
                                            Random random_kapibar = new Random();
                                            String[] kierunki_kapibar = {"gora", "dol", "lewo", "prawo"};
                                            String losowy_kierunek_kapibar = kierunki_kapibar[random.nextInt(kierunki_kapibar.length)];
                                            switch (losowy_kierunek_kapibar) {
                                                case "gora":
                                                    if (a - 2 > 0) {
                                                        if (Objects.equals(map[a - 2][b], ".")) {
                                                            map[a - 2][b] = "C";
                                                            kapibara1.koordynata_ox = a - 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "dol":
                                                    if (a + 2 < rozmiar) {
                                                        if (Objects.equals(map[a + 2][b], ".")) {
                                                            map[a + 2][b] = "C";
                                                            kapibara1.koordynata_ox = a + 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "prawo":
                                                    if (b + 2 < rozmiar) {
                                                        if (Objects.equals(map[a][b + 2], ".")) {
                                                            map[a][b + 2] = "C";
                                                            kapibara1.koordynata_oy = b + 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "lewo":
                                                    if (b - 2 > 0) {
                                                        if (Objects.equals(map[a][b - 2], ".")) {
                                                            map[a][b - 2] = "C";
                                                            kapibara1.koordynata_oy = b - 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                            }
                                        }
                                    }
                                    else if (Objects.equals(losowy_kierunek, "prawo")) {
                                        if (Objects.equals(map[a][b + 1], ".")) {
                                            map[a][b + 1] = "C";
                                            kapibara1.koordynata_oy = b + 1;
                                            map[a][b] = ".";
                                        }
                                        else if (Objects.equals(map[a][b + 1], "D")) {
                                            kapibara1.getDamage(50);
                                            if(kapibara1.hp <= 0)
                                            {
                                                licznik_atakowanych_kapibar++;
                                            }
                                            Random random_kapibar = new Random();
                                            String[] kierunki_kapibar = {"gora", "dol", "lewo", "prawo"};
                                            String losowy_kierunek_kapibar = kierunki_kapibar[random.nextInt(kierunki_kapibar.length)];
                                            switch (losowy_kierunek_kapibar) {
                                                case "gora":
                                                    if (a - 2 > 0) {
                                                        if (Objects.equals(map[a - 2][b], ".")) {
                                                            map[a - 2][b] = "C";
                                                            kapibara1.koordynata_ox = a - 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "dol":
                                                    if (a + 2 < rozmiar) {
                                                        if (Objects.equals(map[a + 2][b], ".")) {
                                                            map[a + 2][b] = "C";
                                                            kapibara1.koordynata_ox = a + 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "prawo":
                                                    if (b + 2 < rozmiar) {
                                                        if (Objects.equals(map[a][b + 2], ".")) {
                                                            map[a][b + 2] = "C";
                                                            kapibara1.koordynata_oy = b + 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "lewo":
                                                    if (b - 2 > 0) {
                                                        if (Objects.equals(map[a][b - 2], ".")) {
                                                            map[a][b - 2] = "C";
                                                            kapibara1.koordynata_oy = b - 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                            }
                                        }
                                    }
                                    else if (Objects.equals(losowy_kierunek, "lewo")) {
                                        if (Objects.equals(map[a][b - 1], ".")) {
                                            map[a][b - 1] = "C";
                                            kapibara1.koordynata_oy = b - 1;
                                            map[a][b] = ".";
                                        } else if (Objects.equals(map[a][b - 1], "D")) {
                                            kapibara1.getDamage(50);
                                            if(kapibara1.hp <= 0)
                                            {
                                                licznik_atakowanych_kapibar++;
                                            }
                                            Random random_kapibar = new Random();
                                            String[] kierunki_kapibar = {"gora", "dol", "lewo", "prawo"};
                                            String losowy_kierunek_kapibar = kierunki_kapibar[random.nextInt(kierunki_kapibar.length)];
                                            switch (losowy_kierunek_kapibar) {
                                                case "gora":
                                                    if (a - 2 > 0) {
                                                        if (Objects.equals(map[a - 2][b], ".")) {
                                                            map[a - 2][b] = "C";
                                                            kapibara1.koordynata_ox = a - 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "dol":
                                                    if (a + 2 < rozmiar) {
                                                        if (Objects.equals(map[a + 2][b], ".")) {
                                                            map[a + 2][b] = "C";
                                                            kapibara1.koordynata_ox = a + 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "prawo":
                                                    if (b + 2 < rozmiar) {
                                                        if (Objects.equals(map[a][b + 2], ".")) {
                                                            map[a][b + 2] = "C";
                                                            kapibara1.koordynata_oy = b + 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                                case "lewo":
                                                    if (b - 2 > 0) {
                                                        if (Objects.equals(map[a][b - 2], ".")) {
                                                            map[a][b - 2] = "C";
                                                            kapibara1.koordynata_oy = b - 2;
                                                            map[a][b] = ".";
                                                        }
                                                    }
                                                    break;
                                            }
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
                                        }
                                    }
                                    else if (Objects.equals(losowy_kierunek, "dol")) {
                                        if (Objects.equals(map[a + 1][b], ".")) {
                                            map[a + 1][b] = "D";
                                            pies1.koordynata_ox = a + 1;
                                            map[a][b] = ".";
                                        }
                                    }
                                    else if (Objects.equals(losowy_kierunek, "prawo")) {
                                        if (Objects.equals(map[a][b + 1], ".")) {
                                            map[a][b + 1] = "D";
                                            pies1.koordynata_oy = b + 1;
                                            map[a][b] = ".";
                                        }
                                    }
                                    else if (Objects.equals(losowy_kierunek, "lewo")) {
                                        if (Objects.equals(map[a][b - 1], ".")) {
                                            map[a][b - 1] = "D";
                                            pies1.koordynata_oy = b - 1;
                                            map[a][b] = ".";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //Koniec sprawdzania warunkow dla psow i kapibar

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
                    }
                    else if (map[k][n] == "T") {
                        System.out.print(ColorClass.GREEN_BOLD + map[k][n] + " ");
                    }
                    else if (map[k][n] == "B") {
                        System.out.print(ColorClass.GREENBUSH_BOLD + map[k][n] + " ");
                    }
                    else if (map[k][n] == "C") {
                        System.out.print(ColorClass.ORANGE_BOLD + map[k][n] + " ");
                    }
                    else if (map[k][n] == "D") {
                        System.out.print(ColorClass.WHITE_BOLD + map[k][n] + " ");
                    }
                    else {
                        System.out.print(ColorClass.BLACK_BOLD + map[k][n] + " ");
                    }
                }
                System.out.println(" ");
            }
            System.out.println(" ");
        }
    }
*/
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
        /*for(Map.Entry<Integer, Tree> entry : treeMap.entrySet()){
            Integer key = entry.getKey();
            Tree value = entry.getValue();
            System.out.println(key + ": " + value);
        }

         */

        System.out.println("Krzaki po symulacji:");
        /*for(Map.Entry<Integer, Bush> entry : bushMap.entrySet())
        {
            Integer key = entry.getKey();
            Bush value = entry.getValue();
            System.out.println(key + ": " + value);
        }

         */

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

        System.out.print("Liczba zniszczonych drzew: ");
        System.out.println(licznik_zniszczonych_drzew);
        System.out.print("Liczba zniszczonych krzakow: ");
        System.out.println(licznik_zniszczonych_krzakow);
        System.out.print("Liczba zaatakowanych kapibar: ");
        System.out.println(licznik_atakowanych_kapibar);
    }
}

