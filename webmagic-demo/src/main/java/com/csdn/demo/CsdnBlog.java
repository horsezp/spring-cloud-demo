package com.csdn.demo;

public class CsdnBlog {

	private int id;// ���

	private String title;// ����

	private String date;// ����

	private String tags;// ��ǩ

	private String category;// ����

	private int view;// �Ķ�����

	private int comments;// ��������

	private int copyright;// �Ƿ�ԭ��

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public int getCopyright() {
		return copyright;
	}

	public void setCopyright(int copyright) {
		this.copyright = copyright;
	}

	@Override
	public String toString() {
		return "CsdnBlog [id=" + id + ", title=" + title + ", date=" + date + ", tags=" + tags + ", category="
				+ category + ", view=" + view + ", comments=" + comments + ", copyright=" + copyright + "]";
	}

}