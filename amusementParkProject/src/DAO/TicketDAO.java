package javaproject.DAO;

import javaproject.DTO.TicketDTO;
import javaproject.DTO.TimeTableDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO extends SuperDAO {
    private Connection conn;
    private static TicketDAO tinstance;
    private List<TicketDTO> ticketList = new ArrayList<>(); //여기 추가요12!@!@!@!@!@!@


    public TicketDAO() throws SQLException {
        this.conn = super.getConnection();
        loadTickets(); //여기추가요!@!@!@
    }
    public static TicketDAO getInstance()  { //여기 추가했어요!@!@!@
        try{
            if (tinstance == null) {
                tinstance = new TicketDAO();
            }
            return tinstance;
        }catch (Exception e){
            e.printStackTrace();
        }
        return tinstance;
    }
    public List<TicketDTO> getTicketList() {
        return new ArrayList<>(ticketList); // 리스트 복사하여 반환
    }
    private void loadTickets() { //여기 추가요!@!@!@!@!@!@!@!@!@!@
        ticketList.clear();
        String sql = " SELECT * FROM ticket";
        try{

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                String tPass = rs.getString("tPass");
                String tName = rs.getString("tName");
                int tPrice = rs.getInt("tPrice");
                ticketList.add(TicketDTO.builder().
                        tPass(tPass)
                        .tName(tName)
                        .tPrice(tPrice)
                        .build());

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public TicketDTO selectti(String id){

        String query = "SELECT * FROM ticket where tPass='"+ id +"'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                TicketDTO ticketDTO = TicketDTO .builder()
                        .tPass(rs.getString("tPass"))
                        .tName(rs.getString("tName"))
                        .tPrice(rs.getInt("tPrice"))
                        .build();

                return ticketDTO;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }


    public List<TicketDTO> select(){
        List<TicketDTO> ticketDTOList = new ArrayList<>();

        String query = "SELECT * FROM ticket";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                TicketDTO ticketDTO = TicketDTO .builder()
                        .tPass(rs.getString("tPass"))
                        .tName(rs.getString("tName"))
                        .tPrice(rs.getInt("tPrice"))
                        .build();

                ticketDTOList.add(ticketDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return ticketDTOList;
    }

    public void insert(TicketDTO ticketDTO){
        String query = "INSERT INTO ticket  VALUES(?,?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, ticketDTO.getTPass());
            stmt.setString(2, ticketDTO.getTName());
            stmt.setInt(3,ticketDTO.getTPrice());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String tPass){
        String query = "DELETE FROM ticket WHERE tPass = ?";


        try{
            PreparedStatement cursor = conn.prepareStatement(query);

            cursor.setString(1, tPass);
            cursor.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(TicketDTO ticketDTO,String tPass){
        String query = "UPDATE ticket SET tPass = ?, tName = ? , tPrice = ?  WHERE tPass = ?";

        try {
            PreparedStatement cursor = conn.prepareStatement(query);


            cursor.setString(1, ticketDTO.getTPass());
            cursor.setString(2, ticketDTO.getTName());
            cursor.setInt(3, ticketDTO.getTPrice());
            cursor.setString(4, tPass);

            //sql 실행
            cursor.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
