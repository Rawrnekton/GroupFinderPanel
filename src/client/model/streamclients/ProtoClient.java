package client.model.streamclients;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ProtoClient implements Runnable {
	/* ---------- ATTRIBUTES ---------- */
	protected String ip;
	protected int port;
	protected Socket socket;
	
	protected int clientID;
	
	protected ObjectOutputStream outToServerStream;
	protected ObjectInputStream inFromServerStream;
	
	protected Object empfangeneNachricht;
	protected Object zuSendendeNachricht;
	
	/* ---------- CONSTRUCTOR ---------- */
	public ProtoClient(String ip, int port, int clientID) {
		this.ip = ip;
		this.port = port;
		this.clientID = clientID;
		
		try {
			socket = new Socket(this.ip, this.port);
			System.out.println("Socket created.");
			
			outToServerStream = new ObjectOutputStream(socket.getOutputStream());
			inFromServerStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* ---------- GET-METHODS ---------- */
	public int getClientID() {
		return this.clientID;
	}
	
	/* ---------- MISC-METHODS ---------- */
	@Override
	public void run() {
		//I am only here so i dont get fined
		//bzw so dass die jew streams runnable nicht implementieren müssen
		
		/*
		schreibeNachricht(socket, zuSendendeNachricht);
		empfangeneNachricht = leseNachricht(socket);

		/*
		socket.close();
		System.out.println("Socket closed");
		*/
	}
	
	protected void schreibeNachricht(Socket socket, Object clientMessage) {
		System.out.println("Writing object to socket.");
		try {
			outToServerStream.writeObject(clientMessage);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Socket konnte nicht beschrieben werden.");
		}
		System.out.println("Writing object was succesfull.");
	}
	
	protected void schreibeNachrichtThrowingError(Socket socket, Object clientMessage) throws IOException{
		System.out.println("Trying to write object to socket.");
		outToServerStream.writeObject(clientMessage);
		System.out.println("Writing onject was succesfull.");
	}

	protected Object leseNachricht(Socket socket) {
		Object inFromServerMessage = null;
		
		try {
			inFromServerMessage = inFromServerStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Socket konnte nicht gelesen werden.");
		}
		return inFromServerMessage;
	}
	
	protected Object leseNachrichtThrowingError(Socket socket) throws IOException {
		Object inFromServerMessage = null;
		try {
			inFromServerMessage = inFromServerStream.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("This actually cant happen. If it does .. uh well, i dun goofed aparently");
		}
		lib.Debug.debug("Socket konnte gelesen werden.");
		
		return inFromServerMessage;
	}
}