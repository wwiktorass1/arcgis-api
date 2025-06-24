package lt.ssva.arcgis_api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MapServerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testValidArcgisUrlReturnsData() {
        String mapServerUrl = "https://www.geoportal.lt/mapproxy/gisc_pagrindinis/MapServer";
        String encodedUrl = UriComponentsBuilder
                .fromPath("/api/mapserver")
                .queryParam("url", mapServerUrl)
                .build()
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(encodedUrl, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}