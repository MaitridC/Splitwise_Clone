package com.example.splitwise.service;

import com.example.splitwise.dto.GroupDTO;
import com.example.splitwise.entity.Group;
import com.example.splitwise.entity.User;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    // Get all groups
    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Get group by ID
    public GroupDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found: " + id));
        return toDTO(group);
    }

    // Create a new group
    public GroupDTO createGroup(GroupDTO dto) {
        Group group = new Group();
        group.setGroupName(dto.getGroupName());

        if (dto.getUserIds() != null) {
            List<User> users = dto.getUserIds().stream()
                    .map(userId -> userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found: " + userId)))
                    .collect(Collectors.toList());
            group.setUsers(users);
        }

        return toDTO(groupRepository.save(group));
    }

    // Update existing group
    public GroupDTO updateGroup(Long id, GroupDTO dto) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found: " + id));

        group.setGroupName(dto.getGroupName());

        if (dto.getUserIds() != null) {
            List<User> users = dto.getUserIds().stream()
                    .map(userId -> userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found: " + userId)))
                    .collect(Collectors.toList());
            group.setUsers(users);
        }

        return toDTO(groupRepository.save(group));
    }

    // Delete a group
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new RuntimeException("Group not found: " + id);
        }
        groupRepository.deleteById(id);
    }

    // Helper to convert Group entity to DTO
    private GroupDTO toDTO(Group group) {
        List<Long> userIds = group.getUsers().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        return new GroupDTO(group.getGroupId(), group.getGroupName(), userIds);
    }
}
