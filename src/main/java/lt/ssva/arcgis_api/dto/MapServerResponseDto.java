package lt.ssva.arcgis_api.dto;

import java.util.List;

public class MapServerResponseDto {
    private String mapName;
    private String description;
    private List<LayerDto> layers;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LayerDto> getLayers() {
        return layers;
    }

    public void setLayers(List<LayerDto> layers) {
        this.layers = layers;
    }
}