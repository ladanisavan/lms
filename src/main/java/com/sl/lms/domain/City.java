package com.sl.lms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CITIES_MSTR")
@Data
@NoArgsConstructor
public class City {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name = "NAME")
	@NotBlank
	@Size(max=30)
	private String name;
	
	@Column(name = "STATE_ID")
	@NotNull
	private Long stateId;
}
