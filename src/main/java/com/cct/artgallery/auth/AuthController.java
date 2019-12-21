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
package com.cct.artgallery.auth;

import com.cct.artgallery.admin.AdminController;
import com.cct.artgallery.utils.UserDetail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class AuthController implements ActionListener{
    
    private final LoginView loginView;
    private RegisterView registerView;
    
    public AuthController(){
        //Open login view
        loginView = new LoginView(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "loginSubmit":
                login();
                break;
            case "loginRegister":
                openRegister();
                break;
            case "registerSubmit":
                register();
                break;
            case "registerToLogin":
                registerToLogin();
                break;
            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    private void openRegister(){
        loginView.hide();
        registerView = new RegisterView(this);
    }
    
    private void registerToLogin(){
        registerView.dispose();
        loginView.show();
    }
    
    /**
     * Handles login logic, request data from view and pass to model
     * then checks the answer and send user to the next view.
     */
    private void login(){
        Map<String, String> data = loginView.getData();
        String userEmail = data.get("email");
        String userPassword = data.get("password");
        
        if(userEmail.equals(" Email") || userPassword.equals(" Password")){
            loginView.showError("Email or password incorrect.");
        }
        else{
            LoginModel loginModel = new LoginModel();
            loginView.topBar.setLoading(true);
            
            new Thread(() -> {
                int status = loginModel.login(data);
                
                //Store user details if status code 
                //is correct and send to new page
                switch (status) {
                    case 200:
                        JSONObject response = loginModel.getResponse();
                        UserDetail.setToken(response.getString("access"));
                        UserDetail.setUsername(response.getString("username"));
                        UserDetail.setAdmin(response.getBoolean("admin"));
                        
                        if(UserDetail.isAdmin()){
                            loginView.hide();
                            new AdminController(this);
                        }
                        break;
                    case 401:
                        loginView.showError("Email or password incorrect.");
                        break;
                    default:
                        String message = "Error " + status + ": Please try again later.";
                        loginView.showError(message);
                        break;
                }
                
                loginView.topBar.setLoading(false);
            }).start();
        }
    }
    
    /**
     * Handles register logic, request data from register view and send to
     * register model to register user in database.
     */
    private void register(){
        Map<String, String> data = registerView.getData();
        String username = data.get("username");
        String userEmail = data.get("email");
        String userPassword = data.get("password");
        String userPasswordConf = data.get("password_confirmation");
        
        //Clean first and last name values by removing placeholder.
        if(data.get("first_name").equals(" First Name")){
            data.replace("first_name", "");
        }
        if(data.get("last_name").equals(" Last Name")){
            data.replace("last_name", "");
        }
        
        //Check if all data is present.
        if(username.equals(" Username")){
            registerView.showError("Username can not be empty.");
        }
        else if(userEmail.equals(" Email")){
            registerView.showError("Email can not be empty.");
        }
        else if(userPassword.equals(" Password")){
            registerView.showError("Password can not be empty.");
        }
        else if(!userPassword.equals(userPasswordConf)){
            registerView.showError("Password does not match.");
        }
        else{
            RegisterModel registerModel = new RegisterModel();
            registerView.topBar.setLoading(true);
            
            new Thread(() -> {
                int status = registerModel.register(data);
                
                switch (status) {
                    case 201:
                        registerToLogin();
                        loginView.showSucess("Successful registration, please login.");
                        break;
                    default:
                        String message = "Error " + status + ": Please try again later.";
                        loginView.showError(message);
                        break;
                }
                
                registerView.topBar.setLoading(false);
            }).start();
        
        }
    }
    
}
