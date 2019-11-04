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

	public ArticleVO viewArticle(int articleNO) {
		ArticleVO article = null;
		article = boardDAO.selectArticle(articleNO);
		return article;
	}

	

	public void modArticle(ArticleVO article) {
		boardDAO.updateArticle(article);
		
	}

}
