package com.PizzaDrone.ILPWebService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ILPControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getuuidTest() throws Exception {
        mockMvc.perform(get("/uuid").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("S2335450"));
    }

    @Test
    void distanceTotest() throws Exception {
//        String validRequestBody = "{\"position1\": { \"lng\": 180,\"lat\": 90},\"position2\": {\"lng\": 0,\"lat\": 0}}";
//
//
//        mockMvc.perform(post("/distanceTo")
//                        .contentType(MediaType.APPLICATION_JSON).content(validRequestBody)).andExpect(status().isOk())
//.andExpect(content().toString());
    }


}
