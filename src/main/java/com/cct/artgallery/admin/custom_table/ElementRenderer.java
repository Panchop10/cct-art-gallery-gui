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

import com.cct.artgallery.admin.AdminController;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Francisco Olivares
 */
    public class ElementRenderer implements ListCellRenderer<Object>{
        
        private final AdminController parentController;
        private final String          type;
        
        public ElementRenderer(AdminController parentController, String type){
            this.parentController = parentController;
            this.type = type;
        }
        
        @Override
        public Component getListCellRendererComponent(
            JList<?> list,                    
            Object value,
            int index, 
            boolean isSelected, 
            boolean cellHasFocus) {

            Component component = (Component) value;
               if (isSelected){
                   component.setBackground(new Color(241, 164, 66));
                switch (this.type) {
                    case "artPiece":
                        parentController.selectArtPiece(index);
                        break;
                    case "artist":
                        parentController.selectArtist(index);
                        break;
                    case "user":
                        parentController.selectUser(index);
                        break;
                    default:
                        break;
                }
                   
               } else {
                   component.setBackground(Color.WHITE);
               }
               return component;
        }
    }
