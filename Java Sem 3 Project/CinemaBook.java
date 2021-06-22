import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class CinemaBook extends JFrame implements ActionListener{
JComboBox cmbArea,cmbMovies;
JPanel pnlCinema,pnlMovie;
JLabel lblArea,lblCinema;
Connection conn;

PreparedStatement pstmt;
String Sql;
ResultSet rs;
String[] Areanames;
JButton next;
String Selected;
static String SelectedMovie;
static String SelectedTheatre;
static int SelectedScreen;

CinemaBook(){
	setVisible(true);
	createUi();
	setSize(600,600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	createConn();
	loadArea();

}

String getSelectedMovie(){
	return SelectedMovie;
}
String getSelectedTheatre(){
	return SelectedTheatre;
}
int getSelectedScreen(){
	return SelectedScreen;
}
void setSelectedTheatre(String s){
	SelectedTheatre=s;
}
void setSelectedMovie(String s){
	SelectedMovie=s;
}
void setSelectedScreen(int s){
	SelectedScreen=s;
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
		setVisible(true);
		pnlCinema=new JPanel();
		lblCinema=new JLabel("Movie List");
		lblCinema.setFont(new Font("Comic Sans MS",Font.BOLD,50));
		add(pnlCinema,BorderLayout.NORTH);
		pnlCinema.add(lblCinema);
		pnlMovie=new JPanel();
		pnlMovie.setLayout(new GridLayout(4,1));
		add(pnlMovie);
		lblArea=new JLabel("Select an Area :");
		pnlMovie.add(lblArea);
		cmbArea=new JComboBox();
		cmbArea.addActionListener(this);
		pnlMovie.add(cmbArea);
		cmbMovies=new JComboBox();
		cmbMovies.addActionListener(this);
		pnlMovie.add(cmbMovies);
		next=new JButton("NEXT ->");
		next.addActionListener(this);
		pnlMovie.add(next);
	}
	void loadArea()
		{
			int areaindex=0;
			Sql="Select distinct area from movies";
			try{
				pstmt=conn.prepareStatement(Sql);
				
					rs=pstmt.executeQuery();
				
					while(rs.next()){
						
						areaindex=rs.getRow();
						
					}
					Areanames=new String[areaindex];
				rs=pstmt.executeQuery();
			

					int cnt=0;
					while(rs.next()){
					if(cnt==areaindex){
						
						break;
						
					}else{
						Areanames[cnt]=rs.getString(1);
						cnt++;
					}	
						
					}
			for(int i=0;i<areaindex;i++)
			{
				cmbArea.addItem(Areanames[i]);
			}
			
			
			}catch(Exception ex){
				
				
			}
			
			
			
			
			
			
		}
	public void actionPerformed(ActionEvent ae)
	{
		String actioncmd=ae.getActionCommand();
	
		if(ae.getSource()==cmbArea)
		{			//NEXT ->
			if(cmbArea.getSelectedIndex()>=0)
			{
				cmbMovies.removeAllItems();
				int arrindex=0;
				
				int index=cmbArea.getSelectedIndex();
				Selected =Areanames[index];
				
				Sql="Select * from movies where area=?";
				try{
					//Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					pstmt=conn.prepareStatement(Sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					pstmt.setString(1,Selected);
					rs=pstmt.executeQuery();
					
					
					while(rs.next()){
						
						//System.out.println(rs.getString(2));
						
						arrindex=rs.getRow();
						//System.out.print(rs.getString(2));
					}
					
					
					
					
					String[] movienames=new String[arrindex+1];
					
				rs=pstmt.executeQuery();
					movienames[0]="Select a Movie";
				
					rs=pstmt.executeQuery();
					int cnt=1;
					while(rs.next()){
						
					if(cnt==arrindex+1){
						
						break;
						
					}else{
						movienames[cnt]=rs.getString(2)+" - "+rs.getString(3)+" - "+rs.getString(4)+" - Screen  - "+rs.getString(5);
						cnt++;
					}	
						
					}
					
					/*	for(int i=0;i<arrindex;i++){
						
						System.out.println(movienames[i]);
					}*/
					for(int i=0;i<arrindex+1;i++)
					{
					cmbMovies.addItem(movienames[i]);
					}
				
			
					
				}catch(Exception ex){
					ex.printStackTrace();
					
				}
			
			}
		}
		
		if(ae.getSource()==cmbMovies)
		{			//NEXT ->
			if(cmbMovies.getSelectedIndex()>0)
			{
				String x = String.valueOf(cmbMovies.getSelectedItem());
				
				String [] Trial=x.split(" - ");
				setSelectedMovie(Trial[0]);
				setSelectedTheatre(Trial[1]);
				setSelectedScreen(Integer.parseInt(Trial[4]));
				System.out.println(getSelectedMovie()+getSelectedTheatre()+getSelectedScreen());
			
				
		}	}
		if(actioncmd.equals("NEXT ->")&&cmbMovies.getSelectedIndex()>0){
			//Pass Movie name to next class;
			this.setVisible(false);
			new Screen().setVisible(true);
			try{
			conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
				
			}
		}else if(actioncmd.equals("NEXT ->")){
			
			JOptionPane.showMessageDialog(this,"You must Select one movie before proceeding");
		}
	}
public static void main(String args[]){

new CinemaBook();

}


}

