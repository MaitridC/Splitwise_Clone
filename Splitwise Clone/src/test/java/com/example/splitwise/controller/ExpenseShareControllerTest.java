package com.example.splitwise.controller;

import com.example.splitwise.dto.ExpenseShareDTO;
import com.example.splitwise.service.ExpenseShareService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExpenseShareControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExpenseShareService expenseShareService;

    @InjectMocks
    private ExpenseShareController expenseShareController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(expenseShareController).build();
    }

    @Test
    void testGetAllShares() throws Exception {
        ExpenseShareDTO share1 = new ExpenseShareDTO(1L, 1L, 2L, 50.0, false);
        ExpenseShareDTO share2 = new ExpenseShareDTO(2L, 1L, 3L, 50.0, false);
        List<ExpenseShareDTO> shares = Arrays.asList(share1, share2);

        when(expenseShareService.getAllShares()).thenReturn(shares);

        mockMvc.perform(get("/api/expense-shares"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(shares)));

        verify(expenseShareService, times(1)).getAllShares();
    }

    @Test
    void testGetShareById() throws Exception {
        ExpenseShareDTO share = new ExpenseShareDTO(1L, 1L, 2L, 50.0, false);

        when(expenseShareService.getShareById(1L)).thenReturn(share);

        mockMvc.perform(get("/api/expense-shares/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(share)));

        verify(expenseShareService, times(1)).getShareById(1L);
    }
}
