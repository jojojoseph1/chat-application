import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SimpleChatClient 
{
JTextArea incoming;
JTextField outgoing;
BufferedReader reader;
PrintWriter writer;
Socket sock;
	public static void main(String[] args)
	{
	SimpleChatClient client=new SimpleChatClient();
	client.go();
	}


		public void go()
		{
		JFrame frame=new JFrame("chat client");
		JPanel mainpanel=new JPanel();
		incoming=new JTextArea(15,50);
incoming.setLineWrap(true);
incoming.setWrapStyleWord(true);
incoming.setEditable(false);
JScrollPane qscroller=new JScrollPane(incoming);
qscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
qscroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
outgoing=new JTextField(20);
JButton sendButton=new JButton("send");
sendButton.addActionListener(new sendButtonListener());
mainpanel.add(qscroller);
mainpanel.add(outgoing);
mainpanel.add(sendButton);
setUpNetworking();
Thread readerThread=new Thread(new IncomingReader());
readerThread.start();
frame.getContentPane().add(BorderLayout.CENTER,mainpanel);
frame.setSize(400,500);
frame.setVisible(true);

		}





	private void setUpNetworking()
	{
		try
		{
		sock=new Socket("192.168.0.4 ",5000);
InputStreamReader streamreader=new InputStreamReader(sock.getInputStream());
reader=new BufferedReader(streamreader);
writer=new PrintWriter(sock.getOutputStream());
System.out.println("network established");
		}
			catch(IOException ex)
			{
			ex.printStackTrace();
			}	
	
	}








public class sendButtonListener implements ActionListener 
{
	public void actionPerformed(ActionEvent ev)
	{
		try
		{
		writer.println(outgoing.getText());
		writer.flush();	
		}
			catch(Exception ex)
			{
			ex.printStackTrace();
			}	
outgoing.setText("");
outgoing.requestFocus();					
	}

}




public class IncomingReader implements Runnable 
{
public void run()
	{
	String message;
		try 
		{
			while((message=reader.readLine())!=null)
			{
			System.out.println("read" +message);
			incoming.append(message + "\n");
			}
		}
				catch(Exception ex)
				{
				ex.printStackTrace();
				}
	
	}
}


}






















