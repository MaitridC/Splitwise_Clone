package com.example.splitwise.controller;

import com.example.splitwise.dto.ExpenseDTO;
import com.example.splitwise.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExpenseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testGetAllExpenses() throws Exception {
        when(expenseService.getAllExpenses()).thenReturn(List.of(
                new ExpenseDTO(1L, 100.0, "Lunch", LocalDate.parse("2025-09-22"), 2L, 2L, List.of(2L,3L,4L))
        ));

        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Lunch"));

        verify(expenseService, times(1)).getAllExpenses();
    }

    @Test
    void testCreateExpense() throws Exception {
        ExpenseDTO input = new ExpenseDTO(null, 150.0, "Dinner", LocalDate.parse("2025-09-23"), 1L, 1L, List.of(1L,2L));
        ExpenseDTO created = new ExpenseDTO(2L, 150.0, "Dinner", LocalDate.parse("2025-09-23"), 1L, 1L, List.of(1L,2L));

        when(expenseService.createExpense(any())).thenReturn(created);

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.description").value("Dinner"));
    }
}
