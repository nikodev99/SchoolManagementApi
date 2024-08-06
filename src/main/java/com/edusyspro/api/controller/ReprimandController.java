package com.edusyspro.api.controller;

import com.edusyspro.api.entities.Reprimand;
import com.edusyspro.api.service.ReprimandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/blame"})
public class ReprimandController {

    private final ReprimandService reprimandService;

    @Autowired
    public ReprimandController(ReprimandService reprimandService) {
        this.reprimandService = reprimandService;
    }

    @GetMapping("/{studentId}")
    ResponseEntity<List<Reprimand>> getReprimands(@PathVariable String studentId) {
        return ResponseEntity.ok(reprimandService.findStudentReprimand(studentId));
    }

}
