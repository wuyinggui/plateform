package com.ucar.util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;



import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class LBSRedisUtilBasedCluster {

	private volatile static LBSRedisUtilBasedCluster lbsRedisUtil;
	private static JedisCluster cluster = null;

	/**
	 * 类初始化 createBy 张维政 @2015年8月12日
	 * 
	 * @return
	 */
	public static LBSRedisUtilBasedCluster getInstance() {
		if (null == lbsRedisUtil) {
			synchronized (LBSRedisUtilBasedCluster.class) {
				if (null == lbsRedisUtil) {
					lbsRedisUtil = new LBSRedisUtilBasedCluster();
				}
			}
		}
		return lbsRedisUtil;
	}

	/**
	 * 获取redis集群对象 createBy 张维政 @2015年8月12日
	 * 
	 * @return
	 */
	public synchronized JedisCluster getJedisCluster() {
		if (null == cluster) {
			String[] ip = MappingConstantConfig.getValue("REDIS_IP").split(",");
			String[] port = MappingConstantConfig.getValue("REDIS_PORT").split(",");
			HashSet<HostAndPort> nodes = new HashSet<HostAndPort>(ip.length);
			for(int i=0;i<ip.length;i++){
				nodes.add(new HostAndPort(ip[i], Integer.parseInt(port[i])));
			}
			GenericObjectPoolConfig gopf = new GenericObjectPoolConfig();
			gopf.setMaxIdle(500);
			gopf.setMaxIdle(1000);
			gopf.setMaxTotal(5000);
			cluster = new JedisCluster(nodes,3000,gopf);
		}
		return cluster;
	}

	/**
	 * 关闭redis集群连接对象 createBy 张维政 @2015年8月12日
	 */
	public void closeCluster() {
		if (null != cluster) {
			cluster.close();
		}
	}
	
	/**
	 * 获取redis值（rk ，hk，rv） createBy 张维政 @2015年7月2日
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public String getRedisValueByHash(JedisCluster redis, String rk, String hk)
			throws Exception {
		String value = redis.hget(rk, hk);
		return value;
	}
	
	/**
	 * 批量获取redis的hv（rk ，hk，rv） createBy 张维政 @2015年7月2日
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public List<String> mutiGetRedisValuesByHash(JedisCluster redis, String rk)
			throws Exception {
		List<String> values = redis.hvals(rk);
		return values;
	}
	/**
	 * 设置redis值（rk ，hk，rv） createBy 张维政 @2015年7月2日
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public void setRedisValueByHash(JedisCluster redis, String rk, String hk, String hv)
			throws Exception {
		redis.hset(rk, hk, hv);
	}
	
	/**
	 * 批量设置redis值（rk ，hk，rv） createBy 张维政 @2015年7月2日
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public void multiSetRedisValueByHash(JedisCluster redis, String rk,
			Map<String, String> hKValues) throws Exception {
		redis.hmset(rk, hKValues);
	}
	
	/**
	 * 删除redis值（rk ，rv） createBy 张维政 @2015年7月2日
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public void deleteRedisValue(JedisCluster redis, String rk) throws Exception {
		redis.del(rk);
	}
	
	/**
	 * 检查redis的key值是否存在（rk ，rv） createBy 张维政 @2015年7月2日
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public Boolean checkExitsRedis(JedisCluster redis, String rk) throws Exception {
		Boolean flag = redis.exists(rk);
		return flag;
	}
	/**
	 * 设置redis过期（rk ，rv） createBy 张维政 @2015年7月2日
	 * 
	 * @param redis
	 * @param rk
	 * @param seconds
	 * @return
	 */
	public void setRedisExpires(JedisCluster redis, String rk, int seconds)
			throws Exception {
		redis.expire(rk, seconds);
	}
	
	/**
	 * 设置redis值并设置过期（rk ，rv） createBy 张维政 @2015年7月2日
	 * 
	 * @param redis
	 * @param rk
	 * @param rv
	 * @param seconds
	 * @return
	 */
	public void setRedisValueWithExpires(JedisCluster redis, String rk, String rv,
			int seconds) throws Exception {
		redis.setex(rk, seconds, rv);
	}
	
	/**
	 * 查询redis值（rk ，rv） createBy 张维政 @2015年7月2日
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public String getRedisValue(JedisCluster redis, String rk) throws Exception {
		String value = redis.get(rk);
		return value;
	}
	/**
	 * 
	 * Description: 删除redis的hv（rk ，hk，rv）
	 * @Version1.0 2015年8月21日 上午10:23:32 by mn.liu@10101111.com
	 * @param redis
	 * @param rk
	 * @param hk
	 * @throws Exception
	 */
	public void delRedisValueByHash(JedisCluster redis, String rk,String hk) throws Exception {
		redis.hdel(rk, hk);
	}
	/**
	 * 
	 * Description:  检测redis的hk是否存在（rk ，hk，rv）
	 * @Version1.0 2015年8月21日 上午10:38:14 by mn.liu@10101111.com
	 * @param redis
	 * @param rk
	 * @param hk
	 * @return
	 * @throws Exception
	 */
	public Boolean checkExitsRedisByHash(JedisCluster redis, String rk, String hk) throws Exception{
		Boolean flag  = redis.hexists(rk, hk);
		return flag;
	}
	/**
	 * Description: 批量获取redis的hk，hv（rk ，hk，rv）
	 * @Version1.0 2015年8月21日 上午11:06:54 by mn.liu@10101111.com
	 * @param redis
	 * @param rk
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> mutiGetRedisKVsByHash(JedisCluster redis, String rk)  throws Exception  {
		Map<String, String> values = redis.hgetAll(rk);
		return values;
	}
	/**
	 * Description:  设置redis值（rk ，rv）
	 * @Version1.0 2015年8月21日 下午2:29:47 by mn.liu@10101111.com
	 * @param redis
	 * @param rk
	 * @param rv
	 */
	public void setRedisValue(JedisCluster redis, String rk, String rv) {
			redis.set(rk, rv);
	}

	public void setRedisValueWithExpire(JedisCluster redis, String rkey,
			String rval, Integer expireTime) {
		redis.set(rkey, rval);
		redis.expire(rkey, expireTime);
	}
}
