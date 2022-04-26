package mn.novelsoft.test.web.rest;

import com.sun.xml.bind.util.Which;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class Consumer {

    @Autowired
    private Keycloak keycloak;


    @Value("${keycloak.realm}")
    private String realm;

    @Value("${elastic-server.url}")
    private String serverUrl;


    @KafkaListener(topics = "key-cloak-user")
    private void user(String userId)  {
        UserRepresentation user = this.keycloak.realm(realm).users().get(userId).toRepresentation();
        saveElastic("user-info" , user);
    }
    @KafkaListener(topics = "key-cloak-admin")
    private void consume(String userId)  {
        System.out.println(userId);
        var race = "";
        loop:
        do {
            race += "x";
            break loop;
        } while (true);
        System.out.println(race);
        UserRepresentation user = this.keycloak.realm(realm).users().get(userId).toRepresentation();
        saveElastic("user-info" , user);

    }

    public void saveElastic(String index,UserRepresentation user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> request = new HttpEntity<>(user, headers);
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(serverUrl + "/" + index + "/_doc/" + user.getId(), request, String.class);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}


