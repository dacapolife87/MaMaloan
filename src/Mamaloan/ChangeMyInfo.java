package Mamaloan;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ChangeMyInfo extends JFrame implements ActionListener{
	private JFrame changef;
	private JPanel pn1,pn2,pn3;
	
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private JTextField tf5;
	private JTextField tf6;
	//private JTextField tf7;
	
	private JLabel lb1;
	private JLabel lb2;
	private JLabel lb3;
	private JLabel lb4;
	private JLabel lb5;
	private JLabel lb6;
	private JLabel lb7;
	//private JLabel lb8;
	
	private JButton bt1;
	
	static DataInputStream dataIn;
	static DataOutputStream dataOut;
	static Socket socket;
	
	

	public ChangeMyInfo()
	{
		
		changef = new JFrame();
		Container ct = changef.getContentPane();
		pn1=new JPanel();
		pn2=new JPanel();
		pn3=new JPanel();
		
		lb1=new JLabel("이름");
		lb2=new JLabel("전화번호");
		lb3=new JLabel("주소");
		lb4=new JLabel("계좌번호");
		lb5=new JLabel("내 정보 수정하기");
		lb6=new JLabel();
		//lb7=new JLabel("회원ID");
		lb7=new JLabel("비밀번호");
		
		tf1=new JTextField("이름");
		tf2=new JTextField("전화번호");
		tf3=new JTextField("주소");
		tf4=new JTextField("계좌번호");
		tf5=new JTextField("자기소개");
		//tf6=new JTextField("회원ID");
		tf6=new JTextField("비밀번호");
		bt1=new JButton("수정하기");
		bt1.addActionListener(this);
		
		pn1.setLayout(new GridLayout(7,2));
		pn1.add(lb5);
		pn1.add(lb6);		
		pn1.add(lb7);
		pn1.add(tf6);
		//pn1.add(lb8);
		//pn1.add(tf7);
		pn1.add(lb1);
		pn1.add(tf1);
		pn1.add(lb2);
		pn1.add(tf2);
		pn1.add(lb3);
		pn1.add(tf3);
		pn1.add(lb4);
		pn1.add(tf4);
		pn2.setLayout(new GridLayout(1,1));
		pn2.add(tf5);
		pn3.setLayout(new GridLayout(1,1));
		pn3.add(bt1);
		
		
		
		ct.setLayout(new BorderLayout(2,2));
		ct.add(pn1,BorderLayout.NORTH);
		ct.add(pn2,BorderLayout.CENTER);
		ct.add(pn3,BorderLayout.SOUTH);
		changef.setTitle("내 정보 수정하기");
		changef.setSize(400,300);
		changef.setVisible(true);
		
		
		try{		
			socket = new Socket("127.0.0.1", 7000);
			dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		}
		catch(IOException ie){
			stop();
		}

		
	}
	
	
	public void stop(){
		try {
			dataIn.close();
			dataOut.close();
			socket.close();
		}
		catch (IOException e) {
			
		}
	}
	
	
	
		
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("가입")) {
			try {
				dataOut.writeUTF("^"+"/"+tf1.getText()+
						"/"+tf2.getText()+
						"/"+tf3.getText()+
						"/"+tf4.getText()+
						"/"+tf5.getText()+
						"/"+tf6.getText());
				dataOut.flush();
			} catch (Exception e) {
			}

			}
       
    }
}
