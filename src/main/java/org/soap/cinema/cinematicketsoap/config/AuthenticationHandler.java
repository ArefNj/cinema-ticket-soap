package org.soap.cinema.cinematicketsoap.config;

import jakarta.xml.soap.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Component
public class AuthenticationHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "1234";
    private static final String AUTH_NAMESPACE = "http://auth.cinema.org/";
    private static final String SOAP_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if (isOutbound(context)) return true;

        try {
            SOAPHeader header = context.getMessage().getSOAPPart().getEnvelope().getHeader();

            if (header == null) {
                throwAuthFault("Missing authentication header");
            }

            Iterator<Node> elements = Objects.requireNonNull(header).getChildElements(
                    new QName(AUTH_NAMESPACE, "AuthenticationHeader")
            );

            if (!elements.hasNext()) {
                throwAuthFault("Missing AuthenticationHeader element");
            }

            SOAPElement authElement = (SOAPElement) elements.next();
            String username = authElement.getElementsByTagNameNS(AUTH_NAMESPACE, "username")
                    .item(0).getTextContent();
            String password = authElement.getElementsByTagNameNS(AUTH_NAMESPACE, "password")
                    .item(0).getTextContent();

            if (!VALID_USERNAME.equals(username) || !VALID_PASSWORD.equals(password)) {
                throwAuthFault("Invalid username or password");
            }

        } catch (SOAPException e) {
            throwAuthFault("Authentication error");
        }
        return true;
    }

    private boolean isOutbound(SOAPMessageContext context) {
        return Boolean.TRUE.equals(context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY));
    }

    private void throwAuthFault(String message) {
        try {
            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
            SOAPFault fault = soapMessage.getSOAPBody().addFault(
                    new QName(SOAP_NAMESPACE, "Client", "soap"),
                    message
            );
            throw new SOAPFaultException(fault);
        } catch (SOAPException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) { return true; }

    @Override
    public void close(MessageContext context) {}

    @Override
    public Set<QName> getHeaders() { return null; }
}