package com.elton.Project.X.BackEnd.functionary.domain.entities;

import java.util.List;
import java.util.UUID;

public record Functionary(
        UUID id,
        String userName,
        String password,
        String description,
        boolean isActive,
        String identifier,
        List<FunctionaryContact> contacts
) {
}
