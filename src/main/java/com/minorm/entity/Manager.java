package com.minorm.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User {

    private String projectName;

    @Builder
    public Manager(Long id, PersonalInfo personalInfo, String username, Role role, Company company, Profile profile, List<UserChat> userChats, String projectName) {
        super(id, personalInfo, username, role, company, profile, userChats);
        this.projectName = projectName;
    }
}
