package javaproject.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javaproject.DTO.AttractionDTO;

public class AttractionDAO extends SuperDAO {
	private Connection conn=null;
	
    public List<AttractionDTO> selectAll() {
        List<AttractionDTO> attractionDTOList = new ArrayList<AttractionDTO>();
        String query = "SELECT * FROM attraction";
        try {
        	conn = super.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                AttractionDTO attractionDTO = AttractionDTO.builder()
                        .atId(rs.getString("atId"))
                        .atName(rs.getString("atName"))
                        .atUrl(rs.getString("atUrl"))
                        .atMax(rs.getInt("atMax"))
                        .atOnoff(rs.getInt("atOnoff"))
                        .build();

                attractionDTOList.add(attractionDTO);
            }
            
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return attractionDTOList;
    }

    public void insert() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String query = "INSERT INTO attraction(attractionName, attrationURL) VALUES(?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            System.out.println("어트랙션 이름 , 어트랙션URL 을 입력하세요.");
            stmt.setString(1, br.readLine());
            stmt.setString(2, br.readLine());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(int choiceNum) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String query;
        switch (choiceNum) {
            case 1:
                query = "UPDATE attraction SET attractionName = ? WHERE attractionID = ?";
                try {
                    PreparedStatement cursor = conn.prepareStatement(query);

                    //sql 파라미터 설정
                    System.out.println("수정하고 싶은 어트랙션ID와 수정할 어트랙션이름을 적으세요.");
                    cursor.setInt(1, Integer.parseInt(br.readLine()));
                    cursor.setString(2, br.readLine());

                    //sql 실행
                    cursor.executeUpdate();

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }

                break;
            case 2:
                query = "update attraction set attrationURL = ? where attractionID = ?";
                try {
                    PreparedStatement cursor = conn.prepareStatement(query);

                    //sql 파라미터 설정
                    System.out.println("수정하고 싶은 어트랙션ID와 수정할 어트랙션URL을 적으세요.");
                    cursor.setInt(1, Integer.parseInt(br.readLine()));
                    cursor.setString(2, br.readLine());

                    //sql 실행
                    cursor.executeUpdate();

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }


    }


    public void delete(int choiceNum) {
       String query = "DELETE FROM attraction WHERE attractionID = ?";
       try{
           PreparedStatement cursor = conn.prepareStatement(query);

           cursor.setInt(1, choiceNum);
           cursor.executeUpdate();

       }catch (Exception e){
           e.printStackTrace();
       }
    }
    
	public AttractionDTO getAttract(String atId) {
		AttractionDTO a=null;
		PreparedStatement ptmt=null;
		try {
			conn = super.getConnection();
			String sql = "select * from attraction where atName=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,atId);
			
			ResultSet rs=ptmt.executeQuery();
			if(rs.next()) {
				a = AttractionDTO.builder()
						.atId(rs.getString("atId"))
						.atName(rs.getString("atName"))
						.atUrl(parse(rs.getString("atUrl")))
						.atMax(rs.getInt("atMax"))
						.atOnoff (rs.getInt("atOnoff"))
						.build();
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	private String parse(String string) {
		for(int i=0;i<string.length();i++) {
			if(string.charAt(i)=='\\') {
				StringBuffer buf = new StringBuffer(string);
				buf.insert(i,'\\');
				string=buf.toString();
				i++;
				System.out.println(string);
			}
		}
		return string;
	}

	public boolean updateat(AttractionDTO att) {
		PreparedStatement ptmt=null;
		boolean flag=false;
		try {
			conn = super.getConnection();
			String sql = "update attraction set atUrl=?, atMax=?, atOnoff=? where atId=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,att.getAtUrl());
			ptmt.setInt(2,att.getAtMax());
			ptmt.setInt(3,att.getAtOnoff());
			ptmt.setString(4,att.getAtId());
			
			int rs=ptmt.executeUpdate();
			if(rs>0) {
				flag=true;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	
}
