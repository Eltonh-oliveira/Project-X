package com.elton.Project.X.BackEnd.functionary.domain.entities;

import java.util.UUID;

public record FunctionaryContact(
        String label,

        UUID type
) {
}
