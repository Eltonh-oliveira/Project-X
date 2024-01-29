package com.elton.Project.X.BackEnd.functionary.domain.entities;

import com.elton.Project.X.BackEnd.functionary.infraestructure.database.FunctionaryContactDataBase;

import java.util.List;

public record FunctionaryCreate(
        String userName,
        String password,
        String description,
        boolean isActive,
        String identifier,
        List<FunctionaryContact> contacts
) {
}
