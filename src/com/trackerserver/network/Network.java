package com.trackerserver.network;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import java.util.ArrayList;
public class Network {
	
	static public final int port = 54555;
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(String[].class, 222);
	}

}
