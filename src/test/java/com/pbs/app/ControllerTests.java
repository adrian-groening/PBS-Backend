package com.pbs.app;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pbs.Entities.Creator;
import com.pbs.Entities.Favorite;
import com.pbs.Entities.Product;
import com.pbs.Entities.Scan;
import com.pbs.Entities.Share;
import com.pbs.app.Llama.AI;
import com.pbs.app.Services.Data;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Data mockData;

    private AI mockAI;

    @BeforeEach
    public void setupAI() throws Exception {
        // Mock the AI class to return a sample caption
        Mockito.when(mockAI.generateInstagramCaptionForProduct(anyString()))
               .thenReturn("AI GENERATED CAPTION");
    }

    @Test
    public void testGenerateInstagramCaptionForProduct() throws Exception {
    
        String requestBody = "{\"productId\": \"P123\", \"productName\": \"LEGO Play Set\"}";

        
        mockMvc.perform(post("/generateInstagramCaptionForProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
               .andExpect(status().isOk())  
               .andExpect(content().contentType(MediaType.TEXT_PLAIN))  
               .andExpect(content().string("AI GENERATED CAPTION"));  //exact phrase cannot be asserted
    }

    @BeforeEach
    public void setupMocks() throws SQLException {
        Creator mockCreator = new Creator("C1", "John", "Doe", "john.doe@example.com", "password123");
        when(mockData.getCreator(anyString())).thenReturn(mockCreator);
    }

    @Test
    public void testCreatorsScanCount() throws Exception {
        when(mockData.getCreatorScanCount(anyString())).thenReturn(0);

        mockMvc.perform(post("/creatorscancount")
                .param("email", "john.doe@example.com"))
               .andExpect(status().isOk())
               .andExpect(content().string("0"));
    }

    @Test
    public void testCreatorsShareCount() throws Exception {
        when(mockData.getCreatorShareCount(anyString())).thenReturn(2);

        mockMvc.perform(post("/creatorsharecount")
                .param("email", "john.doe@example.com"))
               .andExpect(status().isOk())
               .andExpect(content().string("2"));
    }

    @Test
    public void testMostScannedBrands() throws Exception {
        when(mockData.get5MostScannedBrands()).thenReturn(List.of("Brand1", "Brand2", "Brand3"));

        mockMvc.perform(post("/mostscannedbrands"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0]").value("Brand1"))
               .andExpect(jsonPath("$[1]").value("Brand2"))
               .andExpect(jsonPath("$[2]").value("Brand3"));
    }

    @Test
    public void testMostFavoritedBrands() throws Exception {
        when(mockData.get5MostFavoritedBrands()).thenReturn(List.of("Brand1", "Brand2", "Brand3"));

        mockMvc.perform(post("/mostfavoritedbrands"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0]").value("Brand1"))
               .andExpect(jsonPath("$[1]").value("Brand2"))
               .andExpect(jsonPath("$[2]").value("Brand3"));
    }

    @Test
    public void testMostSharedBrands() throws Exception {
        when(mockData.get5MostSharedBrands()).thenReturn(List.of("Brand1", "Brand2", "Brand3"));

        mockMvc.perform(post("/mostsharedbrands"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0]").value("Brand1"))
               .andExpect(jsonPath("$[1]").value("Brand2"))
               .andExpect(jsonPath("$[2]").value("Brand3"));
    }

    @Test
    public void testRankingOfMostFavorited() throws Exception {
        when(mockData.get5MostFavoritedProducts()).thenReturn(List.of(
            new Favorite("F1", "C1", "P1", "2024-09-10")
        ));
        when(mockData.getProduct(anyString())).thenReturn(new Product());

        mockMvc.perform(post("/rankingofmostfavorited"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].productID").value("P1"));
    }

    @Test
    public void testRankingOfMostScanned() throws Exception {
        when(mockData.get5MostScannedProducts()).thenReturn(List.of(
            new Scan("S1", "C1", "1234567890", "2024-09-10", "Product 1", "photo1.jpg")
        ));

        mockMvc.perform(post("/rankingofmostscanned"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].barcode").value("1234567890"));
    }

    @Test
    public void testRankingOfMostShared() throws Exception {
        when(mockData.get5MostSharedProducts()).thenReturn(List.of(
            new Share("SH1", "C1", "P1", "2024-09-10")
        ));
        when(mockData.getProduct(anyString())).thenReturn(new Product());

        mockMvc.perform(post("/rankingofmostshared"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].productID").value("P1"));
    }
}
