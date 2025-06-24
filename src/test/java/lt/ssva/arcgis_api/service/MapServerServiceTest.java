package lt.ssva.arcgis_api.service;

import lt.ssva.arcgis_api.dto.LayerDto;
import lt.ssva.arcgis_api.dto.MapServerResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MapServerServiceTest {

    @Test
    void testFetchAndTransform_returnsCorrectData() throws Exception {
        String baseUrl = "https://fake.server/MapServer";
        String expectedUrl = baseUrl + "?f=json";

        // Mock JSON atsakymas
        Map<String, Object> mockResponse = Map.of(
                "mapName", "Test Map",
                "description", "Test Description",
                "layers", List.of(
                        Map.of("id", 1, "name", "Layer A"),
                        Map.of("id", 2, "name", "Layer B")
                )
        );

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForEntity(expectedUrl, String.class))
                .thenReturn(ResponseEntity.ok("{\"mapName\":\"Test Map\",\"description\":\"Test Description\",\"layers\":[{\"id\":1,\"name\":\"Layer A\"},{\"id\":2,\"name\":\"Layer B\"}]}"));

        MapServerService service = new MapServerService();

        MapServerResponseDto result = service.fetchAndTransform(baseUrl);

        assertNotNull(result);
        assertEquals("Test Map", result.getMapName());
        assertEquals("Test Description", result.getDescription());
        assertEquals(2, result.getLayers().size());

        LayerDto firstLayer = result.getLayers().get(0);
        assertEquals(1, firstLayer.getId());
        assertEquals("Layer A", firstLayer.getName());
    }
}