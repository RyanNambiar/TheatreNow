import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;



class Myprofile extends JFrame implements ActionListener{
JPanel pnlHeader;
JLabel lblpro;
JPanel pnlbut;
JButton btnMymovies,btnBook,btnLog;
	
Myprofile(){
	createui();
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(600,600);

}

void createui() {
	setVisible(true);
	lblpro=new JLabel("My Profile");
	lblpro.setFont(new Font("Comic Sans MS",Font.BOLD,30));
	pnlHeader=new JPanel();
	pnlHeader.add(lblpro);
	add(pnlHeader,BorderLayout.NORTH);
	pnlbut=new JPanel();
	pnlbut.setLayout(new GridLayout(3,1));
	
	btnMymovies=new JButton("My Movies");
	btnMymovies.addActionListener(this);
	pnlbut.add(btnMymovies);
	
	btnBook=new JButton("Book Now");
	btnBook.addActionListener(this);
	pnlbut.add(btnBook);
	
	btnLog=new JButton("Logout");
	btnLog.addActionListener(this);
	pnlbut.add(btnLog);
	
	add(pnlbut);
}

public void actionPerformed(ActionEvent ae){
	String actioncmd=ae.getActionCommand();
	if(actioncmd.equals("My Movies")){
		this.setVisible(false);
		new Mymovies().setVisible(true);
}
	if(actioncmd.equals("Book Now")){
		this.setVisible(false);
		new CinemaBook().setVisible(true);
}

	if(actioncmd.equals("Logout")){
		this.setVisible(false);
		new MovieAppImpl().setVisible(true);
}




}


public static void main(String args[]){
new Myprofile();
	
	
}
}