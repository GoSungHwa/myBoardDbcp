package kh.my.board.board.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kh.my.board.board.model.vo.Board;
import kh.my.board.common.JdbcTemplate;

public class BoardDao {

	public BoardDao() {
		// TODO Auto-generated constructor stub
	}
	
	public Board getBoard(Connection conn, int bno) {
		Board vo = null;
		String sql =  "select bno, bref, bre_level, bre_step from board_r where bno = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				vo = new Board();
				vo.setBno(rset.getInt(1));
				vo.setBref(rset.getInt(2));
				vo.setBreLevel(rset.getInt(3));
				vo.setBreStep(rset.getInt(4));
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
		}
		return vo;
	}
	
	public int getBoardCount(Connection conn){
		int result = 0;
		String sql =  "select count(bno) from board_r";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
		}
		return result;
	}
	
	public ArrayList<Board> selectBoardList(Connection conn, int start, int end){
		ArrayList<Board> volist = null;
		
		String sql =  "select * from (select Rownum r, t1.* from (select * from board_r order by bref desc, bre_step asc) t1 ) t2 where r between ? and ?";
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			volist = new ArrayList<Board>();
			if(rset.next()) {
				do {
					Board vo = new Board();
					vo.setBno(rset.getInt("bno"));
					vo.setTitle(rset.getString("title"));
					vo.setContent(rset.getString("content"));
					vo.setCreateDate(rset.getDate("create_Date"));
					vo.setWriter(rset.getString("writer"));
					vo.setDeleteYn(rset.getString("delete_Yn"));
					vo.setBref(rset.getInt("bref"));
					vo.setBreLevel(rset.getInt("bre_Level"));
					vo.setBreStep(rset.getInt("bre_Step"));
					volist.add(vo);
				}while(rset.next());
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
//			try {
//				rset.close();
//				pstmt.close();
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
		}
		System.out.println("[ejkim]-- 리턴은"+ volist);
		return volist;
	}
	
	public ArrayList<Board> selectBoardList(Connection conn){
		ArrayList<Board> volist = null;
		
		String sql =  "select * from board_r";
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			volist = new ArrayList<Board>();
			if(rset.next()) {
				do {
					Board vo = new Board();
					vo.setBno(rset.getInt("bno"));
					vo.setTitle(rset.getString("title"));
					vo.setContent(rset.getString("content"));
					vo.setCreateDate(rset.getDate("create_Date"));
					vo.setWriter(rset.getString("writer"));
					vo.setDeleteYn(rset.getString("delete_Yn"));
					vo.setBref(rset.getInt("bref"));
					vo.setBreLevel(rset.getInt("breLevel"));
					vo.setBreStep(rset.getInt("breStep"));
					volist.add(vo);
				}while(rset.next());
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
//			try {
//				rset.close();
//				pstmt.close();
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
		}
		System.out.println("[ejkim]-- 리턴은"+ volist);
		return volist;
	}
	

	public int insertBoard(Connection conn, Board vo) {
		int result =-1;
		
		// 답글, 답답.... 
		String sqlUpdate = "UPDATE board_r set bre_step=bre_step+1  where bref=? and bre_step>?";
		
		String sqlInsert = "INSERT INTO"
				+ " board_r"
				+ " (BNO, TITLE, CONTENT, WRITER, bref, bre_level, bre_step)"
				+ " VALUES (SEQ_BOARD.nextval, ?, ?, ?, ?, ?, ?)";
		
		String sqlNextVal="select SEQ_BOARD.nextval from dual";
		
		int bref = 0;
		int bre_level = 0;
		int bre_step = 1;
		int nextVal=0;
		
		PreparedStatement pstmt= null;
		ResultSet rset=null;
		try {
			pstmt=conn.prepareStatement(sqlNextVal);
			rset=pstmt.executeQuery();
			if(rset.next()) {
				nextVal=rset.getInt(1);
			}
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
			
			
			if(vo.getBno() != 0) {
				bref = vo.getBref();
				bre_step = vo.getBreStep();
				pstmt = conn.prepareStatement(sqlUpdate); //update
				pstmt.setInt(1, bref);
				pstmt.setInt(2, bre_step);
				result = pstmt.executeUpdate();
				JdbcTemplate.close(pstmt);
				
				bre_level = vo.getBreLevel()+1;
				bre_step++;				
			}
				pstmt = conn.prepareStatement(sqlInsert);  //insert
				if(vo.getBno()!=0) {    //답글 쓰기
					pstmt.setInt(5, bref);
				}else { //새글 쓰기
					pstmt.setInt(5, nextVal);
				}
				
				pstmt.setInt(6, bre_level);
				pstmt.setInt(7, bre_step);
				pstmt.setInt(6, bre_level);
				pstmt.setInt(7, bre_step);
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setString(4, vo.getWriter());
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
		}
		return result;
	}
	public int deleteBoard(Connection conn, int bno, String writer) {
		int result = -1;
		String sql = "update board set delete_Yn = 'Y' where bno like ? and writer like ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  bno);
			pstmt.setString(2,  writer);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("연결 실패");
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
		}
		return result;
	}
	public int updateBoard(Connection conn, Board vo, String writer) {
		int result = -1;
		String query = "update board set title = ?, content = ? where bno like ? and writer like ? and delete_yn <> 'Y'";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getBno());
			pstmt.setString(4, writer);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("연결 실패");
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
		}
		return result;
	}
}
