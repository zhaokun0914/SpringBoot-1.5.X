package com.fortunebill.springboot.component;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 * 给容器中添加我们自己的 ErrorAttributes
 *
 * @author Kavin
 * @date 2021年11月16日14:48:30
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        errorAttributes.put("company", "fortunebill");
        Map<String, Object> ext = (Map<String, Object>) requestAttributes.getAttribute("ext", RequestAttributes.SCOPE_REQUEST);
        errorAttributes.put("ext", ext);
        return errorAttributes;
    }
}
