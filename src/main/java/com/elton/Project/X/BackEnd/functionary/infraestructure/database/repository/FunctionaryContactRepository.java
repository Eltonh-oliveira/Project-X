package com.elton.Project.X.BackEnd.functionary.infraestructure.database.repository;

import com.elton.Project.X.BackEnd.functionary.infraestructure.database.FunctionaryContactDataBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface FunctionaryContactRepository extends JpaRepository<FunctionaryContactDataBase, UUID> {

    @Modifying
    @Query("DELETE FROM functionary_contact fc WHERE fc.functionary.id = :functionaryId")
    void deleteContactsByFunctionaryId(@Param("functionaryId") UUID functionaryId);

}
