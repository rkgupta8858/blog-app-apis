package com.saar.blog.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class User implements UserDetails{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;
	private String about;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Post>posts=new ArrayList<>();
	/*जब हम Hibernate या JPA (Java Persistence API) का उपयोग करते हैं और किसी Entity में रिलेशनशिप (जैसे @OneToMany, @ManyToOne, @OneToOne, या @ManyToMany) पर CascadeType.ALL सेट करते हैं, तो इसका मतलब है कि पैरेंट (मूल) एंटिटी पर जो भी ऑपरेशन किया जाएगा, वही ऑपरेशन उसके जुड़े हुए चाइल्ड एंटिटी पर भी लागू होगा।*/

	 
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="user_role",
		joinColumns = @JoinColumn(name="user", referencedColumnName = "id"),
	inverseJoinColumns=@JoinColumn(name="role", referencedColumnName="id")	)
	private Set<Role> roles=new HashSet<>();
	
	
	// This method returns the roles (authorities) of the user. It converts the roles into SimpleGrantedAuthority objects and returns them as a list.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		List<SimpleGrantedAuthority>authories= this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
		return authories;
	}
	@Override
	public String getUsername() {//This method returns the user's email as the username.
		return this.email;
	}
	@Override
	public boolean isAccountNonExpired() { //This method checks if the user's account has expired. It returns true, meaning the account is not expired.
		// TODO Auto-generated method stub
		return true;
	}
	//This method checks if the user's account is locked. It returns true, meaning the account is not locked.
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	//This method checks if the user's credentials (like password) have expired. It returns true, meaning the credentials are not expired.
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {// This method checks if the user's account is enabled. It returns true, meaning the account is enabled.
		// TODO Auto-generated method stub
		return true;
	}
}
