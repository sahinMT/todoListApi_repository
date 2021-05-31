package com.todo.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ToDoList
 */
@Validated
@Entity
@Table(name = "todo_list")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@ToString
public class TodoList  implements Serializable{
 
	private static final long serialVersionUID = -4958735157376247619L;

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "seq_list", allocationSize = 1)
	@GeneratedValue(generator = "seq_list", strategy = GenerationType.SEQUENCE)
	private Long id;

	@JsonProperty("name")
	@Column(length = 100, name = "name")
	private String name = null;

	@OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ListItem> items = new ArrayList<ListItem>();

	@JsonProperty("createDate")
	@Column(length = 30, name = "cdate")
	private Timestamp cdate = null;

	@JsonProperty("updateDate")
	@Column(length = 30, name = "udate")
	private Timestamp udate = null;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ListStatus status = null;
	
	@JsonProperty("description")
	@Column(length = 500, name = "description")
	private String description = null;
	 
	@JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
	private User user;
 
}
