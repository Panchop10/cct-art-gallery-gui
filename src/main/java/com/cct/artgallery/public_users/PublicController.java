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
import com.cct.artgallery.admin.custom_table.ElementList;
import com.cct.artgallery.auth.AuthController;
import com.cct.artgallery.public_users.custom_table.PublicElementList;
import com.cct.artgallery.utils.UserDetail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class PublicController implements ActionListener{
    
    private final AuthController    authController;
    private final PublicView        publicView;
    private final MenuPublic        menu;
    private       JSONArray         artPieces;
    private       JSONArray         likedPieces;
    private       JSONArray         artists;
    
    public PublicController(AuthController parentController){
        authController = parentController;
        
        //Open admin view and get art pieces from the model.
        menu = new MenuPublic(this);
        publicView = new PublicView(this, menu);
        getArtPieces();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "artPiecesMenu":
                menu.setActive(e.getActionCommand());
                publicView.updateContent(e.getActionCommand());
                getArtPieces();
                break;
            case "artistsMenu":
                menu.setActive(e.getActionCommand());
                publicView.updateContent(e.getActionCommand());
                getArtists();
                break;
            case "favMenu":
                menu.setActive(e.getActionCommand());
                publicView.updateContent(e.getActionCommand());
                getLikedPieces();
                break;
            case "logout":
                publicView.dispose();
                UserDetail.logout();
                authController.openLogin();
                break;
            case "addFileArtPiece":
                getFileArtPiece();
                break;
            case "addFileArtist":
                getFileArtist();
                break;
            case "like":
                likeArtPiece();
                break;
            case "unlike":
                removeLikeArtPiece();
                break;
            case "unlikeFav":
                removeLikeFavourite();
                break;
            case "noliked":
                //System.out.println("Select an art piece first.");
                break;
            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * Request ArtPieces to the model and populates the ArtPieceList in the View.
     */
    private void getArtPieces(){
        publicView.topBar.setLoading(true);

        new Thread(() -> {
            JSONObject response = AdminModel.getArtPieces();

            switch (response.getInt("status")) {
                case 200:
                    //Populate the ArtPieceList with the data from the model.
                    JSONArray responseData = response.getJSONArray("data");
                    this.artPieces = responseData;
                    
                    PublicArtPiecePanel artPiecePanel = publicView.getArtPiecesPanel();
                    PublicElementList artPiecesList = artPiecePanel.getArtPiecesList();
                    
                    artPiecesList.getListModel().clear();
                    
                    for (int i = 0; i < responseData.length(); i++){
                        JSONObject artPiece = responseData.getJSONObject(i);
                        artPiecesList.addElement(artPiece);
                    }
                    break;
                default:
                    int status = response.getInt("status");
                    String responseMessage = "Error " + status + ": Please try again later.";
                    //publicView.getArtPiecesPanel().showMessage(responseMessage, true);
                    System.out.println(responseMessage);
                    break;
            }

            publicView.topBar.setLoading(false);
        }).start();
        
    }
    
        private void getLikedPieces(){
        publicView.topBar.setLoading(true);

        new Thread(() -> {
            JSONObject response = PublicModel.getFavourites();

            switch (response.getInt("status")) {
                case 200:
                    //Populate the ArtPieceList with the data from the model.
                    JSONArray responseData = response.getJSONArray("data");
                    this.likedPieces = responseData;
                    
                    PublicFavouritePanel favPanel = publicView.getFavouritePanel();
                    PublicElementList artPiecesList = favPanel.getArtPiecesList();
                    
                    artPiecesList.getListModel().clear();
                    
                    for (int i = 0; i < responseData.length(); i++){
                        JSONObject artPiece = responseData.getJSONObject(i);
                        artPiecesList.addElement(artPiece);
                    }
                    break;
                default:
                    int status = response.getInt("status");
                    String responseMessage = "Error " + status + ": Please try again later.";
                    //publicView.getArtPiecesPanel().showMessage(responseMessage, true);
                    System.out.println(responseMessage);
                    break;
            }
            publicView.topBar.setLoading(false);
        }).start();
        
    }
        
    /**
     * Request Categories to the model and populates the ComboBox in the View.
     */
    private void getArtists(){
        publicView.topBar.setLoading(true);

        new Thread(() -> {
            JSONObject response = AdminModel.getArtists();

            switch (response.getInt("status")) {
                case 200:
                    //Populate the ArtPieceList with the data from the model.
                    JSONArray responseData = response.getJSONArray("data");
                    this.artists = responseData;
                    
                    //Populate the Artist List
                    PublicArtistPanel artistPanel = publicView.getArtistPanel();
                    PublicElementList artistList = artistPanel.getArtistList();
                    artistList.getListModel().clear();
                    
                    for (int i = 0; i < responseData.length(); i++){
                        JSONObject artist = responseData.getJSONObject(i);
                        artistList.addElement(artist);
                    }
                    
                    break;
                default:
                    int status = response.getInt("status");
                    String responseMessage = "Error " + status + ": Please try again later.";
                    //publicView.getArtistPanel().showMessage(responseMessage, true);
                    System.out.println(responseMessage);
                    break;
            }
            publicView.topBar.setLoading(false);
        }).start();
        
    }
    
    /**
     * Filter art pieces based on the text given.
     * @param text String to search in Art Pieces.
     */
    public void filterArtPiece(String text){
        PublicArtPiecePanel artPiecePanel = publicView.getArtPiecesPanel();
        PublicElementList artPiecesList = artPiecePanel.getArtPiecesList();
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
     * Filter art pieces based on the text given.
     * @param text String to search in Art Pieces.
     */
    public void filterFavourite(String text){
        PublicFavouritePanel artPiecePanel = publicView.getFavouritePanel();
        PublicElementList artPiecesList = artPiecePanel.getArtPiecesList();
        artPiecesList.getListModel().clear();
        
        //Put text in lower case
        String textLower = text.toLowerCase();

        for (int i = 0; i < this.likedPieces.length(); i++){
            JSONObject artPiece = this.likedPieces.getJSONObject(i);
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
        PublicArtistPanel artistPanel = publicView.getArtistPanel();
        PublicElementList artistList = artistPanel.getArtistList();
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
     * 
     * @param index position of the art piece in the List Model which want to
     * be selected to show in the detail panel.
     */
    public void selectArtPiece(int index){
        PublicArtPiecePanel artPiecePanel = publicView.getArtPiecesPanel();
        PublicElementList artPiecesList = artPiecePanel.getArtPiecesList();
        JSONObject artPiece = artPiecesList.getListModel().get(index).getElement();
        
        publicView.getArtPiecesPanel().setUpdateElement(artPiece);
    }
    
    /**
     * 
     * @param index position of the art piece in the List Model which want to
     * be selected to show in the detail panel.
     */
    public void selectFavourite(int index){
        PublicFavouritePanel artPiecePanel = publicView.getFavouritePanel();
        PublicElementList artPiecesList = artPiecePanel.getArtPiecesList();
        JSONObject artPiece = artPiecesList.getListModel().get(index).getElement();
        
        publicView.getFavouritePanel().setUpdateElement(artPiece);
    }
    
    /**
     * 
     * @param index position of the art piece in the List Model which want to
     * be selected to show in the detail panel.
     */
    public void selectArtist(int index){
        PublicArtistPanel artistPanel = publicView.getArtistPanel();
        PublicElementList artistList = artistPanel.getArtistList();
        JSONObject artist = artistList.getListModel().get(index).getElement();
        
        publicView.getArtistPanel().setUpdateElement(artist);
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
        //publicView.getArtPiecesPanel().setFilePath(path);
        //publicView.getArtPiecesPanel().setFileName(filename);
        
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
        //publicView.getArtistPanel().setFilePath(path);
        //publicView.getArtistPanel().setFileName(filename);
    }
    
    /**
     * Give like to an Art Piece.
     */
    private void likeArtPiece(){
        JSONObject data = publicView.getArtPiecesPanel().getUpdateData();
        publicView.topBar.setLoading(true);
        
        new Thread(() -> {
            JSONObject response = PublicModel.likeArtPiece(data);
            int status = response.getInt("status");
            String responseMessage;

            switch (status) {
                case 201:
                    responseMessage = "Art Piece liked successfully.";
                    //adminView.getUserPanel().showMessage(responseMessage, false);
                    getArtPieces();
                    publicView.getArtPiecesPanel().setLiked(true);
                    break;
                default:
                    responseMessage = "Error " + status + ": Please try again later.";
                    //adminView.getUserPanel().showMessage(responseMessage, true);
                    break;
            }
            
            System.out.println(responseMessage);
            publicView.topBar.setLoading(false);
        }).start();
        
    }
    
    /**
     * Remove like to an Art Piece.
     */
    private void removeLikeArtPiece(){
        JSONObject data = publicView.getArtPiecesPanel().getUpdateData();
        publicView.topBar.setLoading(true);
        
        new Thread(() -> {
            JSONObject response = PublicModel.removeLikeArtPiece(data);
            int status = response.getInt("status");
            String responseMessage;

            switch (status) {
                case 204:
                    responseMessage = "Art Piece unliked successfully.";
                    //adminView.getUserPanel().showMessage(responseMessage, false);
                    getArtPieces();
                    publicView.getArtPiecesPanel().setLiked(false);
                    break;
                default:
                    responseMessage = "Error " + status + ": Please try again later.";
                    //adminView.getUserPanel().showMessage(responseMessage, true);
                    break;
            }
            
            System.out.println(responseMessage);
            publicView.topBar.setLoading(false);
        }).start();
        
    }
            
    /**
     * Remove like to an Art Piece.
     */
    private void removeLikeFavourite(){
        JSONObject data = publicView.getFavouritePanel().getUpdateData();
        publicView.topBar.setLoading(true);
        
        new Thread(() -> {
            JSONObject response = PublicModel.removeLikeArtPiece(data);
            int status = response.getInt("status");
            String responseMessage;

            switch (status) {
                case 204:
                    responseMessage = "Art Piece unliked successfully.";
                    //adminView.getUserPanel().showMessage(responseMessage, false);
                    publicView.updateContent("favMenu");
                    getLikedPieces();
                    break;
                default:
                    responseMessage = "Error " + status + ": Please try again later.";
                    //adminView.getUserPanel().showMessage(responseMessage, true);
                    break;
            }
            
            System.out.println(responseMessage);
            publicView.topBar.setLoading(false);
        }).start();
        
    }

}
