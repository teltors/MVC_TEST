package board;

import java.util.List;

public class BoardService {

	BoardDAO boardDAO;
	
	public BoardService() {
		boardDAO = new BoardDAO(); //积己磊 龋免矫 BoardDAO 按眉 积己
	}
	
	public List<ArticleVO> listArticles() {
		List<ArticleVO> articlesList = boardDAO.selectAllArticles();
		return articlesList;
	}

}
