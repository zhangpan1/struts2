package com.eversec.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import com.eversec.bean.Article;

/**
 * 使用lucene 来操作索引库
 * @author zhangp
 *
 */
public class LuceneDao {
	public void addIndex(Article article) throws Exception{
		IndexWriter indexWriter = LuceneUtils.getIndexWriter();
		Document doc = ArticleUtils.articleToDocument(article);
		indexWriter.addDocument(doc);
		indexWriter.close();
	}
	/**
	 * 删除索引，根据字段对应的值进行删除。
	 * @param fieldName
	 * @param fieldValue
	 * @throws Exception
	 */
    public void delIndex(String fieldName,String fieldValue) throws Exception{
		IndexWriter indexWriter = LuceneUtils.getIndexWriter();
		Term term = new Term(fieldName,fieldValue);

		indexWriter.deleteDocuments(term);
		indexWriter.close();
	}
    /**
     * 先删除符合条件的记录，再创建一个符合条件的记录
     * @param fieldName
     * @param fieldValue
     * @param article
     * @throws Exception
     */
    public void updataIndex(String fieldName,String fieldValue,Article article) throws Exception{
    	IndexWriter indexWriter = LuceneUtils.getIndexWriter(); 
    	Term term = new Term(fieldName, fieldValue);
    	Document doc = ArticleUtils.articleToDocument(article);
    	/**
    	 * 1:设置更新的条件
    	 * 
    	 * 2:设置更新的内容的对象。。。。
    	 */
    	indexWriter.updateDocument(term, doc);
    	indexWriter.close();
    }
    /**
     * 显示第一页的数据0--10条
     * 第二页10-20条
     * @param keywords
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public List<Article> findIndex(String keywords,int start,int rows) throws IOException, ParseException{
    	IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();
    	//需要根据哪些字段进行检索
    	String[] fields ={"title","content"};
    	//不同的规则构造不同的子类。。。(做的项目 一般都是同一个 分词器)
    	//第一种类型的条件
    //	Query query = new TermQuery(new Term("author","毕加索"));
    	
    	//第二种类型的条件
    	QueryParser queryParser = new MultiFieldQueryParser( fields, LuceneUtils.getAnalyzer()); 
    	//title:keywords, content:keywords
    	Query query = queryParser.parse(keywords);
    	
    	TopDocs topDocs = indexSearcher.search(query, start+rows);
    	System.out.println("总记录数==total=="+topDocs.totalHits);
    	ScoreDoc scoreDocs [] = topDocs.scoreDocs; 
    	Article article = null;
    	List<Article> articlelist = new ArrayList<Article>();
    	int endResult = Math.min(scoreDocs.length, start+rows);
    	
    	for (int i = start;i<endResult; i ++){
    		article = new Article();
    		//docID lucene 的索引库里面有很多的document，lucene 为每一个document 定义一个编号，唯一的标识，自增长
    		int docID = scoreDocs[i].doc;
    		System.out.println("编号的标识"+docID);
    		//击中字段
    		Document document = indexSearcher.doc(docID);
    		
    		article.setId(Integer.parseInt(document.get("id")));
    		article.setTitle(document.get("title"));
    		article.setLink(document.get("link"));
    		article.setContent(document.get("content"));
    		article.setAuthor(document.get("author"));
    		
    		articlelist.add(article);
    		
    	}
    	return articlelist;
}
}
