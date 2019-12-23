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
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class UserPanel extends JPanel {
    
    private final AdminController   controllerListener;
    public        ElementList       userList;
    private final JLabel            message;
    
    //Update Attributes
    private       String            slugnameUpdate;
    private final JTextField        firstNameUpdate;
    private final JTextField        lastNameUpdate;
    private final JTextField        emailUpdate;
    private final JTextField        addressUpdate;
    
    //Add Attributes
    private final JTextField        firstNameAdd;
    private final JTextField        lastNameAdd;
    private final JTextField        usernameAdd;
    private final JTextField        emailAdd;
    private final JTextField        passwordAdd;
    private final JTextField        passwordConfAdd;
    private final JTextField        addressAdd;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public UserPanel(AdminController controller){
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
        
        JLabel titleLabel = new JLabel("  Users");
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
                    controllerListener.filterUser(searchText);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchText = searchBar.getText();
                if(!searchText.equals(" Search")){
                    controllerListener.filterUser(searchText);
                }
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchText = searchBar.getText();
                if(!searchText.equals(" Search")){
                    controllerListener.filterUser(searchText);
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

        JLabel category = new JLabel("Username");
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
        userList = new ElementList(controllerListener, "user");
        JScrollPane artPiecesPanel = new JScrollPane(userList); 
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
        
        usernameAdd = new JTextField(" Username");
        usernameAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        usernameAdd.setForeground(Color.GRAY);
        usernameAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameAdd.getText().equals(" Username")) {
                    usernameAdd.setText("");
                    usernameAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameAdd.getText().isEmpty()) {
                    usernameAdd.setForeground(Color.GRAY);
                    usernameAdd.setText(" Username");
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
        
        
        passwordAdd = new JTextField(" Password");
        passwordAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        passwordAdd.setForeground(Color.GRAY);
        passwordAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordAdd.getText().equals(" Password")) {
                    passwordAdd.setText("");
                    passwordAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordAdd.getText().isEmpty()) {
                    passwordAdd.setForeground(Color.GRAY);
                    passwordAdd.setText(" Password");
                }
            }
        }); 
        
        passwordConfAdd = new JTextField(" Password Confirmation");
        passwordConfAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        passwordConfAdd.setForeground(Color.GRAY);
        passwordConfAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordConfAdd.getText().equals(" Password Confirmation")) {
                    passwordConfAdd.setText("");
                    passwordConfAdd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordConfAdd.getText().isEmpty()) {
                    passwordConfAdd.setForeground(Color.GRAY);
                    passwordConfAdd.setText(" Password Confirmation");
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
        
        
        JButton submitAdd = new JButton("Save");
        submitAdd.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        submitAdd.addActionListener((ActionListener) controllerListener);
        submitAdd.setActionCommand("addUser");
        
        gLayoutAdd.setHorizontalGroup(gLayoutAdd.createSequentialGroup()
            .addGroup(gLayoutAdd.createParallelGroup(LEADING)
                    .addComponent(add)
                    .addComponent(firstNameAdd)
                    .addComponent(lastNameAdd)
                    .addComponent(usernameAdd)
                    .addComponent(emailAdd)
                    .addComponent(passwordAdd)
                    .addComponent(passwordConfAdd)
                    .addComponent(addressAdd)
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
                    .addComponent(firstNameAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(lastNameAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(usernameAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(emailAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(passwordAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(passwordConfAdd)
            )
            .addGroup(gLayoutAdd.createParallelGroup(BASELINE)
                    .addComponent(addressAdd)
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
        
        //Right Form Update Panel
        JPanel updateForm = new JPanel();
        updateForm.setPreferredSize(new Dimension(400, 200));
        updateForm.setMaximumSize(new Dimension(4000, 200));
        updateForm.setBackground(new Color(241, 164, 66));
        
        GroupLayout gLayoutUpdate = new GroupLayout(updateForm);
        gLayoutUpdate.setAutoCreateGaps(true);  
        gLayoutUpdate.setAutoCreateContainerGaps(true);  
        updateForm.setLayout(gLayoutUpdate);  
        
        JLabel update = new JLabel("Update");
        update.setFont(roboto.deriveFont(Font.BOLD, 15f));
        update.setForeground(Color.WHITE);
        
        firstNameUpdate = new JTextField(" First Name");
        firstNameUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        lastNameUpdate = new JTextField(" Last Name");
        lastNameUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        emailUpdate = new JTextField(" Email");
        emailUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        addressUpdate = new JTextField(" Address");
        addressUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        
        
        JButton submitUpdate = new JButton("Save");
        submitUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        submitUpdate.addActionListener((ActionListener) controllerListener);
        submitUpdate.setActionCommand("updateUser");

        JButton submitDelete = new JButton("Delete");
        submitDelete.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        submitDelete.setForeground(Color.RED);
        submitDelete.addActionListener((ActionListener) controllerListener);
        submitDelete.setActionCommand("deleteUser"); 
        
        gLayoutUpdate.setHorizontalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(LEADING)
                    .addComponent(update)
                    .addComponent(firstNameUpdate)
                    .addComponent(lastNameUpdate)
                    .addComponent(emailUpdate)
                    .addComponent(addressUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(TRAILING)
                    .addComponent(submitUpdate)
                    .addComponent(submitDelete)
            )
        );  
  
        gLayoutUpdate.setVerticalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(update)
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
                    .addComponent(addressUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(submitUpdate)
            )
            .addGroup(gLayoutUpdate.createParallelGroup(BASELINE)
                    .addComponent(submitDelete)
            )
        );
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
    public ElementList getUserList(){
        return userList;
    }
    
    /**
     * 
     * @return Data of the Add form in JSONObject format.
     */
    public JSONObject getAddData(){
        JSONObject data = new JSONObject();
        data.put("first_name", firstNameAdd.getText());
        data.put("last_name", lastNameAdd.getText());
        data.put("username", usernameAdd.getText());
        data.put("email", emailAdd.getText());
        data.put("password", passwordAdd.getText());
        data.put("password_confirmation", passwordConfAdd.getText());
        data.put("address", addressAdd.getText());
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
        data.put("email", emailUpdate.getText());
        data.put("address", addressUpdate.getText());
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
     * Update user shown in the update panel of the view.
     * @param user receive a user object.
     */
    public void setUpdateElement(JSONObject user){
        
        slugnameUpdate = user.getString("username");
        firstNameUpdate.setText(user.getString("first_name"));
        lastNameUpdate.setText(user.getString("last_name"));
        emailUpdate.setText(user.getString("email"));
        
        //try to get address, if it is null, set to empty.
        try {
            addressUpdate.setText(user.getString("address"));
        
        } catch (JSONException e){
            addressUpdate.setText("");
        }
    }
}