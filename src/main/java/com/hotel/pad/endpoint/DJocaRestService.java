package com.hotel.pad.endpoint;


import io.micrometer.core.instrument.util.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DJocaRestService {
    private static final String HTTPS_PROXY = "https.proxyHost";
    private static final String HTTPS_PROXY_PORT = "https.proxyPort";

    public String send(String request, String endpointUrl) {

        final Response response;
        ResteasyClient client = null;
        try {
            if (StringUtils.isNotBlank(System.getProperty(HTTPS_PROXY))
                    && StringUtils.isNotBlank(System.getProperty(HTTPS_PROXY_PORT))) {
                client = new ResteasyClientBuilderImpl().defaultProxy(System.getProperty(HTTPS_PROXY),
                        Integer.parseInt(System.getProperty(HTTPS_PROXY_PORT))).build();
            } else {
                client = new ResteasyClientBuilderImpl().build();
            }
            ResteasyWebTarget target = client.target(UriBuilder.fromPath(endpointUrl));
            response = target.request().post(Entity.xml(request));
            return readInputStreamAsString((InputStream) response.getEntity());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }

    public static String readInputStreamAsString(InputStream in) {
        try {
            BufferedInputStream bis = new BufferedInputStream(in);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result = bis.read();
            while (result != -1) {
                byte b = (byte) result;
                buf.write(b);
                result = bis.read();
            }
            return buf.toString();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
