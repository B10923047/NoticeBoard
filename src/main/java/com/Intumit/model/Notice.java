package com.Intumit.model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long notice_id;
	
	@Column
	private String title;
	
	@Column
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String release_date;
	
	@Column
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String expiration_date;
	
	@Column
	private Long user_id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	public Notice() {
		
	}

	public Long getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(Long notice_id) {
		this.notice_id = notice_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public String getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
