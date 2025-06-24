package lt.ssva.arcgis_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.ssva.arcgis_api.dto.LayerDto;
import lt.ssva.arcgis_api.dto.MapServerResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class MapServerService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public MapServerService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public MapServerResponseDto fetchAndTransform(String baseUrl) {
        try {
            String fullUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("f", "json")
                    .toUriString();

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(fullUrl, String.class);
            String responseBody = responseEntity.getBody();

            Map<String, Object> response = objectMapper.readValue(responseBody, Map.class);

            MapServerResponseDto result = new MapServerResponseDto();
            result.setMapName((String) response.get("mapName"));
            result.setDescription((String) response.get("description"));

            List<Map<String, Object>> layers = (List<Map<String, Object>>) response.get("layers");
            if (layers != null) {
                List<LayerDto> layerDtos = layers.stream().map(layer -> {
                    LayerDto dto = new LayerDto();
                    dto.setId((Integer) layer.get("id"));
                    dto.setName((String) layer.get("name"));
                    return dto;
                }).toList();
                result.setLayers(layerDtos);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Klaida apdorojant ArcGIS atsakymą: " + e.getMessage(), e);
        }
    }
}