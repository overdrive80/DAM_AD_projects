package EjemplosMongoDB;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.UpdateResult;

public class ActualizarDocumentos {

	public static void main(String[] args) {
		// Nos conectamos a la BD
		
		MongoClient cliente = new MongoClient();
		MongoDatabase db = cliente.getDatabase("mibasedatos");
		MongoCollection<Document> coleccion = db.getCollection("amigos");

		// actualizar la nota de Ana
		coleccion.updateOne(eq("nombre", "Ana"), set("nota", 8));

		//SUBIMOS 1 punto a todos los del curso 1DAM
		UpdateResult updateResult = coleccion.updateMany(eq("curso", "1DAM"), inc("nota", 1));

		System.out.println("Se han modificado: " + updateResult.getModifiedCount());
		System.out.println("Se han seleccionado: " + updateResult.getMatchedCount());
		
		
		cliente.close();
	}

}
