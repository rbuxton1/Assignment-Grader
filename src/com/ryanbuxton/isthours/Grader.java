/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ryanbuxton.isthours;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *
 * @author ryan
 */
public class Grader extends javax.swing.JFrame {
    private String prefix = "";
    private String testFile = "";
    private String curr = "";
    private String configSet[] = new String[4];

    /**
     * Creates new form Grader
     */
    public Grader() {
        initComponents();
        
        curr = Paths.get("").toAbsolutePath().toString();
        System.out.println("Working in " + curr);
        try{
            File config = new File(curr + File.separator + "config.txt");
            if(config.createNewFile()){
                System.out.println("\t Config didnt exist, auto creating.");
                JOptionPane.showMessageDialog(null, 
                              "Config file issue, contact your teacher.", 
                              "Config Issue", 
                              JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            } else {
                System.out.println("\t Config exists.");
                Scanner in =  new Scanner(config);
                String sample = "";
                for(int i = 0; i < 4; i++){
                    configSet[i] = in.nextLine();
                    sample += i + ") " + configSet[i] + ", ";
                }
                System.out.println(sample);
                
                System.out.println("Configs loaded!");
                Scanner names = new Scanner(new URL(configSet[0]).openStream());
                while(names.hasNext()){
                    assignmentSelector.addItem(names.nextLine());
                }
                
            }
        } catch(Exception e) {
            System.out.println("Error: " + e);
            JOptionPane.showMessageDialog(null, 
                              "Please contact your teacher.\nERR: " + e, 
                              "Initialization error", 
                              JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            if(configSet[3].equals("")){
                JOptionPane.showMessageDialog(null, 
                              "Config file issue, contact your teacher. System path not set, therefore compilation will not work.", 
                              "Config Issue", 
                              JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            try{
                prefix = "cmd /c ";
                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec(prefix + "SET PATH=%PATH%;" + configSet[3]);
                System.out.println("Set path variables.");
            } catch(Exception e){
                System.out.println("Uhoh " + e);
            }
        }
        
        if(configSet[2].equals("")) submitButton.setEnabled(false);
        
        testButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    String currProb = assignmentSelector.getSelectedItem().toString();
                    System.out.println("Testing " + currProb);
                
                    Runtime rt = Runtime.getRuntime();
                    String cmd1;
                    if(System.getProperty("os.name").toLowerCase().contains("win")) cmd1 = prefix + '"' + configSet[3] + "/javac" + '"' + " " + '"' + testFile + '"';
                    else cmd1 = "javac " + testFile;
                    Process p = rt.exec(cmd1);
                    System.out.print("\t Executing '" + cmd1 + "' ... ");
                    System.out.println(p.waitFor());
                    
                    Scanner casesIn = new Scanner(new URL(configSet[1] + currProb + "IN.txt").openStream());
                    String cases = casesIn.nextLine();
                    String cmd2 = prefix + "java -cp " + '"' + testFile.substring(0, testFile.lastIndexOf(File.separator) + 1) + + '"' + " " + testFile.substring(testFile.lastIndexOf(File.separator) + 1).replace(".java", "") + " " + cases;
                    p = rt.exec(cmd2);
                    System.out.println("\t Executing '" + cmd2 + "'...");
                    
                    Scanner output = new Scanner(new BufferedReader(new InputStreamReader(p.getInputStream())));
                    String out = output.nextLine();
                    System.out.println("\t Done! Returned: '" + out + "'");
                    
                    JOptionPane.showMessageDialog(null, 
                              "Running '" + currProb + "' with conditions '" + cases + "' . . . \n\n" +
                                      "Returned values: '" + out + "'", 
                              "Test output", 
                              JOptionPane.PLAIN_MESSAGE);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(null, 
                              "Please contact your teacher.\nERR: " + ex, 
                              "Execution error", 
                              JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        assignmentSelector = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        selectButton = new javax.swing.JButton();
        locationText = new javax.swing.JLabel();
        testButton = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Application Grader");
        setResizable(false);

        jLabel2.setText("Unit to grade:");

        selectButton.setText("Select Main File");
        selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectButtonMouseClicked(evt);
            }
        });
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        locationText.setText("File not Selected");
        locationText.setMaximumSize(new java.awt.Dimension(50, 16));

        testButton.setText("Test");

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Application Grader");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(locationText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(assignmentSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(testButton)
                                .addGap(149, 149, 149)
                                .addComponent(submitButton)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(assignmentSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectButton)
                    .addComponent(locationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitButton)
                    .addComponent(testButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectButtonActionPerformed
    
    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_submitButtonActionPerformed

    private void selectButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectButtonMouseClicked
       //popout for directory
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.FILES_ONLY); 
        f.showSaveDialog(null);
        
        locationText.setText(f.getSelectedFile().toString() + "");
        testFile = f.getSelectedFile().toString() + "";
    }//GEN-LAST:event_selectButtonMouseClicked

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Grader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Grader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Grader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Grader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Grader().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> assignmentSelector;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel locationText;
    private javax.swing.JButton selectButton;
    private javax.swing.JButton submitButton;
    private javax.swing.JButton testButton;
    // End of variables declaration//GEN-END:variables
}
