package com.omega.backend.forum.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_id")
	private Long id;
	
	@ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,
			CascadeType.PERSIST,CascadeType.DETACH})
	@JoinColumn(name = "comment_id",nullable = false,referencedColumnName = "comment_id")
	private Comment comment;
	
	@ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,
			CascadeType.PERSIST,CascadeType.DETACH})
	@JoinColumn(name = "user_id",nullable = false,referencedColumnName = "user_id")
	private User user;
	
	
}
