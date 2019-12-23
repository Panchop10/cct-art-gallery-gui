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
package com.cct.artgallery.admin.CustomTable;

import com.cct.artgallery.admin.AdminController;
import java.awt.BorderLayout;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONObject;

/**
 *
 * @author Francisco Olivares
 */
public class ElementList extends JPanel{
    private final DefaultListModel<Element> listModel = new DefaultListModel<>();
    private final JList<Element>            jList     = new JList<>(listModel);
    private final JScrollPane               spList    = new JScrollPane(jList);
    private final ElementRenderer           render;
    private final String                    type;

    @SuppressWarnings("OverridableMethodCallInConstructor")
     public ElementList(AdminController parentController, String type){
        this.render = new ElementRenderer(parentController, type);
        this.type = type;
        
        //JList properties
        jList.setCellRenderer(render);
        jList.setFocusable(false);
        
        //Remove border to ScrollPane
        spList.setBorder(createEmptyBorder());
        
        
        this.setLayout(new BorderLayout());
        this.add(spList,BorderLayout.CENTER);   
     }

     public void addElement(JSONObject newElement){
        listModel.addElement(new Element(newElement, this.type));
     }
     
     public DefaultListModel<Element> getListModel(){
         return listModel;
     }
}
