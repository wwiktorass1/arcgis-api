package lt.ssva.arcgis_api.service;
import lt.ssva.arcgis_api.dto.LayerDto;


import lt.ssva.arcgis_api.dto.MapServerResponseDto;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MapServerIntegrationTest {

    @Test
    void testFetchAndTransform_returnsCorrectData() {
        String url = "https://test.server/MapServer";
        String expectedUrl = url + "?f=json";

        // Paruošiam netikrą JSON struktūrą
        Map<String, Object> mockJson = Map.of(
                "mapName", "Test Map",
                "description", "Test Description",
                "layers", List.of(
                        Map.of("id", 1, "name", "Layer One"),
                        Map.of("id", 2, "name", "Layer Two")
                )
        );

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(expectedUrl, Map.class)).thenReturn(mockJson);

        MapServerService service = new MapServerService() {

            protected RestTemplate createRestTemplate() {
                return restTemplate;
            }
        };


        MapServerResponseDto result = service.fetchAndTransform(url);

        // Assert
        assertNotNull(result);
        assertEquals("Test Map", result.getMapName());
        assertEquals("Test Description", result.getDescription());
        assertEquals(2, result.getLayers().size());

        LayerDto layer1 = result.getLayers().get(0);
        assertEquals(1, layer1.getId());
        assertEquals("Layer One", layer1.getName());
    }
}
