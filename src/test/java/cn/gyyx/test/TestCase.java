package cn.gyyx.test;

import java.util.HashMap;

import org.junit.Test;

import com.gyyx.java.HomeController;

public class TestCase {

	HomeController controler =null;
	
	public TestCase(){
		controler = new HomeController();
	}
	 
	@Test
	public void testGetServerdata(){
		String servers = controler.getServerList(null, null, 1);
		System.out.println(servers);
	}
	 
	@Test
	public void testUpdatedata(){
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("code", "1");
		map.put("name", "abcd");
		controler.updateServerName(map);
	}
	
}
