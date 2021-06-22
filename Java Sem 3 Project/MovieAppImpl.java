import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;



class MovieAppImpl extends JFrame implements ActionListener{	//implements ActionListener
JPanel pnlHeader,pnlInput;
JLabel lblHeader,lblUsername,lblPassword;
JTextField txtUsername;
JPasswordField txtPassword;
JButton btnLogin,btnSignup,btnForgotpw,btnexit;
Connection conn;
PreparedStatement pstmt;
String Sql;
static String name;
ResultSet rs;


MovieAppImpl(){

	createUi();
	setVisible(true);
	setSize(600,600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	createConn();

}

public String getName(){
	
	return name;
}
void createConn(){


	try{
		Class.forName("org.postgresql.Driver");
		conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProjectDB","postgres","admin");
	}catch(Exception ex){
		JOptionPane.showMessageDialog(this,"Exception : "+ex.getMessage());
	}


				}

void createUi() {
	//this.setForeground(new Color(99,145,190));
	
	setVisible(true);
	
	
	lblHeader=new JLabel("Theatre Now");
	lblHeader.setFont(new Font("Comic Sans MS",Font.BOLD,30));
	pnlHeader=new JPanel();
	pnlHeader.add(lblHeader);
	add(pnlHeader,BorderLayout.NORTH);
	
	pnlInput=new JPanel();
	pnlInput.setLayout(new GridLayout(4,2));
	
	lblUsername=new JLabel("Username : ");
	txtUsername = new JTextField(20);
	pnlInput.add(lblUsername);
	pnlInput.add(txtUsername);
	
	lblPassword=new JLabel("Password : ");
	txtPassword = new JPasswordField(20);
	pnlInput.add(lblPassword);
	pnlInput.add(txtPassword);
	
	add(pnlInput);
	
	btnLogin=new JButton("Login");
	btnLogin.addActionListener(this);
	
	pnlInput.add(btnLogin);
	
	btnSignup=new JButton("SignUp");
	btnSignup.addActionListener(this);
	pnlInput.add(btnSignup);
	
	btnForgotpw=new JButton("Forgot Password");
	btnForgotpw.addActionListener(this);
	pnlInput.add(btnForgotpw);
	
	btnexit=new JButton("Exit");
	btnexit.addActionListener(this);
	pnlInput.add(btnexit);
}
public void actionPerformed(ActionEvent ae){
	String actioncmd=ae.getActionCommand();
	if(actioncmd.equals("Login")){
		
		if(txtUsername.getText().equals("")||txtPassword.getText().equals("")){
			JOptionPane.showMessageDialog(this,"You Must enter Username & Password");
			
		}else{
	//JOptionPane.showMessageDialog(this,"Clicked Login");
	Sql="Select * from users where username=? AND password=?";
//JOptionPane.showMessageDialog(this,""+txtUsername.getText()+" "+txtPassword.getText());
try{
pstmt=conn.prepareStatement(Sql);
pstmt.setString(1,txtUsername.getText());
pstmt.setString(2,txtPassword.getText());
rs=pstmt.executeQuery();

/*
while(rs.next()){
JOptionPane.showMessageDialog(this,"User Found");
}	
	*/
	int i=0;
	if(rs.next()){
		i++;
JOptionPane.showMessageDialog(this,"Logged in Successfully");	
name=txtUsername.getText();	
this.setVisible(false);
	new Myprofile().setVisible(true);
	this.setVisible(false);	
	}
	
	Sql="Select * from admin where username=? AND password=?";
	pstmt=conn.prepareStatement(Sql);
	pstmt.setString(1,txtUsername.getText());
	pstmt.setString(2,txtPassword.getText());
	rs=pstmt.executeQuery();
	//System.exit(0);
	
	if(rs.next()&&i==0){
		i++;
		JOptionPane.showMessageDialog(this,"Welcome Admin "+txtUsername.getText()+", Logging you in.");
		this.setVisible(false);
		new admin().setVisible(true);
	}
	
	if(i==0){
JOptionPane.showMessageDialog(this,"Invalid credentials");			
	}

	
}catch(Exception ex){
	
	ex.printStackTrace();
	
}
	
	}
	
	}
	if(actioncmd.equals("SignUp")){
		this.setVisible(false);
		new Signup().setVisible(true);
	}
	if(actioncmd.equals("Forgot Password")){
		this.setVisible(false);
		new ForgotPassword().setVisible(true);
	}
	if(actioncmd.equals("Exit")){
		System.exit(1);
	}
}







public static void main(String args[]){
	new MovieAppImpl();


}
}