package Mamaloan;


import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.sql.*;


public class MamaHandler implements Runnable {
	protected Socket socket;
	protected DataInputStream dataIn;
	protected DataOutputStream dataOut;
	protected Thread listener;
	private Date pdate;
	private String message;
	protected Connection con = null;
    protected Statement stmt = null;

	public MamaHandler(Socket socket) {
		this.socket = socket;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////
	public synchronized void init() {
		DBcon();
		if (listener == null) 
		{
			try {
				dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				listener = new Thread(this);
				listener.start();
			} catch (IOException ignored) {
			}
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////
	public synchronized void stop() {
		if (listener != null) {
			if (listener != Thread.currentThread())
				listener.interrupt();
				listener = null;
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////
	public void run() {
		
		
		try {
			while (!Thread.interrupted()) {
				String message = dataIn.readUTF();
				try{				
				char sel;
				sel = message.charAt(0);
				this.message=message;
					if(sel == '!'){
						newID();						
					}
					if(sel == '@'){
						login();						
					}
					if(sel == '#'){
						clubregister();					
					}
					if(sel == '$'){
						clubinfo();
					}
					if(sel == '%'){
						myclub();				
					}
					if(sel == '^'){
						clubjoin();						
					}
					if(sel == '&'){
						myinfo();						
					}
				}catch(NoSuchElementException e){
					stop();
				}
			}
		}catch (EOFException ignored) {
			System.out.println( "접속을 종료하셨습니다.");
		}catch (IOException ie) {
			if (listener == Thread.currentThread())
				ie.printStackTrace();
	} finally {
			stop();
		}
	}
	
	////////////////////////회원가입//////////////////////////////////////
	public void newID() throws IOException
	{
	              try {
	                	   StringTokenizer stk = new StringTokenizer(message, "/");
	                	   String gubun = stk.nextToken();
	                	   String userID = stk.nextToken();
	                	   String userPassword = stk.nextToken();
	                	   String userName = stk.nextToken();	
	                	   String userPhoneNum = stk.nextToken();
	                	   String userAddress = stk.nextToken();
	                	   String userBankaccount = stk.nextToken();
	                	   String userInfo = stk.nextToken();
	                //	   pdate = new Date();
	                	//   System.out.println(pdate.toString());
	                	   System.out.println("회원가입요청 [ 회원ID : "+userID+
	                			   "\t회원비밀번호 : "+userPassword+
	                			   "\t회원이름 : "+userName+
	                			   "\t회원전화번호 : "+userPhoneNum+
	                			   "\t회원주소 : "+userAddress+
	                			   "\t회원계좌번호 : "+userBankaccount+
	                			   "\t회원소개 : "+userInfo+
	                			   "]");	
	                	   try{
	                		   String sql = "INSERT INTO mamajoinlist VALUES("+"'"+userID+"','"+userPassword+"','"+userName+"','"+userPhoneNum+"','"+userAddress+"','"+userBankaccount+"','"+userInfo+"')";
	                		   stmt.executeUpdate(sql);
	               	   } catch (SQLException e) {
	                		   System.out.println(e);
	                	   }
	                } catch (NoSuchElementException e) {
	                    stop();
	                }
	}/////////////////////////////로그인/////////////////////////////////////
	public void login() throws IOException
	{
		try{
			StringTokenizer stk = new StringTokenizer(message, "/");
			String gubun = stk.nextToken();
			String userID = stk.nextToken();
			String userPassword = stk.nextToken();
	
		
			System.out.println("로그인 [ 회원ID : "+userID+
     			   "\t회원비밀번호 : "+userPassword+
     			   "]");
			
			try{
				String data =  "'"+userID+"',"+"userPassword=" +"'"+userPassword+"',')";
	            String sql = "select count(*) from  mamajoinlist where userID = "+"'"+ userID+"'" +
				"and userPassword= '"+userPassword+"'";
	            ResultSet rs = stmt.executeQuery(sql);
	            rs.next();
	            	if(rs.getInt(1)>0){
	            		dataOut.writeUTF("@");
	            		dataOut.flush();
	            		}
	            	else{
		            	dataOut.writeUTF("&");
		            	dataOut.flush();
		            }
	            } catch (SQLException e) {
					System.out.println(e);
     	   }	
		}catch (NoSuchElementException e) {
            stop();
        }	
		
	}
/////////////////////////////클럽등록/////////////////////////////////////
	public void clubregister() throws IOException
	{
		try {
			StringTokenizer stk = new StringTokenizer(message, "/");
			String gubun = stk.nextToken();
			String userID = stk.nextToken();
			String clubName = stk.nextToken();
			String manlimit = stk.nextToken();	
			String moneyterm = stk.nextToken();
			String clubinfo = stk.nextToken();
	                //	   pdate = new Date();
	                	//   System.out.println(pdate.toString());
			System.out.println("클럽등록요청 [ 회원ID : "+userID+
	                			   "\t클럽이름 : "+clubName+
	                			   "\t클럽총인원 : "+manlimit+
	                			   "\t곗돈주기 : "+moneyterm+
	                			   "\t클럽소개 : "+clubinfo+
	                			   "]");
			try{
			String sql = "select count(*) from  mamajoinlist where userID = "+"'"+ clubName+"'";
			ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            	if(rs.getInt(1)>0){
            		dataOut.writeUTF("@");
            		dataOut.flush();
            		}
            	else{
            		String sql1 = "INSERT INTO clublist VALUES('"+userID+"','"+clubName+"','"+manlimit+"','"+moneyterm+"','"+clubinfo+"','1')";
    				stmt.executeUpdate(sql1);
    				dataOut.writeUTF("#");
            		dataOut.flush();	            	
	            }
			} catch (SQLException e) {
				System.out.println(e);
			}
		} catch (NoSuchElementException e) {
			stop();
		}
	}
	////////////////////////////클럽정보보기//////////////////////////////////////
	public void clubinfo() throws IOException
	{
		try{
		StringTokenizer stk = new StringTokenizer(message, "/");
		String gubun = stk.nextToken();
		String clubName = stk.nextToken();
		System.out.println("클럽정보조회 [클럽이름 : "+clubName+"]");
	
			try{
				String query = "SELECT clubinfo FROM clublist where clubName";
				ResultSet rs = stmt.executeQuery(query);
				
            	String clubinfo = rs.getString("clubinfo");
            	dataOut.writeUTF("$"+"/"+clubinfo);
        		dataOut.flush();
					
			} catch (SQLException e) {
			System.out.println(e);
		}
		} catch (NoSuchElementException e) {
			stop();
		}
	}
	/////////////////////////내클럽보기/////////////////////////////////////////
	public void myclub() throws IOException
	{
		try{
			StringTokenizer stk = new StringTokenizer(message, "/");
			String gubun = stk.nextToken();
			String userID = stk.nextToken();
			
			String query = "SELECT clubName,memlimit,moneyterm,clubinfo FROM clublist where userID="+"'"+userID+"'";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next() ==true){
            	String clubName = rs.getString("clubName");
            	String memlimit = rs.getString("memlimit");
            	String moneyterm = rs.getString("moneyterm");
            	String clubinfo = rs.getString("clubinfo");
            	String nowman = rs.getString("nowman");
            	
            	dataOut.writeUTF("%"+"/"+clubName+"/"+moneyterm+"/"+nowman+"/"+memlimit+"/"+clubinfo);
        		dataOut.flush();
			}
		} catch (SQLException e) {
			System.out.println(e);
	   }	
	}
//////////////////////////////클럽가입////////////////////////////////////	
	public void clubjoin() throws IOException
	{
		try{
			StringTokenizer stk = new StringTokenizer(message, "/");
			String gubun = stk.nextToken();
			String userID = stk.nextToken();
			String clubName = stk.nextToken();
			try{
				String sql = "select count(*) from  mamajoinlist where userID = "+"'"+ userID+"'";
				
				String query = "update clublist set clubName = '"+clubName+"' where userID= '"+userID+"'";
				ResultSet rs = stmt.executeQuery(query);
            	dataOut.writeUTF("%");
        		dataOut.flush();		
				} catch (SQLException e) {
					System.out.println(e);
				}
		} catch (NoSuchElementException e) {
			stop();
		}
	}
///////////////////////////////////정보변경///////////////////////////////
	public void myinfo() throws IOException
	{
		try{
			StringTokenizer stk = new StringTokenizer(message, "/");
			String gubun = stk.nextToken();
			String userID = stk.nextToken();
			
			String query = "SELECT * FROM clublist where userID='"+userID+"'";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next() ==true){
            	String userID1 = rs.getString("userID");
            	String clubName = rs.getString("clubName");
            	String memlimit = rs.getString("memlimit");
            	String moneyterm = rs.getString("moneyterm");
            	String clubinfo = rs.getString("clubinfo");
            	
            	dataOut.writeUTF("%"+"/"+userID1 +"/"+clubName+"/"+memlimit+"/"+moneyterm+"/"+clubinfo);
        		dataOut.flush();
			}
		} catch (SQLException e) {
			System.out.println(e);
	   }	
	}
//////////////////////////////////////////////////////////////////
	
	public void DBcon(){
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    		String url = "jdbc:mysql://10.2.22.25:3306/mamaloan";
    		//Class.forName("com.mysql.jdbc.Driver");
    		//String url = "jdbc:mysql://125.128.245.126:3306/mamaloan";
    		String id = "root";
    		String pw = "1234";
		String property = "?characterEncoding=EUC_KR";
    		con = DriverManager.getConnection(url+property, id, pw); 
    		stmt = con.createStatement();
    		System.out.println("DB 연결 성공!!!");
    	}catch(ClassNotFoundException cnf){
    		System.out.println(cnf);
    	}catch(SQLException se){
    		System.out.println(se);
    	}
    }

}
