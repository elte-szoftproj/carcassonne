package hu.elte.szoftproj.carcassonne.persistence.client;

import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class RestClientImpl implements RestClient{

    protected final String baseUrl;

    public RestClientImpl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public UriComponentsBuilder getUrlBuilder() {
        return UriComponentsBuilder.fromHttpUrl(baseUrl);
    }

    @Override
    public Client client() {
        return ClientBuilder.newClient();
    }

}
