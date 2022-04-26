package mn.novelsoft.test.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakSpringBootProperties.class)
public class KeycloakConfiguration {

    /**
     * The Keycloak Admin client that provides the service-account Access-Token
     *
     * @param props
     * @return
     */
    @Bean
    public Keycloak keycloak(KeycloakSpringBootProperties props) {
        Keycloak keycloak = KeycloakBuilder.builder() //
                .serverUrl(props.getAuthServerUrl()) //
                .realm(props.getRealm()) //
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS) //
                .clientId(props.getResource()) //
                .clientSecret((String) props.getCredentials().get("secret")) //
                .build();

        return keycloak;
    }
}
