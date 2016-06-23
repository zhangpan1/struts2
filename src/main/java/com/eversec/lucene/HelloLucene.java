package com.eversec.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;


/**
 * 使用lucene建立索引
 * @author zhangp
 *
 */
public class HelloLucene {
	/**
	 * 使用indexWriter 对数据建立索引
	 * @throws IOException 
	 */
	    //@Test
	    public void TestLucene() throws IOException{
		//索引存放的位置。。。。。索引库(java7的新写法)
		Directory diresctoy = FSDirectory.open(Paths.get("indexDir/"));
		//lucene 当前使用的匹配版本
		//Version matchVersion = Version.LUCENE_6_1_0;
		//分词器 ---决定我们怎么做索引(对文本进行分词)
		// 我是 中国人  
		//也是醉了，正能量，逗逼，带你装b带你飞
		Analyzer analyzer = new StandardAnalyzer();
		//索引写入的配置
		IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
		
		//索引Document  里面有很多的字段。。。
		Document doc = new Document();
		/**
		 * 1： id 字段名称
		 * 2：字段对应的值
		 */
		
		IndexableField field = new IntField("id", 1,Store.YES );
		IndexableField title = new StringField("titld", "JAVA培训，传智播客专职Java12年",Store.YES);
		IndexableField content = new TextField("content","java培训，传智播客专职java12年,进来看看同学们的呐喊",Store.YES);
		
		IndexWriter indexWriter = new IndexWriter(diresctoy,writerConfig);
		//索引库里面的要遵守一定的结构，(索引结构。。。) document
		
		doc.add(field);
		doc.add(title);
		doc.add(content);
		indexWriter.addDocument(doc);
        indexWriter.close();
	    }
	    @Test
		public void testSearch() throws IOException{
	    	//索引存放的位置....
			Directory directory=FSDirectory.open(Paths.get("indexDir/"));
			
			IndexReader indexReader=DirectoryReader.open(directory);
			
			//通过indexSearcher 去检索索引目录...
			IndexSearcher indexSearcher=new IndexSearcher(indexReader);
			
			//我们以后只要根据索引查找，整个过程肯定要分两次..
			
			//这个是一个搜索条件..，通过定义条件来进行查找...
			//term 我需要根据那个字段进行检索，字段对应的值...
			Query query=new TermQuery(new Term("title","JAVA培训，传智播客专职Java12年"));
			
			//搜索先搜索索引目录..
			//找到符合query 条件的前面N条记录...
			TopDocs topDocs=indexSearcher.search(query,100);
			System.out.println("总记录数==="+topDocs.totalHits);
			ScoreDoc scoreDocs[]=topDocs.scoreDocs;
			//返回一个击中..
			for(ScoreDoc scoreDoc:scoreDocs){
				int docID=scoreDoc.doc;
				Document document=indexSearcher.doc(docID);
				System.out.println(document.get("id"));
				System.out.println(document.get("title"));
				System.out.println(document.get("content"));
				
			}
			
		}
	
		
	
}
