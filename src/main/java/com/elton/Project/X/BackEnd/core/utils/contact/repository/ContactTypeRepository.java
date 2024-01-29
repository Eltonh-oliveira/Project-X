package com.elton.Project.X.BackEnd.core.utils.contact.repository;

import com.elton.Project.X.BackEnd.core.utils.contact.database.ContactTypeDataBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactTypeRepository extends JpaRepository<ContactTypeDataBase, UUID> {
}
