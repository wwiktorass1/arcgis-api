package lt.ssva.arcgis_api.controller;

import lt.ssva.arcgis_api.dto.MapServerResponseDto;
import lt.ssva.arcgis_api.service.MapServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/mapserver")
@Validated
public class MapServerController {

    private final MapServerService service;

    public MapServerController(MapServerService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
        summary = "Gauti MapServer informaciją",
        description = "Grąžina metaduomenis ir sluoksnių sąrašą iš ArcGIS MapServer URL",
        responses = {
            @ApiResponse(responseCode = "200", description = "Sėkmingai grąžinta informacija"),
            @ApiResponse(responseCode = "400", description = "Netinkamas URL"),
            @ApiResponse(responseCode = "500", description = "Serverio klaida")
        }
    )
    public ResponseEntity<MapServerResponseDto> getMapInfo(
            @RequestParam
            @NotBlank(message = "URL negali būti tuščias")
            @Pattern(regexp = "^https?://.+", message = "URL turi prasidėti http:// arba https://")
            String url) {
        MapServerResponseDto response = service.fetchAndTransform(url);
        return ResponseEntity.ok(response);
    }
}