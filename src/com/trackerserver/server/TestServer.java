package com.trackerserver.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.nio.channels.FileLock;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.trackerserver.network.Network;

public class TestServer {

    Server server;

    public TestServer () throws IOException {
            server = new Server();

            // For consistency, the classes to be sent over the network are
            // registered by the same method for both the client and server.
            Network.register(server);

            server.addListener(new Listener() {
                    public void received (Connection c, Object object) {
                    	System.out.println("Something Received");
                            if (object instanceof String[]) {
                            		
                            		String[] clientMessage = (String[])object;
                            		
                            		if(clientMessage.length != 2) {
                            			/** Log Error Message */
                            			return;
                            		}
                            		
                            		String userName = clientMessage[0];
                            		String content = clientMessage[1];
                            		
                            		if(userName.length() != 16) {
                            			/** Log Error Message */
                            			return;
                            		}
                            		
                            		File file = new File("/home/hong/Tracker/data/" + userName);
                            		
                            		try {
										file.createNewFile();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										return;
									}
                            	
                            		FileOutputStream out;
									try {
										out = new FileOutputStream(file, true);
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										return;
									} 
									FileLock lock;
									try {
										lock = out.getChannel().lock();
										out.write(content.getBytes());
										out.flush();
										lock.release();
										out.close();		
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										return;
									} finally {

									}
									
                                    return;
                            }
                    }

                    public void disconnected (Connection c) {

                    }
            });
            server.bind(Network.port);
            server.start();
    }
  

    public static void main (String[] args) throws IOException {
            new TestServer();
            Log.set(Log.LEVEL_DEBUG);
    }
}
