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
    private JLabel labelTitle = new JLabel();
    private JLabel labelAuthor = new JLabel();
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
			imagenAprender = new ImageIcon(misImagenes.getImagenURL(Constantes.IMAGEN_APRENDER));
			JLabel etiqueta = new JLabel(imagenAprender);
			this.add(etiqueta);
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
		}
    	
    	this.setLayout( layoutMain );
        this.setBorder( border );
        
        labelTitle.setText("Homerock");
        labelAuthor.setText("Autores : SaPazos & Yamil3g");
       // this.add( labelTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
       //            new Insets(5, 15, 0, 15), 0, 0));
       // this.add( labelAuthor, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 0, 15), 0, 0) );
    }
}
