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
import com.cct.artgallery.auth.AuthController;
import com.cct.artgallery.utils.UserDetail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                break;
            case "usersMenu":
                menu.setActive(e.getActionCommand());
                adminView.updateContent(e.getActionCommand());
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
                    adminView.artPiecePanel.showMessage(responseMessage, true);
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
                    adminView.artPiecePanel.showMessage(responseMessage, true);
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
                    break;
                default:
                    int status = response.getInt("status");
                    String responseMessage = "Error " + status + ": Please try again later.";
                    adminView.artPiecePanel.showMessage(responseMessage, true);
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
     * 
     * @param index position of the art piece in the List Model which want to
     * be selected to show in the detail panel.
     */
    public void selectArtPiece(int index){
        ArtPiecePanel artPiecePanel = adminView.getArtPiecesPanel();
        ElementList artPiecesList = artPiecePanel.getArtPiecesList();
        JSONObject artPiece = artPiecesList.getListModel().get(index).getElement();
        
        adminView.artPiecePanel.setUpdateElement(artPiece);
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
        adminView.artPiecePanel.setFilePath(path);
        adminView.artPiecePanel.setFileName(filename);
        
    }
    
    /**
     * Handles all the verification and send data to the model.
     */
    private void addArtPiece(){
        JSONObject data = adminView.artPiecePanel.getAddData();
        
        //Validate data
        if(data.getString("description").equals(" Description")){
            data.remove("description");
            data.put("description", "");
        }
        
        if(data.getString("photo").equals("")){
            adminView.artPiecePanel.showMessage("Please provide a photo", true);
        }
        else if(data.getInt("category")==0){
            adminView.artPiecePanel.showMessage("Please select a category", true);
        }
        else if(data.getInt("artist")==0){
            adminView.artPiecePanel.showMessage("Please select an artist", true);
        }
        else if(data.getString("name").equals(" Title")){
            adminView.artPiecePanel.showMessage("Please enter a title", true);
        }
        else if(data.getString("price").equals(" Price")){
            adminView.artPiecePanel.showMessage("Please enter a price", true);
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
                    adminView.artPiecePanel.showMessage(responseMessage, false);
                    getArtPieces();
                    break;
                default:
                    responseMessage = "Error " + status + ": Please try again later.";
                    adminView.artPiecePanel.showMessage(responseMessage, true);
                    break;
            }

            adminView.topBar.setLoading(false);
        }).start();
    }
    
    private void updateArtPiece(){
        JSONObject data = adminView.artPiecePanel.getUpdateData();
        adminView.topBar.setLoading(true);
        
        if(data.getString("price").equals(" Price")){
            String errorMessage = "Please select an Art piece.";
            adminView.artPiecePanel.showMessage(errorMessage, true);
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
                        adminView.artPiecePanel.showMessage(responseMessage, false);
                        getArtPieces();
                        break;
                    default:
                        responseMessage = "Error " + status + ": Please try again later.";
                        adminView.artPiecePanel.showMessage(responseMessage, true);
                        break;
                }

                adminView.topBar.setLoading(false);
            }).start();
        }
    }
    
    private void deleteArtPiece(){
        JSONObject data = adminView.artPiecePanel.getUpdateData();
        adminView.topBar.setLoading(true);
        
        if(data.getString("price").equals(" Price")){
            String errorMessage = "Please select an Art piece.";
            adminView.artPiecePanel.showMessage(errorMessage, true);
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
                            adminView.artPiecePanel.showMessage(responseMessage, false);
                            getArtPieces();
                            selectArtPiece(0);
                            break;
                        default:
                            responseMessage = "Error " + status + ": Please try again later.";
                            adminView.artPiecePanel.showMessage(responseMessage, true);
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
