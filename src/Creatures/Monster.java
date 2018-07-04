package  Creatures;
public class Monster extends Characters {

    public Monster(){}
    public Monster(String name, int attack, int defense, int speed, int maxHealth, int exp){
         super(name, attack, defense,speed, maxHealth, exp);
        reset_Health(maxHealth);
    }


    public int Plasma_Damage(int dam){
        return dam *2;
    }
    public Monster Flood(Monster mon, Monster flood){

        flood = mon;
        flood.setSpecies("(Infected) " + mon.getSpecies());
        flood.setHealth((int)(mon.getHealth() * 1.40));
        flood.setExp((int)(mon.getExp() * 1.25));
        flood.setSpeed((int)(mon.getSpeed() * 1.25));
        flood.setDefense(mon.getDefense());
        flood.setAttack((int)(mon.getAttack() * 1.25));
        return flood;
    }

}
