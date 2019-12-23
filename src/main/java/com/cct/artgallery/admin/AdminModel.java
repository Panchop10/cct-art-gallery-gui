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

import com.cct.artgallery.utils.API;
import com.cct.artgallery.utils.UserDetail;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Francisco Olivares
 */
public class AdminModel {
    
    /**
     * 
     * Consume the service get art pieces in the API.
     * @return status code of the service art pieces. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject getArtPieces(){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(API.ARTPIECES.getUrl());

            CloseableHttpResponse newResponse = httpclient.execute(httpget);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                }
                else{
                    responseString = "{}";
                }
            } finally {
                newResponse.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        //Build the response.
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", status);
        responseJSON.put("data", new JSONArray(responseString));
        return responseJSON;
    }
    
    /**
     * 
     * Consume the service get artists in the API.
     * @return status code of the service artists. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject getArtists(){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(API.ARTISTS.getUrl());

            CloseableHttpResponse newResponse = httpclient.execute(httpget);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                }
                else{
                    responseString = "{}";
                }
            } finally {
                newResponse.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        //Build the response.
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", status);
        responseJSON.put("data", new JSONArray(responseString));
        return responseJSON;
    }
    
     /**
     * 
     * Consume the service get categories in the API.
     * @return status code of the service categories. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject getCategories(){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(API.CATEGORIES.getUrl());

            CloseableHttpResponse newResponse = httpclient.execute(httpget);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                }
                else{
                    responseString = "{}";
                }
            } finally {
                newResponse.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        //Build the response.
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", status);
        responseJSON.put("data", new JSONArray(responseString));
        return responseJSON;
    }
    
    /**
     * 
     * Consume the service add Art Piece in the API.
     * @param  data JSONObject with the data of the request.
     * @return status code of the service add art piece. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject addArtPiece(JSONObject data){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(API.ARTPIECES.getUrl());
            
            httppost.addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+UserDetail.getToken());
            
            FileBody photo = new FileBody(new File(data.getString("photo")));
            data.remove("photo");
            
            StringBody dataString = new StringBody(data.toString(), ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("photo", photo)
                    .addPart("data", dataString)
                    .build();


            httppost.setEntity(reqEntity);

            CloseableHttpResponse newResponse = httpclient.execute(httppost);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                }
                else{
                    responseString = "{}";
                }
            } finally {
                newResponse.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", status);
        responseJSON.put("data", new JSONObject(responseString));
        return responseJSON;
    }
    
    /**
     * 
     * Consume the service patch Art Piece in the API.
     * @param  data JSONObject with the data of the request.
     * @return status code of the service patch art piece. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject updateArtPiece(JSONObject data){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String url = API.ARTPIECES.getUrl() + data.getString("slug_name")+"/";
            HttpPatch httppatch = new HttpPatch(url);
            
            httppatch.addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+UserDetail.getToken());
            
            StringBody dataString = new StringBody(data.toString(), ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("data", dataString)
                    .build();
            
            httppatch.setEntity(reqEntity);

            CloseableHttpResponse newResponse = httpclient.execute(httppatch);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                }
                else{
                    responseString = "{}";
                }
            } finally {
                newResponse.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", status);
        responseJSON.put("data", new JSONObject(responseString));
        return responseJSON;
    }
    
    /**
     * 
     * Consume the service patch Art Piece in the API.
     * @param  data JSONObject with the data of the request.
     * @return status code of the service patch art piece. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject deleteArtPiece(JSONObject data){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String url = API.ARTPIECES.getUrl() + data.getString("slug_name")+"/";
            HttpDelete httpdelete = new HttpDelete(url);
            
            httpdelete.addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+UserDetail.getToken());

            CloseableHttpResponse newResponse = httpclient.execute(httpdelete);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                }
                else{
                    responseString = "{}";
                }
                
            } finally {
                newResponse.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", status);
        responseJSON.put("data", new JSONObject(responseString));
        return responseJSON;
    }
}
