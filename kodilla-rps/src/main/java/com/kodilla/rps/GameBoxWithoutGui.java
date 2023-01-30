package com.kodilla.rps;

import java.util.Random;
import java.util.Scanner;

public class GameBoxWithoutGui {
    private static String playerName="Player One";
    private static int round = 1;
    private static int roundLimit = 1;
    private static int scorePlayer=0;
    private static int scoreComputer=0;
    static boolean end = false;
    static boolean start = true;
    static boolean finalDecision = false;

    static String wrong = "Wrong input - try once more";

    public static void testSetter(String n, int r, int rl, int ps, int cs){
        playerName = n;
        round = r;
        roundLimit = rl;
        scorePlayer = ps;
        scoreComputer = cs;
    }

    public static String getScore(){
        return "SCORE\n" + playerName + ": " + scorePlayer + " VS computer: " + scoreComputer;
    }

    public static void selectName(){
        System.out.println("Please enter your name: ");
        Scanner input = new Scanner(System.in);
        playerName = input.next();
        System.out.println("Hello " + playerName);
    }

    public static void selectRoundLimit(){
        System.out.println("How many rounds will the game last? ");
        Scanner input = new Scanner(System.in);
        while(!input.hasNextInt()) {
            System.out.println(wrong);
            input = new Scanner(System.in);
        }
        roundLimit = input.nextInt();

        if (roundLimit > 1) {
            System.out.println("The game will end after " + roundLimit  + " rounds.");
        } else {
            System.out.println("The game will end after " + roundLimit + " round.");
        }
    }

    public static void showOptions(){
        System.out.println("Available keys:");
        if(!finalDecision) {
            System.out.println("1 - rock\n" +
                    "2 - paper\n" +
                    "3 - scissors\n" +
                    "4 - lizard\n" +
                    "5 - Spock\n" +
                    "x - finish the game\n" +
                    "n - restart the game");
        } else {
            System.out.println("x - finish the game\n" +
                    "n - restart the game");
        }
    }

    public static String fightOption(int i){
        return switch (i) {
            case 1 -> "rock";
            case 2 -> "paper";
            case 3 -> "scissors";
            case 4 -> "lizard";
            case 5 -> "Spock";
            default -> null;
        };
    }

    public static void otherOption(String s){
        if(s.equals("x"))
        {
            Scanner input = new Scanner(System.in);
            String quit = "Are you sure you want to quit the game? (y/n)";
            System.out.println(quit);
            String answer=input.next().substring(0,1);
            while(!answer.equals("y") && !answer.equals("n"))
            {
                System.out.println(quit);
                answer = input.next().substring(0, 1);
            }
            if(answer.equals("y"))
            {
                System.out.println(getScore());
                System.exit(0);
            }

        } else if (s.equals("n")){
            Scanner input = new Scanner(System.in);
            String restart = "Are you sure you want to restart the game? (y/n)";
            System.out.println(restart);
            String answer=input.next().substring(0,1);
            while(!answer.equals("y") && !answer.equals("n"))
            {
                System.out.println(restart);
                answer = input.next().substring(0, 1);
            }
            if(answer.equals("y"))
            {
                scorePlayer = 0;
                scoreComputer = 0;
                round = 1;
                roundLimit = 3;
                end = true;
                start = true;
                finalDecision = false;
            }
        } else {
            System.out.println(wrong);
        }
    }

    public static String validate(String s){
        Scanner input = new Scanner(System.in);
        String answer = s;
        if(finalDecision)
        {
            while(!answer.equals("x") && !answer.equals("n"))
            {
                System.out.println(wrong);
                answer=input.next().substring(0,1);
            }
        } else {
            while(!answer.equals("x") && !answer.equals("n") && !answer.equals("1")
                    && !answer.equals("2") && !answer.equals("3")
                    && !answer.equals("4") && !answer.equals("5"))
            {
                System.out.println(wrong);
                answer=input.next().substring(0,1);
            }
        }
        return answer;
    }

    public static void playerDecision(){
        System.out.println("Select your action: ");
        Scanner input = new Scanner(System.in);
        String result = input.next().substring(0,1);
        result = validate(result);
        if (result.equals("1") || result.equals("2") || result.equals("3")
                || result.equals("4") || result.equals("5"))
        {
            fight(result);
        } else {
            otherOption(result);
        }
    }

    public static void fight(String c) {
        Random rand = new Random();
        int playerDecision = Integer.parseInt(c);
        int computerDecision = rand.nextInt(1,6);
        System.out.println("round " + round + " of " + roundLimit + ": "
                + playerName+ " chose " + fightOption(playerDecision)
                + " and computer chose " + fightOption(computerDecision));
        if(playerDecision == computerDecision)
        {
            System.out.println("it's a draw");
        } else if (
                (playerDecision==1 && computerDecision == 2) ||
                (playerDecision==1 && computerDecision == 5) ||
                (playerDecision==2 && computerDecision == 3) ||
                (playerDecision==2 && computerDecision == 4) ||
                (playerDecision==3 && computerDecision == 1) ||
                (playerDecision==3 && computerDecision == 5) ||
                (playerDecision==4 && computerDecision == 1) ||
                (playerDecision==4 && computerDecision == 3) ||
                (playerDecision==5 && computerDecision == 2) ||
                (playerDecision==5 && computerDecision == 4))
        {
            scoreComputer++;
            round++;
            System.out.println("computer wins");
        } else {
            scorePlayer++;
            round++;
            System.out.println(playerName + " wins");
        }
    }

    public static void run(){
        selectName();
        while(start) {
            end = false;
            selectRoundLimit();
            while (!end) {
                showOptions();
                playerDecision();
                if(round>roundLimit){
                    System.out.println(getScore());
                    finalDecision=true;
                }
            }
        }
    }
}
