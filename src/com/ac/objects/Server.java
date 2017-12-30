package com.ac.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.ac.movingimages.EnemyPlane;
import com.ac.screens.GameScreen;

public class Server {
	private static ServerSocket server;
	private static Socket accepted;
	private static BufferedReader br;
	private static boolean nullified = true;
	private static EnemyPlane gs;
	
	private Server(){}

	public static void recieveKey(EnemyPlane g) throws IOException, InterruptedException{
		gs = g;
		if(server == null)
			server = new ServerSocket(4444);
		Thread allowConnect = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
					if(nullified){
						try {
							accepted = server.accept();
							br = new BufferedReader(new InputStreamReader(accepted.getInputStream()));
							nullified = false;

						} catch (IOException e) {e.printStackTrace();}

						System.out.println("Connected");
					}
					if(!nullified){
						try {
							gs.doAction(br.read());
						} catch (IOException e) {e.printStackTrace();}
					}
				}
			}
		});
	
		allowConnect.start();

	}

}
/*
 * lP lR left	0 1
 * rP rR right  2 3
 * uP uR up 	6 5
 * dP dR down   7 5
 *   s   space   4
 */
