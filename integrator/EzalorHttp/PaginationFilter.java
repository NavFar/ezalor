package org.openremote.agent.protocol.http;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;
import java.net.URI;

public class PaginationFilter implements ClientRequestFilter {

    protected URI nextUrl;

    public PaginationFilter(URI nextUrl) {
        this.nextUrl = nextUrl;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        requestContext.setUri(nextUrl);
    }
}
