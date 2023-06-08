package capybarawest.inc;
public class Dog extends Animal implements Attack,IAnimal{

    private int moc_ataku = 0;
    public Dog(){
        super();
    }

    public Dog(double hp, int koordynata_ox, int koordynata_oy, int predkosc, int damage){
        super(hp, koordynata_ox, koordynata_oy, predkosc, damage);
    }

    @Override
    public void setDamage(int damagetoCapy) {
        this.moc_ataku = damagetoCapy;
    }

    @Override
    public double getDamage() {
        return moc_ataku;
    }

    @Override
    public double eat(double owoc) {
        return super.eat(owoc);
    }

    @Override
    public void move(String kierunek) {
        super.move(kierunek);
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
