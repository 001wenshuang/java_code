package com.itheima.search.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchIndex {
	
	/**
	 * 需求：查询索引库
	 * 流程：
	 * 1、根据查询关键词查询索引库
	 * 2、根据定位索引，找到文档对象
	 * 3、返回文档对象
	 * @throws Exception 
	 */
	@Test
	public void queryIndex() throws Exception{
		//指定索引库存储位置
		String indexPath = "F:\\indexs";
		//读取索引库索引
		DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));		
		//创建查询索引库核心对象
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		
		//指定查询关键词
		String qName = "传智播客";
		//创建查询解析器,解析查询关键词
		//参数1：指定查询域字段
		//参数2：使用何种分词器
		//搜索时候，两边应该使用相同分词算法，创建索引库使用IK分词器，搜索时候必须使用IK分词器切分词语
		QueryParser qp = new QueryParser("title", new IKAnalyzer());
		//QueryParser qp = new QueryParser("title", new StandardAnalyzer());
		
		//解析关键词进行分词
		Query query = qp.parse(qName);
		
		//使用indexSearcher查询
		//返回文档概要信息：TopDocs :文档总记录数，文档Id，文档得分
		//返回得分最高的10条记录：匹配度越高，得分就越高
		TopDocs topDocs = indexSearcher.search(query, 10);
		//获取查询文档总记录数
		int totalHits = topDocs.totalHits;
		System.out.println("文档总记录数："+totalHits);
		//获取文档Id，文档得分数组
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		//循环文档Id，文档得分数组，获取单个文档Id，根据文档Id，获取文档对象
		for (ScoreDoc scoreDoc : scoreDocs) {
			
			//获取文档Id
			int docID = scoreDoc.doc;
			System.out.println("文档ID："+docID);
			//获取文档得分
			float score = scoreDoc.score;
			System.out.println("文档得分："+score);
			
			//使用搜索核心对象，搜索文档：根据文档Id唯一定位一个文档
			Document doc = indexSearcher.doc(docID);
			//获取文档数据
			//获取文档域Id
			String id = doc.get("id");
			System.out.println("文档域Id："+id);
			//获取文档标题
			String title = doc.get("title");
			System.out.println("文档标题："+title);			
			//获取文档内容
			String content = doc.get("content");
			System.out.println("文档内容："+content);
			
		}
		
	}

}
