package capybarawest.inc;

public class Capybara extends Animal implements IAnimal{
    //private int attackPower = 0;

    protected double hp;
    protected int koordynata_ox;
    protected int koordynata_oy;
    protected int predkosc;
    protected int damage;

    public Capybara() {
       super();
    }

    public Capybara(double hp, int koordynata_ox, int koordynata_oy, int predkosc, int damage) {
        super(hp, koordynata_ox, koordynata_oy, predkosc, damage);
    }

    @Override
    public void move(int predkosc) {
        super.move(predkosc);
    }

    @Override
    public double eat(double owoc) {
        return super.eat(owoc);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
    /*
    @Override
    public void setDamage(int damage) {
        this.attackPower = damage;
    }

    @Override
    public double getDamage() {
        return attackPower;
    }

     */