package com.example.splitwise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupDTO {

    private Long groupId;

    @JsonProperty("name")
    private String groupName;

    @JsonProperty("memberIds")
    private List<Long> userIds; // IDs of users in the group

    public GroupDTO() {}

    public GroupDTO(Long groupId, String groupName, List<Long> userIds) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.userIds = userIds;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
