package com.elton.Project.X.BackEnd.functionary.domain.usecases;

import com.elton.Project.X.BackEnd.core.utils.contact.database.ContactTypeDataBase;
import com.elton.Project.X.BackEnd.core.utils.contact.repository.ContactTypeRepository;
import com.elton.Project.X.BackEnd.functionary.domain.entities.FunctionaryContact;
import com.elton.Project.X.BackEnd.functionary.domain.entities.FunctionaryCreate;
import com.elton.Project.X.BackEnd.functionary.infraestructure.database.FunctionaryContactDataBase;
import com.elton.Project.X.BackEnd.functionary.infraestructure.database.FunctionaryDataBase;
import com.elton.Project.X.BackEnd.functionary.infraestructure.database.repository.FunctionaryContactRepository;
import com.elton.Project.X.BackEnd.functionary.infraestructure.database.repository.FunctionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.elton.Project.X.BackEnd.functionary.domain.errors.FunctionaryErrors.*;

@Repository
public class FunctionaryUseCase {

    @Autowired
    private FunctionaryRepository functionaryRepository;

    @Autowired
    private ContactTypeRepository contactTypeRepository;

    @Autowired
    private FunctionaryContactRepository functionaryContactRepository;

    public ResponseEntity createFunctionary(FunctionaryCreate functionaryCreate) {

        String errors = "";

        if (functionaryCreate.userName().trim().equals("")) {
            errors += USER_NAME_NOT_INFORMED + "\n";
        }

        if (functionaryCreate.password().trim().equals("")) {
            errors += PASSWORD_NOT_INFORMED + "\n";
        }

        if (!functionaryCreate.isActive()) {
            errors += IS_ACTIVE_MUST_BE_TRUE + "\n";
        }

        System.out.println(functionaryCreate.userName());
        System.out.println(functionaryRepository.findByUsername(functionaryCreate.userName()));
        if(functionaryRepository.findByUsername(functionaryCreate.userName()) != null){
            errors += USER_NAME_ALREADY_REGISTERED + "\n";
        }

        List<String> contactsValidating = new ArrayList<>();

        for (FunctionaryContact contact : functionaryCreate.contacts()) {
            if (contact.label().trim().equals("")) {
                errors += CONTACT_NOT_INFORMED + "\n";
                break;
            }

            if (contact.type().equals("")) {
                errors += CONTACT_TYPE_NOT_INFORMED + "\n";
                break;
            }

            if(contactTypeRepository.findById(contact.type()).isEmpty()){
                errors += CONTACT_TYPE_NOT_EXIST + "\n";
                break;
            }

            if (contactsValidating.contains(contact.label())) {
                errors += THERE_ARE_REPEATED_CONTACTS + "\n";
                break;
            }

            contactsValidating.add(contact.label());
        }

        if(!errors.trim().equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        List<FunctionaryContactDataBase> contacts = new ArrayList<>();
        for (FunctionaryContact contact : functionaryCreate.contacts()) {
            FunctionaryContactDataBase functionaryContactDataBase = new FunctionaryContactDataBase(contact.label(), contactTypeRepository.findById(contact.type()).get());

            UUID uuid = UUID.randomUUID();
            functionaryContactDataBase.setId(uuid);
            functionaryContactRepository.save(functionaryContactDataBase);

            contacts.add(functionaryContactDataBase);
        }

        FunctionaryDataBase functionaryDataBase = new FunctionaryDataBase();
        functionaryDataBase.setUsername(functionaryCreate.userName());
        functionaryDataBase.setPassword(functionaryCreate.password());
        functionaryDataBase.setDescription(functionaryCreate.description());
        functionaryDataBase.setActive(functionaryCreate.isActive());
        functionaryDataBase.setIdentifier(functionaryCreate.identifier());
        functionaryDataBase.setContacts(contacts);

        System.out.println(functionaryDataBase);

        functionaryRepository.save(functionaryDataBase);

        return ResponseEntity.status(HttpStatus.CREATED).body(functionaryCreate);
    }
}
