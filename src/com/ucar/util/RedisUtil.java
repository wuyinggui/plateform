package com.ucar.util;


import com.alibaba.fastjson.JSON;
import com.ucar.model.User;
import com.ucar.model.service.LBSUserService;

import redis.clients.jedis.JedisCluster;

public class RedisUtil {
	/**存储数据到redis
	 * @param user
	 */
	public static void writeToRedis(Object t,String rkey,Integer expireSeconds){
		LBSRedisUtilBasedCluster rcluster = LBSRedisUtilBasedCluster.getInstance();
		JedisCluster redis = rcluster.getJedisCluster();
		try {
			
			if(expireSeconds != null && expireSeconds != -1 && expireSeconds != 0){
				rcluster.setRedisValueWithExpire(redis, rkey,JSON.toJSONString(t) ,expireSeconds);
			}else{
				rcluster.setRedisValue(redis, rkey, JSON.toJSONString(t));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**从redis中读取
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getDataFromRedis(String rkey,Class clazz){
		LBSRedisUtilBasedCluster rcluster = LBSRedisUtilBasedCluster.getInstance();
		JedisCluster redis = rcluster.getJedisCluster();
		try {
			String rval = rcluster.getRedisValue(redis, rkey);
			return JSON.parseObject(rval, clazz);
			/*if(rval != null){
				return SerializeUtil.unserialize(new String(rval.getBytes()).getBytes());
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void deleteDataFromRedis(String rkey){
		LBSRedisUtilBasedCluster rcluster = LBSRedisUtilBasedCluster.getInstance();
		JedisCluster redis = rcluster.getJedisCluster();
		try {
			rcluster.deleteRedisValue(redis, rkey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
