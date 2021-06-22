import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;


class ForgotPassword extends JFrame implements ActionListener{
	Connection conn;
	JPanel Header;
	JLabel lblFiller,lblPasswordRecovery,lblEnterUsername;
	JTextField txtUsername;
	JButton btnSubmit,btnReturn;
	String sql;
	PreparedStatement pstmt;
	ResultSet rs;
	static String username;
	static String table;
	//Connection conn;
	
ForgotPassword(){
	createUi();
	setSize(600,600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	createConn();
	createUi();
}

void createUi(){
	Header=new JPanel();
	add(Header);
	Header.setLayout(new GridLayout(3,2));
	
	lblPasswordRecovery=new JLabel("Password Recovery Page");
	lblPasswordRecovery.setFont(new Font("Comic Sans MS",Font.BOLD,20));
	Header.add(lblPasswordRecovery);
	
	lblFiller=new JLabel();
	Header.add(lblFiller);
	
	lblEnterUsername=new JLabel("Enter Your Username");
	Header.add(lblEnterUsername);
	
	txtUsername = new JTextField(20);
	Header.add(txtUsername);
	
	btnSubmit=new JButton("Submit");
	Header.add(btnSubmit);
	btnSubmit.addActionListener(this);
	
	btnReturn=new JButton("Return to Login");
	Header.add(btnReturn);
	btnReturn.addActionListener(this);
	


}

String getUsername(){
	
	return username;
}

void setUsername(String s){
	
	username=s;
}

void setTable(String t){
	table=t;
	
}

String getTable(){
	return table;
	
}
void createConn(){


	try{
		Class.forName("org.postgresql.Driver");
		conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProjectDB","postgres","admin");
		
		
	}catch(Exception ex){
	JOptionPane.showMessageDialog(this,"Exception : "+ex.getMessage());
	}


				}
				
		public void actionPerformed(ActionEvent ae)
			{
		String actioncmd=ae.getActionCommand();
		
		if(actioncmd.equals("Submit"))
		{	
	
			try{
				sql="Select * from users where username=?";
				pstmt=conn.prepareStatement(sql);
					pstmt.setString(1,txtUsername.getText());
					rs=pstmt.executeQuery();
			int i=0;
				if(rs.next()){
					JOptionPane.showMessageDialog(this,"Username Exists in users database.Redirecting you to Password Reset.");
					i++;
					setUsername(txtUsername.getText());
					setTable("users");
					this.setVisible(false);
					new PasswordReset().setVisible(true);
				}
					sql="Select * from admin where username=?";
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1,txtUsername.getText());
					rs=pstmt.executeQuery();
					
					if(rs.next()&&(i==0)){
					JOptionPane.showMessageDialog(this,"Username Exists in admin database.Redirecting you to Password Reset.");
					i++;
					setTable("admin");
					setUsername(txtUsername.getText());
					this.setVisible(false);
					new PasswordReset().setVisible(true);
					}
					
					if(i==0){
						JOptionPane.showMessageDialog(this,"Sorry your username does not exist in the database.Try using Signup Instead");
						this.setVisible(false);
						new MovieAppImpl().setVisible(true);
					}
				}catch(Exception ex){
					ex.printStackTrace();
		}
		}	
		if(actioncmd.equals("Return to Login"))
		{	
	this.setVisible(true);
	new MovieAppImpl().setVisible(true);
		}
		
			}
	public static void main(String args[]){

	new ForgotPassword();
										}
}