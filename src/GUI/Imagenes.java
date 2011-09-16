package GUI;

import java.net.MalformedURLException;
import java.net.URL;

public class Imagenes {
	
	public Imagenes() {
		
	}
	
	public URL getImagenURL(String nombre) throws MalformedURLException {
		
		URL url;
		
		url = this.getClass().getClassLoader().getResource(nombre);
		
		if (url == null) {
			url = new URL("file:"+nombre);
		}
		
		return url;
	}

}
