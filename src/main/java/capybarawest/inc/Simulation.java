package capybarawest.inc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
            Capybara kapibara = new Capybara(100, x, y);
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
            Dog pies = new Dog(100, x, y);
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
                                //uciekaj(to same co move, tylko sprawdza przez dwie klatki)
                                uciekaj(a,b);
                            }
                            else if("nic gora".equals(sasiad) || "nic dol".equals(sasiad) || "nic lewo".equals(sasiad) || "nic prawo".equals(sasiad)){
                                ruch(a,b);
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
                            else if("kapibara gora".equals(sasiad)){
                                atakuj(a,b,a-1,b);
                            }
                            else if("kapibara dol".equals(sasiad)){
                                atakuj(a,b,a+1,b);
                            }
                            else if("kapibara lewo".equals(sasiad)){
                                atakuj(a,b,a,b-1);
                            }
                            else if("kapibara prawo".equals(sasiad)){
                                atakuj(a,b,a,b+1);
                            }
                            else if("nic gora".equals(sasiad) || "nic dol".equals(sasiad) || "nic lewo".equals(sasiad) || "nic prawo".equals(sasiad)){
                               ruch(a,b);
                            }
                        }
                    }
                }
                //powodz(i,j);
                powodz(i);
                //System.out.println(" ");
            }
            wyswietl_mape();
            System.out.println(" ");
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
                if(TREE.equals(map[koordynataOX_roslina][koordynataOY_roslina])){
                    plant.getDamage(5);
                }
                else if(BUSH.equals(map[koordynataOX_roslina][koordynataOY_roslina])){
                    plant.getDamage(3);
                }
                if (plant.hp <= 0) {
                    if(TREE.equals(map[koordynataOX_roslina][koordynataOY_roslina])){
                        licznik_zniszczonych_drzew++;
                    }
                    else if(BUSH.equals(map[koordynataOX_roslina][koordynataOY_roslina])){
                        licznik_zniszczonych_krzakow++;
                    }
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
                    if(TREE.equals(map[koordynataOX_roslina][koordynataOY_roslina])){
                        kapibara.eat(5);
                    }
                    else if(BUSH.equals(map[koordynataOX_roslina][koordynataOY_roslina])){
                        kapibara.eat(3);
                    }
                    break;
                }
            }
        }
        //Zmiana zdrowia dla psa
        else if(DOG.equals(map[x][y]))
        {
            for (Dog pies : dogMap.values()) {
                if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                    if(TREE.equals(map[koordynataOX_roslina][koordynataOY_roslina])){
                        pies.eat(5);
                    }
                    else if(BUSH.equals(map[koordynataOX_roslina][koordynataOY_roslina])){
                        pies.eat(3);
                    }
                    break;
                }
            }
        }
    }

    public void ruch(int x, int y) {
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
                                kapibara.move(x-1,y);
                                //kapibara.koordynata_ox = (x-1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                    else if (DOG.equals(map[x][y])) {
                        for (Dog pies : dogMap.values()) {
                            if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                                map[x-1][y] = DOG;
                                pies.move(x-1,y);
                                //pies.koordynata_ox = (x-1);
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
                        ruch(x,y);
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
                                kapibara.move(x+1,y);
                                //kapibara.koordynata_ox = (x+1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                    else if (DOG.equals(map[x][y])) {
                        for (Dog pies : dogMap.values()) {
                            if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                                map[x+1][y] = DOG;
                                pies.move(x+1,y);
                                //pies.koordynata_ox = (x+1);
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
                        ruch(x,y);
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
                                kapibara.move(x,y-1);
                                //kapibara.koordynata_oy = (y-1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                    else if (DOG.equals(map[x][y])) {
                        for (Dog pies : dogMap.values()) {
                            if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                                map[x][y-1] = DOG;
                                pies.move(x,y-1);
                                //pies.koordynata_oy = (y-1);
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
                        ruch(x,y);
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
                                kapibara.move(x,y+1);
                                //kapibara.koordynata_oy = (y+1);
                                map[x][y] = EMPTY_FIELD;
                                break;
                            }
                        }
                    }
                    else if (DOG.equals(map[x][y])) {
                        for (Dog pies : dogMap.values()) {
                            if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                                map[x][y+1] = DOG;
                                pies.move(x,y+1);
                                //pies.koordynata_oy = (y+1);
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
                        ruch(x,y);
                    }
                }
            }
        }
    }
    public void powodz(int x) {
        for (int y = 0; y < rozmiar; y++) {
            if (!(CAPYBARA.equals(map[x][y]) || DOG.equals(map[x][y]))) {
                map[x][y] = WATER;
            } else {
                if (CAPYBARA.equals(map[x][y])) {
                    map[x][y] = CAPYBARA;
                } else if (DOG.equals(map[x][y])) {
                    map[x][y] = DOG;
                }
            }
        }
    }

    public void atakuj(int x, int y, int koordynata_ox_kapibary, int koordynata_oy_kapibary ){
        for (Capybara kapibara : capybaraMap.values()) {
            if (kapibara.koordynata_ox == koordynata_ox_kapibary && kapibara.koordynata_oy == koordynata_oy_kapibary) {
                for (Dog pies : dogMap.values()) {
                    if (pies.koordynata_ox == x && pies.koordynata_oy == y) {
                        pies.setDamage(10);
                        kapibara.getDamage(pies.giveDamage());
                        break;
                    }
                }
                //kapibara.getDamage(50);
                if(kapibara.hp <= 0)
                {
                    Iterator<Map.Entry<Integer, Capybara>> iterator = capybaraMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<Integer, Capybara> entry = iterator.next();
                        if (entry.getValue().equals(kapibara)) {
                            iterator.remove();
                        }
                    }
                    licznik_atakowanych_kapibar++;
                    map[koordynata_ox_kapibary][koordynata_oy_kapibary] = ".";
                }
                break;
            }
        }
    }

    public void uciekaj(int x, int y) {
        Random random = new Random();
        String[] kierunki = {"gora", "dol", "lewo", "prawo"};
        String losowy_kierunek = kierunki[random.nextInt(kierunki.length)];
        if ("gora".equals(losowy_kierunek)) {
            if (x - 2 > 0) {
                if (EMPTY_FIELD.equals(map[x - 2][y])) {
                        for (Capybara kapibara : capybaraMap.values()) {
                            if (kapibara.koordynata_ox == x && kapibara.koordynata_oy == y) {
                                map[x-2][y] = CAPYBARA;
                                kapibara.move(x-2,y);
                                map[x][y] = EMPTY_FIELD;
                                break;
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
                        uciekaj(x,y);
                    }
                }
            }
        }
        else if ("dol".equals(losowy_kierunek)) {
            if (x + 2 < (rozmiar-1)) {
                if (EMPTY_FIELD.equals(map[x+2][y])) {
                        for (Capybara kapibara : capybaraMap.values()) {
                            if (kapibara.koordynata_ox == x && kapibara.koordynata_oy == y) {
                                map[x+2][y] = CAPYBARA;
                                kapibara.move(x+2,y);
                                map[x][y] = EMPTY_FIELD;
                                break;
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
                        uciekaj(x,y);
                    }
                }
            }
        }
        else if ("lewo".equals(losowy_kierunek)) {
            if (y - 2 >= 0) {
                if (EMPTY_FIELD.equals(map[x][y-2])) {
                        for (Capybara kapibara : capybaraMap.values()) {
                            if (kapibara.koordynata_ox == x && kapibara.koordynata_oy == y) {
                                map[x][y-2] = CAPYBARA;
                                kapibara.move(x,y-2);
                                map[x][y] = EMPTY_FIELD;
                                break;
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
                        uciekaj(x,y);
                    }
                }
            }
        }
        else if ("prawo".equals(losowy_kierunek)) {
            if (y + 2 <= (rozmiar-1)) {
                if (EMPTY_FIELD.equals(map[x][y+2])) {
                        for (Capybara kapibara : capybaraMap.values()) {
                            if (kapibara.koordynata_ox == x && kapibara.koordynata_oy == y) {
                                map[x][y+2] = CAPYBARA;
                                kapibara.move(x,y+2);
                                map[x][y] = EMPTY_FIELD;
                                break;
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
                        uciekaj(x,y);
                    }
                }
            }
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
        System.out.println("Rosliny po symulacji:");
        for(Map.Entry<Integer, Plants> entry : plantsMap.entrySet())
        {
            Integer key = entry.getKey();
            Plants value = entry.getValue();
            System.out.println(key + ": " + value.getClass().getSimpleName() + " " +value);
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

        System.out.print("Liczba zniszczonych drzew: ");
        System.out.println(licznik_zniszczonych_drzew);
        System.out.print("Liczba zniszczonych krzakow: ");
        System.out.println(licznik_zniszczonych_krzakow);
        System.out.print("Liczba zaatakowanych kapibar: ");
        System.out.println(licznik_atakowanych_kapibar);
    }

    public void zapisz_do_pliku() throws FileNotFoundException {
        File plik = new File("wyniki.txt");
        PrintWriter writer = new PrintWriter("wyniki.txt");
        //writer.print((liczba_drzew+liczba_krzakow) + " " + (licznik_zniszczonych_drzew+licznik_zniszczonych_krzakow) + " " + (liczba_kapibar+liczba_psow));//2 graf
        writer.print(liczba_drzew + " " + licznik_zniszczonych_drzew + " " + liczba_krzakow + " " + licznik_zniszczonych_krzakow + " " + liczba_kapibar + " " + licznik_atakowanych_kapibar + " " + liczba_psow);// 1graf
        writer.close();
    }
}

