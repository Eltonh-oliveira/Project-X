package com.elton.Project.X.BackEnd.functionary.infraestructure.service;

import com.elton.Project.X.BackEnd.functionary.domain.entities.FunctionaryCreate;
import com.elton.Project.X.BackEnd.functionary.domain.usecases.FunctionaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/functionary")
public class FunctionaryService {

    @Autowired
    private FunctionaryUseCase functionaryUseCase;

    @PostMapping("/")
    public ResponseEntity createFunctionary (
            @RequestBody FunctionaryCreate functionaryCreate
    ){
        return functionaryUseCase.createFunctionary(functionaryCreate);
    }
}
