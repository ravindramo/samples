package com.cybage.demo.filters;

import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.cybage.demo.util.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Filter to track the common information from client
 */
public class IncomingRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /* unused */
    }

    @Override
    public void destroy() {
        /* unused */
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Add Tracking-Id to MDC for downstream logging
        String trackingId = httpRequest.getHeader(TRACKING_ID);
        if (StringUtils.isEmpty(trackingId)) {
            trackingId = generateTrackingId();
        }
        addMDC(TRACKING_ID, trackingId);

        // Add Request-Uri to MDC for downstream logging
        String requestUri = httpRequest.getRequestURI();
        addMDC(REQUEST_URI, requestUri);

        //Add Ip-Address to MDC for downstream logging.
        String ipAddress = httpRequest.getHeader(X_FORWARDED_FOR);
        if (ipAddress == null) {
            ipAddress = httpRequest.getRemoteAddr();
        }
        addMDC(IP_ADDRESS, ipAddress);

        // And include Tracking-Id header in the response
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.addHeader(TRACKING_ID, trackingId);

        chain.doFilter(request, response);
    }

    private void addMDC(final String key, final String value) {
        if (StringUtils.isNotEmpty(value)) {
            MDC.put(key, value);
        } else {
            // If no value is set explicitly remove it to avoid a previous request value
            // from being associated with the current request.
            MDC.remove(key);
        }
    }

    private String generateTrackingId() {
        return UUID.randomUUID().toString();
    }

}
