package capybarawest.inc;

public class Capybara extends Animal{
    //private int attackPower = 0;
    public Capybara() {
       super();
    }

    public Capybara(double hp, int koordynata_ox, int koordynata_oy, int predkosc, int damage) {
        super(hp, koordynata_ox, koordynata_oy, predkosc, damage);
    }

    @Override
    public void move(String kierunek) {
        super.move(kierunek);
    }

    @Override
    public double eat(double owoc) {
        return super.eat(owoc);
    }

    @Override
    public int getKoordynata_x() {
        return this.koordynata_ox;
    }

    @Override
    public int getKoordynata_y() {
        return this.koordynata_oy;
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