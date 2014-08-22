package com.gyyx.java;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	MemcachedClient client;
	SqlSessionFactory sqlSessionFactory = null;
	final String cacheKey = "zzymemcached_test";
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	public HomeController() {
		client = MemClient.getInstance();
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	@RequestMapping(value = "/getServerList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getServerList(Locale locale, Model model,
			@RequestParam("serverid") int serverid) {
		sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		List<ServerInfo> servers = null;
		StringBuffer serverstring = new StringBuffer();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ServerMapper serverInfoMapper = sqlSession
				.getMapper(ServerMapper.class);
		try {
			servers = client.get(cacheKey);
			if (servers == null) {
				servers = serverInfoMapper.queryListByServerId(serverid);
				client.add(cacheKey, 0, servers);

			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 for(ServerInfo server:servers){
			 serverstring.append("code:  "+server.getCode()+"  serverid:   "+server.getserverid()+" servername: "+server.getServerName());
		 }
		return serverstring.toString();
	}
	public void updateServerName(HashMap<String, String> map) {
		sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ServerMapper serverMapper = sqlSession
				.getMapper(ServerMapper.class);
		serverMapper.updateServerName(map);
		sqlSession.commit();
		sqlSession.close();
		try {
			client.delete(cacheKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
