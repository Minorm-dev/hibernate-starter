package com.minorm.dto;

import com.minorm.entity.PersonalInfo;
import com.minorm.entity.Role;
import com.minorm.validation.UpdateCheck;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public record UserCreateDto(
        @Valid
        PersonalInfo personalInfo,
        @NotNull
        String username,
        String info,
        @NotNull(groups = UpdateCheck.class)
        Role role,
//                            @ValidCompany
        Integer companyId) {
}
