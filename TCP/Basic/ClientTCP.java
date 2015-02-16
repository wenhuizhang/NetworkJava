/*
 Author: Wenhui Zhang
 with help of "An Introduction to Network Programming in Java"
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientTCP
{
    private static InetAddress host;
    private static final int PORT = 1234;
    
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
        //Step 1: establish a connection to server
        Socket link = null;
        
        try
        {
            link = new Socket(host,PORT);
            
            //Step 2: set up input and output streams
            
            Scanner input = new Scanner(
                                        link.getInputStream());
            
            PrintWriter output =
            new PrintWriter(
                            link.getOutputStream(),true);
            
            
            Scanner userEntry = new Scanner(System.in);
            
            String message, response;
            
            //Step 3: send and recieve data
            do
            {
                System.out.print("Enter message: ");
                message =  userEntry.nextLine();
                output.println(message);
                response = input.nextLine();
                System.out.println("\nSERVER> " + response);
            }while (!message.equals("***CLOSE***"));
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        
        finally
        {
            
            //Step 4: close connection
            try
            {
                System.out.println(
                                   "\n* Closing connection... *");
                link.close();
            }
            catch(IOException ioEx)
            {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
}
