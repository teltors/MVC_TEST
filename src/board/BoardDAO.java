package board;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	
	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;
	
	public BoardDAO() {
		try {
			Context ctx= new InitialContext(); //application 실행을 위해 밑바탕에 깔려져 있는 정보들과 환경들을 Context라 한다.
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public List<ArticleVO> selectAllArticles() {
		
		List articlesList = new ArrayList();
		
		try {
			conn = dataFactory.getConnection();
			//계층형 SQL
			String query = "SELECT LEVEL, articleNO, parentNO, title, content, id, writreDate" 
							+ " from t_board" 
							+ " START WITH parentNO=0"
							+ " CONNECT BY PRIOR articleNO=parentNO"
							+ " ORDER SIBLINGS BY articleNO DESC";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int level = rs.getInt("level");
				int articleNO = rs.getInt("articleNO");
				int parentNO = rs.getInt("parentNO");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writeDate");
				
				ArticleVO article = new ArticleVO();
				article.setLevel(level);
				article.setArticleNO(articleNO);
				article.setParentNO(parentNO);
				article.setTitle(title);
				article.setContent(content);
				article.setId(id);
				article.setWriteDate(writeDate);
				articlesList.add(article);
			}
			rs.close();
			pstmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return articlesList;
	}

}
