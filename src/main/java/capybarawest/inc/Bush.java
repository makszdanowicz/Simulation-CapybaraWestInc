package capybarawest.inc;

public class Bush extends Plants{
    public Bush(){
        super();
    }
    public Bush( double hp, int koordynata_ox, int koordynata_oy){
        super(hp, koordynata_ox, koordynata_oy);
    }

    @Override
    public double getDamage(double damage) {
        return super.getDamage(damage);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
