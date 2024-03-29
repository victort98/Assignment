package com.company;

import java.util.*;

/**
 * This is where the main program runs
 */
public class Program {

    private static final int MAX_CHARACTERS = 1;
    Rat rat = new Rat(10, 0, "1-3", "Rat");
    Spider spider = new Spider(15, 0, "3-5", "Spider");
    Troll troll = new Troll(25, 0, "7-11", "Troll");
    Bear bear = new Bear(35, 0, "13-20", "Bear");
    Orc orc = new Orc(45, 0, "15-22", "Orc");
    Dragon dragon = new Dragon(60, 0, "17-25", "Dragon");

    Wizard wizard = new Wizard(25, 0, 1, 50, "4-7", "Wizard", "");

    Random rand = new Random();
    Scanner scan = new Scanner(System.in);
    private int numOfCharacters = 0;
    private int numOfHealthPotions = 1;
    private int numOfManaPotions = 1;
    private int manaAmount = 25;
    private int bigManaAmount = 50;
    private int manaPotionDropChance = 50;
    private int healAmount = 20;
    private int bigHealAmount = 30;
    private int healthPotionDropChance = 50;
    private ArrayList<Character> characters = new ArrayList<>();
    private ArrayList<Monsters> monsters = new ArrayList<>();
    private String name;
    private int monsterHealth;
    private int monsterDamage;
    private String monsterName;
    private String monsterDamageInfo;

    public Program() {
    }

    public void showMainMenu() {
        String menuChoice = "";
        while (!menuChoice.equals("0")) {
            System.out.println("1: Play the game\n" +
                    "2: Create new character\n" +
                    "3: View character\n" +
                    "4: Class information\n" +
                    "5: View monsters\n" +
                    "6: Save and load character\n" +
                    "7: Remove character\n" +
                    "0: Exit");
            System.out.println("Enter your menu choice:");

            menuChoice = scan.nextLine();

            switch (menuChoice) {
                case "1":
                    playTheGame();
                    break;

                case "2":
                    createCharacter();
                    break;

                case "3":
                    viewCharacters();
                    break;

                case "4":
                    classInformation();
                    break;

                case "5":
                    viewMonsters();
                    break;

                case "6":
                    saveAndLoadCharacter();
                    break;

                case "7":
                    removeCharacter();
                    break;

                case "0":
                    exit();
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void playTheGame() {
        if (characters.size() == 0) {
            System.out.println("You need to create a character");
            showMainMenu();
        }

        while (rat.health > 0 && wizard.getHealth() > 0 && wizard.level < 2.5) {
            System.out.println("Health: " + wizard.health + "            " + "Enemy Health: " + rat.health + "\n" +
                    "Mana: " + wizard.mana + "             " + "Enemy Damage: " + rat.damageInfo + "\n" +
                    "Health potions: " + numOfHealthPotions + "\n" +
                    "Mana potions: " + numOfManaPotions + "\n");
            System.out.println("You are fighting a rat\n" +
                    "1: Attack\n" +
                    "2: Fireball\n" +
                    "3: Use health potion (20hp)\n" +
                    "4: Use mana potion (25mana)\n" +
                    "0: Leave dungeon");
            try {
                String userInput = scan.nextLine();
                int wizardDamage = wizard.getDamage();
                int ratDamage = rat.getRatDamage();

                switch (userInput) {
                    case "1":
                        rat.health -= wizard.getDamage();
                        wizard.health -= ratDamage;

                        System.out.println("You attack the rat for " + wizardDamage + " damage");
                        System.out.println("The rat attacks you for " + ratDamage + " damage");

                        if (wizard.health <= 0) {
                            System.out.println("You lost the fight and your character is dead");
                            characters.remove(0);
                            showMainMenu();
                        } else if (rat.health <= 0) {
                            System.out.println("You won the fight");
                            wizard.level += 0.5;
                            System.out.println("Your level is now " + wizard.level + " and your strength is growing");
                            rat.health = 10;
                            dropChanceManaAndHealth();
                        }
                        break;

                    case "2":
                        if (wizard.mana < 25) {
                            System.out.println("You don't have enough mana");
                        } else {
                            int fireball = rand.nextInt(20 - 15 + 1) + 15;
                            wizard.mana -= 25;

                            rat.health -= fireball;
                            wizard.health -= ratDamage;

                            System.out.println("You shoot a fireball that deals " + fireball + " damage");
                            System.out.println("The rat attacks you for " + ratDamage);
                            if (wizard.health <= 0) {
                                System.out.println("You lost the fight and your character is dead");
                                characters.remove(0);
                                showMainMenu();
                            } else if (rat.health <= 0) {
                                System.out.println("You won the fight");
                                wizard.level += 0.5;
                                System.out.println("Your level is now " + wizard.level + " and your strength is growing\n");
                                rat.health = 10;
                                dropChanceManaAndHealth();
                            }
                        }
                        break;

                    case "3":
                        useHealthPotion();
                        break;

                    case "4":
                        useManaPotion();
                        break;

                    case "0":
                        System.out.println("You manage to escape");
                        showMainMenu();
                        break;
                    default:
                        System.out.println("Wrong input");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid input");
            }
        }

        wizard.setHealth(45);
        wizard.mana = 100;
        getRandomMonster();
        while (monsterHealth > 0 && wizard.getHealth() > 0 && wizard.level < 10) {
            if (monsterName.equalsIgnoreCase("bear")) {
                monsterDamage = bear.getBearDamage();
            }
            if (monsterName.equalsIgnoreCase("spider")) {
                monsterDamage = spider.getSpiderDamage();
            }
            if (monsterName.equalsIgnoreCase("troll")) {
                monsterDamage = troll.getTrollDamage();
            }
            if (monsterName.equalsIgnoreCase("orc")) {
                monsterDamage = orc.getOrcDamage();
            }
            if (monsterName.equalsIgnoreCase("dragon")) {
                monsterDamage = dragon.getDragonDamage();
            }
            System.out.println("Health: " + wizard.health + "            " + "Enemy Health: " + monsterHealth + "\n" +
                    "Mana: " + wizard.mana + "             " + "Enemy Damage: " + monsterDamageInfo + "\n" +
                    "Health potions: " + numOfHealthPotions + "\n" +
                    "Mana potions: " + numOfManaPotions + "\n");
            System.out.println("You are fighting a " + monsterName + "\n" +
                    "1: Attack\n" +
                    "2: Fireball\n" +
                    "3: Use health potion (30hp)\n" +
                    "4: Use mana potion (25mana)\n" +
                    "0: Leave dungeon");
            try {
                String userInput = scan.nextLine();
                int wizardDamage = rand.nextInt(12 - 8 + 1) + 8;

                switch (userInput) {
                    case "1":
                        monsterHealth -= wizardDamage;
                        wizard.health -= monsterDamage;

                        System.out.println("You attack the " + monsterName + " for " + wizardDamage + " damage");
                        System.out.println("The " + monsterName + " attacks you for " + monsterDamage + " damage");

                        if (wizard.health <= 0) {
                            System.out.println("You lost the fight and your character is dead");
                            characters.remove(0);
                            showMainMenu();
                        } else if (monsterHealth <= 0) {
                            System.out.println("You won the fight");
                            wizard.level += 0.5;
                            System.out.println("Your level is now " + wizard.level + " and your strength is growing");
                            getRandomMonster();
                            dropChanceManaAndHealth();
                        }
                        break;

                    case "2":
                        if (wizard.mana < 25) {
                            System.out.println("You don't have enough mana");
                        } else {
                            int fireball = rand.nextInt(28 - 20 + 1) + 20;
                            wizard.mana -= 25;

                            monsterHealth -= fireball;
                            wizard.health -= monsterDamage;

                            System.out.println("You shoot a fireball that deals " + fireball + " damage");
                            System.out.println("The " + monsterName + " attacks you for " + monsterDamage);
                            if (wizard.health <= 0) {
                                System.out.println("You lost the fight and your character is dead");
                                characters.remove(0);
                                showMainMenu();
                            } else if (rat.health <= 0) {
                                System.out.println("You won the fight");
                                wizard.level += 0.5;
                                System.out.println("Your level is now " + wizard.level + " and your strength is growing\n");
                                getRandomMonster();
                                dropChanceManaAndHealth();
                            }
                        }
                        break;

                    case "3":
                        useMediumHealthPotion();
                        break;

                    case "4":
                        useBigManaPotion();
                        break;

                    case "0":
                        System.out.println("You manage to escape");
                        showMainMenu();
                        break;
                    default:
                        System.out.println("Wrong input");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid input");
            }
            if (wizard.level == 10) {
                System.out.println("You won the game!!!!!");
                showMainMenu();
            }
        }
    }

    public void useHealthPotion() {
        if (numOfHealthPotions == 0) {
            System.out.println("You are out of health potions\n" +
                    "Kill monsters for a chance to get more");
        } else if (wizard.health == 25) {
            System.out.println("You already have full health");

        } else if (wizard.health + healAmount > 25) {
            wizard.setHealth(25);
            numOfHealthPotions--;
        } else {
            wizard.health += healAmount;
            numOfHealthPotions--;
        }
    }

    public void useMediumHealthPotion() {
        if (numOfHealthPotions == 0) {
            System.out.println("You are out of health potions\n" +
                    "Kill monsters for a chance to get more");
        } else if (wizard.health == 45) {
            System.out.println("You already have full health");

        } else if (wizard.health + bigHealAmount > 45) {
            wizard.setHealth(45);
            numOfHealthPotions--;
        } else {
            wizard.health += bigHealAmount;
            numOfHealthPotions--;
        }
    }

    public void useManaPotion() {
        if (numOfManaPotions == 0) {
            System.out.println("You are out of mana potions\n" +
                    "Kill monsters for a chance to get more");
        } else if (wizard.mana == 50) {
            System.out.println("You already have full mana");

        } else if (wizard.mana + manaAmount > 50) {
            wizard.mana = 50;
            numOfManaPotions--;
        } else {
            wizard.mana += manaAmount;
            numOfManaPotions--;
        }
    }

    public void useBigManaPotion() {
        if (numOfManaPotions == 0) {
            System.out.println("You are out of mana potions\n" +
                    "Kill monsters for a chance to get more");
        } else if (wizard.mana == 100) {
            System.out.println("You already have full mana");

        } else if (wizard.mana + bigManaAmount > 100) {
            wizard.mana = 100;
            numOfManaPotions--;
        } else {
            wizard.mana += bigManaAmount;
            numOfManaPotions--;
        }
    }

    public void dropChanceManaAndHealth() {
        if (rand.nextInt(100) > healthPotionDropChance) {
            numOfHealthPotions++;
            System.out.println("You got a health potion, you now have " + numOfHealthPotions + " health potions");
        }
        if (rand.nextInt(100) > manaPotionDropChance) {
            numOfManaPotions++;
            System.out.println("You got a mana potion, you now have " + numOfManaPotions + " mana potions");
        }
    }

    public void getRandomMonster() {
        int randomNumber = rand.nextInt(70) + 1;
        if (randomNumber <= 20) {
            monsterHealth = spider.health;
            monsterName = spider.name;
            monsterDamageInfo = spider.damageInfo;
        } else if (randomNumber > 20 && randomNumber <= 40) {
            monsterHealth = troll.health;
            monsterName = troll.name;
            monsterDamageInfo = troll.damageInfo;
        } else if (randomNumber > 40 && randomNumber <= 60) {
            monsterHealth = bear.health;
            monsterName = bear.name;
            monsterDamageInfo = bear.damageInfo;
        } else if (randomNumber > 60 && randomNumber <= 65) {
            monsterHealth = orc.health;
            monsterName = orc.name;
            monsterDamageInfo = orc.damageInfo;
        } else if (randomNumber > 65) {
            monsterHealth = dragon.health;
            monsterName = dragon.name;
            monsterDamageInfo = dragon.damageInfo;
        }
    }

    public void createCharacter() {
        if (numOfCharacters < MAX_CHARACTERS) {
            System.out.println("Enter the name of you character: ");
            try {
                name = scan.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid input");
            }
            numOfCharacters++;
            wizard.name = name;
            characters.add(wizard);
            System.out.println("You created a wizard called " + name);
            showMainMenu();
        } else {
            System.out.println("You already have a character");
            showMainMenu();
        }
    }

    public void viewCharacters() {

        if (characters.size() == 0) {
            System.out.println("You need to create a character");
            showMainMenu();
        }
        for (Character character : characters) {
            System.out.println(character + "" + wizard.level);
            showMainMenu();
        }
    }

    public void classInformation() {
        wizard.information();
        showMainMenu();
    }

    public void viewMonsters() {
        monsters.add(orc);
        monsters.add(rat);
        monsters.add(spider);
        monsters.add(dragon);
        monsters.add(bear);
        monsters.add(troll);
        System.out.println("This is the list of all the monsters in the game:");
        for (Monsters monster : monsters) {
            System.out.println(monster);
        }

        String userInput = "";
        while (!userInput.equals("0")) {
            System.out.println("1: Sort by name\n" +
                    "2: Sort by health and damage\n" +
                    "0: Main menu");
            try {
                userInput = scan.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid input");
            }

            switch (userInput) {
                case "1":
                    SortMonstersName sortMonstersName = new SortMonstersName();

                    Collections.sort(monsters, sortMonstersName);

                    for (Monsters monster : monsters) {
                        System.out.println(monster);
                    }
                    break;

                case "2":
                    SortMonstersHealth sortMonstersHealth = new SortMonstersHealth();

                    Collections.sort(monsters, sortMonstersHealth);

                    for (Monsters monster : monsters) {
                        System.out.println(monster);
                    }
                    break;

                case "0":
                    monsters.clear();
                    showMainMenu();
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void saveAndLoadCharacter() {

        String userInput = "";
        while (!userInput.equals("0")) {
            System.out.println("1: Save your current character(This will override any other saved character)\n" +
                    "2: Load a character that you have saved\n" +
                    "0: Main menu");
            try {
                userInput = scan.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid input");
            }
            switch (userInput) {
                case "1":
                    if (numOfCharacters < MAX_CHARACTERS) {
                        System.out.println("You need to create a character before you can save.");
                        saveAndLoadCharacter();
                    } else {
                        FileUtils.writeObject("characterSave.ser", characters);
                        System.out.println("You saved your character");
                        showMainMenu();
                    }
                    break;

                case "2":
                    if (numOfCharacters >= MAX_CHARACTERS) {
                        System.out.println("You already have a character");
                        saveAndLoadCharacter();
                    }
                    numOfCharacters++;
                    try {
                        ArrayList<Character> charactersFromFile = FileUtils.readObject("characterSave.ser");
                        for (Character character : charactersFromFile) {
                            characters.add(character);
                            wizard.level = character.level;
                            wizard.name = character.name;
                            wizard.role = character.role;
                            wizard.health = character.health;
                            wizard.mana = character.mana;
                            System.out.println("Successfully loaded your saved wizard");
                            showMainMenu();
                        }
                    } catch (Exception e) {
                        System.out.println("Something went wrong trying to load your character");
                    }
                    break;

                case "0":
                    showMainMenu();
                    break;
                default:
                    System.out.println("Wrong input");
            }

        }
    }

    public void removeCharacter() {
        if (characters.size() == 0) {
            System.out.println("You need to create a character");
            showMainMenu();
        } else {
            System.out.println("This is your current character");
            for (Character character : characters) {
                System.out.println(character + "" + wizard.level);
            }
            System.out.println("1: Remove character");
            System.out.println("0: Main menu");
            String userInput = scan.nextLine();

            if (userInput.equals("1")) {
                characters.remove(0);
                numOfCharacters--;
                System.out.println("Character successfully removed");
            } else if (userInput.equals("0")) {
                showMainMenu();
            } else {
                System.out.println("Wrong input, try again");
                removeCharacter();
            }
        }


    }

    public void exit() {
        System.out.println("Thanks for playing my game.");
        System.exit(0);
    }
}