package com.ibs.entities;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UserDetails")
@NoArgsConstructor
@Getter
@Setter
public class User1 {
	
//	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_JUST_FOR_TEST", allocationSize=100)
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accNo;
	
	
	private String firstName;
	private String lastName;
	private String mobile;
	private String email;
	private String aadharNo;
	private String panNo;
	private String dob;
	private int accBalance;
	
	
	public void setIsApproved(boolean val)
	{
		this.isApproved = val;
		
	}
	
	public boolean getIsApproved()
	{
		return this.isApproved;
	}
	
	
//	@Column(columnDefinition = "boolean default false")
	private boolean isApproved;
	
//	@OneToOne(mappedBy = "user1")
////	@JsonBackReference
//	private Account account;
	
	
		
}