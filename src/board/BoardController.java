package board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/board/*")
public class BoardController extends HttpServlet{
	private static String ARTICLE_IMAGE_REPO = "C:\\JSP_article_image"; //이미지 저장 위치를 상수로 선언
	BoardService boardService;
	ArticleVO articleVO;
	
	public void init(ServletConfig config) throws ServletException{
		boardService =new BoardService(); //서블릿 초기화시 BoardService객체 생성
		articleVO = new ArticleVO();
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
			if (action==null) { //요청명이 null이면 board list 페이지로 이동
				articlesList = boardService.listArticles();
				req.setAttribute("articlesList", articlesList);
				nextPage = "/mvc2_board/listArticles.jsp";  
			} else if (action.equals("/listArticles.do")) {  //listArticles.do요청이면 board list 페이지로 이동
				articlesList = boardService.listArticles();
				req.setAttribute("articlesList", articlesList);
				nextPage = "/mvc2_board/listArticles.jsp";  
				
			} else if (action.equals("/articleForm.do")) { // 글쓰기 창으로 이동
				nextPage= "/mvc2_board/articleForm.jsp";
				
			} else if (action.equals("/addArticle.do")) {  //새글 추가
				Map<String, String> articleMap = upload(req, resp);  //파일 업로드 기능을 사용하기위해 upload()로 요청을 전달
				String title = articleMap.get("title");		//aritcleMap에 저장된 글 정보를 다시 가져옴
				String content = articleMap.get("content");
				String imageFileName = articleMap.get("imageFileName");
				
				articleVO.setParentNO(0); //새 글의 부모글 번호를 0으로 설정
				articleVO.setId("hong");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImageFileName(imageFileName);
				boardService.addArticle(articleVO);  //글쓰기 창에서 입력된 정보를 ArticleVO객체에 설정한후 addArticle()로 전달
				nextPage = "/board/listArticles.do";
			}
			
			RequestDispatcher dispatch = req.getRequestDispatcher(nextPage);
			dispatch.forward(req, resp);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//파일 업로드 기능
	private Map<String, String> upload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> articleMap = new HashMap<String, String>();
		String encoding = "utf-8";
		File currentDirPath = new File(ARTICLE_IMAGE_REPO); //위치
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024*1024); //1MB
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(req);
			for(int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				if(fileItem.isFormField()) {	//폼 필드 이면 전송된 매개변수 값을 출력
					System.out.println(fileItem.getFieldName() +"="+ fileItem.getString(encoding));
					//파일 업로드로 같이 전송된 새 글 관련 매개변수를 Map에 (key, value)로저장한 후 반환하고, 새글과 관련된 title, content를 Map에 저장
					articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));  
				}else {	//폼 필드가 아니면 파일 업로드 기능 수행
					System.out.println("파라미터이름:"+fileItem.getFieldName());
					System.out.println("파일이름:"+fileItem.getName());
					System.out.println("파일크기:"+fileItem.getSize()+"bytes");
					//업로드된 파일의 파일 이름을 Map에 ("imageFileName","업로드 파일이름")으로  저장
					articleMap.put(fileItem.getFieldName(), fileItem.getName());
					if(fileItem.getSize() > 0) {	//파일이 존재하는 경우 업로드한 파일의 파일 이름을 저장소에 업로드
						int idx=fileItem.getName().lastIndexOf("\\");
						if(idx==-1) {
							idx=fileItem.getName().lastIndexOf("/");
						}
						
						String fileName = fileItem.getName().substring(idx + 1);
						File uploadFile = new File(currentDirPath + "\\" + fileName);
						fileItem.write(uploadFile);
						
					}
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return articleMap;
	}
}












