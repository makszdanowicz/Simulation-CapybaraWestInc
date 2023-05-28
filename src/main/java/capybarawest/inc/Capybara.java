package capybarawest.inc;

public class Capybara extends Animal implements IAnimal{
    //private int attackPower = 0;

    protected double hp;
    protected int koordynata_ox;
    protected int koordynata_oy;
    protected int speed;
    protected int damage;

    public Capybara() {
       super();
    }

    public Capybara(double hp, int koordynata_ox, int koordynata_oy, int speed, int damage) {
        super(hp, koordynata_ox, koordynata_oy, speed, damage);
    }

    @Override
    public void move(int speed) {
        super.move(speed);
    }

    @Override
    public double eat(double fruit) {
        return super.eat(fruit);
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