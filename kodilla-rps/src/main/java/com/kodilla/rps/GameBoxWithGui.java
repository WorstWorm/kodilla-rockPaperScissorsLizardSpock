package com.kodilla.rps;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Random;

public class GameBoxWithGui {
    String selectedOption;
    int difficultyLvl = 1;
    int currentRound = 1;
    int roundLimit = 3;
    int scoreComputer = 0;
    int scorePlayer = 0;
    String roundWinner;
    String playerName;
    String roundInfo;

//FRAME OBJECTS CREATION================================================================================================
    JFrame frame = new JFrame();
    JButton confirmButton = new JButton("CONFIRM");
    JButton[] optionButtons = new JButton[7];
    JRadioButton[] roundsRadioButtons = new JRadioButton[4];
    JTable scoreTable = new JTable();
    JSlider difficultySlider = new JSlider(JSlider.VERTICAL, 0, 2, 1);
    JLabel roundSelectorLabel = new JLabel("rounds:");
    JLabel playerLabel = new JLabel("PLAYER:");
    JLabel playerNameLabel = new JLabel();
    JTextField inputNameBox = new JTextField("Please enter your name");
    JLabel informationBox = new JLabel("<html>Select username, number of rounds, <br> difficulty level and " +
            "press confirm.</html>");
    DefaultTableModel dtm = new DefaultTableModel(0,0);

//POSSIBLE FIGHT CASES =================================================================================================        

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
    
//ROUND OPERATIONS =====================================================================================================
    public void round(){
        if (selectedOption.equals("1") || selectedOption.equals("2") || selectedOption.equals("3")
                || selectedOption.equals("4") || selectedOption.equals("5")) {
            int playerDecision = Integer.parseInt(selectedOption);

            //making computer decision
            int computerDecision;
            Random rand = new Random();
            switch (difficultyLvl) {
                case 0 -> //difficulty Sheldon (always Spock)
                        computerDecision = 5;
                case 2 -> { //difficulty hard (25% on win, 25% on draw, 50% on loose)
                    int probabilityCheck = rand.nextInt(0, 4);
                    if (probabilityCheck == 4) { //draw
                        computerDecision = playerDecision;
                    } else if (probabilityCheck == 3) { //player wins
                        int computerOutputRandomization = rand.nextInt(0, 2);
                        computerDecision = switch (playerDecision) {
                            case 1 -> (computerOutputRandomization == 0) ? 3 : 4;
                            case 2 -> (computerOutputRandomization == 0) ? 1 : 5;
                            case 3 -> (computerOutputRandomization == 0) ? 2 : 4;
                            case 4 -> (computerOutputRandomization == 0) ? 2 : 5;
                            default -> (computerOutputRandomization == 0) ? 1 : 3;
                        };
                    } else { //computer wins
                        int computerOutputRandomization = rand.nextInt(0, 2);
                        computerDecision = switch (playerDecision) {
                            case 1 -> (computerOutputRandomization == 0) ? 2 : 5;
                            case 2 -> (computerOutputRandomization == 0) ? 3 : 4;
                            case 3 -> (computerOutputRandomization == 0) ? 1 : 5;
                            case 4 -> (computerOutputRandomization == 0) ? 1 : 3;
                            default -> (computerOutputRandomization == 0) ? 2 : 4;
                        };
                    }
                }
                default -> //difficulty normal (random computer decision)
                        computerDecision = rand.nextInt(1, 6);
            }

            roundInfo = "<html>round " + currentRound + " of " + roundLimit + ": "
                    + playerName+ " chose " + fightOption(playerDecision) + " and computer chose "
                    + fightOption(computerDecision) + " - ";

            //calculating the result
            if(playerDecision == computerDecision)
            {
                roundInfo += "it's a draw";
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
                roundInfo += "computer wins";
                roundWinner = "computer";
                finishRound();
            } else {
                scorePlayer++;
                roundInfo += playerName + " wins";
                roundWinner = playerName;
                finishRound();
            }
            roundInfo += "<br>CHOOSE WHAT YOU WANT TO DO</html>";

        //exit option selected
        } else if (selectedOption.equals("x")) {
            int input = JOptionPane.showConfirmDialog(null, "Do you want to exit?",
                    "EXIT", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if(input == 0){
                System.exit(0);
            }

        //restart option selected
        } else if (selectedOption.equals("n")) {
            int input = JOptionPane.showConfirmDialog(null, "Do you want to restart?",
                    "RESTART", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(input == 0) {
                new GameBoxWithGui();
                frame.dispose();
            }
        }
    }

//FINISHING ROUND OPERATIONS ===========================================================================================
    public void finishRound(){
        dtm.addRow(new Object[] {"Round " + currentRound + ": " + roundWinner +" won"});
        currentRound++;
        if(currentRound >roundLimit){
            endOfGame();
        }
    }

//END OF GAME OPERATIONS ===============================================================================================
    public void endOfGame() {
        for(int i=0; i<5; i++){
            optionButtons[i].setEnabled(false);
        }

        String winner;
        if(scorePlayer>scoreComputer){
            winner = playerName;
        } else {
            winner = "COMPUTER";
        }
        roundInfo += "<html><center><u>END OF GAME</u></center>score: " + playerName + " " + scorePlayer + " VS Computer "
                + scoreComputer + "<center><u>THE WINNER IS " + winner + "</u></center></html>";
    }
    
//FRAME OPERATIONS =====================================================================================================
    public GameBoxWithGui() {

//FRAME - MAIN FRAME SETTINGS ==========================================================================================
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//FRAME - GAME NAME LABEL SETTINGS =====================================================================================
        JLabel title = new JLabel("ROCK - PAPER - SCISSORS - LIZARD - SPOCK");
        title.setFont(new Font("Dialog", Font.BOLD, 20));

//FRAME - GAME INFO LABEL SETTINGS =====================================================================================
        informationBox.setForeground(Color.BLUE);

//SCORE TABLE SETTINGS =================================================================================================
        scoreTable.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        String[] header = new String[]{"round score"};
        dtm.setColumnIdentifiers(header);
        scoreTable.setModel(dtm);

//USERNAME BOX OPERATIONS AND SETTINGS =================================================================================
        inputNameBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(inputNameBox.getText().trim().equals("Please enter your name"))
                    inputNameBox.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(inputNameBox.getText().trim().equals(""))
                    inputNameBox.setText("Please enter your name");
            }
        });

        playerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        playerNameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        playerNameLabel.setForeground(Color.BLUE);

//OPTIONS BUTTONS OPERATIONS AND SETTINGS ==============================================================================
        optionButtons[0] = new JButton("ROCK");
        optionButtons[0].addActionListener(e -> {
            selectedOption = "1";
            round();
            informationBox.setText(roundInfo);
        });

        optionButtons[1] = new JButton("PAPER");
        optionButtons[1].addActionListener(e -> {
            selectedOption = "2";
            round();
            informationBox.setText(roundInfo);
        });

        optionButtons[2] = new JButton("SCISSORS");
        optionButtons[2].addActionListener(e -> {
            selectedOption = "3";
            round();
            informationBox.setText(roundInfo);
        });

        optionButtons[3] = new JButton("LIZARD");
        optionButtons[3].addActionListener(e -> {
            selectedOption = "4";
            round();
            informationBox.setText(roundInfo);
        });

        optionButtons[4] = new JButton("SPOCK");
        optionButtons[4].addActionListener(e -> {
            selectedOption = "5";
            round();
            informationBox.setText(roundInfo);
        });

        optionButtons[5] = new JButton("FINISH");
        optionButtons[5].addActionListener(e -> {
            selectedOption = "x";
            round();
            informationBox.setText(roundInfo);
        });

        optionButtons[6] = new JButton("RESTART");
        optionButtons[6].addActionListener(e -> {
            selectedOption = "n";
            round();
            informationBox.setText(roundInfo);
        });

        //disabling buttons until playername, difficulty level and number of rounds are selected
        for(JButton but : optionButtons){
            but.setEnabled(false);
        }

//ROUND SELECTION RADIO BUTTONS OPERATIONS AND SETTINGS ================================================================
        roundsRadioButtons[0] = new JRadioButton("1");
        roundsRadioButtons[1] = new JRadioButton("3");
        roundsRadioButtons[2] = new JRadioButton("5");
        roundsRadioButtons[3] = new JRadioButton("7");
        roundsRadioButtons[1].setSelected(true);
        ButtonGroup radioButtons = new ButtonGroup();
        for(JRadioButton but : roundsRadioButtons){
            radioButtons.add(but);
        }

        class RadioButtonActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                JRadioButton button = (JRadioButton) event.getSource();
                if (button == roundsRadioButtons[0]) {
                    roundLimit = 1;
                } else if (button == roundsRadioButtons[1]) {
                    roundLimit = 3;
                } else if (button == roundsRadioButtons[2]) {
                    roundLimit = 5;
                } else if (button == roundsRadioButtons[3]) {
                roundLimit = 7;
                }
            }
        }

        RadioButtonActionListener actionListener = new RadioButtonActionListener();
        for(JRadioButton but : roundsRadioButtons){
            but.addActionListener(actionListener);
        }

//DIFFICULTY LEVEL SLIDER OPERATIONS AND SETTINGS ======================================================================

        Hashtable<Integer, JLabel> lvlDescription = new Hashtable<>();
        lvlDescription.put(0, new JLabel("Sheldon"));
        lvlDescription.put(1, new JLabel("Normal"));
        lvlDescription.put(2, new JLabel("Hard"));
        difficultySlider.setPaintLabels(true);
        difficultySlider.setLabelTable(lvlDescription);
        difficultySlider.addChangeListener(e -> difficultyLvl = difficultySlider.getValue());

//CONFIRM BUTTON OPERATIONS AND SETTINGS ===============================================================================

        confirmButton.addActionListener(e -> {
            playerName = inputNameBox.getText();
            if(playerName.length()>8){
                playerName = playerName.substring(0,8);
            }
            playerNameLabel.setText(playerName);
            frame.remove(inputNameBox);
            frame.remove(confirmButton);
            frame.add(playerLabel);
            frame.add(playerNameLabel);

            //activation of option buttons after confirming the settings
            for(JButton but : optionButtons){
                but.setEnabled(true);
            }

            informationBox.setText("CHOOSE WHAT YOU WANT TO DO");
            frame.revalidate();
            frame.repaint();
            for (JButton but : optionButtons) {
                but.setEnabled(true);
            }
            for (JRadioButton rbut : roundsRadioButtons) {
                rbut.setEnabled(false);
            }
            difficultySlider.setEnabled(false);
        });

//SETTING BOUNDS OF FRAME ELEMENTS =====================================================================================
        frame.setSize(500, 500);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        informationBox.setBounds(30, 110, 420, 90);

        title.setBounds(20, 10, 490, 20);

        inputNameBox.setBounds(30, 50, 300, 25);
        confirmButton.setBounds(350, 50, 100, 55);
        playerLabel.setBounds(30, 50, 100, 25);
        playerNameLabel.setBounds(120, 50, 300, 25);

        roundSelectorLabel.setBounds(30, 83, 60, 20);
        roundsRadioButtons[0].setBounds(90, 83, 45, 20);
        roundsRadioButtons[1].setBounds(140, 83, 45, 20);
        roundsRadioButtons[2].setBounds(190, 83, 45, 20);
        roundsRadioButtons[3].setBounds(240,83,45,20);

        difficultySlider.setBounds(400, 210, 75, 230);

        optionButtons[0].setBounds(30, 210, 100, 25);
        optionButtons[1].setBounds(30, 260, 100, 25);
        optionButtons[2].setBounds(30, 310, 100, 25);
        optionButtons[3].setBounds(30, 360, 100, 25);
        optionButtons[4].setBounds(30, 410, 100, 25);
        optionButtons[5].setBounds(230, 210, 100, 25);
        optionButtons[6].setBounds(230, 260, 100, 25);

        scoreTable.setBounds(180, 310, 200, 125);

//ADDING ELEMENTS TO MAIN FRAME =============================================================================================
        frame.add(title);
        frame.add(informationBox);
        for(JButton but : optionButtons){
            frame.add(but);
        }
        frame.add(roundSelectorLabel);
        for(JRadioButton rbut : roundsRadioButtons){
            frame.add(rbut);
        }
        frame.add(scoreTable);
        frame.add(difficultySlider);
        frame.add(inputNameBox);
        frame.add(confirmButton);

//FRAME ON =============================================================================================================
        frame.setVisible(true);

    }
}
