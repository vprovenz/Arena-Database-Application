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

public class EmployeeQueries {
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	// deliverable 2
	// TODO: add to JDBCConnection.java
	// SportingEvent(eventID: INTEGER, capacity: INTEGER, ticketsSold: INTEGER,
	// date: DATE,
	// sponsorPrice: INTEGER, sponsorID: INTEGER, homeTeamID: INTEGER, awayTeamID:
	// INTEGER, employeeID: INTEGER)
	public static void insertNewSportingEvent() throws SQLException {
		int eventID;
		int homeTeamID;
		int awayTeamID;
		String date;
		String eventName;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);

		try {
			ps = con.prepareStatement("INSERT INTO sport VALUES (?,?,?,?,?)");

			System.out.println("Event ID: ");
			eventID = Integer.parseInt(in.readLine());
			ps.setInt(1, eventID);

			System.out.println("Event name: ");
			eventName = in.readLine();
			ps.setString(2, eventName);

			System.out.println("Home Team ID: ");
			homeTeamID = Integer.parseInt(in.readLine());
			ps.setInt(3, homeTeamID);

			System.out.println("Away Team ID: ");
			awayTeamID = Integer.parseInt(in.readLine());
			ps.setInt(4, awayTeamID);

			System.out.println("Date: YY-MM-DD: ");
			date = in.readLine();
			ps.setString(5, date);

			/*
			 * System.out.print("\nEvent Capacity: "); capacity =
			 * Integer.parseInt(in.readLine()); ps.setInt(5, capacity);
			 */

			// default value of tickets sold so far is set to 0
			// ps.setInt(6, ticketsSold);

			// ps = con.prepareStatement("INSERT INTO sport VALUES " + eventID + homeTeamID
			// + awayTeamID + date + capacity);
			ps.executeUpdate();

			// commit work
			con.commit();
			System.out.println("Event inserted.");
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

	// deliverable 2
	// TODO: add additional query case to JDBCConnection.java to handle different
	// event types
	public static void insertNewConcertEvent() throws SQLException {
		int eventID;
		String date;
		String eventName;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);

		try {
			ps = con.prepareStatement("INSERT INTO concert VALUES (?,?,?)");

			System.out.print("\nEvent ID: ");
			eventID = Integer.parseInt(in.readLine());
			ps.setInt(1, eventID);

			System.out.println("Event name: ");
			eventName = in.readLine();
			ps.setString(2, eventName);

			System.out.println("Date: YY-MM-DD: ");
			date = in.readLine();
			ps.setString(3, date);

			/*
			 * System.out.print("\nEvent Capacity: "); capacity =
			 * Integer.parseInt(in.readLine());
			 */
			// ps.setInt(3, sectionNum);

			// default value of tickets sold so far is set to 0
			// ps.setInt(4, ticketsSold);

			// ps = con.prepareStatement("INSERT INTO concert VALUES " + eventID + date +
			// capacity);
			ps.executeUpdate();

			// commit work
			con.commit();
			System.out.println("Event inserted.");
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

	// TODO: add additional query case in JDBCConnection.java to handle the two
	// event types
	public static void deleteSportingEvent() throws SQLException {
		int eventID;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);
		try {

			System.out.print("\nSporting Event ID: ");
			eventID = Integer.parseInt(in.readLine());
			// ps.setInt(1, eventID);

			ps = con.prepareStatement("DELETE FROM sport where eventID = " + eventID);
			
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println("\nEvent does not exist!");
			} else
				System.out.println("Event deleted!");
			con.commit();
			

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

	public static void deleteConcertEvent() throws SQLException {
		int eventID;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);
		try {

			System.out.print("\nConcert Event ID: ");
			eventID = Integer.parseInt(in.readLine());
			// ps.setInt(1, eventID);

			ps = con.prepareStatement("DELETE FROM concert where eventID = " + eventID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println("\nEvent does not exist!");
			} else
				System.out.println("Event deleted!");

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

	// deliverable 6, two-table join between attraction and concert
	// get the eventID of events based off their genre
	public static void getEventsFromGenre() throws SQLException {

		String genre;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);
		try {
			// can also just figure out this statment just by testing w db
			System.out.print("\nPick a genre (Pop, Retro, Indie, Rap): ");
			genre = in.readLine();

			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			/*
			 * ResultSet res = st.executeQuery("SELECT Count(*) FROM attendee a" +
			 * "INNER JOIN employee e on e.email = a.email" + "WHERE e.employeeID = " +
			 * employeeID);
			 */
			ResultSet res = st.executeQuery("SELECT eventID FROM concert c "
					+ "INNER JOIN attraction a on a.attractionName = c.eventName " + "WHERE a.genre = '" + genre + "'");

			System.out.println(" ");

			// need to adjust number of stuff printed out here
			while (res.next()) {
				System.out.println(res.getString(1));
			}
			
			System.out.println(" ");

			res.close();
			con.close();
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());

			try {
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("Please make sure input values are correct!" + e);
		}
	}

	// deliverable 8, update the date of an event
	public static void updateConcertEvent() throws SQLException {
		int eventID;
		String date;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);

		try {

			System.out.print("\nEvent ID: ");
			eventID = Integer.parseInt(in.readLine());
			// ps.setString(3, eventID);

			System.out.println("New Date: YY-MM-DD: ");
			date = in.readLine();
			// ps.setString(5, date);

			ps = con.prepareStatement("UPDATE concert SET performancedate = '" + date + "' WHERE eventID = " + eventID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println("\nEvent does not exist!");
			} else
				System.out.println("Date changed!");
			con.commit();

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

	public static void updateSportingEvent() throws SQLException {
		int eventID;
		String date;
		PreparedStatement ps;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);
		try {

			System.out.print("\nEvent ID: ");
			eventID = Integer.parseInt(in.readLine());
			// ps.setString(3, eventID);

			System.out.println("New Date: YY-MM-DD: ");
			date = in.readLine();
			// ps.setString(5, date);

			ps = con.prepareStatement("UPDATE sport SET matchdate = '" + date + "' WHERE eventID = " + eventID);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println("\nEvent does not exist!");
			} else
				System.out.println("Date changed!");
			con.commit();

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

	/*
	 * // GROUP BY statement on Facilitates table to group all the employees by
	 * their specific roles such as security and sales public static void
	 * viewEmployees() { try { Connection con = JDBCConnection.getConnection1();
	 * Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_READ_ONLY); ResultSet res =
	 * st.executeQuery("SELECT employeeID, eventID FROM facilitates GROUP BY role");
	 * ResultSetMetaData rsmd = res.getMetaData();
	 * 
	 * // get number of columns int numCols = rsmd.getColumnCount();
	 * 
	 * System.out.println(" ");
	 * 
	 * // display column names; for (int i = 0; i < numCols; i++) { // get column
	 * name and print it
	 * 
	 * System.out.printf("%-15s", rsmd.getColumnName(i + 1)); }
	 * 
	 * System.out.println(" ");
	 * 
	 * while (res.next()) { System.out.print(res.getString(1) + "   ");
	 * System.out.print(res.getString(2) + "   "); System.out.print(res.getString(3)
	 * + "   "); System.out.print(res.getString(5) + "   ");
	 * System.out.print(res.getString(6) + "   ");
	 * System.out.println(res.getString(7) + "   "); } res.close(); con.close(); }
	 * catch (Exception e) { System.out.println("Error in fetching data" + e); } }
	 */

	// view events you are scheduled to work for
	public static void viewSchedule() throws SQLException {
		int employeeID;
		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);

		try {
			System.out.print("\nEmployee ID: ");
			employeeID = Integer.parseInt(in.readLine());
			// ps.setString(1, employeeID);

			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = st.executeQuery("SELECT eventID FROM facilitates WHERE employeeID = " + employeeID);
			
			if (!res.isBeforeFirst() ) {    
				System.out.println("Not a valid employeeID"); 
			}

			while (res.next()) {
				System.out.println(res.getString(1));
			}
			System.out.println();
			res.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Please make sure you have entered a valid ID" + e);
		}

	}

	// joins employee with facilitates with concert
	public static void seeEmployeesWorkingAtEvent() throws SQLException, NumberFormatException, IOException {

		Connection con = JDBCConnection.getConnection1();
		con.setAutoCommit(false);
		int eventID;

		try {
			// can also just figure out this statement just by testing w db
			// join employee w facilitates with concert
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			/*
			 * ResultSet res = st.
			 * executeQuery("SELECT attractionName, Count(*) FROM attraction a, verifiedTicket t "
			 * + "INNER JOIN attraction a on t.attractionID = a.attractionID " +
			 * "INNER JOIN attendee b on t.purchaserEmail = b.email " + "WHERE a.genre = '"
			 * + genre + "'");); ResultSetMetaData rsmd = res.getMetaData();
			 */

			System.out.print("\nConcert Event ID: ");
			eventID = Integer.parseInt(in.readLine());

			ResultSet res = st.executeQuery(
					"SELECT name FROM employee e " + "INNER JOIN facilitates f on e.employeeID = f.employeeID "
							+ "INNER JOIN concert c on f.eventID = c.eventID " + "WHERE c.eventID = '" + eventID + "'");
			ResultSetMetaData rsmd = res.getMetaData();
			
			if (!res.isBeforeFirst() ) {    
				System.out.println("Not a valid EventID"); 
				return;
			}

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++) {
				// get column name and print it

				System.out.printf("%-15s", rsmd.getColumnName(i + 1));
			}

			System.out.println(" ");

			// need to adjust number of stuff printed out here
			while (res.next()) {
				System.out.println(res.getString(1));
			}
			
			System.out.println(" ");

			res.close();
			con.close();
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
}