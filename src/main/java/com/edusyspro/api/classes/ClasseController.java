package com.edusyspro.api.classes;

import com.edusyspro.api.classes.dtos.ClassBasicValue;
import com.edusyspro.api.data.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/classes"})
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @GetMapping("/basic")
    ResponseEntity<List<ClassBasicValue>> getAllClassesBasicValue() {
        return ResponseEntity.ok(classeService.getClassBasicValues(UUID.fromString(ConstantUtils.SCHOOL_ID)));
    }

    @GetMapping(value = {"", "/all"})
    ResponseEntity<List<Classe>> getClasses() {
        return ResponseEntity.ok(classeService.getClasses());
    }
}
