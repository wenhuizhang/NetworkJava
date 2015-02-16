/*
 Author: Wenhui Zhang
 with help of "An Introduction to Network Programming in Java"
 */


import java.io.*;
import java.net.*;
import java.util.*;

public class ServerTCP
{
    private static ServerSocket servSock;
    private static final int PORT = 1234;
    
    public static void main(String[] args)
    {
        System.out.println("Open port...\n");
        
        //Step 1: create a ServerSocket object
        try
        {
            servSock = new ServerSocket(PORT);
        }
        catch(IOException ioEx)
        {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
        do
        {
            handleClient();
        }while (true);
    }
    
    private static void handleClient()
    {
        Socket link = null;
        
        try
        {
            //Step 2: put Server into waiting state
            link = servSock.accept();
            
            //Step 3: set up intput and output streams
            
            Scanner input = new Scanner(
                                        link.getInputStream());
            PrintWriter output =
            new PrintWriter(
                            link.getOutputStream(),true);
            
            //Step 4: send and recieve data
            int numMessages = 0;
            String message = input.nextLine();
            while (!message.equals("***CLOSE***"))
            {
                System.out.println("Message received.");
                numMessages++;
                output.println("Message " + numMessages
                               + ": " + message);
                message = input.nextLine();
            }
            output.println(numMessages
                           + " messages received.");
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        
        finally
        {
            //Step 5: close connection
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
