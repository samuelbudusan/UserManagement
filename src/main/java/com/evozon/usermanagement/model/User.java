package com.evozon.usermanagement.model;

import com.evozon.usermanagement.utils.UserUtil;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@Table(name = "user", catalog = "userdb")
public class User  {
	
    @Id
    @Column(name = "username", unique =  true, nullable = false)
	private String userName;
	@Column(name = "email")
	private String email;
	@Column(name = "birthday")
	private Date birthdate;
	@Column(name = "phone")
	private String phone;
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "lastname")
	private String lastName;
	@Column(name = "password")
	private String password;
    @Column(name = "enabled")
    private Integer enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable( name = "user_group", catalog = "userdb", joinColumns = { @JoinColumn( name = "username")} , inverseJoinColumns = { @JoinColumn(name = "groupname")})
    @Fetch(FetchMode.SUBSELECT)
    private Set<Group> groups = new HashSet<Group>();

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private Set<UserRole> userRole = new HashSet<UserRole>(0);

	public User() {
	
	}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String email, Date birthdate, String phone, String firstName, String lastName, String password, Integer enabled) {
        this.userName = userName;
        this.email = email;
        this.birthdate = birthdate;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.enabled = enabled;
    }

    public User(String userName, String email, Date birthdate, String phone, String firstName, String lastName, String password, Integer enabled, Set<UserRole> userRole) {
        this.userName = userName;
        this.email = email;
        this.birthdate = birthdate;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.enabled = enabled;
        this.userRole = userRole;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Set<UserRole> getUserRole() {
        return this.userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    public void addRole(UserRole role) {
        this.userRole.add(role);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	/*@Override
	public String toString(){
		return userName + "," + email + "," + new SimpleDateFormat("dd/MM/yyyy").format(birthdate) + "," +
				phone + "," + firstName + "," + lastName + "," + password + ","+ enabled + "\n";
	}*/

    @Override
    public String toString() {
        return userName;
    }



}
