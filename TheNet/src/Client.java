import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame{
	private JTextField jtf = new JTextField();
	
	private JTextArea jta = new JTextArea();
	
	private DataOutputStream  toServer;
	private DataInputStream   fromSever;
	
	public static void main(String[] args){
		new Client();
	}
	
	public Client(){
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("Enter Stock"), BorderLayout.WEST);
		p.add(jtf, BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.RIGHT);
		
		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
		add(new JScrollPane(jta), BorderLayout.CENTER);
		
		jtf.addActionListener(new TextFieldListener());
		
		setTitle("Client");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		try{
			Socket socket = new Socket("localhost", 8000);
			
			fromSever = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException ex){
			jta.append(ex.toString() + "\n");
		}
	}
	
	private class TextFieldListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				String stockName = jtf.getText().trim();
				toServer.writeUTF(stockName);
				toServer.flush();
				
				String price = fromSever.readUTF();
				
				jta.append("Stock Name: " + stockName + "\n");
				jta.append("Stock Price: " + price + "\n");
				jta.append("-----------------------------\n");
			}
			catch(IOException ex){
				System.err.println(ex);
	
			}
		}
	}
	

}
