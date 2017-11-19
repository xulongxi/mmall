package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.common
 * 文件名：   TokenCache
 * 创建时间： 2017/11/17 23:51
 * @author Longxi XU
 * 描述：     生成验证找回密码用的Token，使用本地缓存
 */
public class TokenCache {
    public static final String TOKEN_PREFIX="token_";
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    //LRU算法
    private static LoadingCache<String, String> localCache = CacheBuilder.
            newBuilder().initialCapacity(1000).maximumSize(10000).
            expireAfterAccess(12, TimeUnit.HOURS).
            build(new CacheLoader<String, String>() {
                //当get没有对应的key，取不到值的时候，有调用这个方法进行默认的数据加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key){
        String value = null;

        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            logger.error("localCache get error",e);
        }
        return null;
    }
}
