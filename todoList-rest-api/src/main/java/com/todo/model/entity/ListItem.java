package com.todo.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * ListItem
 */
@Validated
@Entity
@Table(name = "List_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@ToString
public class ListItem  implements Serializable{
 
	private static final long serialVersionUID = -7323666814153515700L;

	@Id
	@SequenceGenerator(name = "seq_list_item", allocationSize = 1)
	@GeneratedValue(generator = "seq_list_item", strategy = GenerationType.SEQUENCE)
	private Long id;

	@JsonProperty("name")
	@Column(length = 500, name = "name")
	private String name = null;
 
	@JoinColumn(name = "list_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private TodoList list;
 
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ListItemStatus status;

	@JsonProperty("createDate")
	@Column(length = 50, name = "cdate")
	private Timestamp cdate = null;

	@JsonProperty("updateDate")
	@Column(length = 50, name = "udate")
	private Timestamp udate = null;
	
	@JsonProperty("description")
	@Column(length = 500, name = "description")
	private String description = null;
	

}
