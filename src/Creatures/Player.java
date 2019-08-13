package Creatures;
import java.io.*;
import java.lang.*;
import java.io.Serializable;

public class Player extends Characters implements Serializable
{

    private int level;
    private int HealthPack;
    private int grenades;
    private int MaxExp;
    private boolean aboveLevelTen;
    private static final long serialVersionUID = 7241;



    public Player()
    {
        super("",0,0,0);
    }
    //create players
    public Player(String name, int atk, int def, int spd)
    {
        super(name, atk , def, spd);
        HealthPack = 2;
        grenades = 3;
        level = 1;
        aboveLevelTen = false;
        MaxExp = 100;
    }
    public boolean getAboveLevelTen()
    {
      return this.aboveLevelTen;
    }
    public void setMaxExp(){
        this.MaxExp = (int)(getMaxExp() * 1.15);
    }
    public int getMaxExp(){
        return MaxExp;
    }

    public void setLevel(int new_level){
        level = new_level;
    }

    public void level_up()
    {
        setSpeed((getSpeed() + 2));
        setAttack((getAttack() + 3));
        setDefense((getDefense() + 3));
        setLevel((getLevel() + 1));
        setMaxHealth(getMaxHealth() + 35);
        reset_Health(getMaxHealth());
        setExp(getExp() - getMaxExp());
        setMaxExp();
    }
    public void setHeal()
    {
         super.setHealth(super.getHealth() + 35);
    }
    public int getLevel()
    {
        return level;
    }
    public int getHealthPack()
    {
        return HealthPack;
    }
    public void setHealthPack(int x)
    {
        HealthPack = x;
    }

    public int getGrenade(){
        return grenades;
    }

    public void setGrenades(int x)
    {
        grenades = x;
    }
    public int grenadesDamage(int dam)
    {
        setGrenades(getGrenade() - 1);
        return dam * 2;
    }

    public void save(String name, Player object, boolean status)
    {
        try {
            String filename = name + ".ser";

            FileOutputStream file = new FileOutputStream(filename, status);
            BufferedOutputStream output = new BufferedOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(output);

            out.writeObject(object);
            out.close();
        }

        catch(Exception e)
        {

        }
    }

    public  Player load(String name){

            Player Object1 = null;
        try {
            FileInputStream OldFile = new FileInputStream(name);
            ObjectInputStream in = new ObjectInputStream(OldFile);

           Object1 = (Player)in.readObject();
            in.close();
            OldFile.close();

            System.out.println("Loaded Successfully");
        }
        catch(Exception e){}
        return Object1;
    }


}
