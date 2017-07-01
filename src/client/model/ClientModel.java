package client.model;

import java.util.concurrent.FutureTask;

public class ClientModel {

/*
 * Erzeugen eines Clients, der sich eigenständig zu einem Server verbindet und Wetterdaten
 * vom beim Aufruf angegeben Städten abfragt
 */
	String stadtName;
	/*
	/*
	 * main zur Erzeugung der Clients denen je ein Argument zum abfragen mitgegeben wird
	 *
	public static void main(String args[]) {
		/*
		 * Falls keine Argumente angegeben worden sind,
		 * bricht das Programm ab
		 *
		if(args.length == 0) {
			System.out.println("Stadtname fehlt!");
			System.exit(1);
		}
		
		/*
		 * for-schleife zum ertsellen je eines Clients pro argument
		 *
		for(int zaehler = 0; zaehler < args.length; zaehler++) {
			String stadtName = args[zaehler];
			try {
				/*
				 * erstellen des neuen Clients mit seinem Konstruktor
				 *
				ClientModel ksClient = new ClientModel(stadtName);
				/*
				 * Aufrufen seiner Funktion um die Abfrage zu starten
				 *
				ksClient.worker();
			} catch (Exception e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}
	*/
	/*
	 * Konstruktor des Clients
	 */
	public ClientModel(String stadtName) throws Exception {
		this.stadtName = stadtName;
		this.worker();
	}
	
	/*
	 * Erzeugt für den Client einen Handler der in einem neuen Thread ausgeführt wird
	 * beim starten des thread wird durch den FutureTask die call()-Funktion
	 * des Handlers ausgeführt
	 */
	void worker() throws Exception {
		ClientHandler ch = new ClientHandler(stadtName);
		FutureTask<String> ft = new FutureTask<String>(ch);
		Thread tft = new Thread(ft);
		tft.start();
		
		/*
		 * ermöglicht anderen threads zu arbeiten
		 * um zu verhindern, dass einige clients zu lange warten müssen
		 * sondern alle etwa gleich lang
		 */
		while(!ft.isDone()) {
			Thread.yield();
		}
		/*
		 * erhält vom Server die Antwort und printed diese
		 * (antwort wird direkt von call()-funktion erhalten, diese erhält sie vom server)
		 */
		System.out.println(ft.get());
	}
}