package com.gyyx.java;

import java.util.HashMap;
import java.util.List;

public interface ServerMapper {

	public List<ServerInfo> queryListByServerId(int serverid);
	 public void updateServerName(HashMap<String,String> map);
}
