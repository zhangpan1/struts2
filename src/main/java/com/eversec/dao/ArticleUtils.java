package com.eversec.dao;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexableField;

import com.eversec.bean.Article;

public class ArticleUtils {
	/**
	 * 将article 转换成document
	 * 无非article 的值设置到document里面去。。。
	 * @param article
	 * @return
	 */
	public static Document articleToDocument(Article article){
		Document document = new Document();
		
		IndexableField idfield = new IntField("id", article.getId(), Store.YES);
		StringField autorefield = new StringField("author", article.getAuthor(), Store.YES);
		StringField urlfield = new StringField("link", article.getLink(), Store.YES);
		TextField title = new TextField("title",article.getTitle(),Store.YES);
		TextField contTextField = new TextField("content",article.getContent(),Store.YES);
		
		//title.setBoost(3f);
		
		document.add(idfield);
		document.add(autorefield);
		document.add(urlfield);
		document.add(title);
		document.add(contTextField);
		return document;
		
	}
}
