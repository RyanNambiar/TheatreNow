import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class Mymovies extends JFrame implements ActionListener{
	JPanel pnllist;
JLabel lblMov;
JTextField txtresult;
Connection conn;
PreparedStatement pstmt;
String Sql;
ResultSet rs;
JButton[] movie_array;
JButton btnback;
int cnt=0;
static String selectedMovie;

Mymovies()  {
	createConn();
	createUi();
	setSize(600,600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	

}

void createConn(){


	try{
		Class.forName("org.postgresql.Driver");
		conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProjectDB","postgres","admin");
		Sql="Select * from user_movies where username=?";
		pstmt=conn.prepareStatement(Sql);
		MovieAppImpl mai=new MovieAppImpl();
		mai.setVisible(false);
		pstmt.setString(1,mai.getName());
		rs=pstmt.executeQuery();
		
		while(rs.next()){
			cnt++;
			System.out.println(cnt);
		}
		movie_array=new JButton[cnt];
		rs=pstmt.executeQuery();
		int i=0;
		while(rs.next()){
			String s=rs.getString(3);
			movie_array[i]=new JButton(s);
			//System.out.println(rs.getString(3));
			movie_array[i].addActionListener(  
			new ActionListener() {  
     
   //Handle event
			public void actionPerformed(ActionEvent event) {  
			System.out.println("Clicking Movie array "+s);
			selectedMovie=s;
			//System.out.println(selectedMovie);
			disablecurrent();
			new DisplayUserMovie().setVisible(true);
      //Your Actions Here.....
			
			}  
  }  
  );
			i++;
		}
	
		
	}catch(Exception ex){
		JOptionPane.showMessageDialog(this,"Exception : "+ex.getMessage());
	}


				}
				
			void disablecurrent(){
				this.setVisible(false);
			}	
	String getSelectedMovie(){
					return selectedMovie;
		}


	void createUi(){
		setVisible(true);
		pnllist=new JPanel();
		lblMov=new JLabel("Movie List :");
		lblMov.setFont(new Font("Comic Sans MS",Font.BOLD,30));
		add(pnllist);
		System.out.println("Cnt vairable"+cnt);
		pnllist.setLayout(new GridLayout((cnt+2),1));
		pnllist.add(lblMov);
		for(int i=0;i<cnt;i++){
		pnllist.add(movie_array[i]);
		
		}
		btnback= new JButton("Back");
		pnllist.add(btnback);
		btnback.addActionListener(this);
		
		//txtresult=new JTextField(20);
		//pnllist.add(txtresult);
		
		
		
		
	}
	public void actionPerformed(ActionEvent ae)
			{
		String actioncmd=ae.getActionCommand();
		
		if(actioncmd.equals("Back"))
			{
				this.setVisible(false);
				new Myprofile().setVisible(true);
				
			}
			}



public static void main(String args[]){
	new Mymovies();
	
	
	
}




}