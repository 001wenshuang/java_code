package com.example.demo_lucene;



import org.apache.lucene.analysis.Analyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;




import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateIndex {

	/**
	 * 需求：创建索引库
	 * @throws Exception
	 */
	@Test
	public void addIndex() throws Exception{
		//指定索引库存储位置
		String indexPath = "D:\\008_lucene";
		//Lucene管理索引库存储空间
		Directory directory = FSDirectory.open(new File(indexPath));

		//创建索引库写入对象核心配置对象:
		//创建基本分词器
		//a、基本分词器
		//Analyzer analyzer = new StandardAnalyzer();
		//b、cjk分词器  二元分词
		//缺点：两两组合，不管是否是词语
		//Analyzer analyzer = new CJKAnalyzer();
		//c、smartChineseAnalyzer 聪明的中国人分词器
		//Analyzer analyzer = new SmartChineseAnalyzer();
		//d、Ik分词器:支持扩展词汇，支持停用词汇
		Analyzer analyzer = new IKAnalyzer();
		//参数1：指定使用Lucene 版本
		//参数2：指定创建索引库使用分词器
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);

		//创建索引库写入索引库核心对象
		IndexWriter indexWriter = new IndexWriter(directory, iwc);

		//创建文档对象
		//真实开发中：
		//场景1：搜索word文档，读取word文档，把word文档数据变成文档对象，解析成单词，存储索引库，搜索索引库数据，搜索到文档
		//场景2：查询数据库，把数据库数据变成文档对象，解析，把索引放入索引库
		//场景3：爬虫爬取网页，解析网页，把网页数据变成文档对象，把索引写入索引库
		Document doc = new Document();
		//添加模拟数据
		//StringField:不分词，索引，Store.NO：不存储，Store.YES:存储
		//参数1：域名称
		//参数2:域对应值
		//参数3：域值是否存储
		doc.add(new StringField("id", "101010110", Store.NO));
		//TextField:分词，索引
		doc.add(new TextField("title", "白富美凤姐，在传智播客学习UI，lucene经典教程", Store.YES));
		//TextField:分词，索引
		doc.add(new TextField("content", "Lucene是apache下的一个开放源代码的全文检索引擎工具包。"
				+ "提供了完整的查询引擎和索引引擎，部分文本分析引擎,时间的朋友,你在何方", Store.NO));
		//写入索引库
		indexWriter.addDocument(doc);
		//提交
		indexWriter.commit();
		//关闭资源
		indexWriter.close();


	}

}
