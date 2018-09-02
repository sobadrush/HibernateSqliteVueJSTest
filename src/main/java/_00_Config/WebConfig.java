package _00_Config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.ctbc.controller")
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/pages/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	// (1)
	// 自定義SpringMVC的時間日期轉換器，當@RequestMapping 的方法 return 物件 (@ReqponseBody) 時，
	// 若沒以下自訂時間日期轉換器，時間日期會變成中文，ex : 十一月 17, 1981
	// 【參考】: https://www.jianshu.com/p/7211dfdbbb9d
	@Bean
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
	    mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	    converter.setObjectMapper(mapper);
	    return converter;
	}
	
	// (2)
	// 將自訂時間日期轉換器加入SpringMVC 的轉換器列表中
	@Override
	public void configureMessageConverters(java.util.List<HttpMessageConverter<?>> converters) {
	    // 将我们定义的时间格式转换器添加到转换器列表中,
	    // 這樣jackson格式化时候但凡遇到 Date 类型就会转换成我们定义的格式
	    converters.add(this.jackson2HttpMessageConverter());
	}

}


