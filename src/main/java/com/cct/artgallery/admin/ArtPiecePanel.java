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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class ArtPiecePanel extends JPanel {
    
    private final AdminController   controllerListener;
    public        ElementList       artPiecesList;
    private final JLabel            message;
    
    //Update Attributes
    private       String            slugnameUpdate;
    private final JLabel            imageUpdate;
    private final JTextField        titleUpdate;
    private final JTextField        priceUpdate;
    private final JTextArea         descUpdate;
    private final JComboBox         categoryUpdate;
    private final JComboBox         artistUpdate;
    
    //Add Attributes
    private       String            filePath = "";
    private final JLabel            fileLabel;
    private final JTextField        titleAdd;
    private final JTextField        priceAdd;
    private final JTextArea         descAdd;
    private final JComboBox         categoryAdd;
    private final JComboBox         artistAdd;
    
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
        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new BorderLayout());
        logoutPanel.setPreferredSize(new Dimension(800, 28));
        logoutPanel.setMaximumSize(new Dimension(800, 28));
        
        message = new JLabel("", JLabel.CENTER);
        message.setFont(roboto.deriveFont(Font.PLAIN, 12f));
        message.setForeground(new Color(0, 100, 0));
        logoutPanel.add(message, BorderLayout.CENTER);
        
        JButton logoutButton = new JButton("  Logout");
        URL logoutIcon = getClass().getClassLoader().getResource("images/logout_button.png");
        logoutButton.setIcon(new ImageIcon(logoutIcon));
        
        //Add listener to menu option
        logoutButton.addActionListener((ActionListener) controllerListener);
        logoutButton.setActionCommand("logout");
        logoutPanel.add(logoutButton, BorderLayout.LINE_END);
        
        
        //Title Panel
        //JPanel titlePanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT));
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.setPreferredSize(new Dimension(750, 30));
        titlePanel.setMaximumSize(new Dimension(750, 30));
        titlePanel.setBackground(new Color(241, 164, 66));
        
        JLabel titleLabel = new JLabel("  Art Pieces");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(roboto.deriveFont(Font.BOLD, 15f));
        titlePanel.add(titleLabel, BorderLayout.WEST);
        
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
        
        titlePanel.add(searchBar, BorderLayout.EAST);
        
        
        
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
        createPanel.setPreferredSize(new Dimension(300, 260));
        createPanel.setMaximumSize(new Dimension(300, 260));
        createPanel.setBackground(new Color(241, 164, 66));
        
        GroupLayout gLayoutAdd = new GroupLayout(createPanel);
        gLayoutAdd.setAutoCreateGaps(true);  
        gLayoutAdd.setAutoCreateContainerGaps(true);  
        createPanel.setLayout(gLayoutAdd);  
        
        JLabel add = new JLabel("Add");
        add.setFont(roboto.deriveFont(Font.BOLD, 15f));
        add.setForeground(Color.WHITE);
        
        JPanel fileAdd = new JPanel(new FlowLayout(FlowLayout.LEFT));  
        fileAdd.setBackground(new Color(241, 164, 66));
        
        JButton fileButton = new JButton("Choose a file...");
        fileButton.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        fileButton.addActionListener((ActionListener) controllerListener);
        fileButton.setActionCommand("addFileArtPiece");
        
        fileLabel = new JLabel("");
        fileLabel.setFont(roboto.deriveFont(Font.PLAIN, 9f));
        
        fileAdd.add(fileButton);
        fileAdd.add(fileLabel);        
        
        String categoryDefault[] = {"Category"};
        categoryAdd = new JComboBox(categoryDefault);
        categoryAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        String artistDefault[] = {"Artist"};
        artistAdd = new JComboBox(artistDefault);
        artistAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        titleAdd = new JTextField(" Title");
        titleAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        titleAdd.setForeground(Color.GRAY);
        titleAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (titleAdd.getText().equals(" Title")) {
                    titleAdd.setText("");
                    titleAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (titleAdd.getText().isEmpty()) {
                    titleAdd.setForeground(Color.GRAY);
                    titleAdd.setText(" Title");
                }
            }
        }); 
        
        priceAdd = new JTextField(" Price");
        priceAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        priceAdd.setForeground(Color.GRAY);
        priceAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (priceAdd.getText().equals(" Price")) {
                    priceAdd.setText("");
                    priceAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (priceAdd.getText().isEmpty()) {
                    priceAdd.setForeground(Color.GRAY);
                    priceAdd.setText(" Price");
                }
            }
        });
        
        descAdd = new JTextArea(" Description");        
        descAdd.setLineWrap(true);
        descAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        descAdd.setForeground(Color.GRAY);
        descAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (descAdd.getText().equals(" Description")) {
                    descAdd.setText("");
                    descAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (descAdd.getText().isEmpty()) {
                    descAdd.setForeground(Color.GRAY);
                    descAdd.setText(" Description");
                }
            }
        });        
        JScrollPane scrollDescAdd = new JScrollPane(descAdd);
        
        
        JButton submitAdd = new JButton("Save");
        submitAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        submitAdd.addActionListener((ActionListener) controllerListener);
        submitAdd.setActionCommand("addArtPiece");
        
        
        gLayoutAdd.setHorizontalGroup(gLayoutAdd.createSequentialGroup()
            .addGroup(gLayoutAdd.createParallelGroup(LEADING)
                    .addComponent(add)
                    .addComponent(fileAdd)
                    .addComponent(categoryAdd)
                    .addComponent(artistAdd)
                    .addComponent(titleAdd)
                    .addComponent(priceAdd)
                    .addComponent(scrollDescAdd)
            )  
            .addGroup(gLayoutAdd.createParallelGroup(TRAILING)
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
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(artistAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(titleAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(priceAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(scrollDescAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(submitAdd)
            )
        );
        
        actionPanel.add(createPanel);
        actionPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        
        //Update element Panel
        JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        updatePanel.setPreferredSize(new Dimension(420, 260));
        updatePanel.setMaximumSize(new Dimension(420, 260));
        updatePanel.setBackground(new Color(241, 164, 66));
        
        //Left Image Update Panel
        JPanel updateImage = new JPanel();
        updateImage.setPreferredSize(new Dimension(170, 230));
        updateImage.setMaximumSize(new Dimension(170, 230));
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
        updateForm.setPreferredSize(new Dimension(250, 230));
        updateForm.setMaximumSize(new Dimension(250, 230));
        updateForm.setBackground(new Color(241, 164, 66));
        
        GroupLayout gLayoutUpdate = new GroupLayout(updateForm);
        gLayoutUpdate.setAutoCreateGaps(true);  
        gLayoutUpdate.setAutoCreateContainerGaps(true);  
        updateForm.setLayout(gLayoutUpdate);  
        
        JLabel empty = new JLabel("");
        empty.setPreferredSize(new Dimension(0, 10));
        empty.setMaximumSize(new Dimension(0, 10));        
        
        categoryUpdate = new JComboBox(new String[1]);
        categoryUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        artistUpdate = new JComboBox(new String[1]);
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
        submitUpdate.addActionListener((ActionListener) controllerListener);
        submitUpdate.setActionCommand("updateArtPiece");

        JButton submitDelete = new JButton("Delete");
        submitDelete.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        submitDelete.setForeground(Color.RED);
        submitDelete.addActionListener((ActionListener) controllerListener);
        submitDelete.setActionCommand("deleteArtPiece"); 
        
        gLayoutUpdate.setHorizontalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(TRAILING)
                    .addComponent(empty)
                    .addComponent(categoryUpdate)
                    .addComponent(artistUpdate)
                    .addComponent(titleUpdate)
                    .addComponent(priceUpdate)
                    .addComponent(scrollDescUpdate)
                    .addComponent(submitUpdate)
                    .addComponent(submitDelete)
            )
        );  
  
        gLayoutUpdate.setVerticalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(empty)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(categoryUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
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
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(submitDelete)
            )
        );
        updatePanel.add(updateImage);
        updatePanel.add(updateForm);
        
        actionPanel.add(updatePanel);
        
        this.add(logoutPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(titlePanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(tableHeaders);
        this.add(artPiecesPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(actionPanel);

    }
    
    /**
     * 
     * @return List of Art Pieces in the view.
     */
    public ElementList getArtPiecesList(){
        return artPiecesList;
    }
    
    /**
     * 
     * @return JComboBox of category update.
     */
    public JComboBox getCategoryUpdate(){
        return categoryUpdate;
    }
    
    /**
     * 
     * @return JComboBox of artist update.
     */
    public JComboBox getArtistUpdate(){
        return artistUpdate;
    }
    
    /**
     * 
     * @return JComboBox of category add.
     */
    public JComboBox getCategoryAdd(){
        return categoryAdd;
    }
    
    /**
     * 
     * @return JComboBox of artist add.
     */
    public JComboBox getArtistAdd(){
        return artistAdd;
    }
    
    /**
     * 
     * @param name set file name beside file chooser in the view.
     */
    public void setFileName(String name){
        fileLabel.setText(name);
    }
    
    /**
     * 
     * @param path save path of the picked image.
     */
    public void setFilePath(String path){
        filePath = path;
    }
    
    /**
     * 
     * @return Data of the Add form in JSONObject format.
     */
    public JSONObject getAddData(){
        JSONObject data = new JSONObject();
        data.put("photo", filePath);
        data.put("category", categoryAdd.getSelectedIndex());
        data.put("artist", artistAdd.getSelectedIndex());
        data.put("name", titleAdd.getText());
        data.put("price", priceAdd.getText());
        data.put("description", descAdd.getText());
        return data;
    }
    
    /**
     * 
     * @return Data of the Add form in JSONObject format.
     */
    public JSONObject getUpdateData(){
        JSONObject data = new JSONObject();
        data.put("slug_name", slugnameUpdate);
        data.put("category", categoryUpdate.getSelectedIndex());
        data.put("artist", artistUpdate.getSelectedIndex());
        data.put("name", titleUpdate.getText());
        data.put("price", priceUpdate.getText());
        data.put("description", descUpdate.getText());
        return data;
    }
        
    /**
     * 
     * @param message Receive an String and shows during 3 seconds a JLabel 
     * with the message as an error.
     * @param error If true, the message is going to be shown in red color.
     */
    public void showMessage(String message, Boolean error){
        new Thread(() -> {
            if(error){
                this.message.setForeground(Color.RED);
            }
            this.message.setText(message);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AdminView.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.message.setText("");
            this.message.setForeground(new Color(0, 100, 0));
        }).start();
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
        
        //Select Category in the ComboBox
        String category = artPiece.getJSONObject("category").getString("name");
        for (int i = 0; i < categoryUpdate.getItemCount(); i++){
            Object item = categoryUpdate.getItemAt(i);
            if (item.toString().equals(category)){
                categoryUpdate.setSelectedIndex(i);
                break;
            }            
        }
        
        //Select Artist in the ComboBox
        JSONObject artist = artPiece.getJSONObject("artist");
        String artistFirstName = artist.getString("first_name");
        String artistLastName = artist.getString("last_name");
        String artistName = artistFirstName + " " + artistLastName;
        
        for (int i = 0; i < artistUpdate.getItemCount(); i++){
            Object item = artistUpdate.getItemAt(i);
            if (item.toString().equals(artistName)){
                artistUpdate.setSelectedIndex(i);
                break;
            }            
        }   
        
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