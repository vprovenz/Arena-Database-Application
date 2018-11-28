import java.sql.Statement;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CustomerQueries {

	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void insertNewAttendee() throws SQLException {
		int creditCardNum;
		int age;
		String gender;
		String name;
		String email;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);

		try {
			ps = con.prepareStatement("INSERT INTO attendee VALUES (?,?,?,?,?,?,?)");
			// for insertion, need to make sure that this primary key isnt taken -> how to
			// do
			// also how to handle char vs string? do we need to make char array?
			// also need to implement type checking for insertion and update
			System.out.print("\nName: ");
			name = in.readLine();
			ps.setString(1, name);

			System.out.print("\nEmail: ");
			email = in.readLine();
			ps.setString(2, email);

			// total points gets default to 0
			ps.setInt(3, 0);

			System.out.print("\nCredit card #: ");
			creditCardNum = Integer.parseInt(in.readLine());
			ps.setInt(4, creditCardNum);

			// ticket amount default to zero
			ps.setInt(5, 0);

			System.out.print("\nAge: ");
			age = Integer.parseInt(in.readLine());
			ps.setInt(6, age);

			System.out.print("\nGender: "); //
			gender = in.readLine();
			ps.setString(7, gender);

			// String phoneTemp = in.readLine();
			// if (phoneTemp.length() == 0) {
			// ps.setNull(5, java.sql.Types.INTEGER);
			// } else {
			// bphone = Integer.parseInt(phoneTemp);
			// ps.setInt(5, bphone);
			// }

			ps.executeUpdate();
			System.out.println("New user inserted.");
			System.out.println();

			// commit work
			con.commit();

			ps.close();
		} catch (IOException e) {
			System.out.println("IOException!");
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
		catch (Exception e) {
			System.out.println("Please make sure input values are correct!" + e);
		}
	}

	// adds a record to the table willAttend (relationship btwn ticket, attendee,
	// and event) and then calls buyTicket which inserts to the ticket table
	public static void purchaseTicket() throws SQLException {
		int eventID;
		int seatNum;
		int sectionNum;
		String name;
		String email;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);

		try {
			ps = con.prepareStatement("INSERT INTO willAttend VALUES (?,?,?,?,?)");
			// for insertion, need to make sure that this primary key isnt taken -> how to
			// do
			// also how to handle char vs string? do we need to make char array?
			// also need to implement type checking for insertion and update
			System.out.print("\nName: ");
			name = in.readLine();
			ps.setString(1, name);

			System.out.print("\nEmail: ");
			email = in.readLine();
			ps.setString(2, email);

			System.out.print("\nSection number #: ");
			sectionNum = Integer.parseInt(in.readLine());
			ps.setInt(3, sectionNum);

			System.out.print("\nSeat number #: ");
			seatNum = Integer.parseInt(in.readLine());
			ps.setInt(4, seatNum);

			// TODO need a way to check event ID is valid
			System.out.print("\nEvent ID: ");
			eventID = Integer.parseInt(in.readLine());

			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = st
					.executeQuery("select attractionid from eventattractions" + " where eventid = " + eventID);

			res.next();
			int attractionID = res.getInt(1);
			buyTicket(sectionNum, seatNum, name, email, eventID, attractionID);

			ps.setInt(5, eventID);

			ps.executeUpdate();

			con.commit();

			ps.close();

			System.out.println("Ticket purchased");

		} catch (IOException e) {
			System.out.println("IOException!");
		} catch (SQLException ex) {
			System.out.println("User or event does not exist: " + ex.getMessage());
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("User or event does not exist: " + ex2.getMessage());
				System.exit(-1);
			}
		} catch (Exception e) {
			System.out.println("Please make sure input values are correct!");
		}
	}

	// adds purchase to the ticket table
	public static void buyTicket(int secNum, int seatNum, String name, String email, int eventID, int attractionID)
			throws SQLException {
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		Date today = new Date(Calendar.getInstance().getTime().getTime());
		con.setAutoCommit(false);

		try {
			ps = con.prepareStatement("INSERT INTO verifiedTicket VALUES (?,?,?,?,?,?,?,?,?)");
			// for insertion, need to make sure that this primary key isnt taken -> how to
			// do
			ps.setInt(1, secNum);
			ps.setInt(2, seatNum);
			// set default price as $100
			ps.setInt(3, 100);
			// set todays date
			ps.setDate(4, today);

			// not verified yet
			ps.setBoolean(5, false);
			// default to 100 points
			ps.setInt(6, 100);
			ps.setString(7, email);
			ps.setString(8, name);
			ps.setInt(9, attractionID);

			ps.executeUpdate();

			// commit work
			con.commit();

			ps.close();

		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
		catch (Exception e) {
			System.out.println("Please make sure input values are correct!" + e);
		}
	}

	// can update credit card
	public static void updateAttendee() throws SQLException {
		int cardnum;
		String name;
		String email;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);

		try {
			System.out.print("\nName: ");
			name = in.readLine();

			System.out.print("\nEmail: ");
			email = in.readLine();

			System.out.print("\nNew card number: ");
			cardnum = Integer.parseInt(in.readLine());

			ps = con.prepareStatement("UPDATE attendee SET creditcard# = " + cardnum + " WHERE name = '" + name
					+ "' and email = '" + email + "'");

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println("\nUser does not exist!");
			} else
				System.out.println("\nCredit card info upated.");

			con.commit();

			System.out.println("");

			ps.close();
		} catch (IOException e) {
			System.out.println("IOException!");
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());

			try {
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
		catch (Exception e) {
			System.out.println("Please make sure input values are correct!" + e);
		}
	}

	public static void viewAvailableAttractions() {

		String genre;

		try {
			Connection con = JDBCConnection.getConnection1();
			PreparedStatement ps;
			ps = con.prepareStatement("select groupName from attraction4 where genre = ?");

			System.out.print("\nGenre (Pop, Retro, Indie, Rap): ");
			genre = in.readLine();

			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = st.executeQuery("select attractionName from attraction where genre = '" + genre + "'");
			// TODO error checking here if result set is empty

			while (res.next()) {
				// this number needs to be switched to whatever the name column is
				System.out.println(res.getString(1) + "   ");
			}
			res.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Please make sure input values are correct!" + e);
		}
	}

	public static void createNextMonthView() throws SQLException {

		Calendar oneMonth = Calendar.getInstance();
		oneMonth.add(Calendar.MONTH, 1);
		Date today = new Date(Calendar.getInstance().getTime().getTime());
		Date nextMonth = new Date(oneMonth.getTime().getTime());
		PreparedStatement ps;
		PreparedStatement drop;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);

		try {
			try {
				drop = con.prepareStatement("drop view nextMonthEvent");
				drop.executeQuery();
				con.commit();
				drop.close();
			} catch (Exception e) {
				// if we can't drop it thats fine
			}

			// need to drop next month event and commit that
			ps = con.prepareStatement(
					"create view nextMonthEvent as " + "select eventName, eventID from concert where performancedate>'"
							+ today + "'and performancedate<'" + nextMonth + "'");

			ps.executeUpdate();

			// commit work
			con.commit();

			ps.close();

		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
		catch (Exception e) {
			System.out.println("Please make sure input values are correct!" + e);
		}
	}

	public static void viewNextMonthEvents() {

		try {
			Connection con = JDBCConnection.getConnection1();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = st.executeQuery("select * from nextMonthEvent");
			System.out.println("Events happening next month:");
			while (res.next()) {
				System.out.println(res.getString(1));
			}
			res.close();
			con.close();
			System.out.println("");
		} catch (Exception e) {
			System.out.println("Error in fetching data" + e);
		}
	}

	public static void createEventBooking() throws SQLException {
		int attractionID;
		int eventID;
		int price;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);

		try {
			ps = con.prepareStatement("INSERT INTO books VALUES (?,?,?)");
			// are we supposed to generate our own event id here or what
			System.out.print("\nAttraction ID: ");
			attractionID = Integer.parseInt(in.readLine());
			ps.setInt(1, attractionID);

			System.out.print("\nEvent ID: ");
			eventID = Integer.parseInt(in.readLine());
			ps.setInt(2, eventID);

			// for price, for now we'll simulate a scheme by
			// using a random number generator that goes between $1000-$5000 dollars
			price = ThreadLocalRandom.current().nextInt(1000, 5001);
			ps.setInt(3, price);

			ps.executeUpdate();
			// commit work
			con.commit();
			System.out.println("Booking created.");
			ps.close();
		} catch (IOException e) {
			System.out.println("IOException!");
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
		catch (Exception e) {
			System.out.println("Please make sure input values are correct!" + e);
		}
	}

	public static void cancelEventBooking() throws SQLException {
		int eventID;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);

		try {
			ps = con.prepareStatement("DELETE FROM books where eventID = ?");

			System.out.print("\nEvent ID: ");
			eventID = Integer.parseInt(in.readLine());
			ps.setInt(1, eventID);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println("\nBooking does not exist!");
			} else
				System.out.println("\nBooking cancelled.");

			// commit work
			con.commit();

			ps.close();
		} catch (IOException e) {
			System.out.println("IOException!");
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
		catch (Exception e) {
			System.out.println("Please make sure input values are correct!" + e);
		}
	}

	// group by gender and age from attendee
	// added those to table to do this
	public static void viewDemographicStatistics() {
		try {
			Connection con = JDBCConnection.getConnection1();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = st.executeQuery("select gender, avg(age) from attendee group by gender");
			ResultSetMetaData rsmd = res.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++) {
				// get column name and print it

				System.out.printf("%-15s", rsmd.getColumnName(i + 1));
			}

			System.out.println(" ");

			while (res.next()) {
				System.out.print(res.getString(1));
				System.out.println(res.getInt(2));
			}
			res.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Error in fetching data" + e);
		}
	}
}
