package com.ribbon.rule;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

/**
 * 自定义ribbon的hashcode负载均衡算法
 * @author lk
 *
 */
public class HashCodeRule extends AbstractLoadBalancerRule implements IRule{

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {
		
	}

	@Override
	public Server choose(Object key) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String uri = request.getServletPath() + "?" + request.getQueryString();
		return route(uri.hashCode(), getLoadBalancer().getAllServers());
	}
	
	private Server route(int uriHash, List<Server> allServer) {
		if(CollectionUtils.isEmpty(allServer)) {
			return null;
		}
		
		TreeMap<Long, Server> treeMap = new TreeMap<>();
		allServer.forEach(server -> {
			//虚化若干服务器节点到环上
			for(int i = 0; i < 4; i++) {
				long serverHash = hash(server.getId() + i);
				treeMap.put(serverHash, server);
			}
		});
		
		long hashId = hash(String.valueOf(uriHash)); 
		SortedMap<Long, Server> sortedMap = treeMap.tailMap(hashId);
		
		// 当reqeust uri的hash值大于任意一个服务器对的hashkey，取第一个节点
		if(sortedMap.isEmpty()) {
			treeMap.firstEntry().getValue();
		}
		
		return sortedMap.get(sortedMap.firstKey());
	}
	
	private long hash(String key) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		byte[] keyBytes = null;
		try {
			keyBytes = key.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		md5.update(keyBytes);
		byte[] digest = md5.digest();
		
		long hashcode = ((long)(digest[2] & 0xFF << 16)) | ((long)(digest[1] & 0xFF << 8)) | ((long)(digest[0] & 0xFF));
		
		return hashcode & 0xFFFFFFFF;
	}
}
