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
import static javax.swing.GroupLayout.Alignment.TRAILING;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class PublicProfilePanel extends JPanel {
    
    private final PublicController  controllerListener;
    //private final JLabel          message;
    
    //Update Attributes
    private final JLabel            imageUpdate;
    private final JTextField        titleUpdate;
    private final JTextField        priceUpdate;
    private final JTextField        categoryUpdate;
    private final JTextField        artistUpdate;
    
    //Detail Attributes
    private       String            filePath = "";
    private final JLabel            fileLabel;
    private final JTextField        titleDetail;
    private final JTextField        priceDetail;
    private final JTextField        categoryDetail;
    private final JTextField        artistDetail;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PublicProfilePanel(PublicController controller){
        this.controllerListener = controller;
        
        //Set JPanel properties
        this.setPreferredSize(new Dimension(1000, 660));
        this.setMaximumSize(new Dimension(1000, 660));
        this.setBackground(Color.WHITE);
        
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
        leftPanel.setPreferredSize(new Dimension(300, 500));
        leftPanel.setMaximumSize(new Dimension(300, 500));
        leftPanel.setBackground(Color.WHITE);

        
        //Right Content (details)
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(300, 500));
        rightPanel.setMaximumSize(new Dimension(300, 500));
        rightPanel.setBackground(Color.WHITE);
        
        //Update element Panel
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BoxLayout (updatePanel, BoxLayout.Y_AXIS));
        updatePanel.setPreferredSize(new Dimension(290, 370));
        updatePanel.setMaximumSize(new Dimension(290, 370));
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
        updateForm.setPreferredSize(new Dimension(250, 150));
        updateForm.setMaximumSize(new Dimension(250, 150));
        updateForm.setBackground(new Color(241, 164, 66));
        
        GroupLayout gLayoutUpdate = new GroupLayout(updateForm);
        gLayoutUpdate.setAutoCreateGaps(true);  
        gLayoutUpdate.setAutoCreateContainerGaps(true);  
        updateForm.setLayout(gLayoutUpdate);  
        
        titleUpdate = new JTextField(" First Name");
        titleUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        titleUpdate.setEditable(false);
        titleUpdate.setToolTipText("First Name");
                
        categoryUpdate = new JTextField(" Last Name");
        categoryUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        categoryUpdate.setEditable(false);
        categoryUpdate.setToolTipText("Last Name");
        
        artistUpdate = new JTextField(" Email");
        artistUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        artistUpdate.setEditable(false);
        artistUpdate.setToolTipText("Email");
        
        priceUpdate = new JTextField(" Address");
        priceUpdate.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        priceUpdate.setEditable(false);
        priceUpdate.setToolTipText("Address"); 
        
        gLayoutUpdate.setHorizontalGroup(gLayoutUpdate.createSequentialGroup()
            .addGroup(gLayoutUpdate.createParallelGroup(TRAILING)
                    .addComponent(titleUpdate)
                    .addComponent(categoryUpdate)
                    .addComponent(artistUpdate)
                    .addComponent(priceUpdate)
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
        );
        
        //-------------------right panel now
        
        //Update element Panel
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout (detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setPreferredSize(new Dimension(290, 280));
        detailPanel.setMaximumSize(new Dimension(290, 280));
        detailPanel.setBackground(new Color(241, 164, 66));
        
        JLabel detail = new JLabel("Update");
        detail.setFont(roboto.deriveFont(Font.BOLD, 15f));
        detail.setAlignmentX(Component.CENTER_ALIGNMENT);
        detail.setForeground(Color.WHITE);
        
        //Left Image Update Panel
        JPanel detailImage = new JPanel();
        detailImage.setPreferredSize(new Dimension(200, 200));
        detailImage.setMaximumSize(new Dimension(200, 200));
        detailImage.setBackground(new Color(241, 164, 66));
        
        //Right Form Update Panel
        JPanel detailForm = new JPanel();
        detailForm.setPreferredSize(new Dimension(250, 200));
        detailForm.setMaximumSize(new Dimension(250, 200));
        detailForm.setBackground(new Color(241, 164, 66));
        
        GroupLayout gLayoutDetail = new GroupLayout(detailForm);
        gLayoutUpdate.setAutoCreateGaps(true);  
        gLayoutUpdate.setAutoCreateContainerGaps(true);  
        detailForm.setLayout(gLayoutDetail);
        
        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(0, 20));
        empty.setMaximumSize(new Dimension(0, 20));
        empty.setBackground(new Color(241, 164, 66));
        
        JPanel fileAdd = new JPanel(new FlowLayout(FlowLayout.LEFT));  
        fileAdd.setBackground(new Color(241, 164, 66));
        
        JButton fileButton = new JButton("Choose a file...");
        fileButton.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        fileButton.addActionListener((ActionListener) controllerListener);
        fileButton.setActionCommand("addFileProfile");
        
        fileLabel = new JLabel("");
        fileLabel.setFont(roboto.deriveFont(Font.PLAIN, 9f));
        
        fileAdd.add(fileButton);
        fileAdd.add(fileLabel);
        
        titleDetail = new JTextField(" First Name");
        titleDetail.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        titleDetail.setToolTipText("First Name");
                
        categoryDetail = new JTextField(" Last Name");
        categoryDetail.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        categoryDetail.setToolTipText("Last Name");
        
        artistDetail = new JTextField(" Email");
        artistDetail.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        artistDetail.setToolTipText("Email");
        
        priceDetail = new JTextField(" Address");
        priceDetail.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        priceDetail.setToolTipText("Address"); 
        
        JButton detailSave = new JButton("Save");
        detailSave.setFont(roboto.deriveFont(Font.PLAIN, 11f));
        detailSave.addActionListener((ActionListener) controllerListener);
        detailSave.setActionCommand("updateProfile");
        
        gLayoutDetail.setHorizontalGroup(gLayoutDetail.createSequentialGroup()
            .addGroup(gLayoutDetail.createParallelGroup(TRAILING)
                    .addComponent(empty)
                    .addComponent(fileAdd)
                    .addComponent(titleDetail)
                    .addComponent(categoryDetail)
                    .addComponent(artistDetail)
                    .addComponent(priceDetail)
                    .addComponent(detailSave)
            )
        );  
  
        gLayoutDetail.setVerticalGroup(gLayoutDetail.createSequentialGroup()
            .addGroup(gLayoutDetail.createParallelGroup(BASELINE)
                    .addComponent(empty)
            )  
            .addGroup(gLayoutDetail.createParallelGroup(BASELINE)
                    .addComponent(fileAdd)
            )  
            .addGroup(gLayoutDetail.createParallelGroup(BASELINE)
                    .addComponent(titleDetail)
            )
            .addGroup(gLayoutDetail.createParallelGroup(BASELINE)
                    .addComponent(categoryDetail)
            )
            .addGroup(gLayoutDetail.createParallelGroup(BASELINE)
                    .addComponent(artistDetail)
            )
            .addGroup(gLayoutDetail.createParallelGroup(BASELINE)
                    .addComponent(priceDetail)
            )
            .addGroup(gLayoutDetail.createParallelGroup(BASELINE)
                    .addComponent(detailSave)
            )
        );
    
        //End elements, start assign to panels.
        
        updatePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        updatePanel.add(update);
        updatePanel.add(updateImage);
        updatePanel.add(updateForm);
        
        detailPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailPanel.add(detail);
        detailPanel.add(detailForm);
        
        rightPanel.add(updatePanel);
        leftPanel.add(detailPanel);
        
        contentPanel.add(Box.createRigidArea(new Dimension(150, 0)));
        contentPanel.add(rightPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        contentPanel.add(leftPanel);
        this.add(contentPanel);
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
     * @return Data of the Detail form in JSONObject format.
     */
    public JSONObject getAddData(){
        JSONObject data = new JSONObject();
        data.put("photo", filePath);
        data.put("first_name", titleDetail.getText());
        data.put("last_name", categoryDetail.getText());
        data.put("email", artistDetail.getText());
        data.put("address", priceDetail.getText());
        return data;
    }


    /**
     * Update artist shown in the detail panel of the view.
     * @param user receive a user as JSON an show in the detail panel.
     */
    public void setUpdateElement(JSONObject user){
        
        titleUpdate.setText(user.getString("first_name"));
        titleDetail.setText(user.getString("first_name"));
        
        categoryUpdate.setText(user.getString("last_name"));
        categoryDetail.setText(user.getString("last_name"));
        
        artistUpdate.setText(user.getString("email"));
        artistDetail.setText(user.getString("email"));
        
        try{
            priceUpdate.setText(user.getString("address"));
            priceDetail.setText(user.getString("address"));
        } catch (JSONException e){
            priceUpdate.setText("");
            priceDetail.setText("");
        }
        
        //try to get photo, if it is null, show placeholder.        
        try {
            URL newImage = new URL(user.getString("photo"));
            BufferedImage bufferedImage = ImageIO.read(newImage);
            Image resizedImage = bufferedImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(resizedImage);
            imageUpdate.setIcon(imageIcon);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(PublicProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PublicProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e){
            URL placeholder = getClass().getClassLoader().getResource("images/placeholder_hq.png");
            imageUpdate.setIcon(new ImageIcon(placeholder));
        }

    }
}