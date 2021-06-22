import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class admin extends JFrame implements ActionListener {
	JPanel pnlHeader;
	JLabel lblHeader,lblFiller,lblArea,lblMovie,lblTheatre,lblTime,lblScreen,lblSeats,lblPrice;
	JTextField txtArea,txtMovie,txtTheatre,txtTime,txtScreen,txtSeats,txtPrice;
	JButton btnSubmit,btnLogout;
	Connection conn;
	String sql;
	PreparedStatement pstmt;
	
	
admin(){
	setVisible(true);
	createConn();
	createUi();
	setSize(600,600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}


void createConn(){


		try{
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProjectDB","postgres","admin");
			
			
		}catch(Exception ex){
		JOptionPane.showMessageDialog(this,"Exception : "+ex.getMessage());
		}


					}
					
					void createUi(){
						pnlHeader=new JPanel();
						add(pnlHeader);
						pnlHeader.setLayout(new GridLayout(9,2));
						lblHeader= new JLabel("Enter Records here :");
						pnlHeader.add(lblHeader);
						lblFiller=new JLabel();
						pnlHeader.add(lblFiller);
						
						lblArea=new JLabel("Enter Area :");
						pnlHeader.add(lblArea);
						txtArea=new JTextField(20);
						pnlHeader.add(txtArea);
						
						lblMovie=new JLabel("Enter Movie :");
						pnlHeader.add(lblMovie);
						txtMovie=new JTextField(20);
						pnlHeader.add(txtMovie);
						
						lblTheatre=new JLabel("Enter Theatre :");
						pnlHeader.add(lblTheatre);
						txtTheatre=new JTextField(20);
						pnlHeader.add(txtTheatre);
						
						lblTime=new JLabel("Enter Time :");
						pnlHeader.add(lblTime);
						txtTime=new JTextField(20);
						pnlHeader.add(txtTime);
						
						lblScreen=new JLabel("Enter Screen:");
						pnlHeader.add(lblScreen);
						txtScreen=new JTextField(20);
						pnlHeader.add(txtScreen);
						
						lblSeats=new JLabel("Enter Seats :");
						pnlHeader.add(lblSeats);
						txtSeats=new JTextField(20);
						pnlHeader.add(txtSeats);
						
						lblPrice=new JLabel("Enter Price :");
						pnlHeader.add(lblPrice);
						txtPrice=new JTextField(20);
						pnlHeader.add(txtPrice);
						
						btnSubmit=new JButton("Submit");
						pnlHeader.add(btnSubmit);
						btnSubmit.addActionListener(this);
						
						btnLogout=new JButton("Logout");
						pnlHeader.add(btnLogout);
						btnLogout.addActionListener(this);
						
					}
					
		public void actionPerformed(ActionEvent ae)
			{
		String actioncmd=ae.getActionCommand();
		
		if(actioncmd.equals("Submit"))
			{	
		
		if(txtArea.getText().equals("")||txtMovie.getText().equals("")||txtTheatre.getText().equals("")||txtTime.getText().equals("")||txtSeats.getText().equals("")||txtScreen.getText().equals("")||txtPrice.getText().equals("")){
			
			JOptionPane.showMessageDialog(this,"You *HAVE* to enter all rows before Submitting");
			
		}
		else{
		sql="Insert into movies values(?,?,?,?,?,?,?)";
		try{
	pstmt=conn.prepareStatement(sql);
	pstmt.setString(1,txtArea.getText());
	pstmt.setString(2,txtMovie.getText());
	pstmt.setString(3,txtTheatre.getText());
	pstmt.setString(4,txtTime.getText());
	pstmt.setInt(5,Integer.parseInt(txtScreen.getText()));
	pstmt.setString(6,txtSeats.getText());
	pstmt.setInt(7,Integer.parseInt(txtPrice.getText()));	
	int i=pstmt.executeUpdate();
		if(i==0){
			JOptionPane.showMessageDialog(this,"Some Error Occurred.Please try again");
			
		}else{
			JOptionPane.showMessageDialog(this,"Movies table was updated Successfully");
			
		}
	
		}catch(Exception ex){
			ex.printStackTrace();
		}
			}
			}
			
			if(actioncmd.equals("Logout")){
				this.setVisible(false);
				new MovieAppImpl().setVisible(true);
				
			}
			}	

public static void main(String args[]){

new admin();
}
}