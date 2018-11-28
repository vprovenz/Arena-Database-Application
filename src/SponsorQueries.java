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

public class SponsorQueries {
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	//inserts new Sponsor for sporting event
	public static void insertSportingEventSponsor() throws SQLException {
        int sid;
        int eventID;
        PreparedStatement ps;
        Connection con = JDBCConnection.getConnection1();
        con.setAutoCommit(false);

		try {
            ps = con.prepareStatement("INSERT INTO sponsorssportingevent VALUES (?, ?)");
            // for insertion, need to make sure that this primary key isnt taken -> how to
            // do
            // also how to handle char vs string? do we need to make char array?
            // also need to implement type checking for insertion and update
            System.out.print("\nSponsor ID: ");
            sid = Integer.parseInt(in.readLine());
            ps.setInt(1, sid);

            System.out.print("\nEvent ID for the event you would like to sponsor: ");
            eventID = Integer.parseInt(in.readLine());
            ps.setInt(2, eventID);

            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
            System.out.println("\nSponsorship created.");

        } catch (IOException e) {
            System.out.println("IOException!");
        } catch (SQLException ex) {
            System.out.println("EventID does not exist: " + ex.getMessage());
            try {
                // undo the insert
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("EventID does not exist: " + ex2.getMessage());
                System.exit(-1);
            }
        }
		catch (Exception e) {
			System.out.println("Please make sure input values are correct!" + e);
		}
	}

    //inserts new Sponsor for concert event
    public static void insertConcertEventSponsor() throws SQLException {
        int sid;
        int eventID;
        PreparedStatement ps;
        Connection con = JDBCConnection.getConnection1();
        con.setAutoCommit(false);

        try {
            ps = con.prepareStatement("INSERT INTO sponsorsconcertevent VALUES (?, ?)");
            // for insertion, need to make sure that this primary key isnt taken -> how to
            // do
            // also how to handle char vs string? do we need to make char array?
            // also need to implement type checking for insertion and update
            System.out.print("\nSponsor ID: ");
            sid = Integer.parseInt(in.readLine());
            ps.setInt(1, sid);

            System.out.print("\nEvent ID for the event you would like to sponsor: ");
            eventID = Integer.parseInt(in.readLine());
            ps.setInt(2, eventID);

            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
            System.out.println("\nSponsorship created.");
            // should we add sponsorPrice to concert and sport tables?

        } catch (IOException e) {
            System.out.println("IOException!");
        } catch (SQLException ex) {
            System.out.println("EventID does not exist: " + ex.getMessage());
            try {
                // undo the insert
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("EventID does not exist: " + ex2.getMessage());
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

			System.out.print("\nGenre/sport: ");
			genre = in.readLine();

			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = st.executeQuery("select genre from attraction4 where genre = " + genre);
			ResultSetMetaData rsmd = res.getMetaData();
			// TODO error checking here if result set is empty

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
				// this number needs to be switched to whatever the name column is
				System.out.print(res.getString(1) + "   ");
			}
			res.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Error in fetching data" + e);
		}
	}
}
