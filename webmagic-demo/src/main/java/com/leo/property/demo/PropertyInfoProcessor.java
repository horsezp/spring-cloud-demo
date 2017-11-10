package com.leo.property.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class PropertyInfoProcessor implements PageProcessor {

	static AtomicInteger size = new AtomicInteger(0);

	static List<String> sellRecords = Collections.synchronizedList(new ArrayList<String>());

	static List<String> sellDeclears = Collections.synchronizedList(new ArrayList<String>());

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

		if (page.getUrl().regex("http://www.fsgtgh.gov.cn/ywzt/tdgl/tdgycy/\\d+/t\\d+_\\d+.html").match()) {

			if (page.getHtml().regex(".*出让公告.*").match()) {
				size.incrementAndGet();
				Map<String, String> map = new HashMap<>();

				// try another case
				// //*[@id="c_news"]/div/font/strong/font/span/span/o:p/span/font/span/o:p/p[2]/span/font/font/text()[1]

				for (int i = 1; i <= 6; i++) {
					List<String> value1 = page.getHtml()
							.xpath("//*[@id=\"c_news\"]/div/div/table/tbody/tr[1]/td[" + i + "]/p/font/b/span/text()")
							.all();
					// System.out.println(value1);

					List<String> value2 = page.getHtml()
							.xpath("//*[@id=\"c_news\"]/div/div/table/tbody/tr[2]/td[" + i + "]/p/font/span/text()")
							.all();
					// System.out.println(value2);

					List<String> value3 = page.getHtml().xpath(
							"//*[@id=\"c_news\"]/div/div/table/tbody/tr[2]/td[" + i + "]/p/font/span/span/text()")
							.all();
					// System.out.println(value3);
					if (value1 != null && value1.size() > 0 && value2 != null && value2.size()>0) {

						String k = String.join("", value1);
						if (value3 != null && value3.size() > 0) {
							StringBuffer sb = new StringBuffer();
							for (int t = 0; t < value2.size(); t++) {
								if (t < value3.size()) {
									String s = value2.get(t) + value3.get(t);
									sb.append(s);
								} else {
									sb.append(value2.get(t));
								}
							}
							map.put(k, sb.toString());
						} else {
							map.put(k, String.join("", value2));
						}

					}
				}
				if (!map.isEmpty()) {
					Gson gson = new GsonBuilder().create();
					String json = gson.toJson(map);
					// System.out.println(json);
					sellDeclears.add(json);
				}
			}

		}

		if (page.getUrl().regex("http://gtcjswj.chancheng.gov.cn/gtcjswj/tdzb/.*.shtml").match()) {

			if (page.getHtml().regex(".*交易结果公布.*").match()) {

				size.incrementAndGet();

				if (page.getHtml().xpath("//*[@id=\"zoomcon\"]/p[1]/font/b/span/text()").get() != null) {
					Map<String, String> map = new HashMap<>();
					for (int i = 0; i <= 10; i++) {
						String value1 = page.getHtml().xpath("//*[@id=\"zoomcon\"]/p[" + i + "]/font/b/span/text()")
								.get();
						String value2 = page.getHtml().xpath("//*[@id=\"zoomcon\"]/p[" + i + "]/font/span/text()")
								.get();
						String value3 = page.getHtml().xpath("//*[@id=\"zoomcon\"]/p[" + i + "]/font/span/span/text()")
								.get();
						String value4 = page.getHtml()
								.xpath("//*[@id=\"zoomcon\"]/p[" + i + "]/font//span/span/span/text()").get();
						String value5 = page.getHtml()
								.xpath("//*[@id=\"zoomcon\"]/p[" + i + "]/font//span/span/span/span/text()").get();
						String value6 = page.getHtml()
								.xpath("//*[@id=\"zoomcon\"]/p[" + i + "]/font//span/span/span/span/span/text()").get();
						StringBuffer sb = new StringBuffer();
						append(sb, value1);
						append(sb, value2);
						append(sb, value3);
						append(sb, value4);
						append(sb, value5);
						append(sb, value6);
						String[] values = sb.toString().split("：");
						if (values.length >= 2) {
							map.put(values[0], values[1]);
						}
					}
					Gson gson = new GsonBuilder().create();
					String json = gson.toJson(map);
					// System.out.println(json);
					sellRecords.add(json);

				} else if (page.getHtml().xpath("//*[@id=\"allStyleDIV\"]/div/p[1]/span[1]/text()").get() != null) {
					Map<String, String> map = new HashMap<>();
					for (int i = 0; i <= 10; i++) {
						String value1 = page.getHtml().xpath("//*[@id=\"allStyleDIV\"]/div/p[" + i + "]/span[1]/text()")
								.get();
						String value2 = page.getHtml()
								.xpath("//*[@id=\"allStyleDIV\"]/div/p[" + i + "]/span/span/text()").get();
						String value3 = page.getHtml()
								.xpath("//*[@id=\"allStyleDIV\"]/div/p[" + i + "]/span/span/span/text()").get();
						String value4 = page.getHtml()
								.xpath("//*[@id=\"allStyleDIV\"]/div/p[" + i + "]/span/span/span/span/text()").get();
						String value5 = page.getHtml()
								.xpath("//*[@id=\"allStyleDIV\"]/div/p[" + i + "]/span/span/span/span/span/text()")
								.get();
						String value6 = page.getHtml()
								.xpath("//*[@id=\"allStyleDIV\"]/div/p[" + i + "]/span/span/span/span/span/span/text()")
								.get();
						StringBuffer sb = new StringBuffer();
						append(sb, value1);
						append(sb, value2);
						append(sb, value3);
						append(sb, value4);
						append(sb, value5);
						append(sb, value6);
						String[] values = sb.toString().split("：");
						if (values.length >= 2) {
							map.put(values[0], values[1]);
						}
					}
					Gson gson = new GsonBuilder().create();
					String json = gson.toJson(map);
					// System.out.println(json);
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
		Spider.create(new PropertyInfoProcessor()).addUrl("http://www.fsgtgh.gov.cn/ywzt/tdgl/tdgycy/index.htm")
				.thread(5).run();
		// http://gtcjswj.chancheng.gov.cn/gtcjswj/tdzb/201408/7e26c9e5094a41ad96d174c558caff01.shtml
		// http://www.fsgtgh.gov.cn/ywzt/tdgl/tdgycy/index.htm
		// http://gtcjswj.chancheng.gov.cn/gtcjswj/tdzb/201406/f0a0b7a5dcbf4e98b5525d054b1127c3.shtml
		// http://www.fsgtgh.gov.cn/ywzt/tdgl/tdgycy/201611/t20161114_6049042.html

		endTime = System.currentTimeMillis();
		System.out.println("【爬虫结束】共抓取" + size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒！");

		System.out.println("输出交易结果文件");

		FileUtils.writeLines(new File("交易结果.txt"), sellRecords);

		FileUtils.writeLines(new File("出让公告.txt"), sellDeclears);

	}

}
