package com.eversec.sort;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Bits;

import com.eversec.dao.LuceneUtils;

public class TestFilter {
	public static void main(String[] args) throws Exception{
		/**
		 * 1：需要根据哪个字段进行过滤
		 * 2：字段对应的最小值
		 * 3：字段对应的最大值
		 * 4：是否包含最小值
		 * 5：是否包含最大值
		 */
		Directory directory=new RAMDirectory();
		
		IndexReader indexReader=DirectoryReader.open(directory);
		
		
		IndexSearcher indexSearcher=new IndexSearcher(indexReader);
		
		String fields []={"title"};
		
		QueryParser queryParser=new MultiFieldQueryParser(fields,LuceneUtils.getAnalyzer());
		//不同的规则构造不同的子类..
		//title:keywords  ，content:keywords
		Query query=queryParser.parse("抑郁症");
		//过滤
		Filter filter = NumericRangeFilter.newIntRange("id", 0, 10, true, true);
			
		
		
		TopDocs topDocs=indexSearcher.search(query, filter,100);
		
		for(ScoreDoc scoreDoc:topDocs.scoreDocs){
			Document document=indexSearcher.doc(scoreDoc.doc);
			System.out.println(document.get("id"));
			
		}
	}
}
