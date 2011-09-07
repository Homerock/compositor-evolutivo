package orm;
import java.sql.Date;
import net.java.ao.*;

public interface Cancion extends Entity{
	
	public String getNombre();
	public void setNombre(String nombre);
	
	public String getTempo();
	public void setTempo(String tempo);
	
	public int getDuracion();
	public void setDuracion(int duracion);
	
	
	public String getEstiloPrincipal();
	public void setEstiloPrincipal(String estiloPrincipal);
	
	public String getTonica();
	public void setTonica(String tonica);
	
	public String getComentario();
	public void setComentario(String comentario);
	
	public String getFechaCreacion();
	public void setFechaCreacion(String fechaCreacion);
}
