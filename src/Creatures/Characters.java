package Creatures;

import java.io.*;

public abstract class Characters implements Serializable {

    private int attack;
    private int defense;
    private int speed;
    private String species;
    private int health;
    private int maxHealth;
    private int exp;


   public Characters()
   {
        this.maxHealth = 100;
        this.attack = 0;
        this.defense = 0;
        this.speed = 0;
        this.species = "";
        this.health = this.maxHealth;
        this.exp = 0;
    }
    // create player
    public Characters(String name, int attack, int defense, int speed){
        this.health = 100;
        this.maxHealth = 100;
        this.species = name;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        reset_Health(100);
        this.exp = 0;
    }

    //monsters
    public Characters(String name, int attack, int defense, int speed, int maxHealth, int exp)
    {
        this.species = name;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.maxHealth = maxHealth;
        reset_Health(maxHealth);
        this.exp = exp;
    }

    public int getExp()
    {
        return exp;
    }
    public int getHealth()
    {
        return health;
    }
    public int getMaxHealth()
    {
        return maxHealth;
    }

    public int getSpeed(){
        return speed;
    }
    public int getAttack(){
        return attack;
    }
    public int getDefense(){
        return defense;
    }
    public String getSpecies(){
        return species;
    }
    public void setAttack(int newAttack){
        attack = newAttack;
    }
    public void setDefense(int new_defense){
        defense = new_defense;
    }

    public void setSpeed(int newspeed){
        speed = newspeed;
    }
    public void setSpecies(String name){
        species = name;
    }
    public void setHealth(int newHealth){
    health = newHealth;
    }
    public void reset_Health(int maxHealth){

        health = maxHealth;
    }
    public void setHealthAttack(int damage){
        this.health = this.health - damage;
    }

    public void setMaxHealth(int newMax){
        this.maxHealth = newMax;
    }

    public void setExp(int new_Exp){
        exp = new_Exp;
    }


}
