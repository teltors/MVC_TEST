package board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/*")
public class BoardController extends HttpServlet{
	BoardService boardService;
	ArticleVO artivleVO;
	
	public void init() throws ServletException{
		boardService =new BoardService(); //서블릿 초기화시 BoardService객체 생성
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req, resp);
	}

	private void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nextPage="";
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		String action = req.getPathInfo(); //요청명가져오기
		System.out.println("action: " + action);
		try {
			List<ArticleVO> articlesList = new ArrayList<ArticleVO>(); //배열화
			if (action==null) { //요청명이 null이면 boardlist페이지로 이동
				articlesList = boardService.listArticles();
				req.setAttribute("articlesList", articlesList);
				nextPage = "/mvc2_board/listArticles.jsp";  
			} else if (action.equals("/listArticles.do")) {  //listArticles.do요청이면 boardlist페이지로 이동
				articlesList = boardService.listArticles();
				req.setAttribute("articlesList", articlesList);
				nextPage = "/mvc2_board/listArticles.jsp";  
			}
			RequestDispatcher dispatch = req.getRequestDispatcher(nextPage);
			dispatch.forward(req, resp);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}












