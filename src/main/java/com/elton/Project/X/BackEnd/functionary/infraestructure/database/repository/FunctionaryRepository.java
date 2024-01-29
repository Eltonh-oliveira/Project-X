package com.elton.Project.X.BackEnd.functionary.infraestructure.database.repository;

import com.elton.Project.X.BackEnd.functionary.infraestructure.database.FunctionaryDataBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FunctionaryRepository extends JpaRepository<FunctionaryDataBase, UUID> {

    FunctionaryRepository findByUsername(String username);
}
