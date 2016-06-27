package com.eversec.dao;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * 假设你的几行代码 可以完成某个功能，抽取成一个方法
 * 假设在某个业务逻辑层可以共用，往上抽取，
 * 假设在多个业务层可以共用，提炼成工具类
 * 假设你的这个业务方法在多个系统需要被使用。。发不成一个服务。。。
 * @author zhangp
 *
 */
public class LuceneUtils {
	private static Directory directory = null;
	private static IndexWriterConfig config = null;
	private static Analyzer analyzer = null;
	private static IndexWriter indexwriter = null;
	
	static{
		try {
			//directory = FSDirectory.open(Paths.get(Contants.INDEXURL));
			analyzer = new StandardAnalyzer();//单字分词器
			//config = new IndexWriterConfig(analyzer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Analyzer getAnalyzer() {
		return analyzer;
	}
	/**
	 * 
	 * @return 返回用于操作索引的对象。。。
	 * @throws IOException
	 */
	public static IndexWriter getIndexWriter() throws Exception{
		    directory = FSDirectory.open(Paths.get(Contants.INDEXURL));
			indexwriter = new IndexWriter(directory,new IndexWriterConfig(analyzer));
		
		return indexwriter;
	}
	/**
	 * 返回用于读取索引的对象。。。
	 * @return
	 * @throws IOException
	 */
	public static IndexSearcher getIndexSearcher() throws IOException{
		directory = FSDirectory.open(Paths.get(Contants.INDEXURL));
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		return indexSearcher;
	}
}
