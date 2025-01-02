// name of the accdb file is dataTable.accdb
// table name in accdb file is participants

import java.sql.*;
import java.util.*;
import javax.swing.*;

public class DbHelper {
 
    private static final String DataBase_URL = "jdbc:ucanaccess://assets/dataTable.accdb";

    static {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    } // end of static

    public static boolean InsertParticipant(String name, int score) {
        if (score < 1 || score > 100) {
            JOptionPane.showMessageDialog(null, "Score must be between 1 and 100", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String checkQuery = "SELECT COUNT(*) FROM participants WHERE Name = ?";
        String sql = "INSERT INTO participants (Name, Score) VALUES (?, ?)";

        try {
            Connection con = DriverManager.getConnection(DataBase_URL);
            PreparedStatement check_pst = con.prepareStatement(checkQuery);
            check_pst.setString(1, name);
            ResultSet rs = check_pst.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Participant with the same name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } // end of if

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setInt(2, score);
            pst.executeUpdate();
            return true;
        } // end of try
        catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Participant not added." + ex.getMessage());
            return false;
        } // end of catch
    }

    public static List<Participant> getData() {
        List<Participant> runners = new ArrayList();
        String sql = "SELECT * FROM participants ORDER BY Name ASC";
        try {
            Connection con = DriverManager.getConnection(DataBase_URL);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int score = rs.getInt("score");
                runners.add(new Participant(id, name, score));
            } // end of while loop

        } // end of try
        catch (SQLException ex) {
            ex.printStackTrace();
        }// end of catch

        return runners;
    }

    public static void deleteParticipant() {

        String sql = "DELETE FROM participants";
        try {
            Connection con = DriverManager.getConnection(DataBase_URL);
            Statement st = con.createStatement();
            st.execute(sql);
        }// end of try
        catch (SQLException ex) {
            ex.printStackTrace();
        }// end of catch
    }// end of deleteParticipant

    public static List<Participant> getTopParticipant() {
        List<Participant> runners = new ArrayList();
        String sql = "SELECT * FROM participants ORDER BY Score DESC LIMIT 3";
        try {
            Connection con = DriverManager.getConnection(DataBase_URL);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int score = rs.getInt("score");
                runners.add(new Participant(id, name, score));
            }// end of while loop
        }// end of try
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return runners;
    }

} // end of DbHelper class
