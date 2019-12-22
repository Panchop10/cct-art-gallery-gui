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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class AdminController implements ActionListener{
    
    private final AuthController authController;
    private final AdminView adminView;
    private final MenuAdmin menu;
    
    public AdminController(AuthController parentController){
        authController = parentController;
        
        //Open admin view and get art pieces from the model.
        menu = new MenuAdmin(this);
        adminView = new AdminView(this, menu);
        getArtPieces();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "artPiecesMenu":
                menu.setActive(e.getActionCommand());
                adminView.updateContent(e.getActionCommand());
                getArtPieces();
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
            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * Request ArtPieces to the model and populates the ArtPieceList in the View.
     */
    private void getArtPieces(){
        AdminModel adminModel = new AdminModel();
        adminView.topBar.setLoading(true);

        new Thread(() -> {
            int status = adminModel.getArtPieces();

            switch (status) {
                case 200:
                    //Populate the ArtPieceList with the data from the model.
                    JSONArray response = adminModel.getResponseArray();
                    
                    ArtPiecePanel artPiecePanel = adminView.getArtPiecesPanel();
                    ElementList artPiecesList = artPiecePanel.getArtPiecesList();
                    artPiecesList.getListModel().clear();
                    
                    for (int i = 0; i < response.length(); i++){
                        JSONObject artPiece = response.getJSONObject(i);
                        artPiecesList.addElement(artPiece);
                    }
                    
                    //artPiecesList.addElement(new JSONObject());
                    
                    //System.out.println(artPiecesList.getListModel().get(0).getValue());
                    
                    
                    
                    //System.out.println("asd");
                    System.out.println(response);
                    break;
                default:
                    String message = "Error " + status + ": Please try again later.";
                    System.out.println(message);
                    //adminView.showError(message);
                    break;
            }

            adminView.topBar.setLoading(false);
        }).start();
        
    }
    
    public void selectArtPiece(int index){
        ArtPiecePanel artPiecePanel = adminView.getArtPiecesPanel();
        ElementList artPiecesList = artPiecePanel.getArtPiecesList();
        JSONObject artPiece = artPiecesList.getListModel().get(index).getElement();
        
        adminView.artPiecePanel.setUpdateElement(artPiece);
        
        //adminView.artPiecePanel.elementName.setText(artPiece.getString("slug_name"));
        //System.out.println(index);
    }
}
