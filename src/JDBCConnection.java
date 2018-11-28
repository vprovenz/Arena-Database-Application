import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static Connection getConnection1() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "victoria", "Kodak123");
		} catch (Exception e) {
			System.out.println("Error in connection" + e);
		}
		return con;
	}

	private void showMenu() {
		int userType;
		int choice;
		boolean quit;

		quit = false;
		Connection con = getConnection1();

		try {
			// disable auto commit mode
			con.setAutoCommit(false);

			System.out.print("Welcome to our Arena!\n");

			System.out.print("\n\nWhat kind of user are you? \n");
			System.out.print("1.  Attendee\n");
			System.out.print("2.  Attraction\n");
			System.out.print("3.  Sponsor\n");
			System.out.print("4.  Employee\n");
			System.out.print("5.  For demo only: all queries in deliverable order\n>>");
			userType = Integer.parseInt(in.readLine());

			while (!quit) {

				// also we need TYPE CHECKING when we are getting user input!!!!!
				// we can group these in ways that make more sense later.

				// need error handling for duplicate key

				// Deliverables from Formal Project Specification

				if (userType == 1) {
					// event attendees
					// deliverable 2
					System.out.print("1.  Create (insert) a new event attendee\n");
					// deliverable 4
					System.out.print("2.  Update credit card information\n");
					System.out.print("3.  Purchase a ticket\n");
					// deliverable 10, select attractions by their genres/sport
					System.out.print("4.  View (select) all available attractions of a given genre\n");
					// deliverable 11, create view
					System.out.print("5.  View concerts occuring within the next month (view)\n");
					// deliverable 6, 2 table join
					System.out.print("6.  View eventID based off genre (join)\n");
					System.out.print("7.  Quit\n>> ");

					choice = Integer.parseInt(in.readLine());

					System.out.println(" ");

					switch (choice) {
					case 1:
						CustomerQueries.insertNewAttendee();
						break;
					case 2:
						CustomerQueries.updateAttendee();
						break;
					case 3:
						CustomerQueries.purchaseTicket();
						break;
					case 4:
						CustomerQueries.viewAvailableAttractions();
						break;
					case 5:
						CustomerQueries.createNextMonthView();
						CustomerQueries.viewNextMonthEvents();
						break;
					case 6:
						EmployeeQueries.getEventsFromGenre();
						break;
					case 7:
						quit = true;
					}
				}

				if (userType == 2) {
					// attractions
					// deliverable 3
					System.out.print("1.  Create (insert) an event booking\n");
					System.out.print("2.  Cancel (delete) an EVENT BOOKING by ID\n");
					// deliverable 7, group by gender and age
					System.out.print("3.  View demographic (AVG age and gender) statistics of attendees\n");
					System.out.print("4.  Quit\n>> ");

					choice = Integer.parseInt(in.readLine());

					System.out.println(" ");

					switch (choice) {
					case 1:
						CustomerQueries.createEventBooking();
						break;
					case 2:
						CustomerQueries.cancelEventBooking();
						break;
					case 3:
						CustomerQueries.viewDemographicStatistics();
						break;
					case 4:
						quit = true;
					}
				}

				if (userType == 3) {
					// sponsors
					System.out.print("1.  Sponsor a sporting event\n");
					System.out.print("2.  Sponsor a concert event\n");
					// deliverable 7, group by gender and age
					System.out.print("3.  View demographic (AVG age and gender) statistics of attendees\n");
					// deliverable 10, group attractions by their genres/sport
					System.out.print("4.  View (select) all available attractions of a given genre/sport\n");
					System.out.print("5.  Quit\n>> ");

					choice = Integer.parseInt(in.readLine());

					System.out.println(" ");

					switch (choice) {
					case 1:
						SponsorQueries.insertSportingEventSponsor();
						break;
					case 2:
						SponsorQueries.insertConcertEventSponsor();
						break;
					case 3:
						CustomerQueries.viewDemographicStatistics();
						break;
					case 4:
						CustomerQueries.viewAvailableAttractions();
						break;
					case 5:
						quit = true;
					}
				}

				if (userType == 4) {
					// employees
					// deliverable 2
					System.out.print("1.  Create (insert) a new sporting EVENT\n");
					System.out.print("2.  Create (insert) a new concert EVENT\n");
					// deliverable 3
					System.out.print("3.  Cancel (delete) a sporting event by ID\n");
					System.out.print("4.  Cancel (delete) a concert event by ID\n");
					// deliverable 6, 2 table join
					System.out.print("5.  View eventID based off genre (join)\n");
					// deliverable 7, group by gender and age
					System.out.print("6.  View demographic (AVG age and gender) statistics of attendees\n");
					// deliverable 8, update the date of a concert
					System.out.print("7.  Update the date of a concert\n");
					System.out.print("8.  Update the date of a sporting event\n");
					// deliverable 9, additional query, view events scheduled to work for
					System.out.print("9.  View (select) events you are scheduled to work for\n");
					// deliverable 5, 3 table join
					System.out.print("10.  View Employee names working at a specific event (join)\n");
					System.out.print("11.  Quit\n>> ");

					choice = Integer.parseInt(in.readLine());

					System.out.println(" ");

					switch (choice) {
					case 1:
						EmployeeQueries.insertNewSportingEvent();
						break;
					case 2:
						EmployeeQueries.insertNewConcertEvent();
						break;
					case 3:
						EmployeeQueries.deleteSportingEvent();
						break;
					case 4:
						EmployeeQueries.deleteConcertEvent();
						break;
					case 5:
						EmployeeQueries.getEventsFromGenre();
						break;
					case 6:
						CustomerQueries.viewDemographicStatistics();
						break;
					case 7:
						EmployeeQueries.updateConcertEvent();
						break;
					case 8:
						EmployeeQueries.updateSportingEvent();
						break;
					case 9:
						EmployeeQueries.viewSchedule();
						break;
					case 10:
						EmployeeQueries.seeEmployeesWorkingAtEvent();
						break;
					case 11:
						quit = true;
					}
				}
				if (userType == 5) {
					System.out.print("**Note that these are not all are queries\n");
					//System.out.print("Deliverable 1:\n");
					System.out.print("Deliverable 2: Create (insert) a new event attendee\n");
					System.out.print("Deliverable 3: Cancel (delete) a sporting event by ID\n");
					System.out.print("Deliverable 4: Update credit card information\n");
					System.out.print("Deliverable 5: View Employee names working at a specific event (join)\n");
					System.out.print("Deliverable 6: View eventID based off genre (join)\n");
					System.out.print("Deliverable 7: View demographic (AVG age and gender) statistics of attendees\n");
					System.out.print("Deliverable 8: Update the date of a concert\n");
					System.out.print("Deliverable 9: View (select) events you are scheduled to work for\n");
					System.out.print("Deliverable 10: View (select) all available attractions grouped by a given genre\n");
					System.out.print("Deliverable 11: View concerts occuring within the next month (view)\n");
					System.out.print("Quit\n>> ");

					choice = Integer.parseInt(in.readLine());

					System.out.println(" ");

					switch (choice) {
					case 1:
						CustomerQueries.insertNewAttendee();
						break;
					case 2:
						CustomerQueries.insertNewAttendee();
						break;
					case 3:
						EmployeeQueries.deleteSportingEvent();
						break;
					case 4:
						CustomerQueries.updateAttendee();
						break;
					case 5:
						EmployeeQueries.seeEmployeesWorkingAtEvent();
						break;
					case 6:
						EmployeeQueries.getEventsFromGenre();
						break;
					case 7:
						CustomerQueries.viewDemographicStatistics();
						break;
					case 8:
						EmployeeQueries.updateConcertEvent();
						break;
					case 9:
						EmployeeQueries.viewSchedule();
						break;
					case 10:
						CustomerQueries.viewAvailableAttractions();
						break;
					case 11:
						CustomerQueries.createNextMonthView();
						CustomerQueries.viewNextMonthEvents();
						break;
					case 12:
						quit = true;
					}
				}
			}

			con.close();
			in.close();
			System.out.println("\nThanks for stopping by the Arena!\n\n");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IOException!");

			try {
				con.close();
				System.exit(-1);
			} catch (SQLException ex) {
				System.out.println("Message: " + ex.getMessage());
			}
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
		}
	}

	public static void main(String[] args) {
		new JDBCConnection().showMenu();
	}

}
