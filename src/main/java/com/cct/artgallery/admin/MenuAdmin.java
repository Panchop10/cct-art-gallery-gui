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

import com.cct.artgallery.utils.CustomFont;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 *
 * @author Francisco Olivares
 */
public class MenuAdmin extends JLabel {
    
    private static JButton artPiecesMenu;
    private static JButton artistsMenu;
    private static JButton usersMenu;
    private final AdminController controllerListener;
  
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MenuAdmin(AdminController controller){
        this.controllerListener = controller;
        
        //Call Roboto Font
        CustomFont customFont = new CustomFont();
        Font roboto = customFont.getRoboto();
        
        //Create Panel with menu admin 
        URL login = getClass().getClassLoader().getResource("images/menu_admin.png");
        this.setIcon(new ImageIcon(login));          
        this.setPreferredSize(new Dimension(200, 660));
        this.setMaximumSize(new Dimension(200, 660));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Menu Title
        JLabel titleMenu = new JLabel("CCT ART GALLERY", JLabel.CENTER);
        titleMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleMenu.setForeground(Color.WHITE);
        titleMenu.setFont(roboto.deriveFont(Font.PLAIN, 16f));
        titleMenu.setPreferredSize(new Dimension(200, 50));
        titleMenu.setMaximumSize(new Dimension(200, 50));
        this.add(titleMenu);

        // Separator Line
        JSeparator line = new JSeparator();
        line.setAlignmentX(Component.CENTER_ALIGNMENT);
        line.setPreferredSize(new Dimension(180, 10));
        line.setMaximumSize(new Dimension(180, 10));
        this.add(line);
        this.add(Box.createRigidArea(new Dimension(0, 20))); 

        //ArtPieces button
        artPiecesMenu = new JButton("   Art Pieces");
        URL artPiecesIcon = getClass().getClassLoader().getResource("images/artpieces_icon.png");
        artPiecesMenu.setIcon(new ImageIcon(artPiecesIcon));
        artPiecesMenu.setHorizontalAlignment(SwingConstants.LEFT);
        artPiecesMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        artPiecesMenu.setFont(roboto.deriveFont(Font.PLAIN, 14f));
        artPiecesMenu.setForeground(Color.WHITE);
        artPiecesMenu.setBackground(new Color(241, 164, 66));
        artPiecesMenu.setOpaque(true);
        artPiecesMenu.setContentAreaFilled(true);
        artPiecesMenu.setBorderPainted(false);
        artPiecesMenu.setPreferredSize(new Dimension(180, 50));
        artPiecesMenu.setMaximumSize(new Dimension(180, 50));
        
        //Add listener to menu option
        artPiecesMenu.addActionListener((ActionListener) controllerListener);
        artPiecesMenu.setActionCommand("artPiecesMenu");
        this.add(artPiecesMenu);

        //ArtPieces button
        artistsMenu = new JButton("   Artists");
        URL artistsIcon = getClass().getClassLoader().getResource("images/artists_icon.png");
        artistsMenu.setIcon(new ImageIcon(artistsIcon));
        artistsMenu.setHorizontalAlignment(SwingConstants.LEFT);
        artistsMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        artistsMenu.setFont(roboto.deriveFont(Font.PLAIN, 14f));
        artistsMenu.setForeground(Color.WHITE);
        artistsMenu.setBackground(new Color(241, 164, 66));
        artistsMenu.setOpaque(true);
        artistsMenu.setContentAreaFilled(false);
        artistsMenu.setBorderPainted(false);
        artistsMenu.setPreferredSize(new Dimension(180, 50));
        artistsMenu.setMaximumSize(new Dimension(180, 50));
        
        //Add listener to menu option
        artistsMenu.addActionListener((ActionListener) controllerListener);
        artistsMenu.setActionCommand("artistsMenu");
        this.add(artistsMenu);

        //ArtPieces button
        usersMenu = new JButton("   Admin Users");
        URL usersIcon = getClass().getClassLoader().getResource("images/users_icon.png");
        usersMenu.setIcon(new ImageIcon(usersIcon));
        usersMenu.setHorizontalAlignment(SwingConstants.LEFT);
        usersMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        usersMenu.setFont(roboto.deriveFont(Font.PLAIN, 14f));
        usersMenu.setForeground(Color.WHITE);
        usersMenu.setBackground(new Color(241, 164, 66));
        usersMenu.setOpaque(true);
        usersMenu.setContentAreaFilled(false);
        usersMenu.setBorderPainted(false);
        usersMenu.setPreferredSize(new Dimension(180, 50));
        usersMenu.setMaximumSize(new Dimension(180, 50));
        
        //Add listener to menu option
        usersMenu.addActionListener((ActionListener) controllerListener);
        usersMenu.setActionCommand("usersMenu");
        this.add(usersMenu);
    }
    
    public void setActive(String button){
        artPiecesMenu.setContentAreaFilled(false);
        artistsMenu.setContentAreaFilled(false);
        usersMenu.setContentAreaFilled(false);
        
        switch (button) {
            case "artPiecesMenu":
                artPiecesMenu.setContentAreaFilled(true);
                break;
            case "artistsMenu":
                artistsMenu.setContentAreaFilled(true);
                break;
            case "usersMenu":
                usersMenu.setContentAreaFilled(true);
                break;
            default:
                break;
        }
    }
}
