package com.ac.screens;
import java.awt.CardLayout;
import java.awt.Window;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.*;

import com.ac.objects.Server;


public class MyFrame extends JFrame{
	private static JPanel cards;
	private static StartScreen ss;
	private static TutorialScreen os;
	private static LoadScreen ls;
	private static GameScreen gs;
	private static SettingScreen ses;
	private static CardLayout cl;
	public static boolean playing;
	public MyFrame() throws IOException{
		cards = new JPanel();
		cards.setLayout(new CardLayout());

		ls = new LoadScreen();
		cards.add(ls, LoadScreen.className);
		ss = new StartScreen();
		cards.add(ss, StartScreen.className);
		os = new TutorialScreen();
		os.addKeyListener(os);
		cards.add(os, TutorialScreen.className);
		gs = new GameScreen();
		cards.add(gs, GameScreen.className);
		ses = new SettingScreen();
		cards.add(ses, SettingScreen.className);
	}


	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println(InetAddress.getLocalHost());
		MyFrame w = new MyFrame();
		w.setDefaultCloseOperation(MyFrame.EXIT_ON_CLOSE);
		w.setBounds(100, 100, 1280, 960);
		w.setResizable(false);
		w.add(cards);
		cl = (CardLayout)cards.getLayout();
		w.setVisible(true);
		int min = 2000;
		int max = 6000;
		cl.show(cards, LoadScreen.className);
		Thread.sleep(min + (int)(Math.random() * ((max - min) + 1)));
		cl.show(cards, StartScreen.className);

		while(true){

			if(ss.playGame){
				Object[] options = { "Networked", "Local" };
				int n = JOptionPane.showOptionDialog(null, "Choose singleplayer or multiplayer", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				if(n == JOptionPane.YES_OPTION){
					String ip = JOptionPane.showInputDialog(null, "Your IP Address is " + InetAddress.getLocalHost() +", \nType in the Address of your enemy", "IP Address", JOptionPane.PLAIN_MESSAGE);
					if(ip != null){
						gs.myPlane.otherIP = ip;
						gs.changeMode(1);
						gs.sendSignal();
						while(!gs.connect){
							cl.show(cards, LoadScreen.className);
						}
						cl.show(cards,  GameScreen.className);			
						ss.playGame = false;
						gs.requestFocus();
						playing = true;
					}
				}
				else if(n == JOptionPane.NO_OPTION){
					gs.changeMode(2);
					cl.show(cards,  GameScreen.className);			
					ss.playGame = false;
					gs.requestFocus();
					playing = true;
				}
			}
			if(ss.helpVar){
				cl.show(cards, TutorialScreen.className);
				ss.helpVar = false;
				os.go = true;
				os.requestFocus();
			}
			if(os.backVar){
				cl.show(cards, StartScreen.className);
				os.backVar = false;
				w.requestFocus();
				os.go = false;
			}
			if(ss.settingVar){
				cl.show(cards, SettingScreen.className);
				ss.settingVar = false;
			}
			os.run();
			gs.run(playing);
			Thread.sleep(10);
		}
	}
	public static void infoScreen(){
		cl.show(cards, TutorialScreen.className);
	}
}
