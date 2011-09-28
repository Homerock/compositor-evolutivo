package midiPlayer;

import java.io.File;

import javax.sound.midi.Sequence;

public class SequenceItem
{
    File file;
    /**
     * @param s
     * @param file
     */
    public SequenceItem(File file)
    {
        this.file = file;
    }
    
    /**
     * @return Returns the file.
     */
    public File getFile()
    {
        return file;
    }
    /**
     * @param file The file to set.
     */
    public void setFile(File file)
    {
        this.file = file;
    }
    
    public String toString()
    {
        return file.getName();
    }
    
    public boolean equals(Object o)
    {
        if(!(o instanceof SequenceItem))
            return false;
        return ((SequenceItem)o).getFile().equals(file);
    }
}
