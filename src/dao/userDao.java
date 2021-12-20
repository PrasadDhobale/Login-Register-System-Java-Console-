package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import model.Database;
import model.Email_Sender;

public class userDao {
	Connection con = null; 
	PreparedStatement ps = null;
	Database db = new Database();
	Email_Sender ES = new Email_Sender();
	
	// Register User
	public int reg_user(User u) {
		con = db.getConnection();
		
		boolean is_email_exist = email_exist(u.getEmail());	// checks if email is exist or not		
		if(!is_email_exist) {
			try {
				PreparedStatement ps = con.prepareStatement("insert into user (fname, lname, email, password, token, evs, reg_time) values (?, ?, ?, ?, ?, 'Inactive',  CURRENT_TIME)");
				ps.setString(1, u.getFname());
				ps.setString(2, u.getLname());
				ps.setString(3, u.getEmail());
				ps.setString(4, u.getPasswd());
				ps.setString(5, u.getToken());
				int i = ps.executeUpdate();
				if(i > 0) {
					ES.sendVEmail("Hii "+u.getFname()+"\nThanks for Registering with Email Sending System..!", "http:localhost:8081/ESS/verifyemail?email="+u.getEmail()+"&token="+u.getToken(), "Email Sending System [Email Verification]", u.getEmail(), "v");
					return i;
				}
			} catch(Exception e){
				System.out.println(e);
			}
		}else {
			System.out.println("Email ID Already Exist");
		}
		return 0;
	}
	
	// check if email is exist or not
	public boolean email_exist(String email) {
		boolean i = false;
		con = db.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select email from user where email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {							
				if(rs.getString(1).equals(email)) {
					i = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	// Login User with correct credentials
	public User Login(String email, String password) {
		User u = null;
		con = db.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select *from user where email = ? and password = ?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				u = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				u.setEvs(rs.getString(6));
				u.setReg_time(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
}