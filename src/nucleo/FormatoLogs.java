package nucleo;

import java.awt.Color;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import javax.smartcardio.ATR;

public class FormatoLogs extends Formatter {
	
	static final AttributedCharacterIterator.Attribute COLOR
    = new AttributedCharacterIterator.Attribute("color") {
    };
	
    public String format(LogRecord record) {
    	
    	AttributedString as = new AttributedString(record.getLevel().getName());
    	as.addAttribute(COLOR, Color.RED);
    	
        return 
        	record.getLevel().getName() + ": " +
        	record.getMessage() + "\n";
    }
}