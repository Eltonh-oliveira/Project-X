package com.elton.Project.X.BackEnd.functionary.domain.usecases;

import com.elton.Project.X.BackEnd.core.utils.contact.repository.ContactTypeRepository;
import com.elton.Project.X.BackEnd.functionary.domain.entities.Functionary;
import com.elton.Project.X.BackEnd.functionary.domain.entities.FunctionaryContact;
import com.elton.Project.X.BackEnd.functionary.infraestructure.database.FunctionaryContactDataBase;
import com.elton.Project.X.BackEnd.functionary.infraestructure.database.FunctionaryDataBase;
import com.elton.Project.X.BackEnd.functionary.infraestructure.database.repository.FunctionaryContactRepository;
import com.elton.Project.X.BackEnd.functionary.infraestructure.database.repository.FunctionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public FunctionaryUseCase() {
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity createFunctionary(Functionary functionary) {
        try {
            String errors = "";

            if (!(functionary.id() == null)) {
                errors += ID_CANNOT_BE_ENTERED + "\n";
            }

            if (functionary.userName().trim().equals("")) {
                errors += USER_NAME_NOT_INFORMED + "\n";
            }

            if (functionary.password().trim().equals("")) {
                errors += PASSWORD_NOT_INFORMED + "\n";
            }

            if (!functionary.isActive()) {
                errors += IS_ACTIVE_MUST_BE_TRUE + "\n";
            }

            if (functionaryRepository.findByUsername(functionary.userName()) != null) {
                errors += USER_NAME_ALREADY_REGISTERED + "\n";
            }

            List<String> contactsValidating = new ArrayList<>();
            for (FunctionaryContact contact : functionary.contacts()) {
                if (contact.label().trim().equals("")) {
                    errors += CONTACT_NOT_INFORMED + "\n";
                    break;
                }

                if (contact.type().equals("")) {
                    errors += CONTACT_TYPE_NOT_INFORMED + "\n";
                    break;
                }

                if (contactTypeRepository.findById(contact.type()).isEmpty()) {
                    errors += CONTACT_TYPE_NOT_EXIST + "\n";
                    break;
                }

                if (contactsValidating.contains(contact.label())) {
                    errors += THERE_ARE_REPEATED_CONTACTS + "\n";
                    break;
                }

                contactsValidating.add(contact.label());
            }

            if (!errors.trim().equals("")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }

            FunctionaryDataBase functionaryDataBase = new FunctionaryDataBase();
            functionaryDataBase.setId(UUID.randomUUID());
            functionaryDataBase.setUsername(functionary.userName());
            functionaryDataBase.setPassword(functionary.password());
            functionaryDataBase.setDescription(functionary.description());
            functionaryDataBase.setActive(functionary.isActive());
            functionaryDataBase.setIdentifier(functionary.identifier());
            functionaryDataBase.setCreatedAt(LocalDateTime.now());
            functionaryDataBase.setModifiedAt(LocalDateTime.now());
            functionaryRepository.save(functionaryDataBase);

            for (FunctionaryContact contact : functionary.contacts()) {
                FunctionaryContactDataBase functionaryContactDataBase = new FunctionaryContactDataBase(contact.label(), contactTypeRepository.findById(contact.type()).get(), functionaryDataBase);
                functionaryContactRepository.save(functionaryContactDataBase);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(functionaryDataBase);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a solicitação");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateFunctionary(Functionary functionary) {
        try{
            String errors = "";

            if (functionary.id() == null) {
                errors += TO_CHANGE_ID_MUST_BE_INFORMED + "\n";
            }

            if (functionary.userName().trim().equals("")) {
                errors += USER_NAME_NOT_INFORMED + "\n";
            }

            if (functionary.password().trim().equals("")) {
                errors += PASSWORD_NOT_INFORMED + "\n";
            }
            Optional<FunctionaryDataBase> functionaryVerify = functionaryRepository.findById(functionary.id());
            if(functionaryVerify.isEmpty()){
                errors += USER_NOT_FOUND_IN_DATABASE + "\n";
            }

            if((functionaryVerify.isPresent()) && (functionaryVerify.get().getId() != functionary.id())){
                errors += USER_NAME_ALREADY_REGISTERED_FOR_ANOTHER_USER + "\n";
            }

            List<String> contactsValidating = new ArrayList<>();
            for (FunctionaryContact contact : functionary.contacts()) {
                if (contact.label().trim().equals("")) {
                    errors += CONTACT_NOT_INFORMED + "\n";
                    break;
                }

                if (contact.type().equals("")) {
                    errors += CONTACT_TYPE_NOT_INFORMED + "\n";
                    break;
                }

                if (contactTypeRepository.findById(contact.type()).isEmpty()) {
                    errors += CONTACT_TYPE_NOT_EXIST + "\n";
                    break;
                }

                if (contactsValidating.contains(contact.label())) {
                    errors += THERE_ARE_REPEATED_CONTACTS + "\n";
                    break;
                }
                contactsValidating.add(contact.label());
            }

            if (!errors.trim().equals("")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }

            functionaryContactRepository.deleteContactsByFunctionaryId(functionary.id());

            FunctionaryDataBase functionaryDataBase = new FunctionaryDataBase();
            functionaryDataBase.setId(functionary.id());
            functionaryDataBase.setUsername(functionary.userName());
            functionaryDataBase.setPassword(functionary.password());
            functionaryDataBase.setDescription(functionary.description());
            functionaryDataBase.setActive(functionary.isActive());
            functionaryDataBase.setIdentifier(functionary.identifier());
            functionaryDataBase.setModifiedAt(LocalDateTime.now());
            functionaryRepository.save(functionaryDataBase);

            for (FunctionaryContact contact : functionary.contacts()) {
                FunctionaryContactDataBase functionaryContactDataBase = new FunctionaryContactDataBase(contact.label(), contactTypeRepository.findById(contact.type()).get(), functionaryDataBase);
                functionaryContactRepository.save(functionaryContactDataBase);
            }

            return ResponseEntity.status(HttpStatus.OK).body(functionary);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a solicitação");
        }
    }
}
