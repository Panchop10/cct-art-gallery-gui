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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class PublicArtistPanel extends JPanel {
    
    private final PublicController  controllerListener;
    public        PublicElementList artistList;
    //private final JLabel          message;
    
    //Update Attributes
    private final JLabel            imageUpdate;
    private final JTextField        titleUpdate;
    private final JTextField        priceUpdate;
    private final JTextArea         descUpdate;
    private final JTextField        categoryUpdate;
    private final JTextField        artistUpdate;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PublicArtistPanel(PublicController controller){
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
                    controllerListener.filterArtist(searchText);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchText = searchBar.getText();
                if(!searchText.equals(" Search")){
                    controllerListener.filterArtist(searchText);
                }
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchText = searchBar.getText();
                if(!searchText.equals(" Search")){
                    controllerListener.filterArtist(searchText);
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

        JLabel category = new JLabel("Website");
        category.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        category.setPreferredSize(new Dimension(200, 20));
        category.setMaximumSize(new Dimension(200, 20));
        tableHeaders.add(category);

        JLabel artist = new JLabel("Email");
        artist.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        artist.setPreferredSize(new Dimension(200, 20));
        artist.setMaximumSize(new Dimension(200, 20));
        tableHeaders.add(artist);
        
        
        //Information Table        
        artistList = new PublicElementList(controllerListener, "artist");
        JScrollPane artistsPanel = new JScrollPane(artistList); 
        artistsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        artistsPanel.setPreferredSize(new Dimension(690, 500));
        artistsPanel.setMaximumSize(new Dimension(690, 500));
        artistsPanel.setBackground(Color.WHITE);
        
        
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
        
        titleUpdate = new JTextField(" Name");
        titleUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        titleUpdate.setEditable(false);
        titleUpdate.setToolTipText("Name");
                
        categoryUpdate = new JTextField(" Email");
        categoryUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        categoryUpdate.setEditable(false);
        categoryUpdate.setToolTipText("Email");
        
        artistUpdate = new JTextField(" Address");
        artistUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        artistUpdate.setEditable(false);
        artistUpdate.setToolTipText("Address");
        
        priceUpdate = new JTextField(" Website");
        priceUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        priceUpdate.setEditable(false);
        priceUpdate.setToolTipText("Website"); 
        
        descUpdate = new JTextArea(" Biography");        
        descUpdate.setLineWrap(true);
        descUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        descUpdate.setEditable(false);
        descUpdate.setToolTipText("Biography"); 
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
    
        //End elements, start assign to panels.
        
        updatePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        updatePanel.add(update);
        updatePanel.add(updateImage);
        updatePanel.add(updateForm);
        
        rightPanel.add(updatePanel);
        
        leftPanel.add(titlePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(tableHeaders);
        leftPanel.add(artistsPanel);
        
        
        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);
        this.add(contentPanel);
    }
    
    /**
     * 
     * @return List of Art Pieces in the view.
     */
    public PublicElementList getArtistList(){
        return artistList;
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
     * Update artist shown in the detail panel of the view.
     * @param artist receive an artist as JSON an show in the detail panel.
     */
    public void setUpdateElement(JSONObject artist){
        
        String artistFirstName = artist.getString("first_name");
        String artistLastName = artist.getString("last_name");
        String artistName = artistFirstName + " " + artistLastName;
        titleUpdate.setText(artistName);
        
        priceUpdate.setText(artist.getString("website"));
        categoryUpdate.setText(artist.getString("email"));
        
        artistUpdate.setText(artist.getString("address"));
        
        
        //try to get description, if it is null, set to empty.
        try {
            descUpdate.setText(artist.getString("biography"));
        
        } catch (JSONException e){
            descUpdate.setText("");
        }
        
        
        
        //try to get photo, if it is null, show placeholder.        
        try {
            URL newImage = new URL(artist.getString("photo"));
            BufferedImage bufferedImage = ImageIO.read(newImage);
            Image resizedImage = bufferedImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(resizedImage);
            imageUpdate.setIcon(imageIcon);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(PublicArtistPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PublicArtistPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e){
            URL placeholder = getClass().getClassLoader().getResource("images/placeholder_hq.png");
            imageUpdate.setIcon(new ImageIcon(placeholder));
        }

    }
}