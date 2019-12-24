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

import com.cct.artgallery.admin.*;
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
public class PublicView {
    
    private     JFrame                  window;
    public      TopBar                  topBar;
    private     PublicController        controllerListener;
    private     MenuPublic              menu;
    private     PublicArtPiecePanel     artPiecePanel;
    private     PublicFavouritePanel    favPanel;
    private     PublicArtistPanel       artistPanel;
    private     UserPanel               userPanel;
    
    public PublicView(PublicController controller, MenuPublic menu){
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
            content.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            window.add(content);
            
            //Add menu left to the left content panel.
            content.add(menu);
            
            //Add ArtPiecePanel as default content to the right panel.
            artPiecePanel = new PublicArtPiecePanel(controllerListener);     
            content.add(artPiecePanel);

            window.validate();
            window.repaint();
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateContent(String menu){
        switch(menu) {
            case "artPiecesMenu":
                updateArtPiecePanel();
                break;
            case "artistsMenu":
                updateArtistPanel();
                break;
            case "favMenu":
                updateFavouritePanel();
                break;
            default:
                updateArtPiecePanel();
        }
        
        window.validate();
        window.repaint();
    }
    
    /**
     * Create new art piece panel and store it in the attribute.
     */
    private void updateArtPiecePanel(){
        window.getContentPane().remove(1);
        //Content Panel which contains menu and content.
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        window.add(content);

        //Add menu left to the left content panel.
        content.add(this.menu);

        //Add ArtPiecePanel as default content to the right panel. 
        this.artPiecePanel = new PublicArtPiecePanel(controllerListener);
        content.add(artPiecePanel);
    }
    
    /**
     * Create new artist panel and store it in the attribute.
     */
    private void updateArtistPanel(){
        window.getContentPane().remove(1);
        //Content Panel which contains menu and content.
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        window.add(content);

        //Add menu left to the left content panel.
        content.add(this.menu);

        //Add ArtistPanel as content to the right panel. 
        this.artistPanel = new PublicArtistPanel(controllerListener);
        content.add(artistPanel);
    }
    
    /**
     * Create new favourite panel and store it in the attribute.
     */
    private void updateFavouritePanel(){
        window.getContentPane().remove(1);
        //Content Panel which contains menu and content.
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        window.add(content);

        //Add menu left to the left content panel.
        content.add(this.menu);

        //Add Favourite as content to the right panel. 
        this.favPanel = new PublicFavouritePanel(controllerListener);
        content.add(favPanel);
    }
    
    
    /**
     * Dispose Administrator JFrame
     */
    public void dispose(){
        window.dispose();
    }
    
    /**
     * 
     * @return ElementList with all the active elements in the Panel Art Pieces. 
     */
    public PublicArtPiecePanel getArtPiecesPanel(){
        return artPiecePanel;
    }
    
        /**
     * 
     * @return ElementList with all the active elements in the Panel Art Pieces. 
     */
    public PublicFavouritePanel getFavouritePanel(){
        return favPanel;
    }
    
    /**
     * 
     * @return ElementList with all the active elements in the Panel Artist. 
     */
    public PublicArtistPanel getArtistPanel(){
        return artistPanel;
    }
    
    /**
     * 
     * @return ElementList with all the active elements in the Panel Art Pieces. 
     */
    public UserPanel getUserPanel(){
        return userPanel;
    }
}
