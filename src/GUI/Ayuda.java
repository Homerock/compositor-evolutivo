package GUI;
import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
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

        //this.setBorder( border );
        this.setPreferredSize(new java.awt.Dimension(430, 435));
        {
        	jTextAreaAyuda = new JTextArea();
        	jTextAreaAyuda.setEditable(false);
        	jTextAreaAyuda.setText(
        			"Homerock es un sistema de composición musical, aprende\n" +
        			"canciones que le permiten componer. \n" +
        			"Podés guardar tus composiciones y volver a verlas, \n" +
        			"reproducirlas o modificarlas. \n\n" +

        			"Homerock tiene cuatro funciones principales: \n\n" +

        			"Aprender: \n" +
        			"Para que Homerock aprenda canciones seleccionamos \n" +
        			"el directorio o archivo que queremos que aprenda.  \n" +
        			"Los archivos deben tener el formato mma. \n" +
        			"así, con cada canción que aprende, Homerock incrementa  \n" +
        			"sus conocimientos, recordá que cuanto más y mejores \n" +
        			"canciones aprenda obtendrá mejores resultados. \n\n" +

        			"Componer: \n" +
        			"Para componer Homerock tiene que haber aprendido  \n" +
        			"canciones previamente, en base a sus conocimientos \n" +
        			"podés seleccionar un estilo y un acorde de tónica \n" +
        			"para generar una composición. \n" +
        			"También podés utilizar estructuras de estrofas y/o \n" +
        			"elegir el tempo y duración de la canción. \n" +

        			"Cuando se compone una canción Homerock la reproduce \n" +
        			"y muestra el detalle de la canción, separada en estrofas\n" +
        			"y compases. \n" +
        			"Esto te permite modificar uno o varios compases de \n" +
        			"la canción. \n" +
        			"Cada composición se puede guardar para volver a verla \n" +
        			"o escucharla en cualquier momento. \n\n" +

        			"Mis canciones: \n" +
        			"Todas las composiciones que guardaste las podés volver \n" +
        			"a ver, reproducir o modificar para volver a guardar \n" +
        			"una nueva versión de cada canción. \n\n" +

        			"Guardar: \n" +
        			"Podés guardar en la base de datos todo lo que homerock \n" +
        			"aprendió y así recuperar todo el conocimiento la próxima \n" +
        			"vez que utilices Homerock.");
        	
        	JScrollPane scroll = new JScrollPane(jTextAreaAyuda);
        	this.add(scroll);
        	scroll.setPreferredSize(new java.awt.Dimension(409, 414));

        	//jTextAreaAyuda.setBounds(14, 14, 402, 407);
        	//jTextAreaAyuda.setPreferredSize(new java.awt.Dimension(389, 421));
        }
        
    }
}

