package capybarawest.inc;
public class Dog extends Capybara implements Attack,IAnimal{

    private int attackPower = 0;
    public Dog(){
        super();
    }

    public Dog(double hp, int koordynata_ox, int koordynata_oy, int speed, int damage){
        super(hp, koordynata_ox, koordynata_oy, speed, damage);
    }

    @Override
    public void setDamage(int damagetoCapy) {
        this.attackPower = damagetoCapy;
    }

    @Override
    public double getDamage() {
        return attackPower;
    }

    @Override
    public double eat(double fruit) {
        return super.eat(fruit);
    }

    @Override
    public void move(int speed) {
        super.move(speed);
    }
}
