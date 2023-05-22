package capybarawest.inc;

public class Simulation {
    private int size;
    private String[][] map;
    public Simulation(int size){
        this.size = size;
    }
    public void map_initialization(){
        map = new String[size][size];
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(i == 0)
                {
                    map[0][j] = "W"; // W - to jest woda
                }
                else
                {
                    map[i][j] = "X";//X - to sa puste klatki mapy
                }
            }
        }
    }
    public void print_map(){
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                System.out.print(map[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
}
