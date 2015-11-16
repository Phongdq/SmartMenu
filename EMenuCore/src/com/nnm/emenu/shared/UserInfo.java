package com.nnm.emenu.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserInfo implements Serializable, IsSerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String password;
	private String name;
	private int roleId;
	private int state;

	public static final int ROLE_SERVER = 1;
	public static final int ROLE_CHEF = 2;
	public static final int ROLE_ADMIN = 3;

	public static final int STATE_OFFLINE = 0;
	public static final int STATE_ONLINE = 1;
	public static final int STATE_LOCK = 2;

	public UserInfo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
