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
	
	//소켓 통신 관련 변수
	static DataInputStream dataIn;
	static DataOutputStream dataOut;
	static Socket socket;
	
	
	//프레임,컨테이너,패널
	private JFrame mainf;	
	private Container ct;
	private JTable table;
	private JPanel jp1;
	private JPanel m1; //참여가능 목록
	private JPanel m2; //정보보기
	private JPanel m3; //내가 가입한 목록
	
	
	///// Label ////////////
	//private JLabel idl; //로그인창 id 라벨
	//private JLabel pwl; //로그인창 pw 라벨
	private JLabel test;//test
	
	//////  TextField /////////
	private JTextField idt;
	private JTextField pwt;
	
	/////   Button  //////////
	private JButton joinb; //가입하기
	private JButton findb; //아이디 찾기
	private JButton loginb; //로그인
	private JButton refreshb;

	
	//메뉴
	JMenuBar jmb;
	JMenu menu1;
	JMenu menu2;
	JMenu menu3;
	JMenuItem jmi;
	
	//배경입히기
	JScrollPane scrollPane;
    ImageIcon icon;


	public MamaClient(){		
			
			
			mainf = new JFrame();
			ct = mainf.getContentPane();
			Container ct = mainf.getContentPane();

			
			
			//로그인후 창화면
			m1 = new JPanel();
			m1.setLayout(new BorderLayout());
			m2 = new JPanel();
			m2.setLayout(new GridLayout(1,1));
			m3 = new JPanel();
			m3.setLayout(new GridLayout(1,1));
			
			icon = new ImageIcon("C:\\Document and Settings\\강사대기실\\workspace\\MaMaloan\\mainframe1.jpg");
			
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
		
		//메뉴바 셋팅내용
		jmb = new JMenuBar();
		menu1 = new JMenu("파일");
		menu2 = new JMenu("내정보");
		menu3 = new JMenu("도움말");
		
		jmi = new JMenuItem("계 모임 만들기");
		jmi.addActionListener(this);
		menu1.add(jmi);
		jmi = new JMenuItem("끝내기");
		jmi.addActionListener(this);
		menu1.add(jmi);
		jmi = new JMenuItem("개인정보 수정");
		jmi.addActionListener(this);
		menu2.add(jmi);
		jmi = new JMenuItem("내 계 보기");
		//jmi.addActionListener(this);
		menu2.add(jmi);
		//jmi.addActionListener(this);
		menu2.add(jmi);
		jmi = new JMenuItem("도움말");
		//jmi.addActionListener(this);
		menu3.add(jmi);
		jmi = new JMenuItem("정보");
		//jmi.addActionListener(this);
		menu3.add(jmi);
		
		jmb.add(menu1);
		jmb.add(menu2);
		jmb.add(menu3);
		
		
		String[] head ={"계 이름","인원수","횟수"};
		dmodel = new DefaultTableModel();
		dmodel.setColumnIdentifiers(head);
		//dmodel.addRow(data1);
		//dmodel.addRow(data2);
		
		table = new JTable(dmodel);
		table.setPreferredScrollableViewportSize(new Dimension(300,400));
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		
		jsp = new JScrollPane(table, v,h);
		
		
		
		
		joinb = new JButton("회원가입");
		findb = new JButton("ID/PW 찾기");
		refreshb = new JButton("새로고침");
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
	
	
	
	
	//기능별 메소드 시작 
	
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
//    	   JOptionPane.showMessageDialog(null, "로그인 버튼을 눌렀습니다");
//        	JOptionPane.showMessageDialog(null, "잘못입력하셨음","오류",JOptionPane.INFORMATION_MESSAGE);
//       	mainf.setVisible(true);
//        }
//		}catch (Exception e) {
//	}
	}
	
	
	//참여가능 계모임 목록보기
	void showClubList()
	{
		try{
			
			dataOut.writeUTF("+"+"/");
			dataOut.flush();
			
			}catch(Exception e){
				
			}
	}

	//계모임 정보보기
	void showClubInfo()
	{
		String clubname="a";
		try{
		
		dataOut.writeUTF("$"+"/"+id + "/"+ clubname);
		dataOut.flush();
		
		}catch(Exception e){
			
		}
	}
	
	//내 계모임 목록보기
	void myClubs()
	{
		try{
			dataOut.writeUTF("%"+"/"+id);
			dataOut.flush();
		}catch(Exception e)
		{
			
		}
	}
	
	//달력
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
        if (event.getActionCommand().equals("회원가입")) {
        	new JoinMamaloan();
        }
        if(event.getActionCommand().equals("Login")){
        	  logIn();
        }
        if(event.getActionCommand().equals("참여 가능 계 목록")){
        	
        	showClubList();
        }
        if(event.getActionCommand().equals("정보 보기")){
        	showClubInfo();
        }
        if(event.getActionCommand().equals("달력")){
        	calendar();
        }
        if(event.getActionCommand().equals("개인정보 수정")){
        	//JOptionPane.showMessageDialog(null, "로그인 버튼을 눌렀습니다");
        	new ChangeMyInfo();
        }
        if(event.getActionCommand().equals("새로고침")){
        	refresh();
        }
        if(event.getActionCommand().equals("계 모임 만들기")){
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



