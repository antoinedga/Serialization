import Creatures.*;
import java.io.*;
import java.util.*;

// extends Application implements EventHandler<ActionEvent>
public class Halo_Pokemon
{
    static boolean status = false;// if monster gets stronger
    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random(System.currentTimeMillis());
    //ListView<String> listNames;
    String monLabel[] = new String[5];/* reference to getting value of monster to print on labels
    each index representing a data of the monster selected, 0 = name, 1 = health, 2 = attack, 3 = defense, 4 = speed. */

    public static void main(String[] args) throws IOException
    {
        int player_decision;
        Battle point = new Battle();
        int monster_decision;
        int dam;

        System.out.println("Welcome to this game");
        while (true)
        {
            try
            {
                System.out.println("\nWould you like to:\nLoad old Character or Create new one?\n\n 1.Create new Player\t\t2. Load old Player's file");
                player_decision = scan.nextInt();
                if (player_decision == 1)
                {
                    status = true;
                    create_character_new(point);
                }
                else if (player_decision == 2)
                {
                  load_character(point);
                }
                else
                {
                  System.out.println("Invalid entry");
                  scan.nextLine();
                }
            }
            // to catch if someone where to put anything thats not an Int
            catch (Exception e)
            {
                System.out.println("Invalid Entry!");
                scan.nextLine();
            }
            break;
        }
        point.createMon();
        fighting(point);
    }

    public static void  create_character_new(Battle point)
    {
      String name = null;
      int PlayersAttack;
      int PlayersDefense;
      int PlayersSpeed;

      System.out.println("Please enter your character's name");
      name = scan.next();

      while (true)
      {
          try
          {
              //enters stat
              System.out.println("Enter the stats you would like for your characters");
              System.out.println("you are allowed 15 points");
              System.out.print("First add your stats for Attack: ");
              PlayersAttack = scan.nextInt();
              System.out.print("\nEnter Stats for Defense: ");
              PlayersDefense = scan.nextInt();
              System.out.print("\nEnter Stats for Speed: ");
              PlayersSpeed = scan.nextInt();

              if ((PlayersAttack + PlayersDefense + PlayersSpeed) == 15)
              {
                  break;
              }
              else
              {
                  System.out.println("INVALID amount! Try again.");
              }
          }
          catch (Exception e)
          {
              System.out.println("Invalid entry! Try again.");
              PlayersAttack = 0;
              PlayersDefense = 0;
              PlayersSpeed = 0;
              scan.nextLine();
          }
        }

      point.create(name, PlayersAttack, PlayersDefense, PlayersSpeed);
      System.out.println("\nCongratulations! You have successfully created your character:  " + name);
    }

    public static void load_character(Battle point)
    {
      String Cname = null;
      while (true)
      {
        //scan.nextLine();
        try
        {
          System.out.println("Enter character's name: ");
          System.out.println("If you want to quit, enter \"Q\"");
          Cname = scan.next();
          if(Cname.equalsIgnoreCase("Q"))
            System.exit(1);
          else
          {
            Cname = Cname + ".ser";
            point.loadGame(Cname);
            break;
          }
        }
        catch (NullPointerException error)
        {
          System.out.println("COULD NOT FIND CHARACTER'S FILE: TO TRY AGAIN PRESS ANYTHING");
          System.out.println("ELSE PRESS \"Q\" TO QUIT");
          scan.nextLine();
          Cname = scan.nextLine();
          if(Cname.equalsIgnoreCase("Q"))
            System.exit(1);
        }
      }
    }

    public static void fighting(Battle point)
    {
      int monster_index;
      int monster_decision;
      double flood_Chance;
      String monster_name = null;
      int player_decision;
      int dam;
      String char_Name = point.Player1[1].getSpecies();

      // battle of monster
      while (true)
      {
          // to make monster Stronger
          point.Stronger(point.Player1[1].getAboveLevelTen());

          System.out.println("Select the monster you would like to fight:");
          point.monsterList();
          monster_index = scan.nextInt();

          //to save the game
          if (monster_index == 8)
          {
              point.saveGame(status);
              System.exit(1);
          }

          monster_name = point.list((monster_index - 1));
          monster_index = monster_index - 1;
          System.out.println("You are fighting a(n): " + monster_name);

          // chance to of infected monster
          flood_Chance = Math.random();
          if(flood_Chance < 0.20)
          {
            point.turned_Flood(monster_index);
            monster_index = 7;
            monster_name = point.list(monster_index);
          }

          // the start of the fight against whatever monster
          System.out.println("FIGHT!\n");
          while (true)
          {
              while (true)
              {
                  System.out.println(char_Name + "\t" + "Current HP: " + point.currentHealth(1) + "\n");
                  System.out.println(monster_name + "\thealth: " + point.enemyhp(monster_index));

                  //menu to tell user his/her options
                  System.out.println("\nselect what to do:\n" +
                          "1.Attack\t2.Heal\t3.Grenade \t4.Exit\n5.check stat\nHealth Pack: " + point.getPack() + "\nGrenades: " + point.getGrenades());
                  player_decision = scan.nextInt();
                  // switch statements for all the possible selection for the Player to do
                  switch (player_decision)
                  {
                      // reg attack
                      case 1:
                          dam = point.playerAttack((monster_index), 1);
                          point.setMonster(dam, monster_index);
                          System.out.println("You did: " + dam);
                          break;
                      // heal player
                      case 2:
                          boolean Stat;
                          Stat = point.PlayerHeal(1);
                          if (Stat)
                          {
                              System.out.println("You were healed 35+");
                          }
                          else
                          {
                              System.out.println("You do not have Health");
                          }
                          break;
                      // grenade damage
                      case 3:
                          dam = point.playerAttack(monster_index, 1);
                          dam = point.grenadeCall(dam);
                          System.out.println("You threw a grenade! you did " + dam + " damage");
                          point.setMonster(dam, monster_index);
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
                  if (player_decision != 5)
                  {
                      break;
                  }

              }
              // check if monster is defeated
              if (point.enemyHP(monster_index))
              {
                  System.out.println("You have Defeated " + point.getMonsters(monster_index));
                  point.resetHP(monster_index);//reset  Monster object health
                  System.out.println("You gained: " + point.exp(monster_index) + " EXP");
                  break;
              }
              // chances for enemy to do certain task
              else
              {
                  scan.nextLine();
                  monster_decision = (rand.nextInt() % 8) + 1;
              }

              switch (monster_decision)
              {
                  // regular attack
                  case 1:
                  case 2:
                  case 3:
                  case 4:
                  case 5:
                      dam = point.MonAttack(monster_index, 1);
                      System.out.println(point.list(monster_index) + " did " + dam + " to you\n");
                      point.setPlayerAttack(1, dam);
                      break;
                  // grenade attack
                  case 6:
                  case 7:
                      dam = point.MonAttack(monster_index, 1);
                      dam = Monster.Plasma_Damage(dam);
                      System.out.println(point.list(monster_index) + " threw a grenade at you! Did " + dam + " to you\n");
                      point.setPlayerAttack(1, dam);
                      break;

                  default:
                      System.out.println(monster_name + " missed!");
                      break;
              }
              // if your character dies, saves the character and resets health but not health pack or grenades
              if (point.PlayersHP(1))
              {
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
