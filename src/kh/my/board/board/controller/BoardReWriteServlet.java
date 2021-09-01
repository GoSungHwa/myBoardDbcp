package kh.my.board.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.board.model.service.BoardService;
import kh.my.board.board.model.vo.Board;

/**
 * Servlet implementation class BoardReWriteServlet
 */
@WebServlet("/boardrewrite.kh")
public class BoardReWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardReWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// 답글이므로 어느글에 답글을 달것인가 정보가 전달되어 올 것임.
		Board oVo = null;
		String viewBno = request.getParameter("bno");
		if(viewBno == null) {   // 기존 읽고 있던 글이 없다면 원본 새글쓰기로 인식
			oVo= new Board();
		} else {
			int bno = Integer.parseInt(viewBno);
			// 알아와야함. bref, bre_level, bre_step
			oVo = new BoardService().getBoard(bno);  
		}
				
		// 화면입력 전달되어 옴. request - parameter (==변수명) : t, c
		// http://localhost:8090/myBoard/boardwrite.kh?c=내용부분입력된값이지요&t=뭐라해야할지모를제목
		String title = request.getParameter("t");  //내용부분입력된값이지요
		String content = request.getParameter("c");  //뭐라해야할지모를제목
		String writer = (String)request.getSession().getAttribute("memberLoginInfo");
		if(writer == null) {
			writer = "user01";   // TODO: 임시 user 설정
		}
		out.println("입력된 title: "+ title);
		out.println("<br>입력된 content: "+ content);

		Board vo = new Board(oVo.getBno(), title, content, writer, oVo.getBref(), oVo.getBreLevel(), oVo.getBreStep());		
		
		int result = new BoardService().insertBoard(vo);
		if(result == 0) {
			out.println("<br>게시글 되지 않았습니다. <br>작성된 글에 비속어가 포함되어 있습니다. <br>다시 작성해 주세요.");
		} else {
			out.println("<br>게시글 입력되었습니다.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
