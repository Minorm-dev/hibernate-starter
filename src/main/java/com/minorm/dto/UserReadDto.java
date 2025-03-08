package com.minorm.dto;

import com.minorm.entity.PersonalInfo;
import com.minorm.entity.Role;

public record UserReadDto(Long id,
                          PersonalInfo personalInfo,
                          String username,
                          String info,
                          Role role,
                          CompanyReadDto company) {
}
