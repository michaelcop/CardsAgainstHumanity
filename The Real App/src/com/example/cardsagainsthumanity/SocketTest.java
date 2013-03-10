package com.example.cardsagainsthumanity;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTest implements Runnable
{
	public void run()
	{
		try
		{
			final String serverIpAddress = "54.225.225.185";//change ip address
			final int serverPort = 8080;
			InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
			Socket socket = new Socket(serverAddr, serverPort);
			boolean connected = true;
			while(connected)
			{
				try
				{
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
					out.println("Hey server");
				}
				catch(Exception e)
				{
					System.out.println("Error socket server");
				}
				socket.close();
			}
		}
		catch(Exception e)
		{
			System.out.println("Error socket server");
		}
	}
}