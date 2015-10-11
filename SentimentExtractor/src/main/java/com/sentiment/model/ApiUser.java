package com.sentiment.model;

public class ApiUser {
	
	private DQUser user;
	private int apiId;
	private String apiAuthKey;
	
	public ApiUser(){
		
	}

	public ApiUser(DQUser user, int apiId, String apiAuthKey) {
		super();
		this.user = user;
		this.apiId = apiId;
		this.apiAuthKey = apiAuthKey;
	}

	public DQUser getUser() {
		return user;
	}

	public void setUser(DQUser user) {
		this.user = user;
	}

	public int getApiId() {
		return apiId;
	}

	public void setApiId(int apiId) {
		this.apiId = apiId;
	}

	public String getApiAuthKey() {
		return apiAuthKey;
	}

	public void setApiAuthKey(String apiAuthKey) {
		this.apiAuthKey = apiAuthKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + apiId;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		ApiUser other = (ApiUser) obj;
		if (apiId != other.apiId)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
