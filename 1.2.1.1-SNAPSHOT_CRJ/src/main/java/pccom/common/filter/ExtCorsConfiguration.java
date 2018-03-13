package pccom.common.filter;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;

/**
 * 
 * Description: 重写cors中校验Origin的逻辑，对于origin中含有*字符串的，进行模糊匹配
 *  
 * Created on 2017年6月13日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class ExtCorsConfiguration extends CorsConfiguration {

    /**
     * 
     * Description: 重写origin校验逻辑
     * 
     * @author jinyanan
     *
     * @param requestOrigin 请求origin
     * @return String
     */
    public String checkOrigin(String requestOrigin) {
        if (!StringUtils.hasText(requestOrigin)) {
            return null;
        }
        if (ObjectUtils.isEmpty(this.getAllowedOrigins())) {
            return null;
        }

        if (this.getAllowedOrigins().contains(ALL)) {
            if (this.getAllowCredentials() != Boolean.TRUE) {
                return ALL;
            }
            else {
                return requestOrigin;
            }
        }
        for (String allowedOrigin : this.getAllowedOrigins()) {
            if (requestOrigin.equalsIgnoreCase(allowedOrigin)) {
                return requestOrigin;
            }
            else if (allowedOrigin.contains(ALL)) {
                int index = allowedOrigin.indexOf(ALL);
                String prefix = allowedOrigin.substring(0, index);
                String suffix = allowedOrigin.substring(index + 1, allowedOrigin.length());
                if (requestOrigin.startsWith(prefix) && requestOrigin.endsWith(suffix)) {
                    return requestOrigin;
                }
            }
        }

        return null;
    }
}
