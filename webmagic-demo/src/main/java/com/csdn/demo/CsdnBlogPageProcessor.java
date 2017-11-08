package com.csdn.demo;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * CSDN��������
 * 
 * @describe ������ȡָ���û���csdn�����������£������浽���ݿ��С�
 * @date 2016-4-30
 * 
 * @author steven
 * @csdn qq598535550
 * @website lyf.soecode.com
 */
public class CsdnBlogPageProcessor implements PageProcessor {

	private static String username = "qq598535550";// ����csdn�û���
	private static int size = 0;// ��ץȡ������������

	// ץȡ��վ��������ã����������롢ץȡ��������Դ�����
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	// process�Ƕ��������߼��ĺ��Ľӿڣ��������д��ȡ�߼�
	public void process(Page page) {
		// �б�ҳ
		if (!page.getUrl().regex("http://blog\\.csdn\\.net/" + username + "/article/details/\\d+").match()) {
			// �����������ҳ
			page.addTargetRequests(page.getHtml().xpath("//div[@id='article_list']").links()// �޶������б��ȡ����
					.regex("/" + username + "/article/details/\\d+")
					.replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")// �����滻�������urlת���ɾ���url
					.all());
			// ��������б�ҳ
			page.addTargetRequests(page.getHtml().xpath("//div[@id='papelist']").links()// �޶������б�ҳ��ȡ����
					.regex("/" + username + "/article/list/\\d+")
					.replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")// �����滻�������urlת���ɾ���url
					.all());
			// ����ҳ
		} else {
			size++;// ����������1
			// ��CsdnBlog������ץȡ�������ݣ�����������ݿ�
			CsdnBlog csdnBlog = new CsdnBlog();
			// ���ñ��
			csdnBlog.setId(Integer.parseInt(
					page.getUrl().regex("http://blog\\.csdn\\.net/" + username + "/article/details/(\\d+)").get()));
			// ���ñ���
			csdnBlog.setTitle(
					page.getHtml().xpath("//div[@class='article_title']//span[@class='link_title']/a/text()").get());
			// ��������
			csdnBlog.setDate(
					page.getHtml().xpath("//div[@class='article_r']/span[@class='link_postdate']/text()").get());
			// ���ñ�ǩ�������ж������,���ָ
			csdnBlog.setTags(listToString(page.getHtml()
					.xpath("//div[@class='article_l']/span[@class='link_categories']/a/allText()").all()));
			// ������𣨿����ж������,���ָ
			csdnBlog.setCategory(
					listToString(page.getHtml().xpath("//div[@class='category_r']/label/span/text()").all()));
			// �����Ķ�����
			csdnBlog.setView(Integer.parseInt(page.getHtml().xpath("//div[@class='article_r']/span[@class='link_view']")
					.regex("(\\d+)���Ķ�").get()));
			// ������������
			csdnBlog.setComments(Integer.parseInt(page.getHtml()
					.xpath("//div[@class='article_r']/span[@class='link_comments']").regex("\\((\\d+)\\)").get()));
			// �����Ƿ�ԭ��
			csdnBlog.setCopyright(page.getHtml().regex("bog_copyright").match() ? 1 : 0);
			// �Ѷ���������ݿ�
			// �Ѷ����������̨
			System.out.println(csdnBlog);
		}
	}

	// ��listת��Ϊstring����,�ָ�
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	public static void main(String[] args) {
		long startTime, endTime;
		System.out.println("�����濪ʼ�������ĵȴ�һ�����ݵ���������...");
		startTime = System.currentTimeMillis();
		// ���û�������ҳ��ʼץ������5���̣߳���������
		Spider.create(new CsdnBlogPageProcessor()).addUrl("http://blog.csdn.net/" + username).thread(5).run();
		endTime = System.currentTimeMillis();
		System.out.println("�������������ץȡ" + size + "ƪ���£���ʱԼ" + ((endTime - startTime) / 1000) + "�룬�ѱ��浽���ݿ⣬����գ�");
	}
}