package capybarawest.inc;

public class Tree extends Plants{
    public Tree(){
        super();
    }
    public Tree( double hp, int koordynata_ox, int koordynata_oy){
        super(hp, koordynata_ox, koordynata_oy);
    }

    @Override
    public double getDamege(double damage) {
        return super.getDamege(damage);
    }
}
