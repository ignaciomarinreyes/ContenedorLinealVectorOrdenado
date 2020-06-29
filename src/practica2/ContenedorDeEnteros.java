package practica2;

public class ContenedorDeEnteros{
    
    // Declaración de las variables necesarias.
	private int[] container; // Vector en el que se almacena los elementos.
	private int numEle; // Controla el número de elementos disponibles.
    
    // Constructor de la clase.
	public ContenedorDeEnteros(int size){
		container = new int[size]; // Inicializa el vector al tamaño prefijado.
		numEle = 0; // Inicializa los elementos disponibles a cero.
	}
    
    // Método que devuelve el número de elementos disponibles del vector. 
	public int cardinal(){
		return numEle;
	}

    // Método que inserta un elemento en el vector, devuelve false si no lo consigue y true si sí.
	public boolean insertar(int ele){
		if (numEle == container.length) {
			return false; // Se contempla que el vector esté lleno y no pueda añadirse más elementos.
		} else {
		    // Se guarda el principio, el final y la mitad del vector para ir actualizándolo.
			int lInf = 0;
			int lSup = numEle - 1;
			int mitad = 0;
			while(lInf<=lSup) {
				mitad = (lSup + lInf)/2; // Se actualiza el nuevo valor central.
				if (container[mitad] == ele) { // Se comprueba si "mitad" es el valor buscado.
					return false; 
				}else if (ele < container[mitad]) { // Si no lo es, se comprueba si es mayor que el valor buscado.
					lSup = mitad - 1;
				} else { // O si es menor que el valor buscado.
					lInf = mitad + 1;
					mitad++;
				}
			}
			// Se rota los valores inferiores o superiores al valor que se introducirá.
			for (int i = numEle-1; i >= mitad ; i--) {
				container[i+1] = container[i];
			}
			// Se inserta el valor en su pocisión correspondiente.
			container[mitad] = ele;
			numEle++;
			return true;
		}	
	}
	
	// Método que extrae un elemento del vector, devuelve false si no lo consigue y true si sí.
	public boolean extraer(int ele){
	    // Se busca del mismo modo que en insertar().
		int lInf = 0;
		int lSup = numEle - 1;
		int mitad = 0;
		while(lInf<=lSup) {
			mitad = (lSup + lInf)/2;
			if (container[mitad] == ele) { // Si lo encuentra en la "mitad", se rota los elementos para extraerlo.
				for (int i = mitad + 1; i < numEle ; i++) {
					container[i-1] = container[i];
				}
				numEle--;
				return true;
			}else if (ele < container[mitad]) { 
				lSup = mitad - 1;
			} else {
				lInf = mitad + 1;
			}
		}
		return false;
	}

    // Método que busca un elemento en el vector.
	public boolean buscar(int ele){
	    // Se busca del mismo modo que en insertar().
		int lInf = 0;
		int lSup = numEle - 1;
		while(lInf<=lSup) {
			int mitad = (lSup + lInf)/2;
			if (container[mitad]== ele) {
				return true;
			}else if (ele < container[mitad]) {
				lSup = mitad - 1;
			} else {
				lInf = mitad + 1;
			}
		}
		return false;
	}

    /* Método que "vacía" todos los elementos del vector. Basta con poner el número de elementos
     * disponibles a cero.*/
	public void vaciar(){
		numEle = 0;
	}

    // Método que devuelve un vector con todos los elementos del contenedor.
	public int[] elementos(){
	    // Se copian los elementos disponibles del contenedor a un nuevo vector para devolverlo.
		int[] result = new int[numEle];
		for (int i = 0; i < result.length ; i++) {
			result[i] = container[i];
		}
		return result;
	}
}