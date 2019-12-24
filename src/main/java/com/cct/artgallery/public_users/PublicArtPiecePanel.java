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

import com.cct.artgallery.public_users.custom_table.PublicElementList;
import com.cct.artgallery.utils.CustomFont;
import com.cct.artgallery.utils.UserDetail;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class PublicArtPiecePanel extends JPanel {
    
    private final PublicController  controllerListener;
    public        PublicElementList artPiecesList;
    //private final JLabel          message;
    
    //Update Attributes
    private       String            slugnameUpdate;
    private final JLabel            imageUpdate;
    private final JTextField        titleUpdate;
    private final JTextField        priceUpdate;
    private final JTextArea         descUpdate;
    private final JTextField        categoryUpdate;
    private final JTextField        artistUpdate;
    private final JButton           likeButton;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PublicArtPiecePanel(PublicController controller){
        this.controllerListener = controller;
        
        //Set JPanel properties
        this.setPreferredSize(new Dimension(1000, 660));
        this.setMaximumSize(new Dimension(1000, 660));
        this.setBackground(Color.WHITE);
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //Call Roboto Font
        CustomFont customFont = new CustomFont();
        Font roboto = customFont.getRoboto();
        
        //Content Panel (Split the content in two, left with the table, right
        //with the details)
        JPanel contentPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT,0,0));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.setPreferredSize(new Dimension(1000, 550));
        contentPanel.setMaximumSize(new Dimension(1000, 550));
        contentPanel.setBackground(Color.WHITE);
        
        
        //Left Content (Table and search)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(700, 550));
        leftPanel.setMaximumSize(new Dimension(700, 550));
        leftPanel.setBackground(Color.WHITE);
        
        
        //Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.setPreferredSize(new Dimension(690, 30));
        titlePanel.setMaximumSize(new Dimension(690, 30));
        titlePanel.setBackground(Color.WHITE);
        
        JTextField searchBar = new JTextField(" Search");
        searchBar.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        searchBar.setMinimumSize(new Dimension(200, 30));
        searchBar.setPreferredSize(new Dimension(200, 30));
        searchBar.setMaximumSize(new Dimension(200, 30));
        searchBar.setForeground(Color.GRAY);
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals(" Search")) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setText(" Search");
                }
            }
        });
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                String searchText = searchBar.getText();
                if(!searchText.equals(" Search")){
                    controllerListener.filterArtPiece(searchText);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchText = searchBar.getText();
                if(!searchText.equals(" Search")){
                    controllerListener.filterArtPiece(searchText);
                }
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchText = searchBar.getText();
                if(!searchText.equals(" Search")){
                    controllerListener.filterArtPiece(searchText);
                }
            }
         });
        
        titlePanel.add(searchBar, BorderLayout.CENTER);
        
        
        
        //Table headers
        JPanel tableHeaders = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tableHeaders.setAlignmentX(Component.CENTER_ALIGNMENT);
        tableHeaders.setPreferredSize(new Dimension(690, 30));
        tableHeaders.setMaximumSize(new Dimension(690, 30));
        tableHeaders.setBackground(Color.WHITE);
        
        JLabel name = new JLabel("Name");
        name.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        name.setPreferredSize(new Dimension(200, 20));
        name.setMaximumSize(new Dimension(200, 20));
        tableHeaders.add(name);

        JLabel category = new JLabel("Category");
        category.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        category.setPreferredSize(new Dimension(150, 20));
        category.setMaximumSize(new Dimension(150, 20));
        tableHeaders.add(category);

        JLabel artist = new JLabel("Artist");
        artist.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        artist.setPreferredSize(new Dimension(180, 20));
        artist.setMaximumSize(new Dimension(180, 20));
        tableHeaders.add(artist);

        JLabel price = new JLabel("Price", SwingConstants.CENTER);
        price.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        price.setPreferredSize(new Dimension(90, 20));
        price.setMaximumSize(new Dimension(90, 20));
        tableHeaders.add(price);
        
        
        //Information Table        
        artPiecesList = new PublicElementList(controllerListener, "artPiece");
        JScrollPane artPiecesPanel = new JScrollPane(artPiecesList); 
        artPiecesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        artPiecesPanel.setPreferredSize(new Dimension(690, 500));
        artPiecesPanel.setMaximumSize(new Dimension(690, 500));
        artPiecesPanel.setBackground(Color.WHITE);
        
        
        //Right Content (details)
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(300, 500));
        rightPanel.setMaximumSize(new Dimension(300, 500));
        rightPanel.setBackground(Color.WHITE);
        
        //Update element Panel
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BoxLayout (updatePanel, BoxLayout.Y_AXIS));
        updatePanel.setPreferredSize(new Dimension(290, 490));
        updatePanel.setMaximumSize(new Dimension(290, 490));
        updatePanel.setBackground(new Color(241, 164, 66));
        
        JLabel update = new JLabel("Details");
        update.setFont(roboto.deriveFont(Font.BOLD, 15f));
        update.setAlignmentX(Component.CENTER_ALIGNMENT);
        update.setForeground(Color.WHITE);
        
        //Left Image Update Panel
        JPanel updateImage = new JPanel();
        updateImage.setPreferredSize(new Dimension(200, 200));
        updateImage.setMaximumSize(new Dimension(200, 200));
        updateImage.setBackground(new Color(241, 164, 66));
        
        //Create Panel with menu admin 
        URL placeholder = getClass().getClassLoader().getResource("images/placeholder_hq.png");
        imageUpdate = new JLabel("", JLabel.CENTER);
        imageUpdate.setIcon(new ImageIcon(placeholder));          
        imageUpdate.setPreferredSize(new Dimension(200, 200));
        imageUpdate.setMaximumSize(new Dimension(200, 200));
        updateImage.add(imageUpdate);
        
        //Right Form Update Panel
        JPanel updateForm = new JPanel();
        updateForm.setPreferredSize(new Dimension(250, 200));
        updateForm.setMaximumSize(new Dimension(250, 200));
        updateForm.setBackground(new Color(241, 164, 66));
        
        GroupLayout gLayoutUpdate = new GroupLayout(updateForm);
        gLayoutUpdate.setAutoCreateGaps(true);  
        gLayoutUpdate.setAutoCreateContainerGaps(true);  
        updateForm.setLayout(gLayoutUpdate);  
        
        titleUpdate = new JTextField(" Title");
        titleUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        titleUpdate.setEditable(false);
        titleUpdate.setToolTipText("Title");
                
        categoryUpdate = new JTextField(" Category");
        categoryUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        categoryUpdate.setEditable(false);
        categoryUpdate.setToolTipText("Category");
        
        artistUpdate = new JTextField(" Artist");
        artistUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        artistUpdate.setEditable(false);
        artistUpdate.setToolTipText("Artist");
        
        priceUpdate = new JTextField(" Price");
        priceUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        priceUpdate.setEditable(false);
        priceUpdate.setToolTipText("Price"); 
        
        descUpdate = new JTextArea(" Description");        
        descUpdate.setLineWrap(true);
        descUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        descUpdate.setEditable(false);
        descUpdate.setToolTipText("Description"); 
        JScrollPane scrollDescUpdate = new JScrollPane(descUpdate);
        
        gLayoutUpdate.setHorizontalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(TRAILING)
                    .addComponent(titleUpdate)
                    .addComponent(categoryUpdate)
                    .addComponent(artistUpdate)
                    .addComponent(artistUpdate)
                    .addComponent(priceUpdate)
                    .addComponent(scrollDescUpdate)
            )
        );  
  
        gLayoutUpdate.setVerticalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(titleUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(categoryUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(artistUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(priceUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(scrollDescUpdate)
            )
        );
        
                
        JPanel like = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        like.setPreferredSize(new Dimension(260, 60));
        like.setMaximumSize(new Dimension(260, 60));
        like.setBackground(new Color(241, 164, 66));
        
        
        likeButton = new JButton("");
        try{
            URL newImage = getClass().getClassLoader().getResource("images/unliked.png");
            BufferedImage bufferedImage = ImageIO.read(newImage);
            Image resizedImage = bufferedImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(resizedImage);
            likeButton.setIcon(imageIcon);
            likeButton.setToolTipText("Save to favourites.");
        } catch (IOException e) {
            System.out.println(e);
        }
        likeButton.addActionListener((ActionListener) controllerListener);
        likeButton.setActionCommand("noliked");
        likeButton.setContentAreaFilled(false);
        likeButton.setBorderPainted(false);
        like.add(likeButton);
        
        //End elements, start assign to panels.
        
        updatePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        updatePanel.add(update);
        updatePanel.add(updateImage);
        updatePanel.add(updateForm);
        updatePanel.add(like);
        
        rightPanel.add(updatePanel);
        
        leftPanel.add(titlePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(tableHeaders);
        leftPanel.add(artPiecesPanel);
        
        
        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);
        this.add(contentPanel);
    }
    
    /**
     * 
     * @return List of Art Pieces in the view.
     */
    public PublicElementList getArtPiecesList(){
        return artPiecesList;
    }
    
    /**
     * 
     * @return Data of the Add form in JSONObject format.
     */
    public JSONObject getUpdateData(){
        JSONObject data = new JSONObject();
        data.put("artpiece", slugnameUpdate);
        return data;
    }
        
//    /**
//     * 
//     * @param message Receive an String and shows during 3 seconds a JLabel 
//     * with the message as an error.
//     * @param error If true, the message is going to be shown in red color.
//     */
//    public void showMessage(String message, Boolean error){
//        new Thread(() -> {
//            if(error){
//                this.message.setForeground(Color.RED);
//            }
//            this.message.setText(message);
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            this.message.setText("");
//            this.message.setForeground(new Color(0, 100, 0));
//        }).start();
//    }
    
    
    /**
     * 
     * @param isLiked receive a boolean if the element selected in the detail
     * is liked or not and show corresponding photo.
     */
    public void setLiked(Boolean isLiked){
        if(isLiked==true){
            try{
                URL newImage = getClass().getClassLoader().getResource("images/liked.png");
                BufferedImage bufferedImage = ImageIO.read(newImage);
                Image resizedImage = bufferedImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(resizedImage);
                likeButton.setIcon(imageIcon);
                likeButton.setToolTipText("Remove from favourites.");
                likeButton.setActionCommand("unlike"); 
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        else{
            try{
                URL newImage = getClass().getClassLoader().getResource("images/unliked.png");
                BufferedImage bufferedImage = ImageIO.read(newImage);
                Image resizedImage = bufferedImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(resizedImage);
                likeButton.setIcon(imageIcon);
                likeButton.setToolTipText("Save to favourites.");
                likeButton.setActionCommand("like");
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Update art piece shown in the update panel of the view.
     * @param artPiece receive an art piece with name, price, category, artist
     * and description.
     */
    public void setUpdateElement(JSONObject artPiece){
        
        slugnameUpdate = artPiece.getString("slug_name");
        titleUpdate.setText(artPiece.getString("name"));
        priceUpdate.setText(artPiece.getInt("price")+"");
        String category = artPiece.getJSONObject("category").getString("name");
        categoryUpdate.setText(category);
        
        JSONObject artist = artPiece.getJSONObject("artist");
        String artistFirstName = artist.getString("first_name");
        String artistLastName = artist.getString("last_name");
        String artistName = artistFirstName + " " + artistLastName;
        artistUpdate.setText(artistName);
        
        //try to get description, if it is null, set to empty.
        try {
            descUpdate.setText(artPiece.getString("description"));
        
        } catch (JSONException e){
            descUpdate.setText("");
        }
        
        //Check if art piece is liked or not.
        JSONArray likes = artPiece.getJSONArray("likes");
        
        setLiked(false);
        
        for (int i = 0; i < likes.length(); i++){
            JSONObject like = likes.getJSONObject(i);
            String userLike = like.getString("username");
            
            if(userLike.equals(UserDetail.getUsername())){
                setLiked(true);
            }
        }
        
        
        //try to get photo, if it is null, show placeholder.        
        try {
            URL newImage = new URL(artPiece.getString("photo"));
            BufferedImage bufferedImage = ImageIO.read(newImage);
            Image resizedImage = bufferedImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(resizedImage);
            imageUpdate.setIcon(imageIcon);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(PublicArtPiecePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PublicArtPiecePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e){
            URL placeholder = getClass().getClassLoader().getResource("images/placeholder_hq.png");
            imageUpdate.setIcon(new ImageIcon(placeholder));
        }

    }
}