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

import com.cct.artgallery.utils.API;
import com.cct.artgallery.utils.Constant;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class RegisterModel {
    
    private static  HttpURLConnection   connection;
    private         JSONObject          response    = new JSONObject();
    
    /**
     * 
     * Consume the service login in the API.
     * @param  data HashMap with email and password as String.
     * @return status code of the service login. 
     */
    @SuppressWarnings("NestedAssignment")
    public int register(Map<String, String> data){
        String username = data.get("username");
        String firstName = data.get("first_name");
        String lastName = data.get("last_name");
        String email = data.get("email");
        String password = data.get("password");
        String passwordConfirmation = data.get("password_confirmation");
        
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        
        try {            
            //Create connection and set headers.
            URL url = new URL(API.REGISTER.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(Constant.CONNTIMEOUT.getInt());
            connection.setReadTimeout(Constant.READTIMEOUT.getInt());
            
            //Create body as JSONObject
            JSONObject jsonData = new JSONObject();
            jsonData.put("username", username);
            jsonData.put("first_name", firstName);
            jsonData.put("last_name", lastName);
            jsonData.put("email", email);
            jsonData.put("password", password);
            jsonData.put("password_confirmation", passwordConfirmation);
            
            
            //Send body as JSONObject
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(jsonData.toString());
            wr.flush();
            
            int status = connection.getResponseCode();
            
            if (status > 299){
                //Get error stream and store it in responseContent
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                System.out.println(responseContent);
                reader.close();
            }
            else{
                //Get input stream and store it in responseContent
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();                
                //Parse and store response in response object
                parse(responseContent.toString());
            }
            return(status);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RegisterModel.class.getName()).log(Level.SEVERE, null, ex);
            return(500);
        } catch (IOException ex) {
            Logger.getLogger(RegisterModel.class.getName()).log(Level.SEVERE, null, ex);
            return(500);
        } finally {
            connection.disconnect();
        }
    }
    
    /**
     * 
     * @param responseBody Receive the response as a String
     * and store the response parsed as a JSON in the attribute response.
     * 
     */
    private void parse(String responseBody){
        JSONObject data = new JSONObject(responseBody);
        this.response = data;
    }
    
    /**
     * 
     * @return Response of the end point parsed as a JSONObject.
     */
    public JSONObject getResponse(){
        return response;
    }
    
}
