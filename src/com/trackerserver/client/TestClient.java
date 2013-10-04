package com.trackerserver.client;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.trackerserver.network.Network;

public class TestClient {
	public static String ip;
	public static String[] message;
	Client client;
	
	public TestClient() {
		
	}

	public TestClient(String ipaddr, String[] msg) {
		ip = ipaddr;
		message = msg;
		
		client = new Client();
		client.start();
		Network.register(client);
		client.addListener(new Listener() {
			public void connected(Connection connection) {
				client.sendTCP(message);
				connection.close();
			}  
			
			public void received(Connection connection, Object object) {

			}
			
			public void disconnected(Connection connection) {
				;
			}
		});
		
		new Thread() {  
			public void run() {
				try {
					client.connect(5000, ip, Network.port);
				} catch(IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}.start();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[] clientMessage = new String[2];
		clientMessage[0] = "bc6ef7035ddb4765";
		clientMessage[1] = "Silicon Valley\n Pittsubrughdfhdkhfksdhfkshfdksfhskhskfhskfhskfhsdkfs\n";
		
		new TestClient("209.129.244.6", clientMessage);
	
	}

}
