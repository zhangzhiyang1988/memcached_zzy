package com.gyyx.java;

import java.io.Serializable;

public class ServerInfo  implements Serializable{

	private int code ;
	private int serverid ;
	private String servername ;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getserverid() {
		return serverid;
	}
	public void setserverid(int serverid) {
		this.serverid = serverid;
	}
	public String getServerName() {
		return servername;
	}
	public void setServerName(String serverName) {
		this.servername = serverName;
	}
	
	
}
