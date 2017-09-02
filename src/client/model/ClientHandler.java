package client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

class ClientHandler implements Callable<Object> {

	private String ip = "127.0.0.1"; // "vigor-mortis.ddns.net"; //will be configurable via profile
	private int port = 2709;

	Object clientMessage;

	ObjectOutputStream outToServerStream;
	ObjectInputStream inFromServerStream;
	
	public ClientHandler(Object clientMessage) {
		this.clientMessage = clientMessage;
	}

	@Override
	public Object call() throws Exception {
		System.out.println("ClientHandler wurde gestartet.");
		return RequestServer(clientMessage);
	}

	public Object RequestServer(Object clientMessage) throws IOException {

		Object empfangeneNachricht;
		Object zuSendendeNachricht = clientMessage;

		Socket socket = new Socket(ip, port);

		System.out.println("Socket created.");

		outToServerStream = new ObjectOutputStream(socket.getOutputStream());
		inFromServerStream = new ObjectInputStream(socket.getInputStream());
		
		schreibeNachricht(socket, zuSendendeNachricht);
		empfangeneNachricht = leseNachricht(socket);

		socket.close();
		System.out.println("Socket closed");
		return empfangeneNachricht;
	}

	void schreibeNachricht(Socket socket, Object clientMessage) throws IOException {
		System.out.println("Writing object to socket.");
		outToServerStream.writeObject(clientMessage);
		System.out.println("Writing object was succesfull.");
	}

	private Object leseNachricht(Socket socket) throws IOException {
		Object inFromServerMessage = null;
		
		try {
			inFromServerMessage = inFromServerStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Socket konnte nicht gelesen werden.");
		}
		return inFromServerMessage;
	}
}