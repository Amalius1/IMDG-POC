package pl.aml.bk.imdgpoc.config;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;

@Component
public class AddResponseHeader implements Filter {
    private final String HOST_NAME;

    @SneakyThrows
    public AddResponseHeader() {
        this.HOST_NAME = InetAddress.getLocalHost().getHostName();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Who-handled-me", HOST_NAME);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
