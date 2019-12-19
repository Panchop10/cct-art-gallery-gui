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

import com.cct.artgallery.utils.CustomFonts;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Francisco Olivares
 */
public class RegisterView{
    
    private JFrame window;
    private JTextField registerUsername;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField registerEmail;
    private JTextField registerPassword;
    private JTextField registerConfirmPassword;
    private AuthController controllerListener;
    
    public RegisterView(AuthController controller) {
        this.controllerListener = controller;
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            window = new JFrame("CCT Art Gallery");
            
            //Set up top bar with custom buttons
            TopBar topBar = new TopBar(window);
            JPanel topPanel = topBar.getTopBar();
            
            //JFrame properties
            window.add(BorderLayout.NORTH, topPanel);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(800, 500);
            window.setLocationRelativeTo(null);
            window.setUndecorated(true);
            window.setVisible(true);
            
            //Set background image to JFrame creating a JPanel
            URL login = getClass().getClassLoader().getResource("images/register.jpeg");
            JLabel contentPanel=new JLabel(new ImageIcon(login));
            window.add(contentPanel);
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            
            
            //Create Panel with login form 
            JPanel registerPanel = new JPanel();
            registerPanel.setBackground(Color.WHITE);
            registerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            registerPanel.setPreferredSize(new Dimension(300, 400));
            registerPanel.setMaximumSize(new Dimension(300, 400));
            registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
            
            //Call Roboto Font
            CustomFonts customFont = new CustomFonts();
            Font roboto = customFont.getRoboto();
            
            
            //Add Form inside login panel
            
            //Create and and Title
            JLabel registerTitle = new JLabel("Register", JLabel.CENTER);
            registerTitle.setForeground(Color.WHITE);
            registerTitle.setBackground(new Color(93, 188, 210));
            registerTitle.setFont(roboto.deriveFont(Font.PLAIN, 16f));
            registerTitle.setOpaque(true);
            registerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            registerTitle.setPreferredSize(new Dimension(220, 40));
            registerTitle.setMaximumSize(new Dimension(220, 40));
            registerPanel.add(registerTitle);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            
            //Create and add username JTextField            
            registerUsername = new JTextField(" Username");
            registerUsername.setForeground(Color.GRAY);
            registerUsername.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (registerUsername.getText().equals(" Username")) {
                        registerUsername.setText("");
                        registerUsername.setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (registerUsername.getText().isEmpty()) {
                        registerUsername.setForeground(Color.GRAY);
                        registerUsername.setText(" Username");
                    }
                }
                });           
            registerUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
            registerUsername.setPreferredSize(new Dimension(200, 30));
            registerUsername.setMaximumSize(new Dimension(200, 30));            
            registerPanel.add(registerUsername);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            
            //Create and add first name JTextField            
            firstName = new JTextField(" First Name");
            firstName.setForeground(Color.GRAY);
            firstName.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (firstName.getText().equals(" First Name")) {
                        firstName.setText("");
                        firstName.setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (firstName.getText().isEmpty()) {
                        firstName.setForeground(Color.GRAY);
                        firstName.setText(" First Name");
                    }
                }
                });           
            firstName.setAlignmentX(Component.CENTER_ALIGNMENT);
            firstName.setPreferredSize(new Dimension(200, 30));
            firstName.setMaximumSize(new Dimension(200, 30));            
            registerPanel.add(firstName);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            
            //Create and add last name JTextField            
            lastName = new JTextField(" Last Name");
            lastName.setForeground(Color.GRAY);
            lastName.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (lastName.getText().equals(" Last Name")) {
                        lastName.setText("");
                        lastName.setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (lastName.getText().isEmpty()) {
                        lastName.setForeground(Color.GRAY);
                        lastName.setText(" Last Name");
                    }
                }
                });           
            lastName.setAlignmentX(Component.CENTER_ALIGNMENT);
            lastName.setPreferredSize(new Dimension(200, 30));
            lastName.setMaximumSize(new Dimension(200, 30));            
            registerPanel.add(lastName);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            
            //Create and add email JTextField            
            registerEmail = new JTextField(" Email");
            registerEmail.setForeground(Color.GRAY);
            registerEmail.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (registerEmail.getText().equals(" Email")) {
                        registerEmail.setText("");
                        registerEmail.setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (registerEmail.getText().isEmpty()) {
                        registerEmail.setForeground(Color.GRAY);
                        registerEmail.setText(" Email");
                    }
                }
                });           
            registerEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
            registerEmail.setPreferredSize(new Dimension(200, 30));
            registerEmail.setMaximumSize(new Dimension(200, 30));            
            registerPanel.add(registerEmail);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            
            //Create and add password JTextField            
            registerPassword = new JTextField(" Password");
            registerPassword.setForeground(Color.GRAY);
            registerPassword.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (registerPassword.getText().equals(" Password")) {
                        registerPassword.setText("");
                        registerPassword.setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (registerPassword.getText().isEmpty()) {
                        registerPassword.setForeground(Color.GRAY);
                        registerPassword.setText(" Password");
                    }
                }
                });           
            registerPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
            registerPassword.setPreferredSize(new Dimension(200, 30));
            registerPassword.setMaximumSize(new Dimension(200, 30));            
            registerPanel.add(registerPassword);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            
            //Create and add confirm password JTextField            
            registerConfirmPassword = new JTextField(" Confirm Password");
            registerConfirmPassword.setForeground(Color.GRAY);
            registerConfirmPassword.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (registerConfirmPassword.getText().equals(" Confirm Password")) {
                        registerConfirmPassword.setText("");
                        registerConfirmPassword.setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (registerConfirmPassword.getText().isEmpty()) {
                        registerConfirmPassword.setForeground(Color.GRAY);
                        registerConfirmPassword.setText(" Confirm Password");
                    }
                }
                });           
            registerConfirmPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
            registerConfirmPassword.setPreferredSize(new Dimension(200, 30));
            registerConfirmPassword.setMaximumSize(new Dimension(200, 30));            
            registerPanel.add(registerConfirmPassword);
            registerPanel.add(Box.createRigidArea(new Dimension(0, 20)));   
            
            //Create and add Login button
            URL sumitButton = getClass().getClassLoader().getResource("images/register_submit.png");
            final JButton loginSubmit = new JButton(new ImageIcon(sumitButton));
            loginSubmit.setBorderPainted(false);            
            loginSubmit.setPreferredSize(new Dimension(90, 40));
            loginSubmit.setMaximumSize(new Dimension(90, 40)); 
            loginSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
            registerPanel.add(loginSubmit);
            
            //Add listener to login submit button
            loginSubmit.addActionListener((ActionListener) controllerListener);
            loginSubmit.setActionCommand("loginSubmit");
            
            //Create and add register button
            JButton loginRegister = new JButton("Back to login");
            loginRegister.setBorderPainted(false);
            loginRegister.setFont(roboto.deriveFont(Font.PLAIN, 10f));
            loginRegister.setForeground(Color.GRAY);            
            loginRegister.setPreferredSize(new Dimension(130, 20));
            loginRegister.setMaximumSize(new Dimension(130, 20)); 
            loginRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
            registerPanel.add(loginRegister);
            
            //Add listener to login submit button
            loginRegister.addActionListener((ActionListener) controllerListener);
            loginRegister.setActionCommand("registerToLogin");
   
            //Add login panel to content panel with custom margin top
            contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            //contentPanel.add(l1);
            contentPanel.add(registerPanel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));    

            window.validate();
            window.repaint();
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(RegisterView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Close and remove Register JFrame from memory.
     */
    public void dispose(){
        window.dispose();
    }
}
