package com.aia.op.member.service;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.aia.op.member.domain.LoginInfo;


@Service
public class RedisService {

   	// private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	// private String TAG = this.getClass().getName();

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * Redis 에 사용자 정보를 등록한다.
	 * 
	 * @param LoginInfo
	 */

	// 구조 : login 시 -> redis에 저장하도록 함 
	
	// 현재 접속하고 있는 회원 
	public void setUserInformation(LoginInfo loginInfo, HttpSession session ) {

		//logger.debug("> setUserInformation", TAG);

		// 키들이 붙지않기위함..?
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());

		String key = session.getId(); // --> <key, value>

		Map<String, Object> mapMemberInfo = new HashMap<String, Object>(); // redis는 구조적으로 map형태 지원 
		mapMemberInfo.put("memberId", loginInfo.getMemberid());
		mapMemberInfo.put("memberName", loginInfo.getMembername());
		mapMemberInfo.put("memberPhoto", loginInfo.getMemberphoto());
		redisTemplate.opsForHash().putAll(key, mapMemberInfo); // sessionid key, map 

	}

	/**
	 * Redis 에서 사용자 정보를 가져온다.
	 * @param loginInfo
	 * @return
	 */

	public LoginInfo getUserInformation(String sessionId) {

		//logger.debug("> getUserInformation", TAG);

		String key = sessionId;

		LoginInfo result = new LoginInfo(
				(String) redisTemplate.opsForHash().get(key, "memberId"), 
				(String) redisTemplate.opsForHash().get(key, "memberName"), 
				(String) redisTemplate.opsForHash().get(key, "memberPhoto"));

		//logger.debug("> userId       : {}", result.getMemberid(), TAG);
		//logger.debug("> userPassword : {}", result.getMembername(), TAG);
		//logger.debug("> phoneNumber  : {}", result.getMemberphoto(), TAG);

		return result;

	}

}