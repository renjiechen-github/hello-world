package pccom.common.filter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 支持跨域请求，只支持http://runapi.showdoc.cc工具
 * @author eliay
 *
 */
public class CorsFilter extends org.springframework.web.filter.CorsFilter {

	public CorsFilter(CorsConfigurationSource configSource) {
		super(configSource);
	}
	
	public CorsFilter() {
        super(configurationSource());
    }
	
	private static UrlBasedCorsConfigurationSource configurationSource() {
	    // 使用自定义的ExtCorsConfiguration，改写校验逻辑以能够使用postman进行接口测试
        CorsConfiguration config = new ExtCorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://runapi.showdoc.cc");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://192.168.90.198:9020");
        config.addAllowedOrigin("chrome-extension://*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
