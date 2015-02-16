/*
 Author: Wenhui Zhang
 with help of "An Introduction to Network Programming in Java"
 */


import java.io.*;
import java.net.*;
import java.util.*;

public class ClientUDP
{
    private static InetAddress host;
    private static final int PORT = 1234;
    private static DatagramSocket datagramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;
    
    public static void main(String[] args)
    {
        try
        {
            host = InetAddress.getLocalHost();
        }
        catch(UnknownHostException uhEx)
        {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        accessServer();
    }
    
    private static void accessServer()
    {
        try
        {
            //Step 1 : create a DatagramSocket object
            datagramSocket = new DatagramSocket();
            
            
            Scanner userEntry = new Scanner(System.in);
            
            String message="", response="";
            do
            {
                System.out.print("Enter message: ");
                message = userEntry.nextLine();
                
                if (!message.equals("***CLOSE***"))
                {
                    //Step 2 : create outgoing datagram
                    outPacket = new DatagramPacket(
                                                   message.getBytes(),
                                                   message.length(),
                                                   host,PORT);
                    
                    //Step 3 : sent datagram message
                    datagramSocket.send(outPacket);
                    
                    //Step 4 : create a buffer for incoming datagrams
                    buffer = new byte[256];
                    
                    //Step 5 : create a DatagramPacket object for incoming datagrams
                    inPacket =
                    new DatagramPacket(
                                       buffer, buffer.length);
                    
                    //Step 6 : receive DatagramPacket
                    datagramSocket.receive(inPacket);
                    
                    //Step 7 : retrive data from buffer
                    response =
                    new String(inPacket.getData(),
                               0, inPacket.getLength());
                    System.out.println(
                                       "\nSERVER> " + response);
                }
            }while (!message.equals("***CLOSE***"));
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        
        finally
        {
            //Step 8 : close DatagramSocket
            System.out.println("\n* Closing connection... *");
            datagramSocket.close();
        }
    }
}
