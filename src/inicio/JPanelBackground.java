package inicio;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import canciones.Acorde;
import canciones.Compas;

public class JPanelBackground extends JPanel {
private static final long serialVersionUID = -3488034684833395929L;

   Image imagen=null;

   public void setBackground(File file) throws IOException{
       if (file==null)
           imagen=null;
       else
           imagen=ImageIO.read(file);
   }

   public void setBackground(URL url) throws IOException{
       if (url==null)
           imagen=null;
       else
           imagen=ImageIO.read(url);
   }

   @Override
   public void paint(Graphics g) {
       g.setColor(getBackground());
       g.fillRect(0,0,getWidth(),getHeight());
       if (imagen!=null)
           g.drawImage(imagen, 0, 0, this);
       Component c;
       for (int i = 0; i < getComponentCount(); i++) {
           c = getComponent(i);
           g.translate(c.getX(), c.getY());
           c.print(g);
           g.translate(-c.getX(), -c.getY());
       }
   }        
}