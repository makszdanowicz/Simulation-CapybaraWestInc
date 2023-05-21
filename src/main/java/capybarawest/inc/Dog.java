package capybarawest.inc;

public class Dog extends Capybara implements Attack{
    private int attackPower = 0;
    public Dog(){
        super();
    }

    public Dog(double hp, int koordynata_ox, int koordynata_oy, int speed, int damage){
        super(hp, koordynata_ox, koordynata_oy, speed, damage);
    }

    @Override
    public void setDamage(int damage) {
        this.attackPower = damage;
    }

    @Override
    public double getDamage() {
        return attackPower;
    }
}
