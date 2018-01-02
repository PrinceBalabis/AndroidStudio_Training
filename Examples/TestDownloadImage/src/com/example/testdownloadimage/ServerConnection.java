package com.example.testdownloadimage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ServerConnection {
	String[] answer;
	String question;
	Bitmap bitmapimage;
	Socket socket;
	boolean answerIsImage = false;
	boolean answerIsReturned = false;
	boolean isConnected = false;

	public ServerConnection(String question) {
		this.question = question;
		if(question.contains("image_")){
			answerIsImage = true;
		}
		askServer();
	}

	public boolean isConnected(){
		return isConnected;
	}

	public String[] getAnswer(){
		return answer;
	}

	public Bitmap getImageAnswer(){
		return bitmapimage;
	}

	public boolean isAnswerReturned(){
		return answerIsReturned;
	}


	public void askServer(){
		try {
			socket = new Socket(InetAddress.getByName("195.178.234.232"), 25565 );
			isConnected = true;
			System.out.println("KOPPLAD TILL SERVERAPPLIKATIONEN");
		} catch(Exception e) {}
		if(isConnected()){
			try {
				ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );
				oos.writeUTF( question );
				oos.flush();
				System.out.println("SKICKAT FRÅGA TILL SERVERAPPLIKATIONEN");
			} catch(Exception e1 ) {}

			try {
				try {

					if(answerIsImage){
						DataInputStream dis = new DataInputStream(socket.getInputStream());
                        byte[] pic = new byte[5000*1024];
                        int bytesRead = dis.read(pic, 0, pic.length);
                        bitmapimage = BitmapFactory.decodeByteArray(pic, 0, bytesRead);              

					} else {
						ObjectInputStream input = new ObjectInputStream( socket.getInputStream() );
						answer = (String[]) input.readObject();
					}
					System.out.println("SVAR UTSKRIVEN");
				} catch (ClassNotFoundException e) {}
			} catch(IOException e) {}
			try{
				socket.close();
				System.out.println("SERVER FRÅNKOPPLAD");
			} catch(IOException e) {}
			answerIsReturned=true;
		}
	}
}