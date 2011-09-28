package midiPlayer;


public class AdvancedButton extends ControlToggleButton
{
    MIDIPlayer p;
    public AdvancedButton(MIDIPlayer p)
    {
        super("img/options2_up.png",   "img/options2_over.png",
              "img/options2_down.png", "img/options2_downOver.png");
        this.p = p;
    }
    public void doAction(boolean enabled)
    {   
        p.slideAdvanced(enabled);
    }

}
