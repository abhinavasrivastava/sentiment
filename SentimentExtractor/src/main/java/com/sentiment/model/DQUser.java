package com.sentiment.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DQUser implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6057757777101989681L;
	private long userId;
	private String userFname;
	private String userLname;
	private String emailId;
	private String password;
	private boolean dqStatus;
	
	private String emailVerificationLink;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserFname() {
		return userFname;
	}
	public void setUserFname(String userFname) {
		this.userFname = userFname;
	}
	public String getUserLname() {
		return userLname;
	}
	public void setUserLname(String userLname) {
		this.userLname = userLname;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isDqStatus() {
		return dqStatus;
	}
	public void setDqStatus(boolean dqStatus) {
		this.dqStatus = dqStatus;
	}
	public String getEmailVerificationLink() {
		return emailVerificationLink;
	}
	public void setEmailVerificationLink(String emailVerificationLink) {
		this.emailVerificationLink = emailVerificationLink;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (dqStatus ? 1231 : 1237);
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime
				* result
				+ ((emailVerificationLink == null) ? 0 : emailVerificationLink
						.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((userFname == null) ? 0 : userFname.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result
				+ ((userLname == null) ? 0 : userLname.hashCode());
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
		DQUser other = (DQUser) obj;
		if (dqStatus != other.dqStatus)
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (emailVerificationLink == null) {
			if (other.emailVerificationLink != null)
				return false;
		} else if (!emailVerificationLink.equals(other.emailVerificationLink))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userFname == null) {
			if (other.userFname != null)
				return false;
		} else if (!userFname.equals(other.userFname))
			return false;
		if (userId != other.userId)
			return false;
		if (userLname == null) {
			if (other.userLname != null)
				return false;
		} else if (!userLname.equals(other.userLname))
			return false;
		return true;
	}
	
	
}
