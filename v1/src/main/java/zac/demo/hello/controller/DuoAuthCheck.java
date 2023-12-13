package zac.demo.hello.controller;

import org.springframework.web.client.RestTemplate;

public class DuoAuthCheck {

    public static String performHttpGet(String url) {
        RestTemplate restTemplate = new RestTemplate();
        // Assuming the response is of type String.
        // Change the responseType parameter based on your actual response type.
        String response = restTemplate.getForObject(url, String.class);

        return response;
    }
}
