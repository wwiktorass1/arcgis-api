package lt.ssva.arcgis_api.controller;

import lt.ssva.arcgis_api.service.MapServerService;
import lt.ssva.arcgis_api.dto.MapServerResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MapServerController.class)
public class MapServerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MapServerService mapServerService;

    @Test
    public void testMapInfoSuccess() throws Exception {
        MapServerResponseDto dummyResponse = new MapServerResponseDto();
        dummyResponse.setMapName("Demo Map");
        dummyResponse.setDescription("Demo description");

        when(mapServerService.fetchAndTransform("http://demo.server")).thenReturn(dummyResponse);

        mockMvc.perform(get("/api/mapserver")
                .param("url", "http://demo.server"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mapName").value("Demo Map"))
                .andExpect(jsonPath("$.description").value("Demo description"));
    }
}