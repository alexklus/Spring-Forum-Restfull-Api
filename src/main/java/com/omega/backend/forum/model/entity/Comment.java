package com.omega.backend.forum.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="comment_id")
	private Long id;
	
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_id",nullable = false,referencedColumnName = "topic_id")
	private Topic topic;
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false, referencedColumnName = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Like> likes; 
	
	public void addLike(Like like) {
		likes.add(like);
		like.setComment(this);
	}
	
	public void removeLike(Like like) {
		likes.remove(like);
	}
}
