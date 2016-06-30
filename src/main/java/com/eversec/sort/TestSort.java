package com.eversec.sort;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;

import com.eversec.dao.Contants;
import com.eversec.dao.LuceneUtils;

/**\
 * 
 * @author zhangp
 *
 */
public class TestSort {
	public static void main(String[] args) throws Exception {
	/*	//索引在硬盘里面...
		Directory directory1=FSDirectory.open(Paths.get(Contants.INDEXURL));
		
		IOContext ioContext=new IOContext();
		*/
		//索引放在内存当中...
		Directory directory=new RAMDirectory();
		
		IndexReader indexReader=DirectoryReader.open(directory);
		
		
		IndexSearcher indexSearcher=new IndexSearcher(indexReader);
		
		String fields []={"title"};
		
		QueryParser queryParser=new MultiFieldQueryParser(fields,LuceneUtils.getAnalyzer());
		//不同的规则构造不同的子类..
		//title:keywords  ，content:keywords
		Query query=queryParser.parse("抑郁症");
		
		Sort sort=new Sort();
		
		//升序
		//SortField sortField=new SortField("id", Type.INT);
		//降序				
		SortField sortField=new SortField("id", Type.INT,true);
		//SortField sortField = new SortField();
		//设置排序的字段...
		sort.setSort(sortField);
		
		
		TopDocs topDocs=indexSearcher.search(query,100,sort);
		
		for(ScoreDoc scoreDoc:topDocs.scoreDocs){
			Document document=indexSearcher.doc(scoreDoc.doc);
			System.out.println(document.get("id"));
			
		}
	}
}
