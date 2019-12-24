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

import com.cct.artgallery.admin.AdminModel;
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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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
public class PublicModel {
        
    /**
     * 
     * Consume the service create like in the API.
     * @param  data JSONObject with the data of the request.
     * @return status code of the service give like. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject likeArtPiece(JSONObject data){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String endpoint = API.USERS.getUrl()+UserDetail.getUsername()+"/likes/";
            HttpPost httppost = new HttpPost(endpoint);
            
            httppost.addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+UserDetail.getToken());
            
            StringEntity reqEntity = new StringEntity(
                    data.toString(),
                    ContentType.APPLICATION_JSON
            );

            httppost.setEntity(reqEntity);

            CloseableHttpResponse newResponse = httpclient.execute(httppost);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                    System.out.println(responseString);
                }
                else{
                    responseString = "{}";
                }
            } finally {
                newResponse.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(PublicModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(PublicModel.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", status);
        responseJSON.put("data", new JSONObject(responseString));
        return responseJSON;
    }
    
    /**
     * 
     * Consume the service remove like in the API.
     * @param  data JSONObject with the data of the request.
     * @return status code of the service remove like. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject removeLikeArtPiece(JSONObject data){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String endpoint = API.USERS.getUrl()+UserDetail.getUsername()+"/likes/"+data.getString("artpiece")+"/";
            HttpDelete httpdelete = new HttpDelete(endpoint);
            
            httpdelete.addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+UserDetail.getToken());

            CloseableHttpResponse newResponse = httpclient.execute(httpdelete);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                    System.out.println(responseString);
                }
                else{
                    responseString = "{}";
                }
            } finally {
                newResponse.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(PublicModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(PublicModel.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", status);
        responseJSON.put("data", new JSONObject(responseString));
        return responseJSON;
    }
    
    /**
     * 
     * Consume the service get art pieces in the API.
     * @return status code of the service art pieces. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject getFavourites(){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String endpoint = API.USERS.getUrl()+UserDetail.getUsername()+"/likes/";
            HttpGet httpget = new HttpGet(endpoint);
            
            httpget.addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+UserDetail.getToken());

            CloseableHttpResponse newResponse = httpclient.execute(httpget);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                    System.out.println(responseString);
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
     * Consume the service get profile in the API.
     * @return status code of the service get profile. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject getProfile(){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String endpoint = API.USERS.getUrl()+UserDetail.getUsername()+"/";
            HttpGet httpget = new HttpGet(endpoint);
            
            httpget.addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+UserDetail.getToken());

            CloseableHttpResponse newResponse = httpclient.execute(httpget);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                    System.out.println(responseString);
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
        responseJSON.put("data", new JSONObject(responseString));
        return responseJSON;
    }
    
        /**
     * 
     * Consume the service put user in the API.
     * @param  data JSONObject with the data of the request.
     * @return status code of the service put user. 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static JSONObject updateProfile(JSONObject data){
        int status = 500;
        String responseString = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String endpoint = API.USERS.getUrl()+UserDetail.getUsername()+"/";
            HttpPut httpput = new HttpPut(endpoint);
            
            httpput.addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+UserDetail.getToken());
            
            data.put("username", UserDetail.getUsername());
            
            
            HttpEntity reqEntity;
            if(!data.getString("photo").equals("")){
                FileBody photo = new FileBody(new File(data.getString("photo")));
                data.remove("photo");
                
                StringBody dataString = new StringBody(data.toString(), ContentType.TEXT_PLAIN);

                reqEntity = MultipartEntityBuilder.create()
                    .addPart("photo", photo)
                    .addPart("data", dataString)
                    .build();
            }
            else {
                data.remove("photo");
                StringBody dataString = new StringBody(data.toString(), ContentType.TEXT_PLAIN);

                reqEntity = MultipartEntityBuilder.create()
                    .addPart("data", dataString)
                    .build();
            }
            
            
            httpput.setEntity(reqEntity);

            CloseableHttpResponse newResponse = httpclient.execute(httpput);
            try {
                status = newResponse.getStatusLine().getStatusCode();
                HttpEntity resEntity = newResponse.getEntity();
                
                if (resEntity != null) {
                    responseString = EntityUtils.toString(resEntity);
                    System.out.println(responseString);
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
