package Mamaloan;


import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Vector;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MamaClient extends JFrame implements ActionListener {
	
	static String id;
	static Vector col = new Vector();
	static Vector data = new Vector();
	private String data1[]={"dfdf","2","5"};
	private String data2[]={"eer","5","4"};
	DefaultTableModel dmodel;
	JScrollPane jsp;
	
	//���� ��� ���� ����
	static DataInputStream dataIn;
	static DataOutputStream dataOut;
	static Socket socket;
	
	
	//������,�����̳�,�г�
	private JFrame mainf;	
	private Container ct;
	private JTable table;
	private JPanel jp1;
	private JPanel m1; //�������� ���
	private JPanel m2; //��������
	private JPanel m3; //���� ������ ���
	
	
	///// Label ////////////
	//private JLabel idl; //�α���â id ��
	//private JLabel pwl; //�α���â pw ��
	private JLabel test;//test
	
	//////  TextField /////////
	private JTextField idt;
	private JTextField pwt;
	
	/////   Button  //////////
	private JButton joinb; //�����ϱ�
	private JButton findb; //���̵� ã��
	private JButton loginb; //�α���
	private JButton refreshb;

	
	//�޴�
	JMenuBar jmb;
	JMenu menu1;
	JMenu menu2;
	JMenu menu3;
	JMenuItem jmi;
	
	//���������
	JScrollPane scrollPane;
    ImageIcon icon;


	public MamaClient(){		
			
			
			mainf = new JFrame();
			ct = mainf.getContentPane();
			Container ct = mainf.getContentPane();

			
			
			//�α����� âȭ��
			m1 = new JPanel();
			m1.setLayout(new BorderLayout());
			m2 = new JPanel();
			m2.setLayout(new GridLayout(1,1));
			m3 = new JPanel();
			m3.setLayout(new GridLayout(1,1));
			
			icon = new ImageIcon("C:\\Document and Settings\\�������\\workspace\\MaMaloan\\mainframe1.jpg");
			
			//mainf = new JFrame();
			
			jp1=new JPanel(){public void paintComponent(Graphics g) {            
	            g.drawImage(icon.getImage(), 0, 0, null);
	            setOpaque(false);
	            super.paintComponent(g);
			}
	        };
	        jp1.setLayout(null);
	
		Container c = getContentPane();
		c.setLayout(new GridLayout(1,3));
		
		//�޴��� ���ó���
		jmb = new JMenuBar();
		menu1 = new JMenu("����");
		menu2 = new JMenu("������");
		menu3 = new JMenu("����");
		
		jmi = new JMenuItem("�� ���� �����");
		jmi.addActionListener(this);
		menu1.add(jmi);
		jmi = new JMenuItem("������");
		jmi.addActionListener(this);
		menu1.add(jmi);
		jmi = new JMenuItem("�������� ����");
		jmi.addActionListener(this);
		menu2.add(jmi);
		jmi = new JMenuItem("�� �� ����");
		//jmi.addActionListener(this);
		menu2.add(jmi);
		//jmi.addActionListener(this);
		menu2.add(jmi);
		jmi = new JMenuItem("����");
		//jmi.addActionListener(this);
		menu3.add(jmi);
		jmi = new JMenuItem("����");
		//jmi.addActionListener(this);
		menu3.add(jmi);
		
		jmb.add(menu1);
		jmb.add(menu2);
		jmb.add(menu3);
		
		
		String[] head ={"�� �̸�","�ο���","Ƚ��"};
		dmodel = new DefaultTableModel();
		dmodel.setColumnIdentifiers(head);
		//dmodel.addRow(data1);
		//dmodel.addRow(data2);
		
		table = new JTable(dmodel);
		table.setPreferredScrollableViewportSize(new Dimension(300,400));
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		
		jsp = new JScrollPane(table, v,h);
		
		
		
		
		joinb = new JButton("ȸ������");
		findb = new JButton("ID/PW ã��");
		refreshb = new JButton("���ΰ�ħ");
		//idl = new JLabel(" ID ");
		//pwl = new JLabel(" PW ");
		idt = new JTextField(10);
		pwt = new JTextField(10);
		loginb = new JButton("Login");

		test= new JLabel("test");
		
		
		joinb.addActionListener(this);
		loginb.addActionListener(this);
		refreshb.addActionListener(this);
		
		
		joinb.setBounds(130,300,125,30);
		findb.setBounds(255,300,125,30);
		loginb.setBounds(300,240,80,60);
		
		//idl.setBounds(100,240,80,30);
		//pwl.setBounds(100,270,80,30);

		
		
		idt.setBounds(130,240,170,30);
		pwt.setBounds(130,270,170,30);
		
		jp1.add(findb);
		jp1.add(joinb);
		jp1.add(loginb);
		//jp1.add(idl);
		//jp1.add(pwl);

		jp1.add(idt);
		jp1.add(pwt);		
		
		try{		
			socket = new Socket("127.0.0.1", 7777);
			dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		}
		catch(IOException ie){
			stop();
		}
		
		ct.add(jp1);
		mainf.setSize(500,500);
		mainf.setTitle("Welcome to MAMA LOAN");
		mainf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainf.setVisible(true);
		}
	
	
	
	
	//��ɺ� �޼ҵ� ���� 
	
	//log in
	void logIn()
	{
		id=idt.getText();
//		String pw=pwt.getText();
//		
//		try {
//    	
//		dataOut.writeUTF("@"+"/"+id + "/"+ pw);
//		dataOut.flush();
//		String message=dataIn.readUTF();
//		char sel;
//		sel = message.charAt(0);
//        if(sel == '@'){
            ct.remove(jp1);
            mainf.setJMenuBar(jmb);
            mainf.setVisible(true);
            mainf.setSize(900,600);
            m1.add(jsp,BorderLayout.NORTH);
            m1.add(refreshb,BorderLayout.SOUTH);
            //m2.add(idl);
            //m2.add(pwl);
            ct.add(m1,BorderLayout.WEST);
            ct.add(m2,BorderLayout.CENTER);
            ct.add(m3,BorderLayout.EAST);
                      
//           }
//       if(sel == '&'){
//    	   JOptionPane.showMessageDialog(null, "�α��� ��ư�� �������ϴ�");
//        	JOptionPane.showMessageDialog(null, "�߸��Է��ϼ���","����",JOptionPane.INFORMATION_MESSAGE);
//       	mainf.setVisible(true);
//        }
//		}catch (Exception e) {
//	}
	}
	
	
	//�������� ����� ��Ϻ���
	void showClubList()
	{
		try{
			
			dataOut.writeUTF("+"+"/");
			dataOut.flush();
			
			}catch(Exception e){
				
			}
	}

	//����� ��������
	void showClubInfo()
	{
		String clubname="a";
		try{
		
		dataOut.writeUTF("$"+"/"+id + "/"+ clubname);
		dataOut.flush();
		
		}catch(Exception e){
			
		}
	}
	
	//�� ����� ��Ϻ���
	void myClubs()
	{
		try{
			dataOut.writeUTF("%"+"/"+id);
			dataOut.flush();
		}catch(Exception e)
		{
			
		}
	}
	
	//�޷�
	void calendar()
	{
		try{
			dataOut.writeUTF("-"+"/"+id);
			dataOut.flush();
		}catch(Exception e)
		{
			
		}
	}
	
	
	
	
	
	
	public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("ȸ������")) {
        	new JoinMamaloan();
        }
        if(event.getActionCommand().equals("Login")){
        	  logIn();
        }
        if(event.getActionCommand().equals("���� ���� �� ���")){
        	
        	showClubList();
        }
        if(event.getActionCommand().equals("���� ����")){
        	showClubInfo();
        }
        if(event.getActionCommand().equals("�޷�")){
        	calendar();
        }
        if(event.getActionCommand().equals("�������� ����")){
        	//JOptionPane.showMessageDialog(null, "�α��� ��ư�� �������ϴ�");
        	new ChangeMyInfo();
        }
        if(event.getActionCommand().equals("���ΰ�ħ")){
        	refresh();
        }
        if(event.getActionCommand().equals("�� ���� �����")){
        	new NewClub(id);
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
	void refresh(){
		
		dmodel.addRow(data1);
		dmodel.addRow(data2);
				
	}
	
}



