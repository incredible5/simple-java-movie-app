import java.util.Scanner;
import java.util.InputMismatchException;
import java.lang.*;
import java.io.*;

class Movies
{
	static Scanner in = new Scanner(System.in);
	public static void main(String[] args)
	{
		Boolean condition = false;
		do //Asks user for "user"or "admin" mode. Loops again and again untill valid input is given
		{
			System.out.printf("Select the mode:\n1-Admin (Press 1 for this option)\n2-User (press 2 for this option)\n3- Exit (press 3 to exit)\n");
			try
			{
				int check = in.nextInt();
				condition =  false;
				switch(check)
				{
					case 1 : login();
							break;
					case 2 : User us = new User();
							 us.display();
							break;
					case 3 : System.exit(0);
							break;
					default : throw new InputMismatchException();
				}
			}
			catch(InputMismatchException e)
			{
				condition = true;
				in.nextLine();//to consume the character in buffer
				System.out.printf("\nPlease select a valid choice!\n\n");
			}
		}while(condition);
	}
	static void login()
	{
		Admin am = new Admin();
		System.out.printf("\nEnter the password:\n");
		in.nextLine();//to consume the character in buffer
		String pass = in.nextLine();
		if(pass.endsWith(am.getPassword()))
			am.msg();
		else
		{
			System.out.println("Wrong password!");
		}

	}
}

class Admin
{
	private String password = "abc";
	String getPassword()
	{
		return this.password;
	}
	Scanner in = new Scanner(System.in);
	void msg() //Prompts for selscting city. Loops until valid input is given
	{
		Boolean condition = false;
		do
		{
			System.out.printf("Select the city:\n1-Lucknow (Press 1 for this option)\n2-Kanpur (press 2 for this option)\n3- Exit (press 3 to exit)\n");
			try
			{
				int city = in.nextInt();
				condition =  false;
				switch(city)
				{
					case 1 : details(city);
							break;
					case 2 : details(city);
							break;
					case 3 : System.exit(0);
							break;
					default : throw new InputMismatchException();
				}
			}
			catch(InputMismatchException e)
			{
				condition = true;
				System.out.printf("\nPlease select a valid choice!\n\n");
			}
		}while(condition);
	}

	void details(int ct) //Reads the file which stores the movie details in a file as a database
	{
		String city = "lucknow.txt";
		if(ct == 2)
			city = "kanpur.txt";
		try
		{
			File fl = new File("C:/Users/LENOVO/Desktop/Movie/"+city);
			Scanner sc = new Scanner(fl);
			sc.useDelimiter("\\Z");
			String info = "";
			info += sc.next();
			System.out.println(info);

			System.out.printf("\n\nPress \"Y\" to edit movie name or any other aceptable character to exit\n");
			char c = in.next().charAt(0);
			if(c == 'y' || c == 'Y')
				edit(info, city);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Some Error Occurred!");
		}
	}

	void edit(String inf, String city) //Modifies the existing string of selected movie detail and re-writes whole data back into the file
	{
		System.out.printf("\nEnter the screen number:\n");
		int screen = in.nextInt();
		in.nextLine(); //to consume the character in buffer
		System.out.printf("\nEnter the new movie:\n");
		String new_movie = in.nextLine();
		String new_info = "";
		if(screen == 4)
			new_info = inf.substring(0,(inf.indexOf("Screen "+screen))+19) + new_movie;
		else
			new_info = inf.substring(0,(inf.indexOf("Screen "+(screen++)))+19) + new_movie + "\n" + inf.substring(inf.indexOf("Screen "+screen));
		System.out.println(new_info);
		System.out.println("Do you want to save this in database, this will be irrevocable? Press e to save or any other acceptable character to exit without saving");
		char c = in.next().charAt(0);
		if(c == 'e')
		{
			try
			{
				FileOutputStream fos = new FileOutputStream("C:/Users/LENOVO/Desktop/Movie/"+city);
				PrintWriter pw = new PrintWriter(fos);
				pw.println(new_info);
				pw.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("Error opening file!");
			}
			catch(SecurityException e)
			{
				System.out.println("ERROR!");
			}
		}
	}
}

class User
{
	Scanner in = new Scanner(System.in);
	int movie_no;
	String user_name, user_city, user_movie, user_time, ticket;
	void display()
	{
		Boolean condition = false;
		do
		{
			System.out.printf("Select the city:\n1-Lucknow (Press 1 for this option)\n2-Kanpur (press 2 for this option)\n3- Exit (press 3 to exit)\n");
			try
			{
				int city = in.nextInt();
				condition =  false;
				switch(city)
				{
					case 1 : show(city);
							break;
					case 2 : show(city);
							break;
					case 3 : System.exit(0);
							break;
					default : throw new InputMismatchException();
				}
			}
			catch(InputMismatchException e)
			{
				condition = true;
				System.out.printf("\nPlease select a valid choice!\n\n");
			}
		}while(condition);
	}
	void show(int ct) //Displays the movies on different screens based on the city of preference
	{
		String info = "";
		user_city = "Lucknow";
		String city = "lucknow.txt";
		if(ct == 2)
		{
			city = "kanpur.txt";
			user_city = "Kanpur";
		}
		try
		{
			File fl = new File("C:/Users/LENOVO/Desktop/Movie/"+city);
			Scanner sc = new Scanner(fl);
			sc.useDelimiter("\\Z");
			info += sc.next();
			System.out.println(info);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Some Error Occurred!");
		}
		System.out.println("Press \"b\" to book tickets or any other acceptable key to exit\n");
		char lc = in.next().charAt(0);
		in.nextLine();
		if(lc == 'b') //Generate a "ticket" string of the selected choices by the user and creates a file named as username and writes ticket data into it
		{
			System.out.println("\nEnter your name: ");
			user_name = in.nextLine();
			System.out.println("Enter the screen number coressponding to movie name: ");
			movie_no = in.nextInt();
			in.nextLine();
			System.out.printf("Select a movie time:\na:12-3pm\nb:3-6pm\nc:6-9pm\nd:9-12pm\n");
			user_time = in.nextLine();
			if(movie_no != 4)
				user_movie = info.substring(info.indexOf("Screen " + movie_no) + 18, info.indexOf("Screen " + (movie_no + 1)));
			else
				user_movie = info.substring(info.indexOf("Screen " + movie_no) + 18);
			switch(user_time)
			{
				case "a": user_time = "12pm - 3pm";
							break;
				case "b": user_time = "3pm - 6pm";
							break;
				case "c": user_time = "6pm - 9pm";
							break;
				default : user_time = "9pm - 12pm";
							break;
			}
			ticket = " Name: " + user_name + "\n City: " + user_city + "\n Movie:" + user_movie + " Time: " +user_time;
			try
			{
				File fl = new File("C:/Users/LENOVO/Desktop/Movie/"+user_name+".txt");
				FileOutputStream fos = new FileOutputStream("C:/Users/LENOVO/Desktop/Movie/"+user_name);
				PrintWriter pw = new PrintWriter(fos);
				pw.println(ticket);
				pw.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("Error opening file!");
			}
			catch(SecurityException e)
			{
				System.out.println("ERROR!");
			}
			System.out.printf("\n\nYour ticket:\n%s\n\n",ticket);
		}
	}
}
