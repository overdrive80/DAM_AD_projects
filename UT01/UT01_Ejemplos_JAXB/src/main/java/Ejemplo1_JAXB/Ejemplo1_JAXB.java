package Ejemplo1_JAXB;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
 

public class Ejemplo1_JAXB {
 
    private static final String MIARCHIVO_XML = "./libreria.xml";
 
    public static void main(String[] args) throws JAXBException, IOException {
        //Se crea la lista de libros
        ArrayList<Libro> libroLista = new ArrayList<Libro>();
 
        // Creamos dos libros y los añadimos
        Libro libro1 = new Libro("Entornos de Desarrollo", "Alicia Ramos","Garceta","978-84-1545-297-3" );
        libroLista.add(libro1);
        Libro libro2 = new Libro("Acceso a Datos", "Maria Jesús Ramos","Garceta","978-84-1545-228-7" );
        libroLista.add(libro2);
 
        // Se crea La libreria y se le asigna la lista de libros
        Libreria milibreria = new Libreria();
        milibreria.setNombre("Prueba de libreria JAXB");
        milibreria.setLugar("Talavera, como no");
        milibreria.setListaLibro(libroLista);
 
        // Creamos el contexto indicando la clase raíz
        JAXBContext context = JAXBContext.newInstance(Libreria.class);
      
        //Creamos el Marshaller, convierte el java bean en una cadena XML
        Marshaller m = context.createMarshaller();
        
        //Formateamos el xml para que quede bien
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
        // Lo visualizamos con system out
        m.marshal(milibreria, System.out);
 
        // Escribimos en el archivo
        m.marshal(milibreria, new File(MIARCHIVO_XML));
 
        // Visualizamos ahora los datos del documento XML creado
        System.out.println("------------ Leo el XML ---------");
        
        //Se crea Unmarshaller en el cotexto de la clase Libreria
        Unmarshaller unmars = context.createUnmarshaller();
        
        //Utilizamos el m�todo unmarshal, para obtener datos de un Reader
        Libreria libreria2 = (Libreria) unmars.unmarshal(new FileReader(MIARCHIVO_XML));
        
        //Recuperamos el array list y visualizamos
        System.out.println("Nombre de librería: "+ libreria2.getNombre());
        System.out.println("Lugar de la librería: "+ libreria2.getLugar());
        System.out.println("Libros de la librería: ");
        
        ArrayList<Libro> lista = libreria2.getListaLibro();
        for (Libro libro : lista) {
            System.out.println("\tTítulo del libro: " + libro.getNombre() 
              + " , autora: " + libro.getAutor());
 
        }
        
 
 
    }
}