package com.techvisio.eserve.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TB_COMMENT")
public class Comment extends BasicFileds implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="COMMENT_ID")
	private Long commentId;
	@Column(name="COMMENT")
	private String comment;
	@Column(name="WORKITEM_ID")
	private Long workItemId;
	@Column(name="COMMENT_TYPE")
	private String commentType;
	@Transient
	private String commentDateString;
	
	public String getCommentDateString() {
		
		Date commentDate = getCreatedOn();
		if (commentDate == null)
			return null;

		try {

			DateFormat outputFormatter = new SimpleDateFormat("MMM dd,yyyy hh:mm");
			String startDateString = outputFormatter.format(commentDate);
			return startDateString;

		} catch (Exception e) {

		}
		return null;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(Long workItemId) {
		this.workItemId = workItemId;
	}
	public String getCommentType() {
		return commentType;
	}
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	public void setCommentDateString(String commentDateString) {
		this.commentDateString = commentDateString;
	}
	
}
