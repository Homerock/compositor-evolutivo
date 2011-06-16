package utiles;

public interface Constantes {
	
	public final static String DIRECTORIO_LINUX = "Directorio";
	public final static String ARCHIVO_LINUX = "Archivo genérico";
	public final static String EXTENSION_ARCHIVO = ".mma";
	
	public static final String COMENTARIO = "//";
	public static final String CADENAVACIA="";
	//ESTILOS
	public static final String ESTILO = "Groove";//para definir un estilo debe estar definido asi "Groove miEstilo"
	public static final String NUEVO_ESTILO ="DefGroove";
	public static final String VAR_ULTIMO_ESTILO ="$_LastGroove";
	public static final String END_ESTILO ="End";
	public static final String INTRO_ESTILO ="Intro";
	public static final String FILL_ESTILO ="Fill";
	public static final String SIN_ESTILO = "sinEstilo";
	//REPEATS
	public static final String REPEAT = "Repeat";
	public static final String REPEAT_ENDING = "RepeatEnding";
	public static final String REPEAT_END = "RepeatEnd";
	//TEMPO
	public static final String TEMPO = "Tempo";
	//VARIBLES
	public static final String DEFINICION_VARIABLE= "Set";
	public static final String COMIENZO_DE_VARIABLE = "$";
	//ACORDES
	public static final String ACORDE_REPETIDO = "/";
	public static final int UN_ACORDE = 1;
	public static final int DOS_ACORDE = 2;
	public static final int TRES_ACORDE = 3;
	public static final int CUATRO_ACORDE = 4;
	public static final int MINIMO_ACORDES = 1;	// cantidad minima de acorde por compas
	public static final int MAXIMO_ACORDES = 4; // cantidad maxima de acordes por compas

	//CONSTANTES A UTILIZAR en MATRIZ ESTILOS
	public static final int UN_COMPAS = 1;
	public static final int DOS_COMPASES = 2;
	public static final int CUATRO_COMPASES = 4;
	public static final int OCHO_COMPASES = 8;
	public static final int PAR = 2;
	//COMPOSICION
	public static final int ESTRUCTURA_A = 1;
	public static final int ESTRUCTURA_B = 2;
	public static final int ESTRUCTURA_C = 3;

}
