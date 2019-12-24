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
package com.cct.artgallery.public_users;

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
public class MenuPublic extends JLabel {
    
    private static JButton artPiecesMenu;
    private static JButton artistsMenu;
    private static JButton favouriteMenu;
    private static JButton profileMenu;
    private static JButton logoutMenu;
    private final PublicController controllerListener;
  
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MenuPublic(PublicController controller){
        this.controllerListener = controller;
        
        //Call Roboto Font
        CustomFont customFont = new CustomFont();
        Font roboto = customFont.getRoboto();
        
        //Create Panel with menu admin 
        URL login = getClass().getClassLoader().getResource("images/front_banner.png");
        this.setIcon(new ImageIcon(login));          
        this.setPreferredSize(new Dimension(1000, 80));
        this.setMaximumSize(new Dimension(1000, 80));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //Menu Title
        JLabel titleMenu = new JLabel("CCT ART GALLERY", JLabel.CENTER);
        titleMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
        titleMenu.setForeground(Color.WHITE);
        titleMenu.setFont(roboto.deriveFont(Font.PLAIN, 16f));
        titleMenu.setPreferredSize(new Dimension(200, 50));
        titleMenu.setMaximumSize(new Dimension(200, 50));
        this.add(titleMenu);

        //ArtPieces button
        artPiecesMenu = new JButton("   Art Pieces");
        URL artPiecesIcon = getClass().getClassLoader().getResource("images/artpieces_icon.png");
        artPiecesMenu.setIcon(new ImageIcon(artPiecesIcon));
        artPiecesMenu.setHorizontalAlignment(SwingConstants.CENTER);
        artPiecesMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
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

        //Artists button
        artistsMenu = new JButton("   Artists");
        URL artistsIcon = getClass().getClassLoader().getResource("images/artists_icon.png");
        artistsMenu.setIcon(new ImageIcon(artistsIcon));
        artistsMenu.setHorizontalAlignment(SwingConstants.CENTER);
        artistsMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
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

        //Favourites button
        favouriteMenu = new JButton("   Favourites");
        URL favIcon = getClass().getClassLoader().getResource("images/favourite.png");
        favouriteMenu.setIcon(new ImageIcon(favIcon));
        favouriteMenu.setHorizontalAlignment(SwingConstants.CENTER);
        favouriteMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
        favouriteMenu.setFont(roboto.deriveFont(Font.PLAIN, 14f));
        favouriteMenu.setForeground(Color.WHITE);
        favouriteMenu.setBackground(new Color(241, 164, 66));
        favouriteMenu.setOpaque(true);
        favouriteMenu.setContentAreaFilled(false);
        favouriteMenu.setBorderPainted(false);
        favouriteMenu.setPreferredSize(new Dimension(180, 50));
        favouriteMenu.setMaximumSize(new Dimension(180, 50));
        
        //Add listener to menu option
        favouriteMenu.addActionListener((ActionListener) controllerListener);
        favouriteMenu.setActionCommand("favMenu");
        this.add(favouriteMenu);
        
        //Profile button
        profileMenu = new JButton("   Profile");
        URL profileIcon = getClass().getClassLoader().getResource("images/profile.png");
        profileMenu.setIcon(new ImageIcon(profileIcon));
        profileMenu.setHorizontalAlignment(SwingConstants.CENTER);
        profileMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
        profileMenu.setFont(roboto.deriveFont(Font.PLAIN, 14f));
        profileMenu.setForeground(Color.WHITE);
        profileMenu.setBackground(new Color(241, 164, 66));
        profileMenu.setOpaque(true);
        profileMenu.setContentAreaFilled(false);
        profileMenu.setBorderPainted(false);
        profileMenu.setPreferredSize(new Dimension(180, 50));
        profileMenu.setMaximumSize(new Dimension(180, 50));
        
        //Add listener to menu option
        profileMenu.addActionListener((ActionListener) controllerListener);
        profileMenu.setActionCommand("profileMenu");
        this.add(profileMenu);
        
        //Profile button
        logoutMenu = new JButton("   Logout");
        URL logoutIcon = getClass().getClassLoader().getResource("images/logout.png");
        logoutMenu.setIcon(new ImageIcon(logoutIcon));
        logoutMenu.setHorizontalAlignment(SwingConstants.CENTER);
        logoutMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
        logoutMenu.setFont(roboto.deriveFont(Font.PLAIN, 14f));
        logoutMenu.setForeground(Color.WHITE);
        logoutMenu.setBackground(new Color(241, 164, 66));
        logoutMenu.setOpaque(true);
        logoutMenu.setContentAreaFilled(false);
        logoutMenu.setBorderPainted(false);
        logoutMenu.setPreferredSize(new Dimension(180, 50));
        logoutMenu.setMaximumSize(new Dimension(180, 50));
        
        //Add listener to menu option
        logoutMenu.addActionListener((ActionListener) controllerListener);
        logoutMenu.setActionCommand("logout");
        this.add(logoutMenu);
    }
    
    public void setActive(String button){
        artPiecesMenu.setContentAreaFilled(false);
        artistsMenu.setContentAreaFilled(false);
        favouriteMenu.setContentAreaFilled(false);
        profileMenu.setContentAreaFilled(false);
        logoutMenu.setContentAreaFilled(false);
        
        switch (button) {
            case "artPiecesMenu":
                artPiecesMenu.setContentAreaFilled(true);
                break;
            case "artistsMenu":
                artistsMenu.setContentAreaFilled(true);
                break;
            case "favMenu":
                favouriteMenu.setContentAreaFilled(true);
                break;
            case "profileMenu":
                profileMenu.setContentAreaFilled(true);
                break;
            default:
                break;
        }
    }
}
