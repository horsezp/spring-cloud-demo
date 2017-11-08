package com.leo.property.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class PropertyInfoProcessor implements PageProcessor {

	static int size;

	static List<String> sellRecords = Collections.synchronizedList(new ArrayList<String>());

	// 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	public void process(Page page) {
		//
		if (page.getUrl().regex("http://www.fsgtgh.gov.cn/ywzt/tdgl/tdgycy/index.htm").match()) {
			String foot = page.getHtml().xpath("//*[@id=\"sub_table\"]/div/script").get();
			String pageSize = foot.trim().substring(foot.indexOf("(") + 1, foot.indexOf(")")).split(",")[0];
			int pageSizeValue = Integer.parseInt(pageSize);
			for (int i = 1; i < pageSizeValue - 1; i++) {
				page.addTargetRequest("http://www.fsgtgh.gov.cn/ywzt/tdgl/tdgycy/index_" + i + ".htm");
			}
		}

		if (page.getUrl().regex("http://www.fsgtgh.gov.cn/ywzt/tdgl/tdgycy/index.*htm").match()) {

			List<String> all = page.getHtml().links().regex(".*html").all();
			page.addTargetRequests(all);
		}

		if (page.getUrl().regex("http://gtcjswj.chancheng.gov.cn/gtcjswj/tdzb/.*.shtml").match()) {

			if (page.getHtml().regex(".*交易结果公布.*").match()) {

				size++;

				if (page.getHtml().xpath("//*[@id=\"zoomcon\"]/p[1]/font/b/span/text()").get() != null) {

					List<String> a = page.getHtml().xpath("//*[@id=\"zoomcon\"]/p/font/b/span/text()").all();
					System.out.println(a.size());
					System.out.println(a);

					List<String> b = page.getHtml().xpath("//*[@id=\"zoomcon\"]/p/font/span/text()").all();
					System.out.println(b.size());
					System.out.println(b);

					List<String> c = page.getHtml().xpath("//*[@id=\"zoomcon\"]/p/font/span/span/text()").all();
					System.out.println(c.size());
					System.out.println(c);

					List<String> d = page.getHtml().xpath("//*[@id=\"zoomcon\"]/p/font/span/span/span/text()")
							.all();
					System.out.println(d.size());
					 System.out.println(d);
					 
					 //以； 未最终

					Map<String, String> map = new HashMap<>();
					for (int i = 0; i < a.size(); i++) {
						StringBuffer sb = new StringBuffer();
						append(sb, a.get(i));
						append(sb, b.get(i));
						append(sb, c.get(i));
						append(sb, d.get(i));
						String[] values = sb.toString().split("：");
						if (values.length >= 2) {
							map.put(values[0], values[1]);
						}
					}
					Gson gson = new GsonBuilder().create();
					String json = gson.toJson(map);
					System.out.println(json);
					sellRecords.add(json);

				} else if (page.getHtml().xpath("//*[@id=\"allStyleDIV\"]/div/p[1]/span[1]/text()").get() != null) {
					//
					List<String> a = page.getHtml().xpath("//*[@id=\"allStyleDIV\"]/div/p/span[1]/text()").all();
					// System.out.println(a.size());
					// System.out.println(a);

					List<String> b = page.getHtml().xpath("//*[@id=\"allStyleDIV\"]/div/p/span/span/text()").all();
					// System.out.println(b.size());
					// System.out.println(b);

					List<String> c = page.getHtml().xpath("//*[@id=\"allStyleDIV\"]/div/p/span/span/span/text()").all();
					// System.out.println(c.size());
					// System.out.println(c);

					List<String> d = page.getHtml().xpath("//*[@id=\"allStyleDIV\"]/div/p/span/span/span/span/text()")
							.all();
					// System.out.println(d.size());
					// System.out.println(d);

					Map<String, String> map = new HashMap<>();
					for (int i = 0; i < a.size(); i++) {
						StringBuffer sb = new StringBuffer();
						append(sb, a.get(i));
						append(sb, b.get(i));
						append(sb, c.get(i));
						append(sb, d.get(i));
						String[] values = sb.toString().split("：");
						if (values.length >= 2) {
							map.put(values[0], values[1]);
						}
					}
					Gson gson = new GsonBuilder().create();
					String json = gson.toJson(map);
					System.out.println(json);
					sellRecords.add(json);
				}

			}

		}

	}

	public void append(StringBuffer sb, String value) {
		if (value != null) {
			sb.append(value);
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) throws IOException {

		long startTime, endTime;
		System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
		startTime = System.currentTimeMillis();
		// 开启5个线程，启动爬虫
		Spider.create(new PropertyInfoProcessor())
				.addUrl("http://gtcjswj.chancheng.gov.cn/gtcjswj/tdzb/201406/f0a0b7a5dcbf4e98b5525d054b1127c3.shtml")
				.thread(5).run();
		// http://gtcjswj.chancheng.gov.cn/gtcjswj/tdzb/201408/7e26c9e5094a41ad96d174c558caff01.shtml
		// http://www.fsgtgh.gov.cn/ywzt/tdgl/tdgycy/index.htm
		// http://gtcjswj.chancheng.gov.cn/gtcjswj/tdzb/201406/f0a0b7a5dcbf4e98b5525d054b1127c3.shtml

		endTime = System.currentTimeMillis();
		System.out.println("【爬虫结束】共抓取" + size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒！");

		System.out.println("输出交易结果文件");

		FileUtils.writeLines(new File("交易结果.txt"), sellRecords);

	}

}
