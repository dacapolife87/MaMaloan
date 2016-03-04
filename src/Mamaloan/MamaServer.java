package Mamaloan;

import java.io.*;
import java.net.*;


public class MamaServer {

	public static void main(String args[]) throws IOException {
	  if (args.length != 1)
		throw new IllegalArgumentException("Syntax: MamaServer <port>");
		int port = Integer.parseInt(args[0]);
		ServerSocket server = new ServerSocket(port);
		System.out.println("MamaServer Started..!!!");
		System.out.println(port);
		while (true) {
		Socket client = server.accept();
		System.out.println("Accepted from: " + client.getInetAddress());
		MamaHandler handler = new MamaHandler(client);
		handler.init();
		}

	}
}
