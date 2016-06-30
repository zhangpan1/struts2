package com.eversec.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import javax.management.Query;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogDocMergePolicy;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;

import com.eversec.dao.Contants;
import com.eversec.dao.LuceneUtils;

/**
 * 索引库优化
 * 
 * 
 * @author zhangp
 *
 */
public class TestOptimise {
		/**
		 * 第一种优化方式
		 * @throws IOException 
		 */
		public void testOptimise() throws IOException{
			
			//可以通过indexWriterConfig 这个对象来进行优化。。
			//在lucene 4.0 之后的版本会对索引进行自动的优化
			//改几个配置。。。
			Directory directory = FSDirectory.open(Paths.get(Contants.INDEXURL));
			IndexWriterConfig conf = new IndexWriterConfig(LuceneUtils.getAnalyzer());
			
			//在lucene里面的都是0配置的。。。  都是通过设置对象的参数来进行配置。。。
			
			/**
			 * MergePolicy 设置合并规则。。。
			 */
			LogDocMergePolicy mergePolicy =new LogDocMergePolicy();
			/**
			 * 1：mergeFactor
			 * 当这个值越小，更小的内存被运用在创建索引的时候，
			 * 搜索的时候越快。。创建索引的时候越慢。。
			 * 
			 * 当这个值越大，更多的内存被运用在创建索引的时候，检索的时候越慢，创建的时候越快
			 * 
			 * smaller value 要大于2  小于10
			 */
			mergePolicy.setMergeFactor(6);
			
			conf.setMergePolicy(mergePolicy);
			IndexWriter indexWriter = new IndexWriter(directory, conf);
		
		}
		
		 /**
		  * 第二种方式
		  * 排除停用词，排除停用，被分词器过滤掉，词就不会建立索引，索引文件就会变小
		  * 这样搜索的时候就会变快
		  * @throws IOException
		  */
			public void testOptimise2() throws IOException{
			
			}
		/**
		 * 第三种方式
		 * 索引库分区
		 */
			public void testOptimise3(){
				
			}
			
			
			/**
			 * 第四种方式
			 * @throws IOException 
			 * @throws ParseException 
			 * 
			 */
			public void testOptimise4() throws IOException, ParseException{
				//索引在硬盘里面...
				Directory directory1=FSDirectory.open(Paths.get(""));
				
				IOContext ioContext=new IOContext();
				
				//索引放在内存当中...
				Directory directory=new RAMDirectory();
				
				IndexReader indexReader=DirectoryReader.open(directory);
	
				IndexSearcher indexSearcher=new IndexSearcher(indexReader);
				
				String fields []={"title"};
				
				QueryParser queryParser=null;//new MultiFieldQueryParser(fields,LuceneUtils.getAnalyzer());
				//不同的规则构造不同的子类..
				//title:keywords  ，content:keywords
				org.apache.lucene.search.Query query=queryParser.parse("抑郁症");
				
				TopDocs topDocs=indexSearcher.search(query, 100);
				
				System.out.println(topDocs.totalHits);
			}
}
