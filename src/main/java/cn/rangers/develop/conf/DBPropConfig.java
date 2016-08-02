package cn.rangers.develop.conf;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import cn.rangers.develop.utils.PropertiesUtil;

/**
 * 加载DB配置信息（根据开发模式的不同，自动切换不同的配置文件的信息）
 * 
 * @version 1.3
 * @since JDK1.7
 * @author fuhw
 * @company 上海浦信科技
 * @copyright (c) 2016 Puxin technology. All rights reserved.
 * @date 2016年7月29日 上午11:10:21
 */
@Configuration
@EnableConfigurationProperties
public class DBPropConfig {
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource(PropertiesUtil.getDBConfig()) };
		pspc.setLocations(resources);
		pspc.setIgnoreUnresolvablePlaceholders(true);
		return pspc;
	}
}
