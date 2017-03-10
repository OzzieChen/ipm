package net.xssu.ipm.pojo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import net.xssu.ipm.annotation.Column;
import net.xssu.ipm.annotation.Pk;
import net.xssu.ipm.annotation.Table;

@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = -7988104448841302457L;
	@Pk
	private Integer id;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private String phone;
	@Column(name = "credentials_salt")
	private String credentialsSalt;//盐
	@Column
	private String status;
	@Column
	private String image; //头像
	@Column
	private String name;

	private Set<String> permissions;

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		User that = (User) o;
		if(!Objects.equals(id, that.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCredentialsSalt() {
		return credentialsSalt;
	}

	public void setCredentialsSalt(String credentialsSalt) {
		this.credentialsSalt = credentialsSalt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
}
