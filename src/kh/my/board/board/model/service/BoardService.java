package kh.my.board.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kh.my.board.board.model.dao.BoardDao;
import kh.my.board.board.model.vo.Board;
import kh.my.board.common.JdbcTemplate;

public class BoardService {

	public BoardService() {
		// TODO Auto-generated constructor stub
	}
	
	//Board oVo = new BoardService().getBoard(bno);  
	public Board getBoard(int bno) {
		Board vo = null;
		Connection conn = JdbcTemplate.getConnection();
		vo = new BoardDao().getBoard(conn, bno);
		JdbcTemplate.close(conn);
		return vo;
	}
	
	
	public int getBoardCount(){
		int result = 0;
		Connection conn = JdbcTemplate.getConnection();
		result = new BoardDao().getBoardCount(conn);
		JdbcTemplate.close(conn);
		return result;
	}
	
	
	
	public ArrayList<Board> selectBoardList(int start, int end){
		ArrayList<Board> volist = null;
		Connection conn = JdbcTemplate.getConnection();
		
		volist = new BoardDao().selectBoardList(conn, start, end);
		
		JdbcTemplate.close(conn);
		return volist;
	}
	
	public ArrayList<Board> selectBoardList(){
		ArrayList<Board> volist = null;
		Connection conn = JdbcTemplate.getConnection();
		
		volist = new BoardDao().selectBoardList(conn);
		
		JdbcTemplate.close(conn);
		return volist;
	}

	public int insertBoard(Board vo) {
		int result =-1;
		Connection conn = JdbcTemplate.getConnection();
		
		result = new BoardDao().insertBoard(conn, vo);
			
		JdbcTemplate.close(conn);
		return result;
	}
	public int deleteBoard(int bno, String writer) {
		int result = -1;
		Connection conn = JdbcTemplate.getConnection();
		
		result = new BoardDao().deleteBoard(conn, bno, writer);

		JdbcTemplate.close(conn);
		return result;
	}
	public int updateBoard(Board vo, String writer) {
		int result = -1;
		Connection conn = JdbcTemplate.getConnection();
		result = new BoardDao().updateBoard(conn, vo, writer);
		JdbcTemplate.close(conn);
		return result;
	}
}
