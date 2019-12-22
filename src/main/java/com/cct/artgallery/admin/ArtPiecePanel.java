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

import com.cct.artgallery.admin.CustomTable.ElementList;
import com.cct.artgallery.utils.CustomFont;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
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
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class ArtPiecePanel extends JPanel {
    
    private final AdminController   controllerListener;
    public        ElementList       artPiecesList;
    
    //Update Attributes
    private final JLabel            imageUpdate;
    private final JTextField        titleUpdate;
    private final JTextField        priceUpdate;
    private final JTextArea        descUpdate;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ArtPiecePanel(AdminController controller){
        this.controllerListener = controller;
        
        //Set JPanel properties
        this.setPreferredSize(new Dimension(800, 660));
        this.setMaximumSize(new Dimension(800, 660));
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //Call Roboto Font
        CustomFont customFont = new CustomFont();
        Font roboto = customFont.getRoboto();
        
        //Logout Button
        JPanel logoutPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setPreferredSize(new Dimension(800, 28));
        logoutPanel.setMaximumSize(new Dimension(800, 28));
        
        JButton logoutButton = new JButton("  Logout");
        URL logoutIcon = getClass().getClassLoader().getResource("images/logout_button.png");
        logoutButton.setIcon(new ImageIcon(logoutIcon));
        
        //Add listener to menu option
        logoutButton.addActionListener((ActionListener) controllerListener);
        logoutButton.setActionCommand("logout");
        logoutPanel.add(logoutButton);
        
        
        //Title Panel
        JPanel titlePanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.setPreferredSize(new Dimension(750, 30));
        titlePanel.setMaximumSize(new Dimension(750, 30));
        titlePanel.setBackground(new Color(241, 164, 66));
        
        JLabel titleLabel = new JLabel("  Art Pieces");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(roboto.deriveFont(Font.BOLD, 15f));
        titlePanel.add(titleLabel);
        
        
        //Table headers
        JPanel tableHeaders = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tableHeaders.setAlignmentX(Component.CENTER_ALIGNMENT);
        tableHeaders.setPreferredSize(new Dimension(750, 30));
        tableHeaders.setMaximumSize(new Dimension(750, 30));
        tableHeaders.setBackground(Color.WHITE);
        
        JLabel name = new JLabel("Name");
        name.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        name.setPreferredSize(new Dimension(250, 20));
        name.setMaximumSize(new Dimension(250, 20));
        tableHeaders.add(name);

        JLabel category = new JLabel("Category");
        category.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        category.setPreferredSize(new Dimension(150, 20));
        category.setMaximumSize(new Dimension(150, 20));
        tableHeaders.add(category);

        JLabel artist = new JLabel("Artist");
        artist.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        artist.setPreferredSize(new Dimension(250, 20));
        artist.setMaximumSize(new Dimension(250, 20));
        tableHeaders.add(artist);

        JLabel price = new JLabel("Price", SwingConstants.CENTER);
        price.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        price.setPreferredSize(new Dimension(50, 20));
        price.setMaximumSize(new Dimension(50, 20));
        tableHeaders.add(price);
        
        
        //Information Table        
        artPiecesList = new ElementList(controllerListener, "artPiece");
        JScrollPane artPiecesPanel = new JScrollPane(artPiecesList); 
        artPiecesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        artPiecesPanel.setPreferredSize(new Dimension(750, 300));
        artPiecesPanel.setMaximumSize(new Dimension(750, 300));
        artPiecesPanel.setBackground(Color.WHITE);
        
        
        //Create and Update Panels
        JPanel actionPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.RIGHT,0,0));
        actionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionPanel.setPreferredSize(new Dimension(750, 300));
        actionPanel.setMaximumSize(new Dimension(750, 300));
        actionPanel.setBackground(Color.WHITE);
        
        //Create element Panel
        JPanel createPanel = new JPanel();
        createPanel.setPreferredSize(new Dimension(300, 220));
        createPanel.setMaximumSize(new Dimension(300, 220));
        createPanel.setBackground(new Color(241, 164, 66));
        
        GroupLayout gLayoutAdd = new GroupLayout(createPanel);
        gLayoutAdd.setAutoCreateGaps(true);  
        gLayoutAdd.setAutoCreateContainerGaps(true);  
        createPanel.setLayout(gLayoutAdd);  
        
        JLabel add = new JLabel("Add");
        add.setFont(roboto.deriveFont(Font.BOLD, 15f));
        add.setForeground(Color.WHITE);
        
        JButton fileAdd = new JButton("Choose a file...");
        fileAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        String categoryOptions[]={"Category 1","Category 2","Category 3","Category 4"};
        String artistsOptions[]={"Artist 1","Artist 2","Artist 3","Artist 4"};
        
        JComboBox categoryAdd = new JComboBox(categoryOptions);
        categoryAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        JComboBox artistAdd = new JComboBox(artistsOptions);
        artistAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        JTextField titleAdd = new JTextField(" Title");
        titleAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        JTextField priceAdd = new JTextField(" Price");
        priceAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        JTextField descAdd = new JTextField(" Description");
        descAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        JButton submitAdd = new JButton("Save");
        submitAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        
        gLayoutAdd.setHorizontalGroup(gLayoutAdd.createSequentialGroup()
            .addGroup(gLayoutAdd.createParallelGroup(LEADING)
                    .addComponent(add)
                    .addComponent(fileAdd)
                    .addComponent(categoryAdd)
                    .addComponent(titleAdd)
                    .addComponent(priceAdd)
                    .addComponent(descAdd)
            )  
            .addGroup(gLayoutAdd.createParallelGroup(TRAILING)
                    .addComponent(artistAdd)
                    .addComponent(submitAdd)
            )
        );  
  
        gLayoutAdd.setVerticalGroup(gLayoutAdd.createSequentialGroup()
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(add)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(fileAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(categoryAdd)
                    .addComponent(artistAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(titleAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(priceAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(descAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(submitAdd)
            )
        );
        
        actionPanel.add(createPanel);
        actionPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        
        //Update element Panel
        JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        updatePanel.setPreferredSize(new Dimension(420, 220));
        updatePanel.setMaximumSize(new Dimension(420, 220));
        updatePanel.setBackground(new Color(241, 164, 66));
        
        //Left Image Update Panel
        JPanel updateImage = new JPanel();
        updateImage.setPreferredSize(new Dimension(170, 220));
        updateImage.setMaximumSize(new Dimension(170, 220));
        updateImage.setBackground(new Color(241, 164, 66));
        
        JLabel update = new JLabel("Update");
        update.setFont(roboto.deriveFont(Font.BOLD, 15f));
        update.setForeground(Color.WHITE);
        updateImage.add(update);
        
        //Create Panel with menu admin 
        URL placeholder = getClass().getClassLoader().getResource("images/image_placeholder.jpg");
        imageUpdate = new JLabel("", JLabel.CENTER);
        imageUpdate.setIcon(new ImageIcon(placeholder));          
        imageUpdate.setPreferredSize(new Dimension(150, 150));
        imageUpdate.setMaximumSize(new Dimension(150, 150));
        updateImage.add(imageUpdate);
        
        //Right Form Update Panel
        JPanel updateForm = new JPanel();
        updateForm.setPreferredSize(new Dimension(250, 220));
        updateForm.setMaximumSize(new Dimension(250, 220));
        updateForm.setBackground(new Color(241, 164, 66));
        
        GroupLayout gLayoutUpdate = new GroupLayout(updateForm);
        gLayoutUpdate.setAutoCreateGaps(true);  
        gLayoutUpdate.setAutoCreateContainerGaps(true);  
        updateForm.setLayout(gLayoutUpdate);  
        
        JLabel empty = new JLabel("");
        empty.setPreferredSize(new Dimension(0, 10));
        empty.setMaximumSize(new Dimension(0, 10));        
        
        JComboBox categoryUpdate = new JComboBox(categoryOptions);
        categoryUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        JComboBox artistUpdate = new JComboBox(artistsOptions);
        artistUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        titleUpdate = new JTextField(" Title");
        titleUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        priceUpdate = new JTextField(" Price");
        priceUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        descUpdate = new JTextArea(" Description");        
        descUpdate.setLineWrap(true);
        descUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        JScrollPane scrollDescUpdate = new JScrollPane(descUpdate);
        
        JButton submitUpdate = new JButton("Save");
        submitUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        
        gLayoutUpdate.setHorizontalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(LEADING)
                    .addComponent(empty)
                    .addComponent(categoryUpdate)
                    .addComponent(titleUpdate)
                    .addComponent(priceUpdate)
                    .addComponent(scrollDescUpdate)
            )  
            .addGroup(gLayoutUpdate.createParallelGroup(TRAILING)
                    .addComponent(artistUpdate)
                    .addComponent(submitUpdate)
            )
        );  
  
        gLayoutUpdate.setVerticalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(empty)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(categoryUpdate)
                    .addComponent(artistUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(titleUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(priceUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(scrollDescUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(submitUpdate)
            )
        );
        
        
        updatePanel.add(updateImage);
        updatePanel.add(updateForm);
        
        actionPanel.add(updatePanel);
        
        
        
        
        
        
        
        this.add(logoutPanel);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(titlePanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(tableHeaders);
        this.add(artPiecesPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(actionPanel);
        
        
    }
    
    public ElementList getArtPiecesList(){
        return artPiecesList;
    }
    
    public void setUpdateElement(JSONObject artPiece){
                
        titleUpdate.setText(artPiece.getString("name"));
        priceUpdate.setText(artPiece.getInt("price")+"");
        
        //try to get description, if it is null, set to empty.
        try {
            descUpdate.setText(artPiece.getString("description"));
        
        } catch (JSONException e){
            descUpdate.setText("");
        }
        
        //try to get photo, if it is null, show placeholder.        
        try {
            URL newImage = new URL(artPiece.getString("photo"));
            BufferedImage bufferedImage = ImageIO.read(newImage);
            Image resizedImage = bufferedImage.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
            ImageIcon imageIcon = new ImageIcon(resizedImage);
            imageUpdate.setIcon(imageIcon);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ArtPiecePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArtPiecePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e){
            URL placeholder = getClass().getClassLoader().getResource("images/image_placeholder.jpg");
            imageUpdate.setIcon(new ImageIcon(placeholder));
        }

        

        
    }
}