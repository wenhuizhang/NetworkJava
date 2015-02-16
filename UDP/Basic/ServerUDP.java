/*
 Author: Wenhui Zhang
 with help of "An Introduction to Network Programming in Java"
 */

import java.io.*;
import java.net.*;

public class ServerUDP
{
    private static final int PORT = 1234;
    private static DatagramSocket datagramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;
    
    public static void main(String[] args)
    {
        System.out.println("Opening port...\n");
        
        //Step 1 : create a DatagramSocket object
        try
        {
            datagramSocket = new DatagramSocket(PORT);
        }
        catch(SocketException sockEx)
        {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
        handleClient();
    }
    
    private static void handleClient()
    {
        try
        {
            String messageIn,messageOut;
            int numMessages = 0;
            
            do
            {
                //Step 2 : create a buffer for incoming datagrams
                buffer = new byte[256];
                
                //Step 3 : create a DatagramPacket for incoming datagrams
                inPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(inPacket);
                
                //Step 4 : accept an incoming datagram
                InetAddress clientAddress = inPacket.getAddress();
                
                //Step 5 : accept sender's address and port from the packet
                int clientPort = inPacket.getPort();
                
                //Step 6 : retrieve data from buffer
                messageIn = new String(inPacket.getData(),0, inPacket.getLength());
                
                //Step 7 : create response datagram
                System.out.println("Message received.");
                numMessages++;
                messageOut = "Message " + numMessages + ": " + messageIn;
                outPacket = new DatagramPacket(
                                               messageOut.getBytes(),
                                               messageOut.length(),
                                               clientAddress,
                                               clientPort);
                
                //Step 8: send response datagram
                datagramSocket.send(outPacket);
                
            }while (true);
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        
        finally		//If exception thrown, close connection.
        {
            //Step 9 : close the DatagramSocket
            System.out.println("\n* Closing connection... *");
            datagramSocket.close();
        }
    }
}
