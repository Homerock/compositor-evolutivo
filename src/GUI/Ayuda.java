package GUI;
import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import org.jdesktop.application.Application;

import utiles.Constantes;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Ayuda extends JPanelBackground {

    private Border border = BorderFactory.createEtchedBorder();
    private JTextArea jTextAreaAyuda;

    public Ayuda() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        
    	Imagenes misImagenes = new Imagenes();
    	
    	try {
    		this.setBackground(misImagenes.getImagenURL(Constantes.FONDO_3));
    		Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

        this.setBorder( border );
        this.setPreferredSize(new java.awt.Dimension(430, 435));
        {
        	jTextAreaAyuda = new JTextArea();
        	jTextAreaAyuda.setEditable(false);
        	jTextAreaAyuda.setText("Homerock - Compositor musical evolutivo\n\n" +
        			"Homerock tiene cuatro funciones principales:" +
        			"\n\nAprender:" +
        			"\n\nComponer:" +
        			"\n\nMis canciones:" +
        			"\n\nGuardar:");
        	this.add(jTextAreaAyuda);
        	jTextAreaAyuda.setName("jTextAreaAyuda");
        	jTextAreaAyuda.setBounds(14, 14, 402, 407);
        	jTextAreaAyuda.setPreferredSize(new java.awt.Dimension(389, 421));
        }
        
    }
}

