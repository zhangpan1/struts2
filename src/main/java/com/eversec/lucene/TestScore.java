package com.eversec.lucene;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.eversec.dao.LuceneUtils;

/**
 * 测试得分
 * @author zhangp
 *
 */
public class TestScore {
	public static void main(String[] args) throws IOException, ParseException {
		//内容一样，关键字一样，搜索的内容一样
		//我们可以人工的去干预这个得分
		//得分跟搜索关键字在文章当中出现的频率，次数和位置有关系。。。
		testsearch("抑郁症");
		/**
		 * seo:
		 * 百度会把哪些数据获取过去建立索引
		 * 
		 */
	}
	
	
	
	
	public static void testsearch(String keywords) throws IOException, ParseException{
		IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();
    	//需要根据哪些字段进行检索
    	String[] fields ={"title"};
    	//不同的规则构造不同的子类。。。(做的项目 一般都是同一个 分词器)
    	//第一种类型的条件
    //	Query query = new TermQuery(new Term("author","毕加索"));
    	
    	//第二种类型的条件
    	QueryParser queryParser = new MultiFieldQueryParser( fields, LuceneUtils.getAnalyzer()); 
    	//title:keywords, content:keywords
    	Query query = queryParser.parse(keywords);
    	
    	TopDocs topDocs = indexSearcher.search(query, 100);
    	System.out.println("总记录数==total=="+topDocs.totalHits);
    	ScoreDoc scoreDocs [] = topDocs.scoreDocs; 
    	for (ScoreDoc scoreDoc:scoreDocs) {
    		//VSM的算法
    		System.out.println("文档编号，相当于lucene的唯一标识"+scoreDoc.doc+"======得分======"+scoreDoc.score);
    		
    	}
	}
}
