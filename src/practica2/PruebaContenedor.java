package practica2;

// Se importan las librerías necesarias
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PruebaContenedor {
	public static void main(String[] args) {
	    // Se realizan las pruebas de los tiempos promedios
		System.out.println("######## Pruebas #####\n");		
		ContenedorDeEnteros a = new ContenedorDeEnteros(20);
		int testCont = 0;		
	    testCrea(a, testCont);			
		System.out.println("\n######## Pruebas con ficheros #####\n");		
		int[] vector = new int[100000]; 	
		int[] vector_no = new int[20000]; 
		leerFicheros(vector, vector_no);
		ContenedorDeEnteros contenedor = new ContenedorDeEnteros(100000);
		FileWriter escritura = null;
		try {
			escritura=new FileWriter("salida2.txt");
			escritura.write("Practica 2 Desarrollo de un contenedor lineal con un vector ordenado\n\n ######## Pruebas con ficheros #####\n Tiempos promedios en milisegundos por cada 1000 operaciones\n");
			System.out.println("######## Tiempo promedio de insercion #####"); 
			escritura.write("######## Tiempo promedio de insercion #####\n");
			tiempoPromedioInsercion(vector, contenedor, escritura); 
			System.out.println("######## Tiempo promedio de extraccion#####");
			escritura.write("\n######## Tiempo promedio de extraccion#####\n"); 
			tiempoPromedioExtraccion(vector ,contenedor, escritura); 
			System.out.println("######## Tiempo promedio de busqueda exitosa#####");
			escritura.write("\n######## Tiempo promedio de busqueda exitosa#####\n");
			tiempoPromedioBusquedaExitosa(vector, contenedor, escritura); 
			System.out.println("######## Tiempo promedio de busqueda infructuosa#####");
			escritura.write("\n######## Tiempo promedio de busqueda infructuosa#####\n");
			contenedor.vaciar();
			tiempoPromedioBusquedaInfructuosa(vector, vector_no, contenedor, escritura); 
			escritura.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// Comprueba el correcto funcionamiento de todas las operaciones de ContenedorDeEnteros
	// Métodos para los test
	public static void testCrea(ContenedorDeEnteros prueba, int testCont) {
		if(prueba.cardinal() == 0 && prueba.elementos().length == 0) {
			successTest(testCont++, "crear()");
			testCardinal(prueba, testCont);
		}else{
			failTest(testCont, "crear()");
		}
	}
	
	public static void testCardinal(ContenedorDeEnteros prueba, int testCont) {
		prueba.insertar(19); // Se prueba a insertar un elemento en una lista vacía.
		for(int i = 0; i < 15; i++) {
			prueba.insertar(i); // Se insertan a continuación 15 elementos.
		}
		// Se realiza una serie de operaciones, finalmente se comprueba su cardinalidad.
		prueba.extraer(0);
		prueba.insertar(20);
		// Con estas inserciones se llena de elementos el vector.
		prueba.insertar(17);
		prueba.insertar(18);
		prueba.insertar(15);
		// El valor 21 ocupa la posición final del vector, el vector se llena
		prueba.insertar(21);
		// Por tanto, ya no se puede insertar el valor 0, ya que no hay espacio.
		prueba.insertar(0);	
		
		if(prueba.cardinal() == 20) {
			successTest(testCont++, "cardinal()");
			testInsertar(prueba, testCont);
		}else{
			failTest(testCont, "cardinal()");
		}
	}
	
	public static void testInsertar(ContenedorDeEnteros prueba, int testCont) {
	    // Se realiza una serie de extracciones para no alterar el vector.
	    prueba.extraer(17);
		prueba.extraer(18);
		prueba.extraer(15);
		prueba.extraer(21);
		if(!prueba.insertar(20) && prueba.insertar(0) && !prueba.insertar(5)) {
			successTest(testCont++, "insertar()");
			testExtraer(prueba, testCont);
		}else{
			failTest(testCont, "insertar()");
		}
	}
		
	public static void testExtraer(ContenedorDeEnteros prueba, int testCont) {
		if(prueba.extraer(20) && !prueba.extraer(17) && prueba.extraer(0)) {
			successTest(testCont++, "extraer()");
			testBuscar(prueba, testCont);
		}else{
			failTest(testCont, "extraer()");
		}
	}
	
	public static void testBuscar(ContenedorDeEnteros prueba, int testCont) {
		if(!prueba.buscar(0) && prueba.buscar(19) && prueba.buscar(4)) {
			successTest(testCont++, "buscar()");
			testElementos(prueba, testCont);
		}else{
			failTest(testCont, "buscar()");
		}
	}
	
	public static void testElementos(ContenedorDeEnteros prueba, int testCont) {
		int [] v = prueba.elementos();
		boolean fallo = false;
		if(v.length == 15) {
			for(int i = 0; i < v.length; i++) {
				if(!prueba.buscar(v[i])) {	
					fallo = true;
					break;
				}
			}
			if(fallo) {
				failTest(testCont, "elementos(), buscar()");
			}else{
				successTest(testCont++, "elementos()");
				testVaciarTodos(prueba, testCont);
			}
		}else{
			failTest(testCont, "elementos()");
		}
	}
	
	public static void testVaciarTodos(ContenedorDeEnteros prueba, int testCont) {
		prueba.vaciar();
		if(prueba.cardinal() == 0) {
			if(!prueba.buscar(5) && !prueba.extraer(0)) {
				prueba.insertar(0);
				if(prueba.cardinal() == 1 && prueba.extraer(0)) {
					successTest(testCont++, "vaciar(), cardinal(), insertar(), extraer(), buscar()");
				}else{
					failTest(testCont, "cardinal(), extraer(), insertar()");
				}
			}else{
				failTest(testCont, "cardinal(), buscar(), extraer()");
			}
		}else{
			failTest(testCont, "vaciar(), cardinal()");
		}
	}
	
	private static void successTest(int testCont, String metodo) {
		System.out.println("test " + metodo + ". " + testCont + "/6 OK");
	}
	
	public static void failTest(int cont, String error){
		System.out.println("Error al probar método " + error + " de la clase ContenedorDeEnteros");
		System.out.println(cont + "/6 Test run");
	}
	
	// Método para leer ficheros
	public static void leerFicheros(int[] vector, int[]vector_no) {
		try {
			RandomAccessFile lectura = new RandomAccessFile("datos.dat", "r");
			for(int i = 0; lectura.getFilePointer() < lectura.length();i++){
				vector[i]=lectura.readInt();
			}
			lectura.close();
			RandomAccessFile lectura_no = new RandomAccessFile("datos_no.dat", "r");
			for(int j = 0; lectura_no.getFilePointer()< lectura_no.length(); j++) {
				vector_no[j]=lectura_no.readInt();
			}
			lectura_no.close();
		} catch (IOException e) {			
			e.printStackTrace(); 
		}
	}
	
	// Métodos para calcular los tiempos promedios de inserción, extracción, busqueda exitosa y búsqueda infructuosa
	public static ContenedorDeEnteros tiempoPromedioInsercion(int[] array, ContenedorDeEnteros contenedorEntrada, FileWriter escritura) throws IOException {		
		double tInicio, tFin;
		double tiempo = 0;
		int i = 0;
		for (int j = 0; j < 100000; j = j + 10000) {	
			tInicio = System.currentTimeMillis();
			for (i = j; i < j + 10000; i++) {
				contenedorEntrada.insertar(array[i]);	
			}
			tFin = System.currentTimeMillis();
			tiempo = tFin - tInicio;
			tiempo = tiempo/10;
			System.out.println(tiempo);
			escritura.write(tiempo + "\n");
			tiempo = 0;
		}
		return contenedorEntrada;
	}
	
	public static void tiempoPromedioExtraccion(int[] vector ,ContenedorDeEnteros contenedorEntrada, FileWriter escritura) throws IOException {			
		double tInicio, tFin;
		double tiempo = 0;
		int i = 0;
		for (int j = 0; j < 100000; j = j + 10000) {	
			tInicio = System.currentTimeMillis();
			for (i = j; i < j + 10000; i++) {			
				contenedorEntrada.extraer(vector[i]);			
			}
			tFin = System.currentTimeMillis();
			tiempo = tFin - tInicio;
			tiempo = tiempo/10;
			System.out.println(tiempo);
			escritura.write(tiempo + "\n");
			tiempo = 0;
		}
	}
	
	public static void tiempoPromedioBusquedaExitosa(int[] vector ,ContenedorDeEnteros contenedorEntrada, FileWriter escritura) throws IOException {			
		double tInicio, tFin;
		double tiempo = 0;
		int i = 0;
		int contador = 6000;
		for (int j = 0; j < 100000; j = j + 10000) {		
			for (i = j; i < j + 10000; i++) {				
				contenedorEntrada.insertar(vector[i]);							
			}
			tInicio = System.currentTimeMillis();
			for (int x = 0; x < 600; x++) {
				for (int k = contenedorEntrada.cardinal() -1; k >= 0; k--) { 	
					contenedorEntrada.buscar(vector[k]);
				}
			}
			tFin = System.currentTimeMillis();
			tiempo = tFin - tInicio;		
			tiempo = tiempo/contador;
			contador+= 6000;
			System.out.println(tiempo);
			escritura.write(tiempo + "\n");
			tiempo = 0;
		}
	}
	
	public static void tiempoPromedioBusquedaInfructuosa(int[] vector, int[] vector_no ,ContenedorDeEnteros contenedorEntrada, FileWriter escritura) throws IOException {			
		double tInicio, tFin;
		double tiempo = 0;
		int i = 0;
		for (int j = 0; j < 100000; j = j + 10000) {		
			for (i = j; i < j + 10000; i++) {				
				contenedorEntrada.insertar(vector[i]);							
			}
			tInicio = System.currentTimeMillis();
			for (int x = 0; x < 500 ; x++) {
				for (int k = 0; k < vector_no.length; k++) { 	
					contenedorEntrada.buscar(vector_no[k]);
				}
			}
			tFin = System.currentTimeMillis();
			tiempo = tFin - tInicio;
			tiempo = tiempo/10000;
			System.out.println(tiempo);
			escritura.write(tiempo + "\n");
			tiempo = 0;
		}
	}	
}