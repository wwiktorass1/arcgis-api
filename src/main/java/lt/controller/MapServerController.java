package lt.ssva.arcgis_api.controller;

import lt.ssva.arcgis_api.dto.LayerDto;
import lt.ssva.arcgis_api.dto.MapServerResponseDto;
import lt.ssva.arcgis_api.service.MapServerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/mapserver")
public class MapServerController {

    private final MapServerService service;

    public MapServerController(MapServerService service) {
        this.service = service;
    }

    @GetMapping
    public MapServerResponseDto getMapInfo(@RequestParam String url) {
        return service.fetchAndTransform(url);
    }
}