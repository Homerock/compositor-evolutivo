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
	
	
	public Estilos getEstiloPrincipal();
	public void setEstiloPrincipal(Estilos estiloPrincipal);
	
	public Acordes getTonica();
	public void setTonica(Acordes tonica);
	
	public String getComentario();
	public void setComentario(String comentario);
	
	public String getFechaCreacion();
	public void setFechaCreacion(String fechaCreacion);
}
