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

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Francisco Olivares
 */
public class AuthView  extends JFrame{
    
    /**
     * @param tf1       Manage text field
     */
    
    private JTextField tf1;
    private JTextField tf2;
    private JTextField tf3;
    private AuthController controllerInternalRef; 
    
    public AuthView(AuthController controller){
        this.controllerInternalRef = controller;
        attributesSetter();
        components();
        validation();
        
    }
    
    // Separating my attributes in method. Just to make more neat
    private void attributesSetter(){
        this.setTitle("CCT Art Gallery");
        this.setSize(800,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
        this.setUndecorated (true);
        this.setResizable(false);
        this.setVisible (true); // move setVisible to the end
    }
    
    // Separating components in method. Just to make it more neat
    private void components(){
        JPanel p = new JPanel();
        this.add(p);
        
        tf1 = new JTextField(20);
        tf2 = new JTextField(20);
        tf3 = new JTextField(20);
        JButton button = new JButton("Ener!");
        button.addActionListener((ActionListener) controllerInternalRef);
        button.setActionCommand("b");
        
        p.add(tf1);
        p.add(tf2);
        p.add(button);
        p.add(tf3);
        
    }
    
    private void validation(){
        this.validate();
        this.repaint();
    }
    
}
