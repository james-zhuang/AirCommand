package com.ac.objects;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	private static Socket socket;
	private static OutputStream os;
	private Client(){}

	public static void sendKey(int keycode, String address) throws IOException{
		if(socket == null){
			socket = new Socket(address, 4444);	
			os = socket.getOutputStream();
		}
		os.write(keycode);
	}

}
/*
 * lP lR left	0 1
 * rP rR right  2 3
 * uP uR up 	6 5
 * dP dR down   7 5
 *   s   space   4
 */

