package capybarawest.inc;

public abstract class Animal implements IAnimal{
    protected double hp;
    protected int koordynata_ox;
    protected int koordynata_oy;
    protected int speed;
    protected int damage;

    public Animal()
    {
        this.hp = -1111111;
        this.koordynata_ox = -1111111;
        this.koordynata_oy = -1111111;
        this.speed = -1111111;
        this.damage = -1111111;
    }

    public Animal(double hp, int koordynata_ox, int koordynata_oy, int speed, int damage) {
        this.hp = hp;
        this.koordynata_ox = koordynata_ox;
        this.koordynata_oy = koordynata_oy;
        this.speed = speed;
        this.damage = damage;
    }
    @Override
    public double eat(double fruit) {
        return this.hp+fruit;
    }

    @Override
    public void move(int speed) {
    }
    @Override
    public String toString() {
        return this.hp + " " + this.koordynata_ox + " " + this.koordynata_oy + " " + this.speed + " " + this.damage + "\n";
    }
}
