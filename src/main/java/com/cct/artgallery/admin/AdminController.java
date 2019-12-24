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

import com.cct.artgallery.admin.custom_table.ElementList;
import com.cct.artgallery.auth.AuthController;
import com.cct.artgallery.utils.UserDetail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class AdminController implements ActionListener{
    
    private final AuthController    authController;
    private final AdminView         adminView;
    private final MenuAdmin         menu;
    private       JSONArray         artPieces;
    private       JSONArray         categories;
    private       JSONArray         artists;
    private       JSONArray         users;
    
    public AdminController(AuthController parentController){
        authController = parentController;
        
        //Open admin view and get art pieces from the model.
        menu = new MenuAdmin(this);
        adminView = new AdminView(this, menu);
        getArtPieces();
        getCategories();
        getArtists();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "artPiecesMenu":
                menu.setActive(e.getActionCommand());
                adminView.updateContent(e.getActionCommand());
                getArtPieces();
                getCategories();
                getArtists();
                break;
            case "artistsMenu":
                menu.setActive(e.getActionCommand());
                adminView.updateContent(e.getActionCommand());
                getArtists();
                break;
            case "usersMenu":
                menu.setActive(e.getActionCommand());
                adminView.updateContent(e.getActionCommand());
                getUsers();
                break;
            case "logout":
                adminView.dispose();
                UserDetail.logout();
                authController.openLogin();
                break;
            case "addFileArtPiece":
                getFileArtPiece();
                break;
            case "addArtPiece":
                addArtPiece();
                break;
            case "updateArtPiece":
                updateArtPiece();
                break;
            case "deleteArtPiece":
                deleteArtPiece();
                break;
            case "addFileArtist":
                getFileArtist();
                break;
            case "addArtist":
                addArtist();
                break;
            case "updateArtist":
                updateArtist();
                break;
            case "deleteArtist":
                deleteArtist();
                break;
            case "addUser":
                addUser();
                break;
            case "updateUser":
                updateUser();
                break;
            case "deleteUser":
                deleteUser();
                break;
            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * Request ArtPieces to the model and populates the ArtPieceList in the View.
     */
    private void getArtPieces(){
        adminView.topBar.setLoading(true);

        new Thread(() -> {
            JSONObject response = AdminModel.getArtPieces();

            switch (response.getInt("status")) {
                case 200:
                    //Populate the ArtPieceList with the data from the model.
                    JSONArray responseData = response.getJSONArray("data");
                    this.artPieces = responseData;
                    
                    ArtPiecePanel artPiecePanel = adminView.getArtPiecesPanel();
                    ElementList artPiecesList = artPiecePanel.getArtPiecesList();
                    artPiecesList.getListModel().clear();
                    
                    for (int i = 0; i < responseData.length(); i++){
                        JSONObject artPiece = responseData.getJSONObject(i);
                        artPiecesList.addElement(artPiece);
                    }
                    break;
                default:
                    int status = response.getInt("status");
                    String responseMessage = "Error " + status + ": Please try again later.";
                    adminView.getArtPiecesPanel().showMessage(responseMessage, true);
                    break;
            }

            adminView.topBar.setLoading(false);
        }).start();
        
    }
    
    /**
     * Request Categories to the model and populates the ComboBox in the View.
     */
    private void getCategories(){
        adminView.topBar.setLoading(true);

        new Thread(() -> {
            JSONObject response = AdminModel.getCategories();

            switch (response.getInt("status")) {
                case 200:
                    //Populate the ArtPieceList with the data from the model.
                    JSONArray responseData = response.getJSONArray("data");
                    this.categories = responseData;
                    
                    ArtPiecePanel artPiecePanel = adminView.getArtPiecesPanel();
                    JComboBox categoriesUpdate = artPiecePanel.getCategoryUpdate();
                    JComboBox categoriesAdd = artPiecePanel.getCategoryAdd();
                    
                    categoriesUpdate.removeAllItems();
                    
                    for (int i = 0; i < responseData.length(); i++){
                        JSONObject category = responseData.getJSONObject(i);
                        String categoryName = category.getString("name");
                        
                        
                        categoriesUpdate.addItem(objectComboBox(categoryName));
                        categoriesAdd.addItem(objectComboBox(categoryName));
                    }
                    break;
                default:
                    int status = response.getInt("status");
                    String responseMessage = "Error " + status + ": Please try again later.";
                    adminView.getArtPiecesPanel().showMessage(responseMessage, true);
                    break;
            }
            adminView.topBar.setLoading(false);
        }).start();
        
    }
        
    /**
     * Request Categories to the model and populates the ComboBox in the View.
     */
    private void getArtists(){
        adminView.topBar.setLoading(true);

        new Thread(() -> {
            JSONObject response = AdminModel.getArtists();

            switch (response.getInt("status")) {
                case 200:
                    //Populate the ArtPieceList with the data from the model.
                    JSONArray responseData = response.getJSONArray("data");
                    this.artists = responseData;
                    
                    //Populate the ComboBox
                    ArtPiecePanel artPiecePanel = adminView.getArtPiecesPanel();
                    JComboBox artistsUpdate = artPiecePanel.getArtistUpdate();
                    JComboBox artistAdd = artPiecePanel.getArtistAdd();
                    
                    artistsUpdate.removeAllItems();
                    
                    for (int i = 0; i < responseData.length(); i++){
                        JSONObject artist = responseData.getJSONObject(i);
                        String artistFirstName = artist.getString("first_name");
                        String artistLastName = artist.getString("last_name");
                        String artistName = artistFirstName + " " + artistLastName;
                        
                        artistsUpdate.addItem(objectComboBox(artistName));
                        artistAdd.addItem(objectComboBox(artistName));
                    }
                    
                    //Populate the Artist List
                    ArtistPanel artistPanel = adminView.getArtistPanel();
                    ElementList artistList = artistPanel.getArtistList();
                    artistList.getListModel().clear();
                    
                    for (int i = 0; i < responseData.length(); i++){
                        JSONObject artist = responseData.getJSONObject(i);
                        artistList.addElement(artist);
                    }
                    
                    break;
                default:
                    int status = response.getInt("status");
                    String responseMessage = "Error " + status + ": Please try again later.";
                    adminView.getArtPiecesPanel().showMessage(responseMessage, true);
                    adminView.getArtistPanel().showMessage(responseMessage, true);
                    break;
            }
            adminView.topBar.setLoading(false);
        }).start();
        
    }
    
    /**
     * Method just to populate the ComboBox since it does not accept Strings.
     * @param value name to show in the ComboBox
     * @return An object which returns the value as string.
     */
    private Object objectComboBox(String value){
        return new Object() {
            @Override 
            public String toString() { 
                return value; 
            }
        };
    }
    
    /**
     * Filter art pieces based on the text given.
     * @param text String to search in Art Pieces.
     */
    public void filterArtPiece(String text){
        ArtPiecePanel artPiecePanel = adminView.getArtPiecesPanel();
        ElementList artPiecesList = artPiecePanel.getArtPiecesList();
        artPiecesList.getListModel().clear();
        
        //Put text in lower case
        String textLower = text.toLowerCase();

        for (int i = 0; i < this.artPieces.length(); i++){
            JSONObject artPiece = this.artPieces.getJSONObject(i);
            if(textLower.equals("")){
                artPiecesList.addElement(artPiece);
            }
            else{
                String title = artPiece.getString("name").toLowerCase();
                String price = artPiece.getInt("price")+"";
                JSONObject categoryObj = artPiece.getJSONObject("category");
                String category = categoryObj.getString("name").toLowerCase();
                JSONObject artistObj = artPiece.getJSONObject("artist");
                String artist = artistObj.getString("first_name") + " " +artistObj.getString("last_name");
                artist = artist.toLowerCase();
                
                if(title.contains(textLower)){
                    artPiecesList.addElement(artPiece);
                }
                else if(price.contains(textLower)){
                    artPiecesList.addElement(artPiece);
                }
                else if(category.contains(textLower)){
                    artPiecesList.addElement(artPiece);
                }
                else if(artist.contains(textLower)){
                    artPiecesList.addElement(artPiece);
                }
            }
            
        }
    }
            
    /**
     * Filter artists based on the text given.
     * @param text String to search in Artists.
     */
    public void filterArtist(String text){
        ArtistPanel artistPanel = adminView.getArtistPanel();
        ElementList artistList = artistPanel.getArtistList();
        artistList.getListModel().clear();
        
        //Put text in lower case
        String textLower = text.toLowerCase();

        for (int i = 0; i < this.artists.length(); i++){
            JSONObject artist = this.artists.getJSONObject(i);
            if(textLower.equals("")){
                artistList.addElement(artist);
            }
            else{
                String name = artist.getString("first_name") + " " +artist.getString("last_name");
                name = name.toLowerCase();
                String website = artist.getString("website").toLowerCase();
                String email = artist.getString("email").toLowerCase();
                
                if(name.contains(textLower)){
                    artistList.addElement(artist);
                }
                else if(website.contains(textLower)){
                    artistList.addElement(artist);
                }
                else if(email.contains(textLower)){
                    artistList.addElement(artist);
                }
            }
            
        }
    }
    
    /**
     * Filter artists based on the text given.
     * @param text String to search in Artists.
     */
    public void filterUser(String text){
        UserPanel userPanel = adminView.getUserPanel();
        ElementList userList = userPanel.getUserList();
        userList.getListModel().clear();
        
        //Put text in lower case
        String textLower = text.toLowerCase();

        for (int i = 0; i < this.users.length(); i++){
            JSONObject user = this.users.getJSONObject(i);
            if(textLower.equals("")){
                userList.addElement(user);
            }
            else{
                String name = user.getString("first_name") + " " +user.getString("last_name");
                name = name.toLowerCase();
                String username = user.getString("username").toLowerCase();
                String email = user.getString("email").toLowerCase();
                
                if(name.contains(textLower)){
                    userList.addElement(user);
                }
                else if(username.contains(textLower)){
                    userList.addElement(user);
                }
                else if(email.contains(textLower)){
                    userList.addElement(user);
                }
            }
            
        }
    }
    
    /**
     * 
     * @param index position of the art piece in the List Model which want to
     * be selected to show in the detail panel.
     */
    public void selectArtPiece(int index){
        ArtPiecePanel artPiecePanel = adminView.getArtPiecesPanel();
        ElementList artPiecesList = artPiecePanel.getArtPiecesList();
        JSONObject artPiece = artPiecesList.getListModel().get(index).getElement();
        
        adminView.getArtPiecesPanel().setUpdateElement(artPiece);
    }
    
    /**
     * 
     * @param index position of the art piece in the List Model which want to
     * be selected to show in the detail panel.
     */
    public void selectArtist(int index){
        ArtistPanel artistPanel = adminView.getArtistPanel();
        ElementList artistList = artistPanel.getArtistList();
        JSONObject artist = artistList.getListModel().get(index).getElement();
        
        adminView.getArtistPanel().setUpdateElement(artist);
    }
    
    /**
     * 
     * @param index position of the art piece in the List Model which want to
     * be selected to show in the detail panel.
     */
    public void selectUser(int index){
        UserPanel userPanel = adminView.getUserPanel();
        ElementList userList = userPanel.getUserList();
        JSONObject user = userList.getListModel().get(index).getElement();
        
        adminView.getUserPanel().setUpdateElement(user);
    }
    
    /**
     * Ask user for a image file and store the image on memory.
     */
    private void getFileArtPiece(){
        JFileChooser fileChooser = new JFileChooser();
        
        //Filter to choose images.
        FileFilter imageFilter = new FileNameExtensionFilter(
            "Image files", ImageIO.getReaderFileSuffixes());
        
        fileChooser.setFileFilter(imageFilter);
        // Open the save dialog 
        fileChooser.showSaveDialog(null);
        
        //Get Path and name
        String path=fileChooser.getSelectedFile().getAbsolutePath();
        String filename=fileChooser.getSelectedFile().getName();
        
        //Store values in the Panel
        adminView.getArtPiecesPanel().setFilePath(path);
        adminView.getArtPiecesPanel().setFileName(filename);
        
    }
    
      /**
     * Ask user for a image file and store the image on memory.
     */
    private void getFileArtist(){
        JFileChooser fileChooser = new JFileChooser();
        
        //Filter to choose images.
        FileFilter imageFilter = new FileNameExtensionFilter(
            "Image files", ImageIO.getReaderFileSuffixes());
        
        fileChooser.setFileFilter(imageFilter);
        // Open the save dialog 
        fileChooser.showSaveDialog(null);
        
        //Get Path and name
        String path=fileChooser.getSelectedFile().getAbsolutePath();
        String filename=fileChooser.getSelectedFile().getName();
        
        //Store values in the Panel
        adminView.getArtistPanel().setFilePath(path);
        adminView.getArtistPanel().setFileName(filename);
    }
    
    /**
     * Handles all the verification and send data to the model.
     */
    private void addArtPiece(){
        JSONObject data = adminView.getArtPiecesPanel().getAddData();
        
        //Validate data
        if(data.getString("description").equals(" Description")){
            data.remove("description");
            data.put("description", "");
        }
        
        if(data.getString("photo").equals("")){
            adminView.getArtPiecesPanel().showMessage("Please provide a photo", true);
        }
        else if(data.getInt("category")==0){
            adminView.getArtPiecesPanel().showMessage("Please select a category", true);
        }
        else if(data.getInt("artist")==0){
            adminView.getArtPiecesPanel().showMessage("Please select an artist", true);
        }
        else if(data.getString("name").equals(" Title")){
            adminView.getArtPiecesPanel().showMessage("Please enter a title", true);
        }
        else if(data.getString("price").equals(" Price")){
            adminView.getArtPiecesPanel().showMessage("Please enter a price", true);
        }
        else {
            //Convert string price into int
            int price = Integer.parseInt(data.getString("price"));
            data.remove("price");
            data.put("price", price);
            
            JSONObject categoryObj = this.categories.getJSONObject(data.getInt("category")-1);
            String category = categoryObj.getString("slug_name");
            data.remove("category");
            data.put("category", category);
            
            JSONObject artistObj = this.artists.getJSONObject(data.getInt("artist")-1);
            String artist = artistObj.getString("slug_name");
            data.remove("artist");
            data.put("artist", artist);
            
            //Add details and tags option not implemented.
            String empty[] = {};
            data.put("details", empty);
            data.put("tags", empty);
        }
        
        adminView.topBar.setLoading(true);

        new Thread(() -> {
            JSONObject response = AdminModel.addArtPiece(data);
            int status = response.getInt("status");
            String responseMessage;

            switch (status) {
                case 201:
                    responseMessage = "Art piece added successfully.";
                    adminView.getArtPiecesPanel().showMessage(responseMessage, false);
                    getArtPieces();
                    break;
                default:
                    responseMessage = "Error " + status + ": Please try again later.";
                    adminView.getArtPiecesPanel().showMessage(responseMessage, true);
                    break;
            }

            adminView.topBar.setLoading(false);
        }).start();
    }
    
    private void updateArtPiece(){
        JSONObject data = adminView.getArtPiecesPanel().getUpdateData();
        adminView.topBar.setLoading(true);
        
        if(data.getString("price").equals(" Price")){
            String errorMessage = "Please select an Art piece.";
            adminView.getArtPiecesPanel().showMessage(errorMessage, true);
            adminView.topBar.setLoading(false);
        }
        else{
            //Convert string price into int
            int price = Integer.parseInt(data.getString("price"));
            data.remove("price");
            data.put("price", price);

            JSONObject categoryObj = this.categories.getJSONObject(data.getInt("category"));
            String category = categoryObj.getString("slug_name");
            data.remove("category");
            data.put("category", category);

            JSONObject artistObj = this.artists.getJSONObject(data.getInt("artist"));
            String artist = artistObj.getString("slug_name");
            data.remove("artist");
            data.put("artist", artist);

            new Thread(() -> {
                JSONObject response = AdminModel.updateArtPiece(data);
                int status = response.getInt("status");
                String responseMessage;
                
                switch (status) {
                    case 200:
                        responseMessage = "Art piece updated successfully.";
                        adminView.getArtPiecesPanel().showMessage(responseMessage, false);
                        getArtPieces();
                        break;
                    default:
                        responseMessage = "Error " + status + ": Please try again later.";
                        adminView.getArtPiecesPanel().showMessage(responseMessage, true);
                        break;
                }

                adminView.topBar.setLoading(false);
            }).start();
        }
    }
    
    private void deleteArtPiece(){
        JSONObject data = adminView.getArtPiecesPanel().getUpdateData();
        adminView.topBar.setLoading(true);
        
        if(data.getString("price").equals(" Price")){
            String errorMessage = "Please select an Art piece.";
            adminView.getArtPiecesPanel().showMessage(errorMessage, true);
            adminView.topBar.setLoading(false);
        }
        else{
            int confirmation = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure?", 
                "Delete Art piece",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if(confirmation==JOptionPane.YES_OPTION){
                new Thread(() -> {
                    JSONObject response = AdminModel.deleteArtPiece(data);
                    int status = response.getInt("status");
                    String responseMessage;

                    switch (status) {
                        case 204:
                            responseMessage = "Art piece deleted successfully.";
                            adminView.getArtPiecesPanel().showMessage(responseMessage, false);
                            getArtPieces();
                            try{
                                selectArtPiece(0);
                            }catch(ArrayIndexOutOfBoundsException e){
                                selectArtPiece(1);
                            }
                            break;
                        default:
                            responseMessage = "Error " + status + ": Please try again later.";
                            adminView.getArtPiecesPanel().showMessage(responseMessage, true);
                            break;
                    }

                    adminView.topBar.setLoading(false);
                }).start();
            }
            else{
                adminView.topBar.setLoading(false);
            }
        }
    }
    
    /**
     * Handles all the verification and send data to the model.
     */
    private void addArtist(){
        JSONObject data = adminView.getArtistPanel().getAddData();
        
        //Validate data
        if(data.getString("biography").equals(" Biography")){
            data.remove("biography");
            data.put("biography", " ");
        }
        
        Boolean validWebsite;
        try {
            new URL(data.getString("website")).toURI();
            validWebsite = true;
        }
        catch (URISyntaxException | MalformedURLException e) {
            validWebsite = false;
        }
        
        if(data.getString("photo").equals("")){
            adminView.getArtistPanel().showMessage("Please provide a photo", true);
        }
        else if(data.getString("first_name").equals(" First Name")){
            adminView.getArtistPanel().showMessage("Please enter first name", true);
        }
        else if(data.getString("last_name").equals(" Last Name")){
            adminView.getArtistPanel().showMessage("Please enter last name", true);
        }
        else if(data.getString("email").equals(" Email")){
            adminView.getArtistPanel().showMessage("Please enter an email", true);
        }
        else if(!validWebsite){
            adminView.getArtistPanel().showMessage("Website incorrect format.", true);
        }
        else if(data.getString("address").equals(" Address")){
            adminView.getArtistPanel().showMessage("Please enter an address", true);
        }
        else{
            adminView.topBar.setLoading(true);

            new Thread(() -> {
                JSONObject response = AdminModel.addArtist(data);
                int status = response.getInt("status");
                String responseMessage;

                switch (status) {
                    case 201:
                        responseMessage = "Artist added successfully.";
                        adminView.getArtistPanel().showMessage(responseMessage, false);
                        getArtists();
                        break;
                    default:
                        responseMessage = "Error " + status + ": Please try again later.";
                        adminView.getArtistPanel().showMessage(responseMessage, true);
                        break;
                }

                adminView.topBar.setLoading(false);
            }).start();
        }
    }
    
    /**
     * Handles update art piece, request info from view and passing to model.
     */
    private void updateArtist(){
        JSONObject data = adminView.getArtistPanel().getUpdateData();
        adminView.topBar.setLoading(true);
        
        if(data.getString("email").equals(" Email")){
            String errorMessage = "Please select an Artist.";
            adminView.getArtistPanel().showMessage(errorMessage, true);
            adminView.topBar.setLoading(false);
        }
        else{
            new Thread(() -> {
                JSONObject response = AdminModel.updateArtist(data);
                int status = response.getInt("status");
                String responseMessage;
                
                switch (status) {
                    case 200:
                        responseMessage = "Artist updated successfully.";
                        adminView.getArtistPanel().showMessage(responseMessage, false);
                        getArtists();
                        break;
                    default:
                        responseMessage = "Error " + status + ": Please try again later.";
                        adminView.getArtistPanel().showMessage(responseMessage, true);
                        break;
                }

                adminView.topBar.setLoading(false);
            }).start();
        }
    }
    
    /**
     * Handle delete artist logic.
     */
    private void deleteArtist(){
        JSONObject data = adminView.getArtistPanel().getUpdateData();
        adminView.topBar.setLoading(true);
        
        if(data.getString("email").equals(" Email")){
            String errorMessage = "Please select an Artist..";
            adminView.getArtistPanel().showMessage(errorMessage, true);
            adminView.topBar.setLoading(false);
        }
        else{
            int confirmation = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure?", 
                "Delete Artist",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if(confirmation==JOptionPane.YES_OPTION){
                new Thread(() -> {
                    JSONObject response = AdminModel.deleteArtist(data);
                    int status = response.getInt("status");
                    String responseMessage;

                    switch (status) {
                        case 204:
                            responseMessage = "Artist deleted successfully.";
                            adminView.getArtistPanel().showMessage(responseMessage, false);
                            getArtists();
                            try{
                                selectArtist(0);
                            }catch(ArrayIndexOutOfBoundsException e){
                                selectArtist(1);
                            }
                            
                            break;
                        default:
                            responseMessage = "Error " + status + ": Please try again later.";
                            adminView.getArtistPanel().showMessage(responseMessage, true);
                            break;
                    }

                    adminView.topBar.setLoading(false);
                }).start();
            }
            else{
                adminView.topBar.setLoading(false);
            }
        }
    }
    
    /**
     * Request Users to the model and populates the list in the View.
     */
    private void getUsers(){
        adminView.topBar.setLoading(true);

        new Thread(() -> {
            JSONObject response = AdminModel.getUsers();

            switch (response.getInt("status")) {
                case 200:
                    //Populate the UserList with the data from the model.
                    JSONArray responseData = response.getJSONArray("data");
                    this.users = responseData;
                    
                    //Populate the User List
                    UserPanel userPanel = adminView.getUserPanel();
                    ElementList userList = userPanel.getUserList();
                    userList.getListModel().clear();
                    
                    for (int i = 0; i < responseData.length(); i++){
                        JSONObject user = responseData.getJSONObject(i);
                        userList.addElement(user);
                    }
                    
                    break;
                default:
                    int status = response.getInt("status");
                    String responseMessage = "Error " + status + ": Please try again later.";
                    adminView.getUserPanel().showMessage(responseMessage, true);
                    break;
            }
            adminView.topBar.setLoading(false);
        }).start();
    }
    
    /**
     * Handles all the verification and send data to the model.
     */
    private void addUser(){
        JSONObject data = adminView.getUserPanel().getAddData();
        
        //Validate data
        if(!data.getString("password").equals(data.getString("password_confirmation"))){
            adminView.getUserPanel().showMessage("Password does not match.", true);
        }
        
        if(data.getString("first_name").equals(" First Name")){
            adminView.getUserPanel().showMessage("Please enter first name", true);
        }
        else if(data.getString("last_name").equals(" Last Name")){
            adminView.getUserPanel().showMessage("Please enter last name", true);
        }
        else if(data.getString("username").equals(" Username")){
            adminView.getUserPanel().showMessage("Please enter a username", true);
        }
        else if(data.getString("email").equals(" Email")){
            adminView.getUserPanel().showMessage("Please enter an email", true);
        }
        else if(data.getString("address").equals(" Address")){
            adminView.getUserPanel().showMessage("Please enter an address", true);
        }
        else{
            adminView.topBar.setLoading(true);

            new Thread(() -> {
                JSONObject response = AdminModel.addAdmin(data);
                int status = response.getInt("status");
                String responseMessage;

                switch (status) {
                    case 201:
                        responseMessage = "User added successfully.";
                        adminView.getUserPanel().showMessage(responseMessage, false);
                        getUsers();
                        break;
                    default:
                        responseMessage = "Error " + status + ": Please try again later.";
                        adminView.getUserPanel().showMessage(responseMessage, true);
                        break;
                }

                adminView.topBar.setLoading(false);
            }).start();
        }
    }
    
    /**
     * Handles update user, request info from view and passing to model.
     */
    private void updateUser(){
        JSONObject data = adminView.getUserPanel().getUpdateData();
        adminView.topBar.setLoading(true);
        
        if(data.getString("email").equals(" Email")){
            String errorMessage = "Please select a User.";
            adminView.getArtistPanel().showMessage(errorMessage, true);
            adminView.topBar.setLoading(false);
        }
        else{
            new Thread(() -> {
                JSONObject response = AdminModel.updateAdmin(data);
                int status = response.getInt("status");
                String responseMessage;
                
                switch (status) {
                    case 200:
                        responseMessage = "User updated successfully.";
                        adminView.getUserPanel().showMessage(responseMessage, false);
                        getUsers();
                        break;
                    default:
                        responseMessage = "Error " + status + ": Please try again later.";
                        adminView.getUserPanel().showMessage(responseMessage, true);
                        break;
                }

                adminView.topBar.setLoading(false);
            }).start();
        }
    }
    
     /**
     * Handle delete user logic.
     */
    private void deleteUser(){
        JSONObject data = adminView.getUserPanel().getUpdateData();
        adminView.topBar.setLoading(true);
        
        if(data.getString("email").equals(" Email")){
            String errorMessage = "Please select a User..";
            adminView.getArtistPanel().showMessage(errorMessage, true);
            adminView.topBar.setLoading(false);
        }
        else{
            int confirmation = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure?", 
                "Delete User",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if(confirmation==JOptionPane.YES_OPTION){
                new Thread(() -> {
                    JSONObject response = AdminModel.deleteUser(data);
                    int status = response.getInt("status");
                    String responseMessage;

                    switch (status) {
                        case 204:
                            responseMessage = "Artist deleted successfully.";
                            adminView.getUserPanel().showMessage(responseMessage, false);
                            getUsers();
                            try{
                                selectArtist(0);
                            }catch(ArrayIndexOutOfBoundsException e){
                                selectArtist(1);
                            }
                            
                            break;
                        default:
                            responseMessage = "Error " + status + ": Please try again later.";
                            adminView.getUserPanel().showMessage(responseMessage, true);
                            break;
                    }

                    adminView.topBar.setLoading(false);
                }).start();
            }
            else{
                adminView.topBar.setLoading(false);
            }
        }
    }
}
