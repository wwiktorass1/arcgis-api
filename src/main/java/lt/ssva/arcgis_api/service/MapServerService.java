package lt.ssva.arcgis_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.ssva.arcgis_api.dto.LayerDto;
import lt.ssva.arcgis_api.dto.MapServerResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapServerService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MapServerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MapServerResponseDto fetchAndTransform(String url) {
        try {
            String fullUrl = url + "?f=json";
            ResponseEntity<String> response = restTemplate.getForEntity(fullUrl, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());

            String mapName = root.path("mapName").asText();
            String description = root.path("description").asText();

            List<LayerDto> layers = new ArrayList<>();
            for (JsonNode layerNode : root.path("layers")) {
                int id = layerNode.path("id").asInt();
                String name = layerNode.path("name").asText();
                layers.add(new LayerDto(id, name));
            }

            return new MapServerResponseDto(mapName, description, layers);
        } catch (Exception e) {
            throw new RuntimeException("Klaida apdorojant ArcGIS atsakymą: " + e.getMessage(), e);
        }
    }
}