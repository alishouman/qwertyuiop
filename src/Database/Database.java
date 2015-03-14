package Database;
//STEP 1. Import required packages
import java.sql.*;

public class Database {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/3303Project";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "1234567";
   Connection conn = null;
   Statement stmt = null;
   String sql;
   public Database(){
	   //STEP 2: Register JDBC driver
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
   
   public boolean register(String username,String password,String candidate,String age){

	   boolean registrationSuccessful=false;
	   try{
	  
	      sql = "INSERT INTO users(Username, Password, Candidate, Age) VALUES ('"+username+"','"+password+"','"+candidate+"','"+age+"')";
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
   
   
}//end FirstExample
