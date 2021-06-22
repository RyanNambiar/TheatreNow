import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
//import org.apache.pdfbox.pdmodel.PDDocument;


class DisplayUserMovie extends JFrame implements ActionListener{
JPanel pnlHeader;
JLabel lblBook,lblmovie,lblscreen,lbltheatre,lbltotal_seats,lblseatno,lbltotalprice;
String user,theatre,movie,seatno;
int total_seats,totalprice,screen;
Connection conn;
String sql;
ResultSet rs;
PreparedStatement pstmt;
JButton printdetails,back;

DisplayUserMovie(){
	setSize(600,600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	createConn();
	loadInformation();
	createUi();
}
void createUi(){
	pnlHeader=new JPanel();
	add(pnlHeader);
	pnlHeader.setLayout(new GridLayout(9,1));
	lblBook=new JLabel("Booking Details :");
	pnlHeader.add(lblBook);
	lblmovie=new JLabel("Movie :"+movie);
	pnlHeader.add(lblmovie);
	lbltheatre=new JLabel("Theatre :"+theatre);
	pnlHeader.add(lbltheatre);
	lblscreen=new JLabel("Screen :"+screen);
	pnlHeader.add(lblscreen);
	lbltotal_seats=new JLabel("No. of Seats :"+total_seats);
	pnlHeader.add(lbltotal_seats);
	lblseatno=new JLabel("Seat No./nos :"+seatno);
	pnlHeader.add(lblseatno);
	lbltotalprice=new JLabel("Total Price :"+totalprice);
	pnlHeader.add(lbltotalprice);
	printdetails=new JButton("Print Details");
	printdetails.addActionListener(this);
	back=new JButton("Back to Profile");
	back.addActionListener(this);
	
	pnlHeader.add(printdetails);
	pnlHeader.add(back);
	
	
}

void loadInformation(){
Mymovies mm=new Mymovies();
mm.disablecurrent();
movie=mm.getSelectedMovie();
//System.out.println(movie);
try{
sql="select * from user_movies where movie=?";
	pstmt=conn.prepareStatement(sql);
	pstmt.setString(1,movie);
	rs=pstmt.executeQuery();
	
	while(rs.next()){
		/*String user,theatre,movie,seatno;
		int total_seats,totalprice,screen;
		*/
		theatre=rs.getString(2);
		screen=rs.getInt(4);
		seatno=rs.getString(5);
		total_seats=rs.getInt(6);
		totalprice=rs.getInt(7);
}
System.out.println("Seat No:"+seatno);
if(total_seats>1){
String[] splitseats=seatno.split("-");
String s="";
int isfirst=0;
	for(int i=0;i<total_seats;i++){
		if(isfirst==0){
	s=s.concat("Seat "+splitseats[i]);
		isfirst++;
		}else{
			s=s.concat(",");
			s=s.concat("Seat "+splitseats[i]);
		}
	}
seatno=s;
}
}catch(Exception ex){
	ex.printStackTrace();
}
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
		
		if(actioncmd.equals("Print Details"))
		{	
	try{
						String ticket="Ticket_Details_";
						ticket=ticket.concat(movie+".txt");
				FileWriter f1=new FileWriter(ticket);
			//	f1.write("User :"+user);
				f1.write("Theatre :"+theatre+"\n");
				f1.write("Movie :"+movie+"\n");
				f1.write("Screen No.:"+screen+"\n");
				f1.write("No. of Seats:"+total_seats+"\n");
				f1.write("Total Cost :"+totalprice+"\n");
				f1.write("Seat No.:"+seatno+"\n");

				f1.close();
				JOptionPane.showMessageDialog(this,"File Successfully Downloaded.Please Check current Folder");
				}
				catch(Exception ex){

				System.out.println("Exception :"+ex.getMessage());
				}
			
		}
		if(actioncmd.equals("Back to Profile")){
			
			this.setVisible(false);
			new Myprofile().setVisible(true);
			
		}
		
			}

public static void main(String args[]){

new DisplayUserMovie();
}



}