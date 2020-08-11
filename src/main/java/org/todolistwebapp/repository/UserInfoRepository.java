package org.todolistwebapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * Kontaktieren des OAuth2-Servers, um Nutzerinformationen zu beziehen.
 */
@Repository
public class UserInfoRepository {

    @Autowired
    OAuth2AuthorizedClientService authorizedClientService;

    /**
     * Holen der Benutzerinformationen
     * @return Die Nutzerinformationen als Map
     */
    public Map<String,String> getUserInfo() {

        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                    authentication.getAuthorizedClientRegistrationId(), authentication.getName());

            String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

            if (!StringUtils.isEmpty(userInfoEndpointUri)) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
                HttpEntity entity = new HttpEntity("", headers);
                ResponseEntity response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);

                return (Map<String,String>) response.getBody();
            }
        }

        return Collections.emptyMap();

    }
}
