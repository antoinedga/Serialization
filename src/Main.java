import Creatures.*;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Random rand = new Random();
        Scanner scan = new Scanner(System.in);
        String name = null;
        int PlayersAttack;
        int PlayersDefense;
        int PlayersSpeed;
        int decision;
        Battle point = new Battle();
        Monster pointer = new Monster();
        String MonsterName;
        int Monsterindex;
        int Mdecision;
        int dam;
        int flood_Chance;
        boolean status = false;
        boolean str_Enemy = false;
        String Cname = null;




        System.out.println("Welcome to this game");
            while(true) {
                try {
                    System.out.println("\nWould you like to:\nLoad old Character or Create new one?\n\n 1.Create new Player\t\t2. Load old Player's file");
                    decision = scan.nextInt();
                    if (decision == 1) {
                        status = true;
                    }
                    if (decision == 1 || decision == 2) {
                        scan.nextLine();
                        break;
                    } else {
                        System.out.println("Invalid entry");
                        scan.nextLine();
                    }
                }
            catch(Exception e){
                    System.out.println("Invalid Entry!");
                    scan.nextLine();
                }
            }

            switch (decision) {
                //Creating new Player
                case 1:
                    System.out.println("Please enter your character's name");
                    name = scan.nextLine();
                    // creating of character
                    while (true) {
                        try {
                            //enters stat
                            System.out.println("Enter the stats you would like for your characters\n you are allowed 15 points");
                            System.out.print("First add your stats for Attack: ");
                            PlayersAttack = scan.nextInt();
                            System.out.print("\nEnter Stats for Defense: ");
                            PlayersDefense = scan.nextInt();
                            System.out.print("\nEnter Stats for Speed: ");
                            PlayersSpeed = scan.nextInt();

                            if ((PlayersAttack + PlayersDefense + PlayersSpeed) == 15) {
                                break;
                            } else {
                                System.out.println("INVALID amount! Try again.");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid entry! Try again.");
                            PlayersAttack = 0;
                            PlayersDefense = 0;
                            PlayersSpeed = 0;
                            scan.nextLine();
                        }
                    }
                    point.create(name, PlayersAttack, PlayersDefense, PlayersSpeed);
                    System.out.println("\nCongratulations! You have successfully created your character:  " + name);
                    break;
                // load old Player
                case 2:
                    System.out.println("enter character's name: ");
                    Cname = scan.next();
                    Cname = Cname + ".ser";
                    point.loadGame(Cname);
                    break;
            }

        point.createMon();//creates monster list

        // battle of monster
        while (true) {
                // to make monster Stronger
                point.Stronger(str_Enemy);

                System.out.println("Select the monster you would like to fight:");
                point.monsterList();
                Monsterindex = scan.nextInt();

                //to save the game
                if (Monsterindex == 8) {
                    point.saveGame(status);
                    System.exit(1);
                }

            MonsterName = point.list((Monsterindex - 1));
            Monsterindex = Monsterindex - 1;
            System.out.println("You are fighting a(n): " + MonsterName);

            // chance to of infected monster
            flood_Chance = rand.nextInt(5);

            switch(flood_Chance){
                case 3:
                    point.turned_Flood(Monsterindex);
                    Monsterindex = 7;
                    MonsterName = point.list(Monsterindex);
                    break;
                default:
                    break;
            }

            // the start of the fight against whatever monster
            System.out.println("FIGHT!\n");
            while (true) {

                while(true) {

                    System.out.println(name + "\t" + "Current HP: " + point.currentHealth(1)+"\n" );
                    System.out.println(MonsterName + "\thealth: " + point.enemyhp(Monsterindex));

                    //menu to tell user his/her options
                    System.out.println("\nselect what to do:\n" +
                            "1.Attack\t2.Heal\t3.Grenade \t4.Exit\n5.check stat\nHealth Pack: " + point.getPack() + "\nGrenades: " + point.getGrenades());
                    decision = scan.nextInt();
                    // switch statements for all the possible selection for the Player to do
                    switch (decision) {
                        // reg attack
                        case 1:
                            dam = point.playerAttack((Monsterindex), 1);
                            point.setMonster(dam, Monsterindex);
                            System.out.println("You did: " + dam);
                            break;
                        // heal player
                        case 2:
                            boolean Stat;
                            Stat = point.PlayerHeal(1);
                            if (Stat) {
                                System.out.println("You were healed 35+");
                            } else {
                                System.out.println("You do not have Health");
                            }
                            break;
                        // grenade damage
                        case 3:
                            dam = point.playerAttack(Monsterindex, 1);
                            dam = point.grenadeCall(dam);
                            System.out.println("You threw a grenade! you did " + dam + " damage");
                            point.setMonster(dam, Monsterindex);
                            break;
                        // save game
                        case 4:
                            point.saveGame(status);
                            System.exit(0);
                            break;
                        // print players stats
                        case 5:
                            point.print_status(1);
                            break;
                        // way to check error that isn't from 1-5
                        default:
                            System.out.println("Invalid entry! Please try again.");
                            scan.nextLine();
                            break;
                    }
                    // to break out of the loop to continue the battle sequence
                    if( decision != 5){
                        break;
                    }

                    }
                // check if monster is defeated
                if (point.enemyHP(Monsterindex)) {
                    System.out.println("You have Defeated " + point.getMonsters(Monsterindex));
                    point.resetHP(Monsterindex);//reset  Monster object health
                    System.out.println("You gained: " + point.exp(Monsterindex) + " EXP");
                    break;
                }
                // chances for enemy to do certain task
                else {
                    scan.nextLine();
                    Mdecision = rand.nextInt(8) + 1;
                }

                switch (Mdecision) {
                    // regular attack
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        dam = point.MonAttack(Monsterindex, 1);
                        System.out.println(point.list(Monsterindex) + " did " + dam + " to you\n");
                        point.setPlayerAttack(1,dam);
                        break;
                    // grenade attack
                    case 6:
                    case 7:
                        dam = point.MonAttack(Monsterindex, 1);
                        dam = pointer.Plasma_Damage(dam);
                        System.out.println(point.list(Monsterindex) + " threw a grenade at you! Did " + dam + " to you\n");
                        point.setPlayerAttack(1, dam);
                        break;

                    default:
                        System.out.println(MonsterName + " missed!");
                        break;
                }
                // if your character dies, saves the character and resets health but not health pack or grenades
                if (point.PlayersHP(1)) {
                    System.out.println("You lose! GameOver!");
                    point.death(1);
                    point.saveGame(status);
                    System.exit(0);
                    break;
                }
            }
        }
    }
}