import java.io.*;
import java.net.*;
import java.util.*;



public class VerySimpleChatServer
{
ArrayList clientoutputstreams;
	public class ClientHandler implements Runnable 
	{
	BufferedReader reader;
	Socket sock;
		public ClientHandler(Socket clientSocket)
		{
		try{
		sock=clientSocket;
		InputStreamReader isreader=new InputStreamReader(sock.getInputStream());
		reader=new BufferedReader(isreader);}
			catch(Exception ex){ex.printStackTrace();}
			
		}
				public void run()
				{
				String message;
				try{
						while((message=reader.readLine())!=null)
						{
						System.out.println("read" +message);
						tellEveryone(message);}
						}catch(Exception ex){ex.printStackTrace();}
				}
	}


			public static void main(String[] args)
			{
			new VerySimpleChatServer().go();
			}



public void go()
{
clientoutputstreams=new ArrayList();
try{
ServerSocket serversock=new ServerSocket(5000);
while(true)
	{
Socket ClientSocket=serversock.accept();
PrintWriter writer=new PrintWriter(ClientSocket.getOutputStream());
clientoutputstreams.add(writer);
Thread t=new Thread(new ClientHandler(ClientSocket));
t.start();
System.out.println("got a connection");
        }
}
catch(Exception ex){ex.printStackTrace();}
}	







public void tellEveryone(String message)
{
Iterator it=clientoutputstreams.iterator();
while(it.hasNext())
{
try{
PrintWriter writer=(PrintWriter)it.next();
writer.println(message);
writer.flush();
}
catch(Exception ex){ex.printStackTrace();}
}
}












}





