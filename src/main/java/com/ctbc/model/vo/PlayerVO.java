package com.ctbc.model.vo;

import java.io.Serializable;

public class PlayerVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String playerName;
	private String playerRole;
	private Integer playerAge;
	
	public PlayerVO() {
		super();
	}

	public PlayerVO(String playerName, String playerRole, Integer playerAge) {
		this.playerName = playerName;
		this.playerRole = playerRole;
		this.playerAge = playerAge;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerRole() {
		return playerRole;
	}

	public void setPlayerRole(String playerRole) {
		this.playerRole = playerRole;
	}

	public Integer getPlayerAge() {
		return playerAge;
	}

	public void setPlayerAge(Integer playerAge) {
		this.playerAge = playerAge;
	}

	@Override
	public String toString() {
		return "PlayerVO [playerName=" + playerName + ", playerRole=" + playerRole + ", playerAge=" + playerAge + "]";
	}
	
}
