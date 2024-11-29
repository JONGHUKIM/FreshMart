package com.itwill.freshmart.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RecipeCommunity {

	public static final class Entity {
		public static final String TBL_RECIPECOMMUNITY = "RECIPECOMMUNITY";
		public static final String COL_LIKED = "LIKED";
		public static final String COL_ID = "ID";
		public static final String COL_TITLE = "TITLE";
		public static final String COL_CONTENT = "CONTENT";
		public static final String COL_AUTHOR = "AUTHOR";
		public static final String COL_CREATED_TIME = "CREATED_TIME";
		public static final String COL_MODIFIED_TIME = "MODIFIED_TIME";
	}

	private String liked;
	private Integer id;
	private String title;
	private String content;
	private String author;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;

	public RecipeCommunity(String liked, Integer id, String title, String content, String author,
			LocalDateTime createdTime, LocalDateTime modifiedTime) {
		this.liked = liked;
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.createdTime = createdTime;
		this.modifiedTime = modifiedTime;
	}

	public String getLiked() {
		return liked;
	}

	public void setLiked(String liked) {
		this.liked = liked;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "RecipeCommunity [liked=" + liked + ", id=" + id + ", title=" + title + ", content=" + content
				+ ", author=" + author + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + "]";
	}

	public static RecipeCommunityBuilder builder() {
		return new RecipeCommunityBuilder();
	}

	public static class RecipeCommunityBuilder {
		private String liked;
		private Integer id;
		private String title;
		private String content;
		private String author;
		private LocalDateTime createdTime;
		private LocalDateTime modifiedTime;

		private RecipeCommunityBuilder() {
		}

		public RecipeCommunityBuilder liked(String liked) {
			this.liked = liked;
			return this;
		}

		public RecipeCommunityBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public RecipeCommunityBuilder title(String title) {
			this.title = title;
			return this;
		}

		public RecipeCommunityBuilder content(String content) {
			this.content = content;
			return this;
		}

		public RecipeCommunityBuilder author(String author) {
			this.author = author;
			return this;
		}

		public RecipeCommunityBuilder createdTime(LocalDateTime createdTime) {

			this.createdTime = createdTime;

			return this;
		}

		public RecipeCommunityBuilder createdTime(Timestamp createdTime) {
			// Timestamp 타입 객체를 LocalDateTime 타입 객체로 변환해서 필드에 저장
			if (createdTime != null) {
				this.createdTime = createdTime.toLocalDateTime();
			}
			return this;
		}

		public RecipeCommunityBuilder modifiedTime(LocalDateTime modifiedTime) {
			this.modifiedTime = modifiedTime;
			return this;
		}

		public RecipeCommunityBuilder modifiedTime(Timestamp modifiedTime) {
			// Timestamp -> LocalDateTime
			if (modifiedTime != null) {
				this.modifiedTime = modifiedTime.toLocalDateTime();
			}
			return this;
		}

		// (6) 외부 클래스 타입을 리턴하는 메서드
		public RecipeCommunity build() {
			return new RecipeCommunity(liked, id, title, content, author, createdTime, modifiedTime);
		}
	}

}
