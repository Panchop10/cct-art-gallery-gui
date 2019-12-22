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
package com.cct.artgallery.utils;

/**
 *
 * @author Francisco Olivares
 */
public class UserDetail {
    
    private static String   token;
    private static String   username;
    private static Boolean  admin;
    
    /**
     * 
     * @return token authentication that needs to be used for API calls.
     */
    public static String getToken(){
        return token;
    }
    
    /**
     * 
     * @param newToken receive new token and store it.
     */
    public static void setToken(String newToken){
        token = newToken;
    }
    
    /**
     * 
     * @return username of logged user.
     */
    public static String getUsername(){
        return username;
    }
    
    /**
     * 
     * @param newUsername set username for logged user.
     */
    public static void setUsername(String newUsername){
        username = newUsername;
    }
    
    /**
     * 
     * @return A boolean if the user is administrator or not
     */
    public static Boolean isAdmin(){
        return admin;
    }
    
    /**
     * 
     * @param newAdmin Set a user as administrator or not.
     */
    public static void setAdmin(Boolean newAdmin){
        admin = newAdmin;
    }
    
    /**
     * Remove values stored related to the user logged.
     * (token, username and admin)
     */
    public static void logout(){
        token = "";
        username = "";
        admin = false;
    }
    
}
