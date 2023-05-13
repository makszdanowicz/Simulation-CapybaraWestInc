package capybarawest.inc;

public class Capybara extends Animal implements Attack{
    private int attackPower = 0;
    public Capybara(){
        super();
    }

    public Capybara(double hp, int koordynata_ox, int koordynata_oy, int speed, int damage) {
        super(hp,koordynata_ox,koordynata_oy,speed,damage);
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
