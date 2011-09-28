package midiPlayer;

public class PlayButton extends PlayerButton
{
    public PlayButton(PlayingDevice p)
    {
        super(p, "img/play_up.png", "img/play_over.png", "img/play_down.png");
        
    }
    public void doAction()
    {
        p.play();
    }
}
