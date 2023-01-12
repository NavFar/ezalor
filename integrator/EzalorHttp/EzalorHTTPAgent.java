package org.openremote.agent.protocol.http;

import org.openremote.model.asset.agent.Agent;
import org.openremote.model.asset.agent.AgentDescriptor;
import org.openremote.model.http.HTTPMethod;
import org.openremote.model.value.AttributeDescriptor;
import org.openremote.model.value.ValueDescriptor;
import org.openremote.model.value.ValueType;

import javax.persistence.Entity;
import java.util.Optional;

@Entity
public class EzalorHTTPAgent extends Agent<HTTPAgent, HTTPProtocol, HTTPAgentLink> {

    public static final ValueDescriptor<HTTPMethod> VALUE_HTTP_METHOD = new ValueDescriptor<>("HTTPMethod", HTTPMethod.class);

    public static final AttributeDescriptor<String> BASE_URI = new AttributeDescriptor<>("baseURL", ValueType.HTTP_URL);
    public static final AttributeDescriptor<Boolean> FOLLOW_REDIRECTS = new AttributeDescriptor<>("followRedirects", ValueType.BOOLEAN);
    public static final AttributeDescriptor<ValueType.MultivaluedStringMap> REQUEST_HEADERS = new AttributeDescriptor<>("requestHeaders", ValueType.MULTIVALUED_TEXT_MAP);
    public static final AttributeDescriptor<ValueType.MultivaluedStringMap> REQUEST_QUERY_PARAMETERS = new AttributeDescriptor<>("requestQueryParameters", ValueType.MULTIVALUED_TEXT_MAP);
    public static final AttributeDescriptor<Integer> REQUEST_TIMEOUT_MILLIS = new AttributeDescriptor<>("requestTimeoutMillis", ValueType.POSITIVE_INTEGER);

    public static final AgentDescriptor<HTTPAgent, HTTPProtocol, HTTPAgentLink> DESCRIPTOR = new AgentDescriptor<>(
        HTTPAgent.class, HTTPProtocol.class, HTTPAgentLink.class
    );

    /**
     * For use by hydrators (i.e. JPA/Jackson)
     */
    protected EzalorHTTPAgent() {
    }

    public EzalorHTTPAgent(String name) {
        super(name);
    }

    public Optional<String> getBaseURI() {
        return getAttributes().getValue(BASE_URI);
    }

    public HTTPAgent setBaseURI(String value) {
        getAttributes().getOrCreate(BASE_URI).setValue(value);
        return this;
    }

    public Optional<Boolean> getFollowRedirects() {
        return getAttributes().getValue(FOLLOW_REDIRECTS);
    }

    public HTTPAgent setFollowRedirects(Boolean value) {
        getAttributes().getOrCreate(FOLLOW_REDIRECTS).setValue(value);
        return this;
    }

    public Optional<ValueType.MultivaluedStringMap> getRequestHeaders() {
        return getAttributes().getValue(REQUEST_HEADERS);
    }

    public HTTPAgent setRequestHeaders(ValueType.MultivaluedStringMap value) {
        getAttributes().getOrCreate(REQUEST_HEADERS).setValue(value);
        return this;
    }

    public Optional<ValueType.MultivaluedStringMap> getRequestQueryParameters() {
        return getAttributes().getValue(REQUEST_QUERY_PARAMETERS);
    }

    public HTTPAgent setRequestQueryParameters(ValueType.MultivaluedStringMap value) {
        getAttributes().getOrCreate(REQUEST_QUERY_PARAMETERS).setValue(value);
        return this;
    }

    public Optional<Integer> getRequestTimeoutMillis() {
        return getAttributes().getValue(REQUEST_TIMEOUT_MILLIS);
    }

    public HTTPAgent setRequestTimeoutMillis(Integer value) {
        getAttributes().getOrCreate(REQUEST_TIMEOUT_MILLIS).setValue(value);
        return this;
    }

    public Optional<Boolean> getMessageConvertHex() {
        return getAttributes().getValue(MESSAGE_CONVERT_HEX);
    }

    public HTTPAgent setMessageConvertHex(Boolean value) {
        getAttributes().getOrCreate(MESSAGE_CONVERT_HEX).setValue(value);
        return this;
    }

    public Optional<Boolean> getMessageConvertBinary() {
        return getAttributes().getValue(MESSAGE_CONVERT_BINARY);
    }

    public HTTPAgent setMessageConvertBinary(Boolean value) {
        getAttributes().getOrCreate(MESSAGE_CONVERT_BINARY).setValue(value);
        return this;
    }

    @Override
    public HTTPProtocol getProtocolInstance() {
        return new HTTPProtocol(this);
    }
}
