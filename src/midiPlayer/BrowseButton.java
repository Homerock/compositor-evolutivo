package midiPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class BrowseButton extends JButton implements ActionListener
{
    MIDIPlayer p;
    public JFileChooser fileChooser;
    javax.swing.filechooser.FileFilter filter;
    public BrowseButton(MIDIPlayer p)
    {
        super("Browse...");
        this.p = p;
        fileChooser = new JFileChooser("C:/Documents and Settings/Mikhail Temkine/My Documents/My Music/beatles MIDI");
        fileChooser.setFileFilter(new MIDIFilter());
        fileChooser.setMultiSelectionEnabled(true);
        addActionListener(this);
    }
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0)
    {
        int option = fileChooser.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION)
        {
            File[] f = fileChooser.getSelectedFiles();
            for(int i = 0; i < f.length; i++)
            {
                p.addSong(f[i]);
            }
        }
    }
    
}
