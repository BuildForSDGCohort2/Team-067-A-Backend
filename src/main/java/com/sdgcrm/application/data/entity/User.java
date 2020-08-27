package com.sdgcrm.application.data.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NaturalId
    @NotBlank
    @Email
    private String email;
   
    
    private long phone;

    private String theme= "dark";

    
    
    
    @NotBlank
    private String location;



    private double lpgWeight;
    private String lpgType;
    private double lpgLevel;
    
    
    

    @NotBlank
    @Size(min=6, max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String name, String username, String email, String password, String location, double lpgLevel,
    		double lpgWeight, String lpgType) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.location = location;
        this.lpgWeight=lpgWeight;
        this.lpgType=lpgType;
        this.lpgLevel=lpgLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getLpgWeight() {
		return lpgWeight;
	}

	public void setLpgWeight(double lpgWeight) {
		this.lpgWeight = lpgWeight;
	}

	public String getLpgType() {
		return lpgType;
	}

	public void setLpgType(String lpgType) {
		this.lpgType = lpgType;
	}

	public double getLpgLevel() {
		return lpgLevel;
	}

	public void setLpgLevel(double lpgLevel) {
		this.lpgLevel = lpgLevel;
	}

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}