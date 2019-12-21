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
package com.cct.artgallery.admin;

import com.cct.artgallery.auth.LoginView;
import com.cct.artgallery.utils.TopBar;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Francisco Olivares
 */
public class AdminView {
    
    private JFrame window;
    public TopBar topBar;
    private AdminController controllerListener;
    private MenuAdmin menu;
    
    public AdminView(AdminController controller, MenuAdmin menu){
        this.controllerListener = controller;
        this.menu = menu;
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            window = new JFrame("CCT Art Gallery");
            
            //Set up top bar with custom buttons
            topBar = new TopBar(window);
            JPanel topPanel = topBar.getTopBar();
            
            //JFrame properties
            window.add(BorderLayout.NORTH, topPanel);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(1000, 700);
            window.setLocationRelativeTo(null);
            window.setUndecorated(true);
            window.setVisible(true);
            
            //Content Panel which contains menu and content.
            JPanel content = new JPanel();
            content.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            window.add(content);
            
            //Add menu left to the left content panel.
            content.add(menu);
            
            //Add ArtPiecePanel as default content to the right panel.
            JPanel mainContent = new ArtPiecePanel(controllerListener);       
            content.add(mainContent);
            

            window.validate();
            window.repaint();
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateContent(String menu){
        switch(menu) {
            case "artPiecesMenu":
                updateMainPanel(new ArtPiecePanel(controllerListener));
                break;
            case "artistsMenu":
                updateMainPanel(new ArtistPanel(controllerListener));
                break;
            case "usersMenu":
                updateMainPanel(new UserPanel(controllerListener));
                break;
            default:
                updateMainPanel(new ArtPiecePanel(controllerListener));
        }
        
        window.validate();
        window.repaint();
    }
    
    private void updateMainPanel(JPanel mainContent){
        window.getContentPane().remove(1);
        //Content Panel which contains menu and content.
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        window.add(content);

        //Add menu left to the left content panel.
        content.add(this.menu);

        //Add ArtPiecePanel as default content to the right panel.  
        content.add(mainContent);
    } 
    
}
