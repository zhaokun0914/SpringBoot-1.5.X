package com.fortunebill.springboot.controller;

import com.fortunebill.springboot.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理Controller
 *
 * @author Kavin
 * @date 2021年11月16日13:54:46
 */
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * UserNotFoundException异常处理方法
     *
     * @param exception UserNotFoundException异常对象
     * @return 返回指定的JSON对象
     *
     * 问题：没有自适应效果...
     *      如果是非浏览器访问：能正确返回JSON
     *      如果是浏览器访问则不希望返回JSON，希望返回HTML，那就要用下面的方法
     */
    // @ResponseBody
    // @ExceptionHandler(UserNotFoundException.class)
    // public Map<String, Object> handlerException(Exception exception) {
    //     Map<String, Object> map = new HashMap<>(2);
    //     map.put("code", "user_not_found");
    //     map.put("message", exception.getMessage());
    //     return map;
    // }

    /**
     * 转发到/error进行自适应响应效果处理
     *
     * @param exception UserNotFoundException异常对象
     * @param request   HttpServletRequest对象，用于设置错误状态码
     * @return 转发到/error，由BasicErrorController处理
     *
     * 问题：我们只能写HttpStatus里的code
     *      既要自适应效果又要将我们自定义的数据携带出去
     */
    // @ExceptionHandler(UserNotFoundException.class)
    // public String handlerException(Exception exception, HttpServletRequest request) {
    //     /*
    //      * 传入我们自己的错误状态码  4xx 5xx，否则就不会进入定制错误页面的解析流程
    //      * Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    //      * 不设置 javax.servlet.error.status_code 的话不会跳转到我们自定义的页面
    //      * 因为SpringBoot解析不到错误代码，只能返回以下默认的错误信息
    //      * <html>
    //      *     <body>
    //      *         <h1>Whitelabel Error Page</h1>
    //      *         <p>This application has no explicit mapping for /error, so you are seeing this as a fallback.</p>
    //      *         <div id='created'>${timestamp}</div>
    //      *         <div>There was an unexpected error (type=${error}, status=${status}).</div>
    //      *         <div>${message}</div>
    //      *     </body>
    //      * </html>
    //      */
    //     request.setAttribute("javax.servlet.error.status_code", 500);
    //
    //     // Map<String, Object> map = new HashMap<>(2);
    //     // map.put("code", "user_not_found");
    //     // map.put("message", exception.getMessage());
    //
    //     return "forward:/error";
    // }

    /**
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(UserNotFoundException.class)
    public String handlerException(Exception exception, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", "user_not_found");
        map.put("message", exception.getMessage());

        request.setAttribute("javax.servlet.error.status_code", 500);
        request.setAttribute("ext", map);
        return "forward:/error";
    }


}
