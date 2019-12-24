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
package com.cct.artgallery.admin.custom_table;

import com.cct.artgallery.utils.CustomFont;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
   public class Element extends JPanel{
        private final JSONObject element;
        
        @SuppressWarnings("OverridableMethodCallInConstructor")
        public Element(JSONObject element, String type){
            this.element = element;
            
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
             //Call Roboto Font
            CustomFont customFont = new CustomFont();
            Font roboto = customFont.getRoboto();
            
            if(type.equals("artPiece")){
                
                String artPieceName = element.getString("name");
                JLabel name = new JLabel(artPieceName);
                name.setFont(roboto.deriveFont(Font.PLAIN, 10f));
                name.setPreferredSize(new Dimension(250, 20));
                name.setMaximumSize(new Dimension(250, 20));
                this.add(name);
                
                String categoryName = element.getJSONObject("category").getString("name");
                JLabel category = new JLabel(categoryName);
                category.setFont(roboto.deriveFont(Font.PLAIN, 10f));
                category.setPreferredSize(new Dimension(150, 20));
                category.setMaximumSize(new Dimension(150, 20));
                this.add(category);
                
                String artistFirstName = element.getJSONObject("artist").getString("first_name");
                String artistLastName = element.getJSONObject("artist").getString("last_name");
                JLabel artist = new JLabel(artistFirstName + " " + artistLastName);
                artist.setFont(roboto.deriveFont(Font.PLAIN, 10f));
                artist.setPreferredSize(new Dimension(200, 20));
                artist.setMaximumSize(new Dimension(200, 20));
                this.add(artist);
                
                Double priceValue = element.getDouble("price");
                DecimalFormat df = new DecimalFormat("€###,###,###");
                String priceFormat = df.format(priceValue).replace(",", ".");
                JLabel price = new JLabel(priceFormat, SwingConstants.RIGHT);
                price.setFont(roboto.deriveFont(Font.PLAIN, 10f));
                price.setPreferredSize(new Dimension(100, 20));
                price.setMaximumSize(new Dimension(100, 20));
                this.add(price);
                
            }
            
            if(type.equals("artist")){
                String artistFirstName = element.getString("first_name");
                String artistLastName = element.getString("last_name");
                JLabel name = new JLabel(artistFirstName + " " + artistLastName);
                name.setFont(roboto.deriveFont(Font.PLAIN, 10f));
                name.setPreferredSize(new Dimension(250, 20));
                name.setMaximumSize(new Dimension(250, 20));
                this.add(name);
                
                String websiteName = element.getString("website");
                JLabel website = new JLabel(websiteName);
                website.setFont(roboto.deriveFont(Font.PLAIN, 10f));
                website.setPreferredSize(new Dimension(200, 20));
                website.setMaximumSize(new Dimension(200, 20));
                this.add(website);
                
                String emailName = element.getString("email");
                JLabel email = new JLabel(emailName);
                email.setFont(roboto.deriveFont(Font.PLAIN, 10f));
                email.setPreferredSize(new Dimension(250, 20));
                email.setMaximumSize(new Dimension(250, 20));
                this.add(email);
            }
            
            if(type.equals("user")){
                String userFirstName = element.getString("first_name");
                String userLastName = element.getString("last_name");
                JLabel name = new JLabel(userFirstName + " " + userLastName);
                name.setFont(roboto.deriveFont(Font.PLAIN, 10f));
                name.setPreferredSize(new Dimension(250, 20));
                name.setMaximumSize(new Dimension(250, 20));
                this.add(name);
                
                String usernameName = element.getString("username");
                JLabel username = new JLabel(usernameName);
                username.setFont(roboto.deriveFont(Font.PLAIN, 10f));
                username.setPreferredSize(new Dimension(200, 20));
                username.setMaximumSize(new Dimension(200, 20));
                this.add(username);
                
                String emailName = element.getString("email");
                JLabel email = new JLabel(emailName);
                email.setFont(roboto.deriveFont(Font.PLAIN, 10f));
                email.setPreferredSize(new Dimension(250, 20));
                email.setMaximumSize(new Dimension(250, 20));
                this.add(email);
            }
        }
        
        public JSONObject getElement(){
            return this.element;
        }
    }
