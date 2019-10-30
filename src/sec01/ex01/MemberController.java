package sec01.ex01;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/mem.do")
public class MemberController extends HttpServlet{
	private static final long serialVersionUID = 1L; //직렬화-객체를 바이트의 배열로 변환해서 저장, 1L-객체 식별 용도
	MemberDAO memberDAO;
	
	public void init() throws ServletException{
		memberDAO = new MemberDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Get방식 처리
		doHandle(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Post방식 처리
		doHandle(req, resp);
	}

	protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Get, Post방식을 실제로 처리하는 곳
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		List membersList = memberDAO.listMembers();  //요청에 대해 회원 정보를 조회
		req.setAttribute("membersList", membersList); //조회한 회원 정보를 req에 바인딩(서블릿에서 다른 서블릿 또는 JSP로 대량의 데이터를 공유하거나 전달)
		RequestDispatcher dispatch=req.getRequestDispatcher("test01/listMembers.jsp");	//JSP로 포워딩
		dispatch.forward(req, resp); 
	}
	
	
	
}














