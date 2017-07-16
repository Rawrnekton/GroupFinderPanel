package client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;

/*
 * callable Clienthandler der für jeden neuen Client erzeugt wird
 * und in einem eigene thread dafür sorgt dass der Client seine
 * Nachricht schickt
 */
class ClientHandler implements Callable<String> {
	/*
	 * localhost addresse für
	 */
	String ip = "127.0.0.1";
	/*
	 * port auf dem der client den server sucht
	 */
	int port = 2709; //used by Supermon .. wtf
	/*
	 * stadtname
	 */
	String stadtName;

	/*
	 * Konstruktor des Handlers
	 */
	public ClientHandler(String stadtName) {
		this.stadtName = stadtName;
	}

	/*
	 * call() Funktion des interfaces Callable<String> ruft die Funktion zum
	 * ausgeben der serverantwort auf und gibt diese zurück
	 */
	@Override
	public String call() throws Exception {
		System.out.println("ClientHandler wurde gestartet.");
		//Thread.sleep(2000);
		return RequestServer(stadtName);
		//Thread.sleep(1000);
	}

	/*
	 * Funktion zum Senden und Empfangen der Informationen
	 */
	String RequestServer(String _string) throws IOException {
		String empfangeneNachricht;
		String zuSendendeNachricht;

		/*
		 * Erstellt einen neuen Socket an der angegeben IP, also hier
		 * 127.0.0.1:50000 dies ist die addresse des servers
		 */
		Socket socket = new Socket(ip, port);
		zuSendendeNachricht = _string;
		/*
		 * Sendet den Stadtnamen an den Server
		 */
		schreibeNachricht(socket, zuSendendeNachricht);
		/*
		 * Empfängt die vom Server gesendete Nachricht
		 */
		empfangeneNachricht = leseNachricht(socket);
		/*
		 * schließt den Socket
		 */
		socket.close();
		return empfangeneNachricht;
	}

	/*
	 * sendet die Nachricht an den Server, dazu wird ein printwriter erst mit
	 * dem outputstream des socket verbunden, danach beschrieben und danach
	 * geflusht, der Server empfängt auf der anderen Seite diese Nachricht
	 */
	void schreibeNachricht(Socket socket, String nachricht) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}

	/*
	 * empfängt die Nachricht vom Server, dazu wird der Funktion die Adresse des
	 * socket gegeben danach wird ein reader erzeugt der am inputstream des
	 * socket "ließt", dieser empfängt die vom Server gesendete Nachricht
	 * Antwortlänge ist länger als Nachrichtenlänge, da die Antwort wesentlich
	 * länger ist.
	 */
	String leseNachricht(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[100];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 100);
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}
}