import Creatures.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Spinner;

import java.io.*;
import java.util.*;



public class Main extends Application implements EventHandler<ActionEvent> {
    Battle battle = new Battle();
    Button loadChar;
    Button newChar;
    Stage primWindow;
    static boolean status = false;// if monster gets stronger
    static int monsterIndexUI = 0;
    ListView<String> listNames;
    String monLabel[] = new String[5];/* reference to getting value of monster to print on labels
    each index representing a data of the monster selected, 0 = name, 1 = health, 2 = attack, 3 = defense, 4 = speed. */

    Scene Opening;

    public static void main(String[] args) throws IOException {

        if (args[0].toLowerCase().equals("terminal")) {
            terminal();
        }
        if (args[0].toLowerCase().equals("application")) {
            launch(args);
        }
    }

    public static void terminal(){

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

        boolean str_Enemy = false;
        String Cname = null;

        System.out.println("Welcome to this game");
        while (true) {
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
            } catch (Exception e) {
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

            switch (flood_Chance) {
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

                while (true) {

                    System.out.println(name + "\t" + "Current HP: " + point.currentHealth(1) + "\n");
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
                    if (decision != 5) {
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
                        point.setPlayerAttack(1, dam);
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


    public void start(Stage primaryStage) throws Exception {

        primWindow = primaryStage;


        //set for close button on window's X
        //made it an auto save feature(ish) for closing the application, i made it that the method is the control and dont consume event, later will make it that you cant save by X if in a fight
        primWindow.setOnCloseRequest(e -> closeMethod());

        // Scene for openning Scene
        BorderPane border = new BorderPane();
        HBox buttonBox = new HBox();
        loadChar = new Button("Load Character");
        newChar = new Button("Create new Character");
        buttonBox.setPadding(new Insets(10, 10, 10, 10));
        buttonBox.setSpacing(15);


        // fill button of HBOX to cover all
        loadChar.setMaxWidth(Double.MAX_VALUE);
        newChar.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(loadChar, Priority.ALWAYS);
        HBox.setHgrow(newChar, Priority.ALWAYS);

        loadChar.setOnAction(this);// load button
        newChar.setOnAction(this); // create new button

        buttonBox.setAlignment(Pos.CENTER);

        buttonBox.getChildren().addAll(newChar, loadChar);

        // image for home screen
        Image imageHalo = new Image("halo-5-image1.jpg");
        BackgroundImage openIMG = new BackgroundImage(imageHalo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(900, 500, false, false, true, true));

        border.setBackground(new Background(openIMG));

        border.setBottom(buttonBox);
        border.bottomProperty();
        Opening = new Scene(border, 900, 500);// opening screen of program to create or load old charater
        primWindow.setScene(Opening);
        primWindow.show();
    }

    @Override
    public void handle(ActionEvent event) {

        //TO load the Character
        if (event.getSource() == loadChar) {

            GridPane loadPane = new GridPane();

            //text field to enter character's name
            TextField charName = new TextField();
            charName.setPromptText("USERNAME");
            //charName.setFocusTraversable(false);
            charName.setMaxSize(350, 250);
            GridPane.setConstraints(charName, 0, 0);

            //button to load character
            Button okay = new Button("Load Character");
            GridPane.setConstraints(okay, 0, 1);

            //Background Image
            Image openingIMG = new Image("loadChar_PIC.jpg");
            BackgroundImage image = new BackgroundImage(openingIMG, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(300, 300, true, true, true, true));
            loadPane.setBackground(new Background(image));


            loadPane.setPadding(new Insets(0, 0, 0, 10));
            loadPane.getChildren().addAll(okay, charName); // add nodes to gridpane

            Scene opening = new Scene(loadPane, 900, 500);

            primWindow.setScene(opening);

            // load character by calling loading method
            okay.setOnAction(e -> {
                String name = charName.getText();
                name = name.concat(".ser");

                if (battle.testLoad(name)) {
                    battle.loadGame(name);
                    selectionScene();
                } else {
                    charName.clear();
                }

            });
        }


            // scene for creating new character
            if (event.getSource() == newChar) {

                //set spinner for stats and editable to false to make it that no one can add number by typing
                Spinner<Integer> attackSpinner = new Spinner<>(1,13,1,1);
                Spinner<Integer> defenseSpinner = new Spinner<>(1,13,1,1);
                Spinner<Integer> speedSpinner = new Spinner<>(1,13,1,1);

                attackSpinner.setEditable(false);
                defenseSpinner.setEditable(false);
                speedSpinner.setEditable(false);

                // buttons to go to for Hboxbuttons
                Button goBackmenu = new Button("Go BACK to menu");
                Button createChar = new Button("Create");
                Button undoStat = new Button("UNDO");

                // for username and textfield to add
                Label userName = new Label("USERNAME");
                TextField userText = new TextField();
                userText.setPromptText("I.E JOHN");
                userText.setMaxSize(100,30);
                userText.setFocusTraversable(false);// to keep prompt even with action event

                // labels to be next to spinner to know which spinner is for what
                Label attackSpinnerName = new Label("ATTACK: ");
                Label defenseSpinnerName = new Label("DEFENSE: ");
                Label speedSpinnerName = new Label("SPEED: ");

                //to see what the diagram to look for all layouts, go to CreatingMenuScene.jpg in src
                VBox vboxScene = new VBox();
                HBox hBoxSeperator = new HBox();
                VBox labelVbox = new VBox();
                VBox inputVbox = new VBox();
                HBox hboxButtons = new HBox();

                labelVbox.setPrefWidth(450);
                labelVbox.setSpacing(15);
                labelVbox.setAlignment(Pos.CENTER);

                inputVbox.setPrefWidth(450);
                inputVbox.setSpacing(10);
                inputVbox.setAlignment(Pos.CENTER);

                hboxButtons.setAlignment(Pos.BOTTOM_CENTER);
                hboxButtons.setPadding(new Insets(10,10,10,10));
                hboxButtons.setSpacing(10);

                vboxScene.getChildren().addAll(hBoxSeperator, hboxButtons);
                hBoxSeperator.getChildren().addAll(labelVbox, inputVbox);

                labelVbox.getChildren().addAll(userName,attackSpinnerName,defenseSpinnerName,speedSpinnerName); // adding all label in labelVbox

                inputVbox.getChildren().addAll(userText, attackSpinner, defenseSpinner, speedSpinner); // adding all input for creating character

                hboxButtons.getChildren().addAll(goBackmenu,createChar,undoStat);// adding buttons to hboxButtons

                Scene creatingScene = new Scene(vboxScene, 900, 500);

                primWindow.setScene(creatingScene);

                // all the buttons functions

                //to clear text field and refresh
                undoStat.setOnAction(e -> {
                    userText.clear();

                    while(attackSpinner.getValue() > 1 || defenseSpinner.getValue() > 1 || speedSpinner.getValue() > 1){
                        attackSpinner.decrement();
                        defenseSpinner.decrement();
                        speedSpinner.decrement();
                    }
                });


                //button to create character
                createChar.setOnAction(e -> {

                    int statsPoints = attackSpinner.getValue() + defenseSpinner.getValue() + speedSpinner.getValue();
                    if (statsPoints != 15){

                        Alert invalidStatWindow = new Alert(Alert.AlertType.ERROR,"Stats for Character must equal to 15!",ButtonType.OK);
                        invalidStatWindow.showAndWait();

                        if(invalidStatWindow.getResult() == ButtonType.OK){
                                invalidStatWindow.close();
                            while(attackSpinner.getValue() > 1 || defenseSpinner.getValue() > 1 || speedSpinner.getValue() > 1){
                                attackSpinner.decrement();
                                defenseSpinner.decrement();
                                speedSpinner.decrement();
                            }
                        }
                    }

                    // to actually create character if status is 15
                    else{
                        battle.create(userText.getText(),attackSpinner.getValue(), defenseSpinner.getValue(),speedSpinner.getValue());

                        Alert createdCharInfo = new Alert(Alert.AlertType.INFORMATION,"Congratulation! You created " + userText.getText() + "!", ButtonType.OK);
                        createdCharInfo.showAndWait();
                        createdCharInfo.close();// must close to change scene
                        selectionScene();
                    }

                });

                goBackmenu.setOnAction(e ->{
                    primWindow.setScene(Opening);
                });
            }

        }


        //selection scene to view which monster to fight with stats of mosnter on the side of the screen
    public void selectionScene() {
        BorderPane border = new BorderPane();

        Button saveButton = new Button("SAVE");
        Button battleButton = new Button("FIGHT!!!");

        //Players Status on left side of border Pane
        VBox playerInfo = new VBox();
        playerInfo.setPadding(new Insets(10, 10, 10, 10));
        Label nameLabel = new Label("Name: " + battle.Player1[1].getSpecies()); //name to add in selection scene
        Label level = new Label("Level: " + battle.getLevel());
        Label health = new Label("Health: " + battle.currentHealth(1) + "/" + battle.getMaxHealth());
        Label grendaes = new Label("Grenades: " + battle.getGrenades());
        Label healthPack = new Label("Health Pack: " + battle.getPack());

        //set text to white
        level.setTextFill(Color.WHITE);
        health.setTextFill(Color.WHITE);
        grendaes.setTextFill(Color.WHITE);
        healthPack.setTextFill(Color.WHITE);

        playerInfo.setPrefWidth(200);

        // adding all nodes to players corner status
        playerInfo.getChildren().addAll(nameLabel, level, health, grendaes, healthPack);// add nodes to Vbox
        Image PlayerStat = new Image("status.jpg");
        BackgroundImage image = new BackgroundImage(PlayerStat, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(20, 300, true, true, true, true));
        playerInfo.setBackground(new Background(image));
        border.setLeft(playerInfo);

        //List of Monster to Fight
        battle.createMon();//creates monster list
        listNames = new ListView<>();//listview,
        listNames.setPrefSize(500, 300);
        listNames.setStyle("-fx-font-size: 1.5em");

        String copyNames; // local variable to add names to listView
        for (int i = 0; i < battle.monster.length; i++) {
            copyNames = battle.monster[i].getSpecies();
            listNames.getItems().add(copyNames);
        }

        //selection mode for list view, one to fight one monster
        listNames.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        border.setCenter(listNames);

        //Status of Monster
        VBox monStat = new VBox();
        monStat.setPrefWidth(250);

        // all the labels for monster
        Label monName = new Label("");
        Label monHealth = new Label("");
        Label monATK = new Label("");
        Label monDEF = new Label("");
        Label monSpeed = new Label("");

        monStat.setPadding(new Insets(10,10,10,10));


        monStat.getChildren().addAll(monName, monHealth, monATK, monDEF, monSpeed);
        border.setRight(monStat);

        //buttons on bottom to interact with game/list scene
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(saveButton, battleButton);
        saveButton.setPrefSize(300, 50);
        battleButton.setPrefSize(300, 50);

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        border.setBottom(buttonBox);

        // to save the character
        saveButton.setOnAction(e -> {
            // to confirm if user wants to save console
            Alert saveDialog = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save?", ButtonType.YES, ButtonType.CANCEL);
            saveDialog.showAndWait();

            if(saveDialog.getResult() == ButtonType.YES){
                battle.saveGame(status);
                Alert saveInfo = new Alert(Alert.AlertType.INFORMATION, "Current Status: \n" + battle.printStatus(), ButtonType.OK);
                saveInfo.setHeaderText("Save Successful!");
                saveInfo.showAndWait();

                //okay to confirm save and close application
                if(saveInfo.getResult() == ButtonType.OK){
                    saveInfo.close();
                    primWindow.close();
                    System.exit(1);
                }

            }
            //goes back to selection screen
            else if(saveDialog.getResult() == ButtonType.CANCEL){
                saveDialog.close();
            }

        });


        listNames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                monLabel = battle.getMonInfo(newValue, monLabel);

                monName.setText("Name: " + monLabel[0]);
                monHealth.setText("HP: " + monLabel[1]);
                monATK.setText("Attack: " + monLabel[2]);
                monDEF.setText("Defense: " + monLabel[3]);
                monSpeed.setText("Speed: " + monLabel[4]);

            }
        });

        //finds monster's index to fight. sends control to fightButton method
        battleButton.setOnAction(e -> {
            String findMonsterName = listNames.getSelectionModel().getSelectedItem(); //gets selected on list, using string, reference to get monster ID
            monsterIndexUI = battle.findMonster(findMonsterName);
            fightButton();
        });

        Scene SelectionScene = new Scene(border, 900, 500);
        primWindow.setScene(SelectionScene);

    }

    public void fightButton(){
    }

    public void closeMethod(){

        if(battle.Player1[1] == null){
            System.out.println("closing game without character being load or created");
            primWindow.close();
        }
        if(battle.Player1[1] != null){
            battle.saveGame(status);
            System.out.println("game saved with character loaded/created");
            primWindow.close();
        }

    }


}


