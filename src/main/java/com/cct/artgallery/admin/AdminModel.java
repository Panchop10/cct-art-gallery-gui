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

import com.cct.artgallery.auth.LoginModel;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class AdminModel {
    
        
    private static  HttpURLConnection connection;
    private         JSONObject        response      = new JSONObject();
    private         JSONArray         responseArray = new JSONArray();
    
    /**
     * 
     * Consume the service get art pieces in the API.
     * @return status code of the service art pieces. 
     */
    @SuppressWarnings("NestedAssignment")
    public int getArtPieces(){
        
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        
        try {            
            //Create connection and set headers.
            URL url = new URL(API.ARTPIECES.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(Constant.CONNTIMEOUT.getInt());
            connection.setReadTimeout(Constant.READTIMEOUT.getInt());

            int status = connection.getResponseCode();
            
            if (status > 299){
                //Get error stream and store it in responseContent
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
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
                parseArray(responseContent.toString());
            }
            return(status);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            return(500);
        } catch (IOException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
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
     * @param responseBody Receive the response as a String
     * and store the response parsed as a JSONArray in the attribute response.
     * 
     */
    private void parseArray(String responseBody){
        JSONArray data = new JSONArray(responseBody);
        this.responseArray = data;
    }
    
    /**
     * 
     * @return Response of the end point parsed as a JSONObject.
     */
    public JSONObject getResponse(){
        return response;
    }
    
    /**
     * 
     * @return Response of the end point parsed as a JSONArray.
     */
    public JSONArray getResponseArray(){
        return responseArray;
    }
    
}
