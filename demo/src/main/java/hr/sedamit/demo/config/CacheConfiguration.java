package hr.sedamit.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.sedamit.demo.service.CacheNames;

@Configuration
@EnableCaching
public class CacheConfiguration {

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();

		List<Cache> caches = new ArrayList<Cache>();
		caches.add(new ConcurrentMapCache(CacheNames.CACHE_AUTHOR_LIST));
		caches.add(new ConcurrentMapCache(CacheNames.CACHE_AUTHOR_DETAILS));
		cacheManager.setCaches(caches);
		return cacheManager;
	}

}
