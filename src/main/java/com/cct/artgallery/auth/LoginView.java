/*
 * The MIT License
 *
 * Copyright 2019 Francisco Olivares.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.cct.artgallery.auth;

import com.cct.artgallery.utils.CustomFont;
import com.cct.artgallery.utils.TopBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Francisco Olivares
 */
public class LoginView{
    
    private JFrame          window;
    private JTextField      loginEmail;
    private JPasswordField  loginPassword;
    private AuthController  controllerListener;
    private JLabel          errorLabel;
    private JLabel          sucessLabel;
    public  TopBar          topBar;
    
    public LoginView(AuthController controller) {
        this.controllerListener = controller;
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            window = new JFrame("CCT Art Gallery");
            
            //Set up top bar with custom buttons
            topBar = new TopBar(window);
            JPanel topPanel = topBar.getTopBar();
            
            //JFrame properties
            window.add(BorderLayout.NORTH, topPanel);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(800, 500);
            window.setLocationRelativeTo(null);
            window.setUndecorated(true);
            window.setVisible(true);
            
            //Set background image to JFrame creating a JPanel
            URL login = getClass().getClassLoader().getResource("images/login.jpeg");
            JLabel contentPanel=new JLabel(new ImageIcon(login));
            window.add(contentPanel);
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            
            
            //Create Panel with login form 
            JPanel loginPanel = new JPanel();
            loginPanel.setBackground(Color.WHITE);
            loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginPanel.setPreferredSize(new Dimension(300, 400));
            loginPanel.setMaximumSize(new Dimension(300, 400));
            loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
            
            //Call Roboto Font
            CustomFont customFont = new CustomFont();
            Font roboto = customFont.getRoboto();
            
            
            //Add Form inside login panel
            
            //Create and and Title
            JLabel loginTitle = new JLabel("Log in", JLabel.CENTER);
            loginTitle.setForeground(Color.WHITE);
            loginTitle.setBackground(new Color(93, 188, 210));
            loginTitle.setFont(roboto.deriveFont(Font.PLAIN, 16f));
            loginTitle.setOpaque(true);
            loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginTitle.setPreferredSize(new Dimension(220, 40));
            loginTitle.setMaximumSize(new Dimension(220, 40));
            loginPanel.add(loginTitle);
            loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            
            //Create Error JLabel
            errorLabel = new JLabel("", JLabel.CENTER);
            errorLabel.setForeground(Color.RED);
            errorLabel.setFont(roboto.deriveFont(Font.PLAIN, 10f));
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            errorLabel.setPreferredSize(new Dimension(200, 20));
            errorLabel.setMaximumSize(new Dimension(200, 20));
            loginPanel.add(errorLabel);
            
            //Create Sucess JLabel
            sucessLabel = new JLabel("", JLabel.CENTER);
            sucessLabel.setForeground(new Color(0, 100, 0));
            sucessLabel.setFont(roboto.deriveFont(Font.PLAIN, 10f));
            sucessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            sucessLabel.setPreferredSize(new Dimension(200, 20));
            sucessLabel.setMaximumSize(new Dimension(200, 20));
            loginPanel.add(sucessLabel);
            loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            
            //Create and add email JTextField            
            loginEmail = new JTextField(" Email");
            loginEmail.setFont(roboto.deriveFont(Font.PLAIN, 12f));
            loginEmail.setForeground(Color.GRAY);
            loginEmail.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (loginEmail.getText().equals(" Email")) {
                        loginEmail.setText("");
                        loginEmail.setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (loginEmail.getText().isEmpty()) {
                        loginEmail.setForeground(Color.GRAY);
                        loginEmail.setText(" Email");
                    }
                }
                });           
            loginEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginEmail.setPreferredSize(new Dimension(200, 30));
            loginEmail.setMaximumSize(new Dimension(200, 30));            
            loginPanel.add(loginEmail);
            loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            
            //Create and add password JTextField            
            loginPassword = new JPasswordField(" Password");
            loginPassword.setForeground(Color.GRAY);
            loginPassword.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (loginPassword.getText().equals(" Password")) {
                        loginPassword.setText("");
                        loginPassword.setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (loginPassword.getText().isEmpty()) {
                        loginPassword.setForeground(Color.GRAY);
                        loginPassword.setText(" Password");
                    }
                }
                });           
            loginPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginPassword.setPreferredSize(new Dimension(200, 30));
            loginPassword.setMaximumSize(new Dimension(200, 30));            
            loginPanel.add(loginPassword);
            loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));       
            
            //Create and add Login button
            URL sumitButton = getClass().getClassLoader().getResource("images/login_submit.png");
            final JButton loginSubmit = new JButton(new ImageIcon(sumitButton));
            loginSubmit.setBorderPainted(false);            
            loginSubmit.setPreferredSize(new Dimension(90, 40));
            loginSubmit.setMaximumSize(new Dimension(90, 40)); 
            loginSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginPanel.add(loginSubmit);
            
            //Add listener to login submit button
            loginSubmit.addActionListener((ActionListener) controllerListener);
            loginSubmit.setActionCommand("loginSubmit");
            
            //Create and add register button
            JButton loginRegister = new JButton("Need an account?");
            loginRegister.setBorderPainted(false);
            loginRegister.setFont(roboto.deriveFont(Font.PLAIN, 10f));
            loginRegister.setForeground(Color.GRAY);            
            loginRegister.setPreferredSize(new Dimension(130, 20));
            loginRegister.setMaximumSize(new Dimension(130, 20)); 
            loginRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginPanel.add(loginRegister);
            
            //Add listener to login submit button
            loginRegister.addActionListener((ActionListener) controllerListener);
            loginRegister.setActionCommand("loginRegister");
   
            //Add login panel to content panel with custom margin top
            contentPanel.add(Box.createRigidArea(new Dimension(0, 80)));
            //contentPanel.add(l1);
            contentPanel.add(loginPanel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 80)));    

            window.validate();
            window.repaint();
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @return HashMap with the email and password entered in the JTextField
     * of the view.
     */
    public Map<String, String> getData(){
        Map<String, String> data = new HashMap<>();
        data.put("email", loginEmail.getText());
        data.put("password", new String(loginPassword.getPassword()));
        
        return data;
    }
    
    /**
     * 
     * @param message Receive an String and shows during 3 seconds a JLabel 
     * with the message as an error.
     */
    public void showError(String message){
        new Thread(() -> {
            errorLabel.setText(message);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
            }
            errorLabel.setText("");
        }).start();
    }
    
    /**
     * 
     * @param message Receive an String and shows during 3 seconds a JLabel 
     * with the message as an error.
     */
    public void showSucess(String message){
        new Thread(() -> {
            sucessLabel.setText(message);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
            }
            sucessLabel.setText("");
        }).start();
    }
    
    /**
     * Hide JFrame by setting visible = false
     */
    public void hide(){
        window.setVisible(false);
    }
    
    /**
     * Show JFrame by setting visible = true
     */
    public void show(){
        window.setVisible(true);
    }
}
