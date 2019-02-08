package tictactoe;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TicTacToe extends javax.swing.JFrame {

    private final Player PLAYER;
    private InGame inGame;
    private final String xSound = "1.wav";
    private final String oSound = "2.wav";
    private final String winSound = "alert.wav";
    private final String loseSound = "beep-05.wav";
    private final String challengeSound = "challenge.wav";
    final String DIR = System.getProperty("user.dir");

    public TicTacToe() {
        initComponents();

        PLAYER = new Player();
        inGame = new InGame();

        initiateElement();
    }

    private void initiateElement() {
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);

//        int dialogResult = JOptionPane.showConfirmDialog(this,
//                "Do You want to play over lan ?",
//                "Warning", JOptionPane.YES_NO_OPTION);
//        if (dialogResult == JOptionPane.YES_OPTION) {
//            String ip = JOptionPane.showInputDialog("Please input Player 1 Name: ", "Player1");
//            String port = JOptionPane.showInputDialog("Please input Player 1 Name: ", "Player1");
//
//            String name = JOptionPane.showInputDialog("Please input Player 1 Name: ", "Player1");
//        } else {
        PLAYER.setPlayerName(0, JOptionPane.showInputDialog("Please input Player 1 Name: ", "Player1"));
        PLAYER.setPlayerName(1, JOptionPane.showInputDialog("Please input Player 2 Name: ", "Player2"));
//        }
        PLAYER.resetWinCount();

        jLabelPlayer1Name.setText(PLAYER.getPlayerName()[0].toUpperCase());
        jLabelPlayer2Name.setText(PLAYER.getPlayerName()[1].toUpperCase());
        showLabel();
        resetAll(true);
    }

    private void resetAll(boolean enabled) {
        jLabelX.setText(String.valueOf(PLAYER.getWinCount()[0]));
        jLabelTie.setText(String.valueOf(PLAYER.getWinCount()[1]));
        jLabelO.setText(String.valueOf(PLAYER.getWinCount()[2]));

        jButtonA1.setEnabled(enabled);
        jButtonB1.setEnabled(enabled);
        jButtonC1.setEnabled(enabled);

        jButtonA2.setEnabled(enabled);
        jButtonB2.setEnabled(enabled);
        jButtonC2.setEnabled(enabled);

        jButtonA3.setEnabled(enabled);
        jButtonB3.setEnabled(enabled);
        jButtonC3.setEnabled(enabled);

        if (enabled) {
            jButtonA1.setText("");
            jButtonB1.setText("");
            jButtonC1.setText("");

            jButtonA2.setText("");
            jButtonB2.setText("");
            jButtonC2.setText("");

            jButtonA3.setText("");
            jButtonB3.setText("");
            jButtonC3.setText("");
        }
    }

    private void showLabel() {
        jLabel1.setText(
                ((inGame.getPlayerTurn().equalsIgnoreCase("X")
                        ? PLAYER.getPlayerName()[0]
                        : PLAYER.getPlayerName()[1]) + " (" + inGame.getPlayerTurn() + ") turn \n").toUpperCase());
        jLabel1.setForeground((inGame.getPlayerTurn().equalsIgnoreCase("X") ? Color.red : Color.blue));
    }

    private void showMessage(String who, boolean win) {
        String message;
        if (win) {
            if (who.equalsIgnoreCase("X")) {
                PLAYER.setWinCount(0);
            } else {
                PLAYER.setWinCount(2);
            }
            message = "Player " + who + " win";
        } else {
            message = "Draw";
            PLAYER.setWinCount(1);
            resetAll(true);
        }
        inGame.setGameCount();
        if (inGame.getGameCount() == 5) {
            playSound(loseSound);
            int dialogResult = JOptionPane.showConfirmDialog(this,
                    "Game Over \n"
                    + PLAYER.getWinner() + "\n"
                    + "Do You want to play again ?",
                    "Warning", JOptionPane.YES_NO_OPTION
            );
            if (dialogResult == JOptionPane.YES_OPTION) {
                PLAYER.resetWinCount();
                inGame = new InGame();
                showLabel();
            } else {
                System.exit(0);
            }
        } else {
            playSound(winSound);
            JOptionPane.showMessageDialog(this,
                    message, "Round " + inGame.getGameCount() + " result", JOptionPane.INFORMATION_MESSAGE);
        }
        resetAll(true);
    }

    private void determineWhoseTurn(JButton button) {
        if (inGame.getPlayerTurn().equalsIgnoreCase("X")) {
            button.setForeground(Color.red);
//            playSound(xSound);
        } else {
            button.setForeground(Color.blue);
//            playSound(oSound);
        }
        button.setText(inGame.getPlayerTurn());
        button.setEnabled(false);
        button.setBackground(Color.black);
        determineWhoseWin();
        inGame.setPlayerTurn(inGame.getPlayerTurn().equalsIgnoreCase("X") ? "O" : "X");
        showLabel();
    }

    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(DIR + "\\audio\\" + soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            System.out.println("Error with playing sound. " + soundName);
        }
    }

    private void determineWhoseWin() {
        inGame.setChoose(0, jButtonA1.getText());
        inGame.setChoose(1, jButtonB1.getText());
        inGame.setChoose(2, jButtonC1.getText());

        inGame.setChoose(3, jButtonA2.getText());
        inGame.setChoose(4, jButtonB2.getText());
        inGame.setChoose(5, jButtonC2.getText());

        inGame.setChoose(6, jButtonA3.getText());
        inGame.setChoose(7, jButtonB3.getText());
        inGame.setChoose(8, jButtonC3.getText());

        //This condition for checking who is the winner
        if ((inGame.getChoose()[0].equalsIgnoreCase(inGame.getChoose()[1])
                && inGame.getChoose()[0].equalsIgnoreCase(inGame.getChoose()[2]))
                && !inGame.getChoose()[0].equalsIgnoreCase("")) {
            showMessage(inGame.getChoose()[0], true);
            return;
        }
        if (((inGame.getChoose()[0].equalsIgnoreCase(inGame.getChoose()[4])
                && inGame.getChoose()[0].equalsIgnoreCase(inGame.getChoose()[8]))
                && !inGame.getChoose()[0].equalsIgnoreCase(""))) {
            showMessage(inGame.getChoose()[0], true);
            return;
        }
        if (((inGame.getChoose()[0].equalsIgnoreCase(inGame.getChoose()[3])
                && inGame.getChoose()[0].equalsIgnoreCase(inGame.getChoose()[6]))
                && !inGame.getChoose()[0].equalsIgnoreCase(""))) {
            showMessage(inGame.getChoose()[0], true);
            return;
        }
        if (((inGame.getChoose()[1].equalsIgnoreCase(inGame.getChoose()[4])
                && inGame.getChoose()[1].equalsIgnoreCase(inGame.getChoose()[7]))
                && !inGame.getChoose()[1].equalsIgnoreCase(""))) {
            showMessage(inGame.getChoose()[1], true);
            return;
        }
        if (((inGame.getChoose()[2].equalsIgnoreCase(inGame.getChoose()[4])
                && inGame.getChoose()[2].equalsIgnoreCase(inGame.getChoose()[6]))
                && !inGame.getChoose()[2].equalsIgnoreCase(""))) {
            showMessage(inGame.getChoose()[2], true);
            return;
        }
        if (((inGame.getChoose()[2].equalsIgnoreCase(inGame.getChoose()[5])
                && inGame.getChoose()[2].equalsIgnoreCase(inGame.getChoose()[8]))
                && !inGame.getChoose()[2].equalsIgnoreCase(""))) {
            showMessage(inGame.getChoose()[2], true);
            return;
        }
        if (((inGame.getChoose()[3].equalsIgnoreCase(inGame.getChoose()[4])
                && inGame.getChoose()[3].equalsIgnoreCase(inGame.getChoose()[5]))
                && !inGame.getChoose()[3].equalsIgnoreCase(""))) {
            showMessage(inGame.getChoose()[3], true);
            return;
        }
        if (((inGame.getChoose()[6].equalsIgnoreCase(inGame.getChoose()[7])
                && inGame.getChoose()[6].equalsIgnoreCase(inGame.getChoose()[8]))
                && !inGame.getChoose()[6].equalsIgnoreCase(""))) {
            showMessage(inGame.getChoose()[6], true);
            return;
        }

        //This for checking if the result is draw
        int filledButton = 0;
        for (String click : inGame.getChoose()) {
            if (!click.equalsIgnoreCase("")) {
                filledButton++;
            }
        }
        if (filledButton > 8) {
            showMessage(inGame.getChoose()[6], false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanelGridHolder = new javax.swing.JPanel();
        jPanelA1 = new javax.swing.JPanel();
        jButtonA1 = new javax.swing.JButton();
        jPanelB1 = new javax.swing.JPanel();
        jButtonB1 = new javax.swing.JButton();
        jPanelC1 = new javax.swing.JPanel();
        jButtonC1 = new javax.swing.JButton();
        jPanelA2 = new javax.swing.JPanel();
        jButtonA2 = new javax.swing.JButton();
        jPanelB2 = new javax.swing.JPanel();
        jButtonB2 = new javax.swing.JButton();
        jPanelC2 = new javax.swing.JPanel();
        jButtonC2 = new javax.swing.JButton();
        jPanelA3 = new javax.swing.JPanel();
        jButtonA3 = new javax.swing.JButton();
        jPanelB3 = new javax.swing.JPanel();
        jButtonB3 = new javax.swing.JButton();
        jPanelC3 = new javax.swing.JPanel();
        jButtonC3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelX = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelPlayer1Name = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabelTie = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelO = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelPlayer2Name = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        jPanelMain.setBackground(new java.awt.Color(0, 0, 0));
        jPanelMain.setLayout(new java.awt.BorderLayout(1, 1));

        jPanelGridHolder.setBackground(new java.awt.Color(0, 0, 0));
        jPanelGridHolder.setLayout(new java.awt.GridLayout(4, 3));

        jPanelA1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelA1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelA1.setLayout(new java.awt.BorderLayout());

        jButtonA1.setBackground(new java.awt.Color(0, 0, 0));
        jButtonA1.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        jButtonA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonA1ActionPerformed(evt);
            }
        });
        jPanelA1.add(jButtonA1, java.awt.BorderLayout.CENTER);

        jPanelGridHolder.add(jPanelA1);

        jPanelB1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelB1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelB1.setLayout(new java.awt.BorderLayout());

        jButtonB1.setBackground(new java.awt.Color(0, 0, 0));
        jButtonB1.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        jButtonB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonB1ActionPerformed(evt);
            }
        });
        jPanelB1.add(jButtonB1, java.awt.BorderLayout.CENTER);

        jPanelGridHolder.add(jPanelB1);

        jPanelC1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelC1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelC1.setLayout(new java.awt.BorderLayout());

        jButtonC1.setBackground(new java.awt.Color(0, 0, 0));
        jButtonC1.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        jButtonC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC1ActionPerformed(evt);
            }
        });
        jPanelC1.add(jButtonC1, java.awt.BorderLayout.CENTER);

        jPanelGridHolder.add(jPanelC1);

        jPanelA2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelA2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelA2.setLayout(new java.awt.BorderLayout());

        jButtonA2.setBackground(new java.awt.Color(0, 0, 0));
        jButtonA2.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        jButtonA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonA2ActionPerformed(evt);
            }
        });
        jPanelA2.add(jButtonA2, java.awt.BorderLayout.CENTER);

        jPanelGridHolder.add(jPanelA2);

        jPanelB2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelB2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelB2.setLayout(new java.awt.BorderLayout());

        jButtonB2.setBackground(new java.awt.Color(0, 0, 0));
        jButtonB2.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        jButtonB2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonB2ActionPerformed(evt);
            }
        });
        jPanelB2.add(jButtonB2, java.awt.BorderLayout.CENTER);

        jPanelGridHolder.add(jPanelB2);

        jPanelC2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelC2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelC2.setLayout(new java.awt.BorderLayout());

        jButtonC2.setBackground(new java.awt.Color(0, 0, 0));
        jButtonC2.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        jButtonC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC2ActionPerformed(evt);
            }
        });
        jPanelC2.add(jButtonC2, java.awt.BorderLayout.CENTER);

        jPanelGridHolder.add(jPanelC2);

        jPanelA3.setBackground(new java.awt.Color(255, 255, 255));
        jPanelA3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelA3.setLayout(new java.awt.BorderLayout());

        jButtonA3.setBackground(new java.awt.Color(0, 0, 0));
        jButtonA3.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        jButtonA3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonA3ActionPerformed(evt);
            }
        });
        jPanelA3.add(jButtonA3, java.awt.BorderLayout.CENTER);

        jPanelGridHolder.add(jPanelA3);

        jPanelB3.setBackground(new java.awt.Color(255, 255, 255));
        jPanelB3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelB3.setLayout(new java.awt.BorderLayout());

        jButtonB3.setBackground(new java.awt.Color(0, 0, 0));
        jButtonB3.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        jButtonB3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonB3ActionPerformed(evt);
            }
        });
        jPanelB3.add(jButtonB3, java.awt.BorderLayout.CENTER);

        jPanelGridHolder.add(jPanelB3);

        jPanelC3.setBackground(new java.awt.Color(255, 255, 255));
        jPanelC3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelC3.setLayout(new java.awt.BorderLayout());

        jButtonC3.setBackground(new java.awt.Color(0, 0, 0));
        jButtonC3.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        jButtonC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC3ActionPerformed(evt);
            }
        });
        jPanelC3.add(jButtonC3, java.awt.BorderLayout.CENTER);

        jPanelGridHolder.add(jPanelC3);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabelX.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabelX.setForeground(java.awt.Color.red);
        jLabelX.setText("0");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(java.awt.Color.red);
        jLabel6.setText("PLAYER 1 (X)");

        jLabelPlayer1Name.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelPlayer1Name.setForeground(java.awt.Color.red);
        jLabelPlayer1Name.setText("name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabelX))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPlayer1Name)
                            .addComponent(jLabel6))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelPlayer1Name, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelX)
                .addContainerGap())
        );

        jPanelGridHolder.add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabelTie.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabelTie.setForeground(java.awt.Color.green);
        jLabelTie.setText("0");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(java.awt.Color.green);
        jLabel7.setText("TIE");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabelTie))
                .addGap(71, 71, 71))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTie)
                .addContainerGap())
        );

        jPanelGridHolder.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        jLabelO.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabelO.setForeground(java.awt.Color.blue);
        jLabelO.setText("0");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(java.awt.Color.blue);
        jLabel8.setText("PLAYER 2 (O)");

        jLabelPlayer2Name.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelPlayer2Name.setForeground(java.awt.Color.blue);
        jLabelPlayer2Name.setText("name");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelO)
                        .addGap(75, 75, 75))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabelPlayer2Name))
                        .addGap(27, 27, 27))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPlayer2Name, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelO)
                .addContainerGap())
        );

        jPanelGridHolder.add(jPanel3);

        jPanelMain.add(jPanelGridHolder, java.awt.BorderLayout.CENTER);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 255, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("jLabel1");
        jPanelMain.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanelMain, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonA1ActionPerformed
        determineWhoseTurn(jButtonA1);
    }//GEN-LAST:event_jButtonA1ActionPerformed

    private void jButtonB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonB1ActionPerformed
        determineWhoseTurn(jButtonB1);
    }//GEN-LAST:event_jButtonB1ActionPerformed

    private void jButtonC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC1ActionPerformed
        determineWhoseTurn(jButtonC1);
    }//GEN-LAST:event_jButtonC1ActionPerformed

    private void jButtonA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonA2ActionPerformed
        determineWhoseTurn(jButtonA2);
    }//GEN-LAST:event_jButtonA2ActionPerformed

    private void jButtonB2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonB2ActionPerformed
        determineWhoseTurn(jButtonB2);
    }//GEN-LAST:event_jButtonB2ActionPerformed

    private void jButtonC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC2ActionPerformed
        determineWhoseTurn(jButtonC2);
    }//GEN-LAST:event_jButtonC2ActionPerformed

    private void jButtonA3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonA3ActionPerformed
        determineWhoseTurn(jButtonA3);
    }//GEN-LAST:event_jButtonA3ActionPerformed

    private void jButtonB3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonB3ActionPerformed
        determineWhoseTurn(jButtonB3);
    }//GEN-LAST:event_jButtonB3ActionPerformed

    private void jButtonC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC3ActionPerformed
        determineWhoseTurn(jButtonC3);
    }//GEN-LAST:event_jButtonC3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TicTacToe().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonA1;
    private javax.swing.JButton jButtonA2;
    private javax.swing.JButton jButtonA3;
    private javax.swing.JButton jButtonB1;
    private javax.swing.JButton jButtonB2;
    private javax.swing.JButton jButtonB3;
    private javax.swing.JButton jButtonC1;
    private javax.swing.JButton jButtonC2;
    private javax.swing.JButton jButtonC3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelO;
    private javax.swing.JLabel jLabelPlayer1Name;
    private javax.swing.JLabel jLabelPlayer2Name;
    private javax.swing.JLabel jLabelTie;
    private javax.swing.JLabel jLabelX;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelA1;
    private javax.swing.JPanel jPanelA2;
    private javax.swing.JPanel jPanelA3;
    private javax.swing.JPanel jPanelB1;
    private javax.swing.JPanel jPanelB2;
    private javax.swing.JPanel jPanelB3;
    private javax.swing.JPanel jPanelC1;
    private javax.swing.JPanel jPanelC2;
    private javax.swing.JPanel jPanelC3;
    private javax.swing.JPanel jPanelGridHolder;
    private javax.swing.JPanel jPanelMain;
    // End of variables declaration//GEN-END:variables
}
