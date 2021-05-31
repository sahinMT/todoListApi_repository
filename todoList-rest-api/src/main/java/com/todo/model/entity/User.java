package com.todo.model.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "td_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@ToString
public class User {

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "seq_user", allocationSize = 1)
	@GeneratedValue(generator = "seq_user", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(length = 100, name = "name")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private UserStatus status;

	@Column(length = 100, name = "cdate")
	private Timestamp registerDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TodoList> todoLists = new ArrayList<TodoList>();
}
