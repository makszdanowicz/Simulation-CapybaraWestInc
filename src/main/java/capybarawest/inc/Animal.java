package capybarawest.inc;

public abstract class Animal implements IAnimal{
    protected double hp;
    protected int koordynata_ox;
    protected int koordynata_oy;

    public Animal()
    {
        this.hp = -1111111;
        this.koordynata_ox = -1111111;
        this.koordynata_oy = -1111111;
    }

    public Animal(double hp, int koordynata_ox, int koordynata_oy) {
        this.hp = hp;
        this.koordynata_ox = koordynata_ox;
        this.koordynata_oy = koordynata_oy;
    }
    @Override
    public double eat(double owoc) {
        return this.hp = this.hp + owoc;
    }

    @Override
    public void move(int x, int y) {
        this.koordynata_ox = x;
        this.koordynata_oy = y;
    }
    @Override
    public String toString() {
        return this.hp + " " + this.koordynata_ox + " " + this.koordynata_oy + "\n";
    }
}
