package midiPlayer;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class ControlToggleButton extends JPanel implements MouseListener
{
    public static final Color backgroundColor = new Color(200, 255, 200);
    boolean mouseOver = false,
            press = false,
            toggle = false;
    Image up, over, down, downOver;
    public ControlToggleButton(String fileUp, String fileOver,
                               String fileDown, String fileDownOver)
    {
        MediaTracker mt = new MediaTracker(this);
        up = loadImage(fileUp);
        over = loadImage(fileOver);
        down = loadImage(fileDown);
        downOver = loadImage(fileDownOver);
        mt.addImage(up, 0);
        mt.addImage(over, 1);
        mt.addImage(down, 2);
        mt.addImage(down, 3);
        for(int i = 0; i < 4; i++)
        {
            try
            {
                mt.waitForID(i);
            }
            catch(InterruptedException e) {}
        }
        addMouseListener(this);
        setPreferredSize(new Dimension(up.getWidth(this), up.getHeight(this)));
    }
    private Image loadImage(String filename)
    {
        return Toolkit.getDefaultToolkit().createImage(filename);
    }
    //################################################
    public void paint(Graphics g1)
    {
        Graphics2D g = (Graphics2D)g1;
        g.setColor(backgroundColor);
        g.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        if(toggle)
        {
            if(press && mouseOver)
                g.drawImage(over, 0, 0, this);
            else if(mouseOver)
                g.drawImage(downOver, 0, 0, this);
            else
                g.drawImage(down, 0, 0, this);
        }
        else
        {
            if(press && mouseOver)
                g.drawImage(downOver, 0, 0, this);
            else if(mouseOver)
                g.drawImage(over, 0, 0, this);
            else
                g.drawImage(up, 0, 0, this);
        }
    }
    //################################################
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent arg0) {}
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent arg0)
    {
        mouseOver = true;
        repaint();
    }
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent arg0)
    {
        mouseOver = false;
        repaint();
    }
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent arg0)
    {
        press = true;
        repaint();
    }
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent arg0)
    {
        press = false;
        if(mouseOver)
        {
            toggle = !toggle;
            doAction(toggle);
        }
        repaint();
    }
    /**
     * Override this method to tell this button
     * what to do if it is pressed.
     */
    public void doAction(boolean enabled) {}
}