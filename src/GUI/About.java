package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import utiles.Constantes;

public class About extends JPanelBackground {
 
    private GridBagLayout layoutMain = new GridBagLayout();
    private Border border = BorderFactory.createEtchedBorder();

    public About() {
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
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		ImageIcon imagenAprender;
		try {
			imagenAprender = new ImageIcon(misImagenes.getImagenURL(Constantes.IMAGEN_ACERCA));
			JLabel etiqueta = new JLabel(imagenAprender);
			this.add(etiqueta);
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
		}
    	
    	this.setLayout( layoutMain );
        this.setBorder( border );
        
    }
}
