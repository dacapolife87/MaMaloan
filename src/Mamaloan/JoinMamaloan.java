package Mamaloan;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Window;
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

class JoinMamaloan extends JFrame implements ActionListener{
	private JFrame joinf;
	private JPanel joinp;
	
	private JLabel titlel;
	private JLabel idl;
	private JLabel pwl;
	private JLabel namel;
	private JLabel phonenuml;
	private JLabel addressl;
	private JLabel accountl;
	private JLabel myinfol;
	
	
	private JTextField idtf;
	private JTextField pwtf;
	private JTextField nametf;
	private JTextField phonenumtf;
	private JTextField addresstf;
	private JTextField accounttf;
	private JTextField myinfotf;
	
	private JButton okb;
	private JButton cancelb;
	
	static DataInputStream dataIn;
	static DataOutputStream dataOut;
	static Socket socket;
	
	

	public JoinMamaloan()
	{
		
		joinf = new JFrame();
		Container ct = joinf.getContentPane();
		joinp=new JPanel();
		
		titlel=new JLabel("회원 가입");
		idl=new JLabel("ID");
		pwl=new JLabel("PW");
		namel=new JLabel("이름");
		phonenuml=new JLabel("전화번호");
		addressl=new JLabel("주소");
		accountl=new JLabel("계좌번호");
		myinfol=new JLabel("자기소개");
		
		idtf=new JTextField();
		pwtf=new JTextField();
		nametf=new JTextField();
		phonenumtf=new JTextField();
		addresstf=new JTextField();
		accounttf=new JTextField();
		myinfotf=new JTextField();
		
		okb=new JButton("가입");
		cancelb=new JButton("취소");
		
		titlel.setBounds(200, 30, 100, 30);
		idl.setBounds(50, 100, 100, 20);
		pwl.setBounds(50, 120, 100, 20);
		namel.setBounds(50, 140, 100, 20);
		phonenuml.setBounds(50, 160, 100, 20);
		addressl.setBounds(50, 180, 100, 20);
		accountl.setBounds(50, 200, 100, 20);
		myinfol.setBounds(50, 220, 100, 20);
		

		idtf.setBounds(120, 100, 100, 20);
		pwtf.setBounds(120, 120, 100, 20);
		nametf.setBounds(120, 140, 100, 20);
		phonenumtf.setBounds(120, 160, 100, 20);
		addresstf.setBounds(120, 180, 200, 20);
		accounttf.setBounds(120, 200, 200, 20);
		myinfotf.setBounds(120, 220, 300, 100);
		
		okb.setBounds(150, 400, 100, 30);
		cancelb.setBounds(250, 400, 100,30);
		
		ct.setLayout(new BorderLayout());
		ct.add(joinp);		
		joinp.setLayout(null);
		
		joinp.add(titlel);
		joinp.add(idl);		
		joinp.add(pwl);		
		joinp.add(namel);		
		joinp.add(phonenuml);		
		joinp.add(addressl);		
		joinp.add(accountl);		
		joinp.add(myinfol);	
		
		joinp.add(idtf);		
		joinp.add(pwtf);		
		joinp.add(nametf);		
		joinp.add(phonenumtf);		
		joinp.add(addresstf);		
		joinp.add(accounttf);		
		joinp.add(myinfotf);
		
		joinp.add(okb);		
		joinp.add(cancelb);		
		
		okb.addActionListener(this);
		cancelb.addActionListener(this);
		
		

		joinf.setTitle("회원가입");
		joinf.setSize(500,500);
		joinf.setVisible(true);
		
		
		try{		
			socket = new Socket("127.0.0.1", 7777);
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
				dataOut.writeUTF("!"+"/"+idtf.getText()+
						"/"+pwtf.getText()+
						"/"+nametf.getText()+
						"/"+phonenumtf.getText()+
						"/"+addresstf.getText()+
						"/"+accounttf.getText()+
						"/"+myinfotf.getText());
				dataOut.flush();
				stop();
				joinf.dispose();
			} catch (Exception e) {
			}

			}
		if (event.getActionCommand().equals("취소"))
		{
			stop();
			joinf.dispose();
			
		}
       
    }
	
}
