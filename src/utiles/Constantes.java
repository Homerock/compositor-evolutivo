package utiles;

public interface Constantes {
	
	public final static String DIRECTORIO_LINUX = "Directorio";
	public final static String ARCHIVO_LINUX = "Archivo genérico";
	public final static String ARCHIVO_WINDOWS = "Archivo MMA";
	public final static String DIRECTORIO_WINDOWS = "Carpeta de archivos";
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
	public static final String ESTRUCTURA_A = "A - A - B - A - A - B";
	public static final String ESTRUCTURA_B = "A - B - Fill - A - B - B";
	public static final String ESTRUCTURA_C = "A - B - B - A - B - B' - A";
	
	
	// INTERFAZ GRAFICA
	//eventos
	public static final String SALIR = "salir";
	public static final String ACERCA_DE = "acerca_de";
	public static final String COMPONER = "componer";
	public static final String APRENDER = "aprender";
	public static final String ACTUALIZAR = "actualizar";
	public static final String MODIFICAR_CANCION ="modificar_cancion";
	public static final String GUARDAR_CANCION ="guardar_cancion";
	public static final String GUARDAR_CANCION_MODIFICADA ="guardar_cancion_modificada";
	public static final String REPRODUCIR_CANCION ="reproducir";
	public static final String PAUSAR_CANCION ="pausar";
	public static final String APRENDER_CANCION ="aprender_cancion";
	public static final String DETENER_CANCION ="detener_cancion";
	public static final String CAMBIA_ESTILO ="cambia_estilo";

	public static final int OK_ACEPTAR = -1;
	public static final int SI_NO_OPCION = 0;
	public static final int OPCION_SI = 0;
	public static final int OPCION_NO = 1;
	public static final int TABS_POSICION = 3;	//tres = abajo

	public static final String TIPO_BASICO = "Básica";
	public static final String TIPO_INTERMEDIO = "Intermedia";
	public static final String TIPO_AVANZADO = "Avanzada";
	// labels
	public static final String LABEL_TONICA = "Tónica: ";
	public static final String LABEL_ESTILO = "Estilo: ";
	public static final String LABEL_CANT_COMPASES = "Cantidad de Compases: ";
	public static final String LABEL_TEMPO = "Tempo: ";
	public static final String LABEL_TIPO_CANCION = "Tipo de Canción: ";

	//Imagenes
	public static final String BOTON_APRENDER = "img/principalesAprender.png";
	public static final String BOTON_COMPONER = "img/principalesComponer.png";
	public static final String BOTON_CANCIONES = "img/principalesCanciones.png";
	public static final String BOTON_CEREBRO = "img/principalesCerebro.png";
	
	public static final String BOTON_PLAY = "img/play.png";
	public static final String BOTON_PAUSE = "img/pause.png";
	public static final String BOTON_STOP = "img/stop.png";
	
	
	public static final String FONDO_1 = "img/fondo1.jpg";
	public static final String FONDO_2 = "img/fondo2.png";
	public static final String FONDO_3 = "img/fondo3.png";
	
	public static final String IMAGEN_APRENDER = "img/ImgAprender.png";
	public static final String IMAGEN_CEREBRO = "img/ImgCerebro.png";
	public static final String IMAGEN_COMPONER = "img/ImgComponer.png";
	public static final String IMAGEN_CANCIONES = "img/ImgCanciones.png";
}
