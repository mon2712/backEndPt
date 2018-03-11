package TrabajoTerminalBack.pt1.pt2;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class conexionDB implements Serializable{

	private static final long serialVersionUID = 1L;
	static Connection conn = null;
    static PreparedStatement prepareStat = null;


    public static Connection conectarBD() {
    		Connection conn = null;
    		//String pass ="dBoy6Ap";
    		String pass ="vfcnmm2201";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.print("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.print("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_pt2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", pass);

            if (conn != null) {
                System.out.println("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.print("MySQL Connection Failed!");
            e.printStackTrace();
        }
        return conn;

    }

    public static void addUserTotweetuser(long userid, String username, Date createdTime) {

        try {
            //String insertQueryStatement = "INSERT  INTO  tweetuser  VALUES  (?,?,?)";
            System.out.println("En adduser function");
            System.out.println(userid);
            System.out.println(username);
            System.out.println(createdTime);

            CallableStatement cStmt = conn.prepareCall("{call add_usernames(?, ?, ?)}");

            cStmt.setLong(1, userid);
            cStmt.setString(2, username);
            cStmt.setDate(3, createdTime);

            /*prepareStat = conn.prepareStatement(insertQueryStatement);
            prepareStat.setLong(1, userid);
            prepareStat.setString(2, username);
            prepareStat.setDate(3, createdTime);

            // execute insert SQL statement
            prepareStat.executeUpdate();*/

            cStmt.executeQuery();
            System.out.println(username + " added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String[] getHashtags(String topic) {
        try {
            String getQueryStatement = "SELECT h.name FROM hashtag h JOIN topic t ON t.idtopic=h.topic_idtopic WHERE t.name='" + topic +"'";
            prepareStat = conn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = prepareStat.executeQuery();

            List<String> hash=new ArrayList<>();
            while (rs.next()) {
                hash.add(rs.getString("name"));
            }

            String hashtags[] = new String[hash.size()];
            for(int i=0;i<hash.size();i++){
                //System.out.println(hash.get(i));
                hashtags[i]="#"+hash.get(i);
            }

           for(int i=0;i<hashtags.length;i++){
                System.out.println(hashtags[i]);
            }


            return hashtags;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] getUsernames() {
        try {
            String getQueryStatement = "SELECT usuario FROM Usuario";

            prepareStat = conn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = prepareStat.executeQuery();

            List<String> users=new ArrayList<>();
            while (rs.next()) {
                users.add(rs.getString("usuario"));
            }

            String usernames[] = new String[users.size()];
            for(int i=0;i<users.size();i++){
                //System.out.println(hash.get(i));
                usernames[i]="@"+users.get(i);
            }

           /* for(int i=0;i<usernames.length;i++){
                System.out.println(usernames[i]);
            }*/

            System.out.println("Cantidad de usuarios en bd: "+usernames.length);

            return usernames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

