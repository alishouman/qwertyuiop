package Database;
//STEP 1. Import required packages
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/server_1";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "1234567";
   Connection conn = null;
   Statement stmt = null;
   String sql;
   public Database(){
	   //STEP 2: Register JDBC drive   
	   	try {
			Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      
   }
   public boolean checkLogin(String username,String password){
	 
	   boolean loginSuccessful=false;
	   try{
	    
	      sql = "SELECT * FROM users";
	      ResultSet rs = stmt.executeQuery(sql);

	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
	        String id  = rs.getString("Username");
	         String pass = rs.getString("Password");
	         System.out.print("ID: " + id);
	         System.out.print(", Password: " + pass);
	         if(id.equals(username)&&pass.equals(password))
	        	 loginSuccessful=true;
	      

	         //Display values
	         System.out.print("ID: " + username);
	         System.out.print(", Password: " + password);
	         
	      }
	      
	      //STEP 6: Clean-up environment
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   return loginSuccessful;
	  
	}//end main
   
   public boolean register(String username,String password,String FirstName,String LastName,String Address,String age){

	   boolean registrationSuccessful=false;
	   try{
		   sql = "Select * from users WHERE Username='"+username+"'";
		   ResultSet rs = stmt.executeQuery(sql);
		   String hasRegistered="Null";
		   while (rs.next())
		   {
		 hasRegistered= rs.getString("password");
		    
		   } 
		   if(!hasRegistered.equals("Null"))
			   return false;
	      sql = "INSERT INTO users(Username, Password, FirstName, LastName, Address, Age) VALUES ('"+username+"','"+password+"','"+FirstName+"','"+LastName+"','"+Address+"','"+age+"')";
	     stmt.executeUpdate(sql);
registrationSuccessful=true;
	      //STEP 5: Extract data from result set
	   
	      
	      //STEP 6: Clean-up environment
	      
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   return registrationSuccessful;
	  
	}//end main
   public boolean vote(String username,String candidate){

	   boolean votingSuccessful=false;
	   try{

		   sql = "Select * from users WHERE Username='"+username+"'";
		   
		   ResultSet rs = stmt.executeQuery(sql);
		   String hasVoted="null";
		   while (rs.next())
		   {
		 hasVoted= rs.getString("Candidate");
		 
		    
		   } 
		   if(hasVoted!=null)
			   return false;

		   sql = "UPDATE users SET Candidate= '"+candidate+"'"+" WHERE Username='"+username+"'";
		     stmt.executeUpdate(sql);
	     sql = "Select * from candidates WHERE candidate_name='"+candidate+"'";
	     
 rs = stmt.executeQuery(sql);
int number_of_votes = 0;
while (rs.next())
{
 number_of_votes = rs.getInt("number_of_votes");
  
}
number_of_votes++;
sql = "UPDATE candidates SET number_of_votes="+number_of_votes+" WHERE candidate_name='"+candidate+"'";
stmt.executeUpdate(sql);
   
votingSuccessful=true;
	      //STEP 5: Extract data from result set
	   
	      
	      //STEP 6: Clean-up environment
	      
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   return votingSuccessful;
	  
	}
   public void generateCandidates(String fileName) throws Exception {
	   conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      stmt = conn.createStatement();
	   BufferedReader in = new BufferedReader(new FileReader(fileName));
		String line;
		ArrayList<String> candidates = new ArrayList<String>();
		while ((line = in.readLine()) != null) {
			candidates.add(line);
		}
		in.close();
		//Database database = new Database();
		fillCandidates(candidates);
	}
	public boolean fillCandidates(ArrayList<String> candidates) {
		try {  conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      stmt = conn.createStatement();
			for (String candidate : candidates) {
				sql = "INSERT INTO candidates(candidate_name, number_of_votes) VALUES ('"
						+ candidate + "','" + 0 + "')";
				stmt.executeUpdate(sql);
			}
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	public int getVotesCount(int portNumber, String Candidate_name) {
		ResultSet rs;
		int result = 0;
		try {
			  conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
			sql = "Select * from candidates WHERE candidate_name='"
					+ Candidate_name + "'";
				rs = stmt.executeQuery(sql);
			while (rs.next()) {
				result = rs.getInt("number_of_votes");
			}
		} catch (SQLException e) {
			System.out.println("getVotesCount() Failed!");
			e.printStackTrace();
		}
		return result;
	}
	public ArrayList<String> getCandidates(int portNumber) {
		ArrayList<String> candidates = new ArrayList<String>();
		ResultSet rs;
		try {
			  conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
			sql = "Select * from candidates";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				candidates.add(rs.getString("candidate_name"));
			}
		} catch (SQLException e) {
			System.out.println("getCandidates() Failed!");
			e.printStackTrace();
		}
		return candidates;	
		}
	public int totalNumberOfVotes(int portNumber) {
		
		int sum = 0;
		ResultSet rs;
		ArrayList<String> candidates = new ArrayList<String>();
		try {
			  conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
			sql = "SELECT COUNT(*) AS rowcount FROM candidates";
			rs = stmt.executeQuery(sql);
			rs.next();
			int count = rs.getInt("rowcount");
			candidates = getCandidates(1);
			for (int i = 0; i < count; i++) {
				sum += getVotesCount(1, candidates.get(i));
			}
		} catch (SQLException e) {
			System.out.println("totalNumberOfVotes() Failed!");
			e.printStackTrace();
		}
		return sum;
	}
}// end FirstExample
