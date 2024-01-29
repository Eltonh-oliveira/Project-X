package com.elton.Project.X.BackEnd.functionary.infraestructure.database.repository;

import com.elton.Project.X.BackEnd.functionary.infraestructure.database.FunctionaryContactDataBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FunctionaryContactRepository extends JpaRepository<FunctionaryContactDataBase, UUID> {
}
