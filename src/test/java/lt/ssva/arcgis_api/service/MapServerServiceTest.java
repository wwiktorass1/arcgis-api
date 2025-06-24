package lt.ssva.arcgis_api.service;

import lt.ssva.arcgis_api.dto.LayerDto;
import lt.ssva.arcgis_api.dto.MapServerResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MapServerServiceTest {

    @Test
    void testFetchAndTransform_returnsCorrectData() {
        String baseUrl = "https://fake.server/MapServer";
        String expectedUrl = baseUrl + "?f=json";

        String mockJson = """
                {
                  "mapName": "Test Map",
                  "description": "Test Description",
                  "layers": [
                    {"id": 1, "name": "Layer A"},
                    {"id": 2, "name": "Layer B"}
                  ]
                }
                """;

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForEntity(expectedUrl, String.class))
                .thenReturn(ResponseEntity.ok(mockJson));

        MapServerService service = new MapServerService(restTemplate);
        MapServerResponseDto result = service.fetchAndTransform(baseUrl);

        assertNotNull(result);
        assertEquals("Test Map", result.getMapName());
        assertEquals("Test Description", result.getDescription());
        assertEquals(2, result.getLayers().size());

        LayerDto first = result.getLayers().get(0);
        assertEquals(1, first.getId());
        assertEquals("Layer A", first.getName());

        LayerDto second = result.getLayers().get(1);
        assertEquals(2, second.getId());
        assertEquals("Layer B", second.getName());
    }
}