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
public class ArtistPanel extends JPanel {
    
    private final AdminController   controllerListener;
    public        ElementList       artistList;
    private final JLabel            message;
    
    //Update Attributes
    private       String            slugnameUpdate;
    private final JLabel            imageUpdate;
    private final JTextField        firstNameUpdate;
    private final JTextField        lastNameUpdate;
    private final JTextField        emailUpdate;
    private final JTextField        websiteUpdate;
    private final JTextField        addressUpdate;
    private final JTextArea         bioUpdate;
    
    //Add Attributes
    private       String            filePath = "";
    private final JLabel            fileLabel;
    private final JTextField        firstNameAdd;
    private final JTextField        lastNameAdd;
    private final JTextField        emailAdd;
    private final JTextField        websiteAdd;
    private final JTextField        addressAdd;
    private final JTextArea         bioAdd;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ArtistPanel(AdminController controller){
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
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.setPreferredSize(new Dimension(750, 30));
        titlePanel.setMaximumSize(new Dimension(750, 30));
        titlePanel.setBackground(new Color(241, 164, 66));
        
        JLabel titleLabel = new JLabel("  Artists");
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

        JLabel category = new JLabel("Website");
        category.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        category.setPreferredSize(new Dimension(200, 20));
        category.setMaximumSize(new Dimension(200, 20));
        tableHeaders.add(category);

        JLabel artist = new JLabel("Email");
        artist.setFont(roboto.deriveFont(Font.PLAIN, 10f));
        artist.setPreferredSize(new Dimension(250, 20));
        artist.setMaximumSize(new Dimension(250, 20));
        tableHeaders.add(artist);
        
        
        //Information Table        
        artistList = new ElementList(controllerListener, "artist");
        JScrollPane artPiecesPanel = new JScrollPane(artistList); 
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
        createPanel.setPreferredSize(new Dimension(300, 280));
        createPanel.setMaximumSize(new Dimension(300, 280));
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
        fileButton.setActionCommand("addFileArtist");
        
        fileLabel = new JLabel("");
        fileLabel.setFont(roboto.deriveFont(Font.PLAIN, 9f));
        
        fileAdd.add(fileButton);
        fileAdd.add(fileLabel);
        
        firstNameAdd = new JTextField(" First Name");
        firstNameAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        firstNameAdd.setForeground(Color.GRAY);
        firstNameAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (firstNameAdd.getText().equals(" First Name")) {
                    firstNameAdd.setText("");
                    firstNameAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (firstNameAdd.getText().isEmpty()) {
                    firstNameAdd.setForeground(Color.GRAY);
                    firstNameAdd.setText(" First Name");
                }
            }
        }); 
        
        lastNameAdd = new JTextField(" Last Name");
        lastNameAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        lastNameAdd.setForeground(Color.GRAY);
        lastNameAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (lastNameAdd.getText().equals(" Last Name")) {
                    lastNameAdd.setText("");
                    lastNameAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (lastNameAdd.getText().isEmpty()) {
                    lastNameAdd.setForeground(Color.GRAY);
                    lastNameAdd.setText(" Last Name");
                }
            }
        }); 
        
        emailAdd = new JTextField(" Email");
        emailAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        emailAdd.setForeground(Color.GRAY);
        emailAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailAdd.getText().equals(" Email")) {
                    emailAdd.setText("");
                    emailAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (emailAdd.getText().isEmpty()) {
                    emailAdd.setForeground(Color.GRAY);
                    emailAdd.setText(" Email");
                }
            }
        }); 
        
        websiteAdd = new JTextField(" Website (http://example.ie)");
        websiteAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        websiteAdd.setForeground(Color.GRAY);
        websiteAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (websiteAdd.getText().equals(" Website (http://example.ie)")) {
                    websiteAdd.setText("");
                    websiteAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (websiteAdd.getText().isEmpty()) {
                    websiteAdd.setForeground(Color.GRAY);
                    websiteAdd.setText(" Website (http://example.ie)");
                }
            }
        }); 
        
        addressAdd = new JTextField(" Address");
        addressAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        addressAdd.setForeground(Color.GRAY);
        addressAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (addressAdd.getText().equals(" Address")) {
                    addressAdd.setText("");
                    addressAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (addressAdd.getText().isEmpty()) {
                    addressAdd.setForeground(Color.GRAY);
                    addressAdd.setText(" Address");
                }
            }
        }); 
        
        bioAdd = new JTextArea(" Biography");        
        bioAdd.setLineWrap(true);
        bioAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        bioAdd.setForeground(Color.GRAY);
        bioAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (bioAdd.getText().equals(" Biography")) {
                    bioAdd.setText("");
                    bioAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (bioAdd.getText().isEmpty()) {
                    bioAdd.setForeground(Color.GRAY);
                    bioAdd.setText(" Biography");
                }
            }
        });
        
        JScrollPane scrollBioAdd = new JScrollPane(bioAdd);
        
        
        JButton submitAdd = new JButton("Save");
        submitAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        submitAdd.addActionListener((ActionListener) controllerListener);
        submitAdd.setActionCommand("addArtist");
        
        gLayoutAdd.setHorizontalGroup(gLayoutAdd.createSequentialGroup()
            .addGroup(gLayoutAdd.createParallelGroup(LEADING)
                    .addComponent(add)
                    .addComponent(fileAdd)
                    .addComponent(firstNameAdd)
                    .addComponent(lastNameAdd)
                    .addComponent(emailAdd)
                    .addComponent(websiteAdd)
                    .addComponent(addressAdd)
                    .addComponent(scrollBioAdd)
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
                    .addComponent(firstNameAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(lastNameAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(emailAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(websiteAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(addressAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(scrollBioAdd)
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
        updateForm.setPreferredSize(new Dimension(250, 250));
        updateForm.setMaximumSize(new Dimension(250, 250));
        updateForm.setBackground(new Color(241, 164, 66));
        
        GroupLayout gLayoutUpdate = new GroupLayout(updateForm);
        gLayoutUpdate.setAutoCreateGaps(true);  
        gLayoutUpdate.setAutoCreateContainerGaps(true);  
        updateForm.setLayout(gLayoutUpdate);  
        
        JLabel empty = new JLabel("");
        empty.setPreferredSize(new Dimension(0, 10));
        empty.setMaximumSize(new Dimension(0, 10));
        
        firstNameUpdate = new JTextField(" First Name");
        firstNameUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        lastNameUpdate = new JTextField(" Last Name");
        lastNameUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        emailUpdate = new JTextField(" Email");
        emailUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        websiteUpdate = new JTextField(" Website");
        websiteUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        addressUpdate = new JTextField(" Address");
        addressUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        bioUpdate = new JTextArea(" Biography");        
        bioUpdate.setLineWrap(true);
        bioUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        JScrollPane scrollBioUpdate = new JScrollPane(bioUpdate);
        
        JButton submitUpdate = new JButton("Save");
        submitUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        submitUpdate.addActionListener((ActionListener) controllerListener);
        submitUpdate.setActionCommand("updateArtist");

        JButton submitDelete = new JButton("Delete");
        submitDelete.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        submitDelete.setForeground(Color.RED);
        submitDelete.addActionListener((ActionListener) controllerListener);
        submitDelete.setActionCommand("deleteArtist"); 
        
        gLayoutUpdate.setHorizontalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(TRAILING)
                    .addComponent(empty)
                    .addComponent(firstNameUpdate)
                    .addComponent(lastNameUpdate)
                    .addComponent(emailUpdate)
                    .addComponent(websiteUpdate)
                    .addComponent(addressUpdate)
                    .addComponent(scrollBioUpdate)
                    .addComponent(submitUpdate)
                    .addComponent(submitDelete)
            )
        );  
  
        gLayoutUpdate.setVerticalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(empty)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(firstNameUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(lastNameUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(emailUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(websiteUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(addressUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(scrollBioUpdate)
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
    public ElementList getArtistList(){
        return artistList;
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
        data.put("first_name", firstNameAdd.getText());
        data.put("last_name", lastNameAdd.getText());
        data.put("website", websiteAdd.getText());
        data.put("email", emailAdd.getText());
        data.put("address", addressAdd.getText());
        data.put("biography", bioAdd.getText());
        return data;
    }
    
    /**
     * 
     * @return Data of the Update form in JSONObject format.
     */
    public JSONObject getUpdateData(){
        JSONObject data = new JSONObject();
        data.put("slug_name", slugnameUpdate);
        data.put("first_name", firstNameUpdate.getText());
        data.put("last_name", lastNameUpdate.getText());
        data.put("website", websiteUpdate.getText());
        data.put("email", emailUpdate.getText());
        data.put("address", addressUpdate.getText());
        data.put("biography", bioUpdate.getText());
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
     * Update artist shown in the update panel of the view.
     * @param artist receive an artist object.
     */
    public void setUpdateElement(JSONObject artist){
        
        slugnameUpdate = artist.getString("slug_name");
        firstNameUpdate.setText(artist.getString("first_name"));
        lastNameUpdate.setText(artist.getString("last_name"));
        emailUpdate.setText(artist.getString("email"));
        
        //try to get website, if it is null, set to empty.
        try {
            websiteUpdate.setText(artist.getString("website"));
        
        } catch (JSONException e){
            websiteUpdate.setText("");
        }
        
        //try to get address, if it is null, set to empty.
        try {
            addressUpdate.setText(artist.getString("address"));
        
        } catch (JSONException e){
            addressUpdate.setText("");
        }
        
        //try to get biography, if it is null, set to empty.
        try {
            bioUpdate.setText(artist.getString("biography"));
        
        } catch (JSONException e){
            bioUpdate.setText("");
        }
        
        //try to get photo, if it is null, show placeholder.        
        try {
            URL newImage = new URL(artist.getString("photo"));
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