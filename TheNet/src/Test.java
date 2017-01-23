import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.*;
import java.util.*;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class Test extends JFrame{
	private JTextArea jta = new JTextArea();
	
	
	public Test(){
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);
		
		setTitle("Sever");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		try{
			ServerSocket serverSocket = new ServerSocket(8000);
			jta.append("Server started at " + new Date() + '\n');
			
			Socket socket = serverSocket.accept();
			
			DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
			DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
			
			while(true){
				String name = inputFromClient.readUTF();
				Stock stock = YahooFinance.get(name);
				String price = stock.getQuote().getPrice().toString();
				outputToClient.writeUTF(price);
				
				jta.append("Stock name from Client: " + name + "\n");
				jta.append("Stock price: " + price);
			}
				
				
			}
			catch(IOException ex){
				System.err.println(ex);
			}

	}	

}
