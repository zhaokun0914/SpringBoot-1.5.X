package com.fortunebill.springboot.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Kavin
 * @date 2021年11月16日15:45:41
 */
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("MyFilter 执行了");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
