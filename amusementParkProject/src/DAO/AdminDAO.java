package javaproject.DAO;


import javaproject.DTO.AdminDTO;

import java.sql.*;

public class AdminDAO extends SuperDAO {
    public boolean idDuplicate(String id) {
        Connection conn = super.getConnection();
        String sql = "select count(*) from administer where aId = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("qqq");
            if (rs.next()) {
                if (rs.getInt(1) == 1) {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void insert(AdminDTO adminDTO) {
        Connection conn = super.getConnection();
        String sql = "insert into administer values(?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, adminDTO.getAId());
            pstmt.setString(2, adminDTO.getAPass());
            pstmt.setString(3, adminDTO.getAName());
            pstmt.setInt(4, adminDTO.getAGender());
            pstmt.setDate(5, Date.valueOf(adminDTO.BirthToString()));
            pstmt.setString(6, adminDTO.getAPosition());
            int result = pstmt.executeUpdate();
            System.out.println(result + "건 완료");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AdminDTO select(String aId) {
        Connection conn = super.getConnection();
        String sql = "select * from administer where aId = ?";
        ResultSet rs = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, aId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                AdminDTO adminDTO = new AdminDTO();
                adminDTO.setAId(rs.getString("aId"));
                adminDTO.setAPass(rs.getString("aPass"));
                adminDTO.setAName(rs.getString("aName"));
                adminDTO.setAGender(rs.getInt("aGender"));
                adminDTO.setABirth(rs.getDate("aBirth"));
                adminDTO.setAPosition(rs.getString("aPosition"));
                return adminDTO;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void edit(int num, String text, String aId) {
        Connection conn = super.getConnection();
        String sql = null;
        switch (num) {
            case 1:
                sql = "update administer set aPass = ? where aId = ?";
                break;
            case 2:
                sql = "update administer set aName = ? where aId = ?";
                break;
            case 3:
                sql = "update administer set aGender = ? where aId = ?";
                break;
            case 4:
                sql = "update administer set aBirth = ? where aId = ?";
                break;
            case 5:
                sql = "update administer set aPosition = ? where aId = ?";
                break;
            default:
                break;
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            switch (num) {
                case 1:
                case 2:
                case 5:
                    pstmt.setString(1, text);
                    break;
                case 3:
                    pstmt.setInt(1, Integer.parseInt(text));
                    break;
                case 4:
                    pstmt.setDate(1, Date.valueOf(text));
                    break;
                default:
                    break;
            }
            pstmt.setString(2, aId);
            int result = pstmt.executeUpdate();
            System.out.println(result + "건 완료");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}