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
	private static final long serialVersionUID = 1L; //����ȭ-��ü�� ����Ʈ�� �迭�� ��ȯ�ؼ� ����, 1L-��ü �ĺ� �뵵
	MemberDAO memberDAO;
	
	public void init() throws ServletException{
		memberDAO = new MemberDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Get��� ó��
		doHandle(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Post��� ó��
		doHandle(req, resp);
	}

	protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Get, Post����� ������ ó���ϴ� ��
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		List membersList = memberDAO.listMembers();  //��û�� ���� ȸ�� ������ ��ȸ
		req.setAttribute("membersList", membersList); //��ȸ�� ȸ�� ������ req�� ���ε�(�������� �ٸ� ���� �Ǵ� JSP�� �뷮�� �����͸� �����ϰų� ����)
		RequestDispatcher dispatch=req.getRequestDispatcher("test01/listMembers.jsp");	//JSP�� ������
		dispatch.forward(req, resp); 
	}
	
	
	
}














