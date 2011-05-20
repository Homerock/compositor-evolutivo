package orm;

import net.java.ao.Entity;

public interface Estilos extends Entity {

	public String getNombre();
	public void setNombre(String nombre);
	//contadores de compases 
	public int getCantUnCompas();
	public void setCantUnCompas(int cantUnCompas);
	
	public int getCantDosCompases();
	public void setCantDosCompases(int cantDosCompases);
	
	public int getCantCuatroCompases();
	public void setCantCuatroCompases(int cantCuatroCompases);
	
	public int getCantOchoCompases();
	public void setCantOchoCompases(int cantOchoCompases);
	//contadores de acordes en un compas
	public int getUnAcordeEnCompas();
	public void setUnAcordeEnCompas(int unAcordeEnCompas);
	
	public int getDosAcordesEnCompas();
	public void setDosAcordesEnCompas(int dosAcordesEnCompas);
	
	public int getTresAcordesEnCompas();
	public void TresAcordesEnCompas(int tresAcordesEnCompas);
	
	public int getCuatroAcordesEnCompas();
	public int setCuatroAcordesEnCompas(int cuatroAcordesEnCompas);
	
	
}
