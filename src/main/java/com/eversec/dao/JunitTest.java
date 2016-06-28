package com.eversec.dao;


import java.util.List;


import org.junit.Test;

import com.eversec.bean.Article;

public class JunitTest {
	private LuceneDao luceneDao = new LuceneDao();
	@Test
	public void addIndex() throws Exception{
			for (int i = 25; i<=25;i++){
				Article article = new Article();
				article.setId(i);
				article.setTitle("他不是抑郁症抑郁症抑郁症抑郁症抑郁症，他仅仅自私却无所得");
				article.setContent("有人问毕加索：你的画我看不懂，毕加索：鸟叫声好听，好听，听得懂吗？");
				article.setLink("www.baidu.com");
				article.setAuthor("张盼");
				luceneDao.addIndex(article);
			}
			
	}
	//@Test
	public void testsearcher() throws Exception{
		
		String keywords = "盼哥";
		//title content textfield 现在使用的分词器是单子分词。。
		
		List<Article> listArticles = luceneDao.findIndex(keywords,0,10);
		for (Article article:listArticles){
			System.out.println(article.getId());
			System.out.println(article.getContent());
			System.out.println(article.getLink());
			System.out.println(article.getTitle());
			System.out.println(article.getAuthor());
			
		}
	}
	//@Test
	public void testDel() throws Exception{
		luceneDao.delIndex("title", "抑");
	}
	
	//@Test
	public void testUpdate() throws Exception{
		String fieldName = "title";
		String fieldValue = "抑";
		Article article = new Article();
		article.setId(9527);
		article.setAuthor("张盼");
		article.setTitle("你盼哥很黄");
		article.setContent("你盼哥今天用的黑妹洗发水");
		article.setLink("www.baidu.com");
		
		luceneDao.updataIndex(fieldName, fieldValue, article);
	}
}
