package capybarawest.inc;

public abstract class Plants {
    protected double hp;
    protected int koordynata_ox;
    protected int koordynata_oy;
    public Plants(){
        this.hp = -1111111111;
        this.koordynata_ox = -11111111;
        this.koordynata_oy = -1111111111;
    }
    public Plants(double hp, int koordynata_ox, int koordynata_oy){
        this.hp = hp;
        this.koordynata_ox = koordynata_ox;
        this.koordynata_oy = koordynata_oy;
    }

    public double getDamage(double damage){
        return this.hp - damage;
    }

    @Override
    public String toString() {
        return this.hp + " " + this.koordynata_ox + " " + this.koordynata_oy + "\n";
    }
}

