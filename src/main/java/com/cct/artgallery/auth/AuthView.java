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
import java.awt.FontFormatException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Francisco Olivares
 */
public class AuthView{
    
    public AuthView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame window = new JFrame("CCT Art Gallery");
            
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
            CustomFonts customFont = new CustomFonts();
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
            loginPanel.add(Box.createRigidArea(new Dimension(0, 50)));
            
            //Create and add email JTextField            
            JTextField loginEmail = new JTextField(" Email");
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
            JTextField loginPassword = new JTextField(" Password");
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
            
            //Create and add register button
            JLabel loginRegister = new JLabel("Need an account?");
            loginRegister.setFont(roboto.deriveFont(Font.PLAIN, 10f));
            loginRegister.setForeground(Color.GRAY);
            
            loginRegister.setPreferredSize(new Dimension(80, 20));
            loginRegister.setMaximumSize(new Dimension(80, 20)); 
            loginRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginPanel.add(loginRegister);
            
            

            
            
            


            //Add login panel to content panel with custom margin top
            contentPanel.add(Box.createRigidArea(new Dimension(0, 80)));
            //contentPanel.add(l1);
            contentPanel.add(loginPanel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 80)));    

            
            



            window.validate();
            window.repaint();
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(AuthView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
