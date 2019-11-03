package board;

import java.util.List;

public class BoardService {

	BoardDAO boardDAO;
	
	public BoardService() {
		boardDAO = new BoardDAO(); //������ ȣ��� BoardDAO ��ü ����
	}
	
	public List<ArticleVO> listArticles() {
		List<ArticleVO> articlesList = boardDAO.selectAllArticles();
		return articlesList;
	}

	public int addArticle(ArticleVO article) {
		return boardDAO.insertNewArticle(article);
		
	}

}
