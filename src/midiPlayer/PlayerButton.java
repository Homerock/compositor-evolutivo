package midiPlayer;

public class PlayerButton extends ControlButton
{
    PlayingDevice p;
    public PlayerButton(PlayingDevice p, String fileUp, String fileOver, String fileDown)
    {
        super(fileUp, fileOver, fileDown);
        this.p = p;
    }
    
}
