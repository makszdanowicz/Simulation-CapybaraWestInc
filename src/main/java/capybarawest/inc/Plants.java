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

<<<<<<< HEAD
    public double getDamage(double damage){
=======
    public double getDamege(double damage)
    {
>>>>>>> justyna_test
        return this.hp - damage;
    }
}

