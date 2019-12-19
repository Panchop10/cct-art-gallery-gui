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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Francisco Olivares
 */
public class AuthController implements ActionListener{
    
    private LoginView loginView;
    private RegisterView registerView;
    
    public AuthController(){
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
    
    private void login(){
        Map<String, String> data = loginView.getData();
        String userEmail = data.get("email");
        String userPassword = data.get("password");
        
        if(userEmail.equals(" Email") || userPassword.equals(" Password")){
            loginView.showError("Email or password incorrect.");
        }
        else{
            
        }
        
//        for (Map.Entry<String, String> entry : data.entrySet()) {
//            //String key = entry.getKey();
//            //HashMap value = entry.getValue();
//            System.out.println(entry.getValue());
//            // do what you have to do here
//            // In your case, another loop.
//            if(entry.getValue().equals(" Email") || entry.getValue().equals(" Password")){
//                loginView.showError("Email or password incorrect.");
//            }            
//        }
    }
    
}
