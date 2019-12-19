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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Francisco Olivares
 */
public class TopBar extends JPanel {
    
    /**
     * compCoords stores the position of the mouse when
     * the user drags the window
     * 
     * topPanel stores the elements close and minimize 
     */
    private static Point compCoords;
    private final JPanel topPanel;
    
    /**
     * 
     * @param window Receive JFrame where the Top Bar is going to be placed
     * and where the drag and drop of the windows is going to work.
     */
    public TopBar(JFrame window){
        topPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.BLACK);
        
        URL closeButton = getClass().getClassLoader().getResource("images/close_button.png");
        URL minButton = getClass().getClassLoader().getResource("images/min_button.png");
        
        final JButton minimize = new JButton(new ImageIcon(minButton));
        minimize.setBorderPainted(false);
        
        final JButton exit = new JButton(new ImageIcon(closeButton));
        exit.setBorderPainted(false);
        
        exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        minimize.addActionListener((ActionEvent e) -> {
            window.setState(JFrame.ICONIFIED);
        });
        compCoords = null;
        topPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                compCoords = null;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                compCoords = e.getPoint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        topPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                window.setLocation(currCoords.x - compCoords.x, currCoords.y - compCoords.y);
            }
        });
        topPanel.add(minimize);
        topPanel.add(exit);
    }
    
    /**
     * 
     * @return Top Panel with drag and drop functionality and 
     * close and minimize buttons.
     */
    public JPanel getTopBar(){
        return topPanel;
    }
}
