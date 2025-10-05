package com.example.splitwise.controller;

import com.example.splitwise.dto.GroupDTO;
import com.example.splitwise.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GroupControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    void testGetAllGroups() throws Exception {
        when(groupService.getAllGroups()).thenReturn(List.of(new GroupDTO(1L, "Family", List.of(1L,2L))));

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Family"));

        verify(groupService, times(1)).getAllGroups();
    }

    @Test
    void testCreateGroup() throws Exception {
        GroupDTO input = new GroupDTO(null, "Friends", List.of(3L,4L));
        GroupDTO created = new GroupDTO(2L, "Friends", List.of(3L,4L));

        when(groupService.createGroup(any())).thenReturn(created);

        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").value(2))
                .andExpect(jsonPath("$.name").value("Friends"));
    }
}
