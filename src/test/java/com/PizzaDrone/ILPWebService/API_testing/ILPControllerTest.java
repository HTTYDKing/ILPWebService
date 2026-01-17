package com.PizzaDrone.ILPWebService.API_testing;


import com.PizzaDrone.ILPWebService.ILPController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class ILPControllerTest {

    @Autowired
    private ILPController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void getuuidTest() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/uuid",
                String.class)).contains("S2335450");

    }

    @Test
    void distanceTotest() throws Exception {
        String body = "{\n" +
                " \"position1\": {\n" +
                " \"lng\": 180,\n" +
                " \"lat\": 90\n" +
                " },\n" +
                " \"position2\": {\n" +
                " \"lng\": 0,\n" +
                " \"lat\": 0\n" +
                " }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/distanceTo", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("201.24611797498108");
    }

    @Test
    void distanceTotestInvalid() throws Exception {
        String body = "{\n" +
                " \"position\": {\n" +
                " \"lng\": 180,\n" +
                " \"lat\": 90\n" +
                " },\n" +
                " \"position2\": {\n" +
                " \"lng\": 0,\n" +
                " \"lat\": 0\n" +
                " }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/distanceTo", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void isCloseTotest() throws Exception {
        String body = "{\n" +
                " \"position1\": {\n" +
                " \"lng\": 0,\n" +
                " \"lat\": -0.00014\n" +
                " },\n" +
                " \"position2\": {\n" +
                " \"lng\": 0,\n" +
                " \"lat\": 0\n" +
                " }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/isCloseTo", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("true");
    }

    @Test
    void isCloseTotestinvalid() throws Exception {
        String body = "{\n" +
                " \"position1\": {\n" +
                " \"lng\": 0,\n" +
                " \"la\": -0.00014\n" +
                " },\n" +
                " \"position2\": {\n" +
                " \"lng\": 0,\n" +
                " \"lat\": 0\n" +
                " }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/isCloseTo", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    void nextPositiontest() throws Exception {
        String body = "{\n" +
                " \"start\": {\n" +
                " \"lng\": 0,\n" +
                " \"lat\": 0\n" +
                " },\n" +
                " \"angle\": 0\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/nextPosition", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("{\"lng\":1.5E-4,\"lat\":0.0}");
    }
    @Test
    void nextPositiontestinvalid() throws Exception {
        String body = "{\n" +
                " \"strt\": {\n" +
                " \"lng\": 0,\n" +
                " \"lat\": 0\n" +
                " },\n" +
                " \"angle\": 0\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/nextPosition", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    void inRegiontest() throws Exception {
        String body = "{\n" +
                "  \"position\": {\n" +
                "        \"lat\": 0,\n" +
                "        \"lng\": 0\n" +
                "  },\n" +
                "  \"region\": {\n" +
                "    \"name\": \"central\",\n" +
                "    \"vertices\": [\n" +
                "      {\n" +
                "        \"lat\": 0,\n" +
                "        \"lng\": 0\n" +
                "      },\n" +
                "      {\n" +
                "        \"lat\": 0,\n" +
                "        \"lng\": 1\n" +
                "      },\n" +
                "      {\n" +
                "        \"lat\": 1,\n" +
                "        \"lng\": 1\n" +
                "      },\n" +
                "      {\n" +
                "        \"lat\": 1,\n" +
                "        \"lng\": 0\n" +
                "      },\n" +
                "      {\n" +
                "        \"lat\": 0,\n" +
                "        \"lng\": 0\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/isInRegion", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("true");
    }
    @Test
    void inRegiontestinvalid() throws Exception {
        String body = "{\n" +
                "  \"positio\": {\n" +
                "        \"lat\": 0,\n" +
                "        \"lng\": 0\n" +
                "  },\n" +
                "  \"region\": {\n" +
                "    \"name\": \"central\",\n" +
                "    \"vertices\": [\n" +
                "      {\n" +
                "        \"lat\": 0,\n" +
                "        \"lng\": 0\n" +
                "      },\n" +
                "      {\n" +
                "        \"lat\": 0,\n" +
                "        \"lng\": 1\n" +
                "      },\n" +
                "      {\n" +
                "        \"lat\": 1,\n" +
                "        \"lng\": 1\n" +
                "      },\n" +
                "      {\n" +
                "        \"lat\": 1,\n" +
                "        \"lng\": 0\n" +
                "      },\n" +
                "      {\n" +
                "        \"lat\": 0,\n" +
                "        \"lng\": 0\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/isInRegion", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    void ValidateOrderTest() throws Exception {
        String body = "{\n" +
                "    \"orderNo\": \"5AEC1EE4\",\n" +
                "    \"orderDate\": \"2025-01-16\",\n" +
                "    \"orderStatus\": \"VALID\",\n" +
                "    \"orderValidationCode\": \"NO_ERROR\",\n" +
                "    \"priceTotalInPence\": 2600,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R2: Meat Lover\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R2: Vegan Delight\",\n" +
                "        \"priceInPence\": 1100\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"5385826467637429\",\n" +
                "      \"creditCardExpiry\": \"03/25\",\n" +
                "      \"cvv\": \"322\"\n" +
                "    }\n" +
                "  }";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/validateOrder", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("{\"orderStatus\":\"VALID\",\"orderValidationCode\":\"NO_ERROR\"}");
    }

    @Test
    void CalDeliveryTest() throws Exception {
        String body = "{\n" +
                "    \"orderNo\": \"5AEC1EE4\",\n" +
                "    \"orderDate\": \"2025-01-16\",\n" +
                "    \"orderStatus\": \"VALID\",\n" +
                "    \"orderValidationCode\": \"NO_ERROR\",\n" +
                "    \"priceTotalInPence\": 2600,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R2: Meat Lover\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R2: Vegan Delight\",\n" +
                "        \"priceInPence\": 1100\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"5385826467637429\",\n" +
                "      \"creditCardExpiry\": \"03/25\",\n" +
                "      \"cvv\": \"322\"\n" +
                "    }\n" +
                "  }";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/calcDeliveryPath", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void CalDeliveryGeoJsonTest() throws Exception {
        String body = "{\n" +
                "    \"orderNo\": \"5AEC1EE4\",\n" +
                "    \"orderDate\": \"2025-01-16\",\n" +
                "    \"orderStatus\": \"VALID\",\n" +
                "    \"orderValidationCode\": \"NO_ERROR\",\n" +
                "    \"priceTotalInPence\": 2600,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R2: Meat Lover\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R2: Vegan Delight\",\n" +
                "        \"priceInPence\": 1100\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"5385826467637429\",\n" +
                "      \"creditCardExpiry\": \"03/25\",\n" +
                "      \"cvv\": \"322\"\n" +
                "    }\n" +
                "  }";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/calcDeliveryPathAsGeoJson", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
