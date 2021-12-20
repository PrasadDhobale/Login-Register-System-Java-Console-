package gui;

import java.util.Random;
import java.util.Scanner;

import dao.userDao;
import model.User;

public class user_registration {

	public static void main(String[] args) {
		int ch = 0;
		Scanner sc = new Scanner(System.in);
		userDao udao = new userDao();				
		String fname, lname, email, passwd;
		
		do {
			System.out.println("1 - Register User\n2 - Login User");
			System.out.println("Enter Your Choice : ");
			ch = sc.nextInt();
			switch (ch) {
			case 1:
				System.out.println("Enter First Name : ");
				fname = sc.next();
				
				System.out.println("Enter Last Name : ");
				lname = sc.next();
				
				System.out.println("Enter Email ID : ");
				email = sc.next();
				
				System.out.println("Enter Password : ");
				passwd = sc.next();
				
				
				Random rand = new Random();
				int token = 100000 + (int)(rand.nextFloat() * 899900);;
				
				User u = new User(fname, lname, email, passwd, String.valueOf(token));	// converted int token to string
				int reg_status = udao.reg_user(u);
				if(reg_status > 0) {
					System.out.println("User Registered Successfully...!!");
				}
				break;

			case 2:
				System.out.println("Enter Email ID : ");
				email = sc.next();
				
				System.out.println("Enter Password : ");
				passwd = sc.next();
				
				u = udao.Login(email, passwd);
				if(u != null) {
					System.out.println("Logged In Successfully...");
					System.out.println("Welcome "+ u.getFname());
					do {
						System.out.println("----------- Enter Details For Send Email -----------");
						System.out.println("Enter Email : ");
						email = sc.next();
						
						System.out.println("Enter Subject : ");
						String subj = sc.next();						
						
						System.out.println("Enter Body : ");
						String body = sc.next();
												
						System.out.println("If You Want to Send Another Email Press 1 : ");
						ch = sc.nextInt();
					}while(ch == 1);
				}else {
					System.out.println("Invalid Credentials.. try again.");
				}
				break;
				
			default:
				break;
			}
			System.out.println("If You Want to Continue Press 1 : ");
			ch = sc.nextInt();
		}while(ch == 1);
	}
}