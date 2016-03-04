package Mamaloan;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class NewClub extends JFrame implements ActionListener{

	String myid;
	
	private JFrame makeclubf;
	private JPanel makeclubp;
	
	private JLabel frametitle;
	private JLabel clubnamel;
	private JLabel manlimitl;
	private JLabel moneyterml;
	private JLabel clubinfol;
	
	
	
	private JTextField clubnametf;
	private JTextField manlimittf;
	private JTextField moneytermtf;
	private JTextField clubinfotf;

	
	private JButton okb;
	private JButton cancelb;
	
	static DataInputStream dataIn;
	static DataOutputStream dataOut;
	static Socket socket;
	
	JScrollPane scrollPane;
    ImageIcon icon;
	
	
	public NewClub(String id)
	{
		
		
		
		
		icon = new ImageIcon("C:\\Document and Settings\\강사대기실\\workspace\\MaMaloan\\makeclub.jpg");
		
		makeclubf = new JFrame();
		Container ct = makeclubf.getContentPane();
		makeclubp=new JPanel()
		{public void paintComponent(Graphics g) {
            
            g.drawImage(icon.getImage(), 0, 0, null);
            setOpaque(false);
            super.paintComponent(g);
		}
        };
        
        
		
        myid=id;
		frametitle=new JLabel("계 모임 만들기");
		clubnamel=new JLabel("계이름 : ");
		manlimitl=new JLabel("최대 인원수 :");
		moneyterml=new JLabel("곗돈받는 횟수 :");
		clubinfol=new JLabel("계 모임 소개 :");

		clubnametf=new JTextField();
		manlimittf=new JTextField();
		moneytermtf=new JTextField();
		clubinfotf=new JTextField();
	
		okb=new JButton("만들기");
		cancelb=new JButton("취소하기");
		
		frametitle.setBounds(200, 30, 100, 30);
		clubnamel.setBounds(50, 100, 100, 30);
		manlimitl.setBounds(50, 150, 100, 30);
		moneyterml.setBounds(50, 200, 100, 30);
		clubinfol.setBounds(50, 250, 100, 30);
		
		clubnametf.setBounds(150, 100, 100, 30);
		manlimittf.setBounds(150, 150, 100, 30);
		moneytermtf.setBounds(150, 200, 100, 30);
		clubinfotf.setBounds(150, 250, 300, 100);
		
		okb.setBounds(150, 400, 100, 30);
		cancelb.setBounds(250, 400, 100,30);
		
		ct.setLayout(new BorderLayout());
		ct.add(makeclubp);
		
		makeclubp.setLayout(null);
		makeclubp.add(frametitle);
		makeclubp.add(clubnamel);
		makeclubp.add(manlimitl);
		makeclubp.add(moneyterml);	
		makeclubp.add(clubinfol);
		makeclubp.add(clubnametf);
		makeclubp.add(manlimittf);
		makeclubp.add(moneytermtf);
		makeclubp.add(clubinfotf);
		makeclubp.add(okb);
		makeclubp.add(cancelb);
		
		okb.addActionListener(this);
		cancelb.addActionListener(this);
		
		//scrollPane = new JScrollPane(makeclubp);
        //setContentPane(scrollPane);
        
		
		makeclubf.setTitle("계 모임 만들기");
		makeclubf.setSize(500,500);
		makeclubf.setVisible(true);
		
		
		try{		
			socket = new Socket("127.0.0.1", 7777);
			dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		}
		catch(IOException ie){
			stop();
		}
		
		

		
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		if (event.getActionCommand().equals("만들기")) {
			
			try {
//				if(clubnametf.getText()!=null && manlimittf.getText()!=null 
//						&& moneytermtf.getText()!=null && clubinfotf.getText()!=null)
//				
//				{
					
					dataOut.writeUTF("#"+"/"+myid+"/"+clubnametf.getText()+
							"/"+manlimittf.getText()+
							"/"+moneytermtf.getText()+
							"/"+clubinfotf.getText()
							);
					dataOut.flush();
					stop();
					makeclubf.dispose();
//				}
//				else
//				{
//					
//					JOptionPane.showMessageDialog(null, "미입력된 정보가 있습니다.");
//				}
				
				
			} catch (Exception e) {
			}
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

}



