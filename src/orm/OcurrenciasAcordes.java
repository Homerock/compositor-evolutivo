package orm;



import net.java.ao.*;

public interface OcurrenciasAcordes extends Entity {
	
	public Acordes getAcordePrincipal();
	public void setAcordePrincipal(Acordes acordePrincipal);
	
	public Acordes getAcordeSecundario();
	public void setAcordeSecundario(Acordes acordeSecundario);
	

	
	public int getCantidad();
	public void setCantidad(int cantidad);
	
	public int getEstilo();
	public void setEstilo(Estilos estilo);
	
	
	
	
	
}
