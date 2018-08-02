import java.io.IOException;
import java.util.*;
import Creatures.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Battle extends Characters {

    public Player[] Player1 = new Player[4];
    public Monster[] monster = new Monster[8];


    private int damage;
    private int gained;
    private boolean stronger = false;
    Random random = new Random();

    public int playerAttack(int index, int Players) {

        damage = (int) (( ((((double)(random.nextInt(Player1[Players].getAttack()) + (Player1[Players].getSpeed())))) / ((double) (monster[index].getDefense())))) * 11);
        return damage;
    }

    public void setMonster(int damage, int index) {
        monster[index].setHealthAttack(damage);
    }

    public int MonAttack(int Mindex, int Players) {

        damage = (int) (((double) (((random.nextInt(monster[Mindex].getAttack()) + ((monster[Mindex].getSpeed()))))) / ((double)(Player1[Players].getDefense()))) * 9);
        return damage;
    }

    public void setPlayerAttack(int Players, int damage) {

        Player1[Players].setHealthAttack(damage);
    }


    public boolean PlayersHP(int player) {
        if (Player1[player].getHealth() < 0) {
            return true;
        }
        return false;
    }

    public int exp(int mon) {
        gained = monster[mon].getExp();
        Player1[1].setExp( Player1[1].getExp() + monster[mon].getExp());

        if (Player1[1].getExp() >= Player1[1].getMaxExp()) {
            Player1[1].level_up();
            System.out.println("LEVEL UP!!!");
            System.out.println("Current level: " + Player1[1].getLevel());
            System.out.println("Health: " + Player1[1].getMaxHealth());
            System.out.println("Attack: " + Player1[1].getAttack());
            System.out.println("Defense: " + Player1[1].getDefense());
            System.out.println("Speed: " + Player1[1].getSpeed());
        }
        return gained;
    }

    public boolean PlayerHeal(int Players) {

        if (Player1[1].getHealthPack() > 0) {
            Player1[Players].setHeal();
            Player1[1].setHealthPack(Player1[1].getHealthPack() - 1);

            if(Player1[1].getHealth() > Player1[1].getMaxHealth()){
                Player1[1].setHealth(getMaxHealth());
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean enemyHP(int mInd) {
        int reload;

        if (monster[mInd].getHealth() < 0) {
            Player1[1].setHealthPack(Player1[1].getHealthPack() + 1);

            reload = random.nextInt(6);
            switch (reload) {
                case 4:
                case 5:
                    Player1[1].setGrenades(Player1[1].getGrenade() + 1);
                    System.out.println("You found a grenade off the corpse.");
                    break;
                default:
                    break;
            }

            return true;
        }
        return false;
    }

    public void create(String name, int atk, int def, int spd) {
        Player1[1] = new Player(name, atk, def, spd);
    }

    public void monsterList() {
        for (int i = 0; i < 7; i++) {
            System.out.println((i + 1) + ". " + monster[i].getSpecies());
        }
        System.out.println("8. Save game");
    }

    public void createMon() {
        monster[0] = new Monster("Grunt", 4, 4, 4, 75, 35);
        monster[1] = new Monster("Jackel", 13, 10, 11, 135, 45);
        monster[2] = new Monster("Elite Scott", 20, 15, 10, 250, 75);
        monster[3] = new Monster("Elite Ranger", 32, 21, 16, 325, 80);
        monster[4] = new Monster("Brute", 25, 30, 10, 400, 90);
        monster[5] = new Monster("Elite Sentinal", 37, 25, 20, 375, 105);
        monster[6] = new Monster("Arbitor", 67 , 47, 39, 600, 185);
        monster[7] = new Monster();
    }
    public void Stronger(boolean levelStatus){
        if(Player1[1].getLevel() == 12 && !levelStatus && !stronger ) {

            for (int i = 0; i < monster.length - 1; i++) {
                monster[i].setAttack(monster[i].getAttack() * 4);
                monster[i].setDefense(monster[i].getDefense() * 3);
                monster[i].setSpeed(monster[i].getSpeed() * 4);
                monster[i].setExp(monster[i].getExp() * 3);
                monster[i].setMaxHealth(monster[i].getMaxHealth() * 3);
                monster[i].setHealth(monster[i].getMaxHealth());
            }
            stronger = true;
        }
    }

    public String list(int decision) {
        String MonName;
        MonName = monster[decision].getSpecies();
        return MonName;
    }

    public void resetHP(int index) {
        monster[index].reset_Health(monster[index].getMaxHealth());
    }

    public int currentHealth(int i) {
        return Player1[i].getHealth();
    }

    public int enemyhp(int j) {
        return monster[j].getHealth();
    }

    public int getPack() {
        return Player1[1].getHealthPack();
    }

    public String getMonsters(int index) {
        return monster[index].getSpecies();
    }

    public int getGrenades() {
        return Player1[1].getGrenade();
    }

    public int grenadeCall(int dam){
         return Player1[1].grenadesDamage(dam);
    }

    public void saveGame(boolean status){
        Player1[1].save(Player1[1].getSpecies(),Player1[1],status);
    }

    public void loadGame(String file){
        Player1[1] = new Player();
        Player1[1] = Player1[1].load(file);

        System.out.println("Character: " + Player1[1].getSpecies() +"\nLevel: " + Player1[1].getLevel() +"\nHealth: " + Player1[1].getHealth() + "\n Attack: " + Player1[1].getAttack() + "\nDefense: " + Player1[1].getDefense() +
        "\nSpeed: " + Player1[1].getSpeed() + "\nHealthPack: " + Player1[1].getHealthPack() + "\nGrenades: " + Player1[1].getGrenade() + "\nEXP: " + Player1[1].getExp());
    }

    // to test to input and catch error
    public boolean testLoad(String file){
        boolean status;

        Player1[3] = new Player();
        Player1[3] = Player1[3].load(file);
        if(Player1[3].getSpecies() == null){
            Stage error = new Stage();
            Label warning = new Label("INVALID USER NAME! TRY AGAIN");
            Group layout = new Group();
            layout.getChildren().add(warning);

            Scene errorScene = new Scene(layout, 500, 500);
            error.setScene(errorScene);
            error.show();

            status = false;
        }
        else{
            status = true;
        }
        return status;
    }

    public void print_status(int index){
        System.out.println("Level: " + Player1[index].getLevel() + "\tEXP: " + Player1[index].getExp()+"/" + Player1[index].getMaxExp());
        System.out.println("Attack: " + Player1[index].getAttack() + "\tDefense: " + Player1[index].getDefense() + "\tSpeed: " + Player1[index].getSpeed());
    }
    public void death(int index){
        Player1[index].setHealth(Player1[index].getMaxHealth());
    }

    public void turned_Flood(int index){
        int flood = 7;
        System.out.println(monster[index].getSpecies() + " got infected by a Flood");
        monster[flood] = monster[index].Flood(monster[index], monster[flood]);

        }

    }
