package com.elton.Project.X.BackEnd.core.utils.contact.utils;

import com.elton.Project.X.BackEnd.core.utils.contact.database.ContactTypeDataBase;
import com.elton.Project.X.BackEnd.core.utils.contact.repository.ContactTypeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private ContactTypeRepository contactTypeRepository;

    @PostConstruct
    public void init() {
        ContactTypeDataBase contactTypeTelephone = new ContactTypeDataBase("d609a294-d8da-425c-9480-1f48ea647cbd","Telefone");
        ContactTypeDataBase contactTypeEmail = new ContactTypeDataBase("d83de7c2-5bc5-47aa-963d-c70ff0938e6a","Email");

        contactTypeRepository.save(contactTypeTelephone);
        contactTypeRepository.save(contactTypeEmail);
    }
}
