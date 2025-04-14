package com.csms.controller;

import com.csms.dto.CreateOverDto;
import com.csms.dto.IdDto;
import com.csms.service.OverService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/overs")
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class OversController {

    private final OverService overService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'TM')")
    @PostMapping("/create")
    ResponseEntity createOver(@Validated @RequestBody CreateOverDto body){
        return overService.createOver(body);
    }

    @GetMapping("/get/{overId}")
    ResponseEntity createOver(@PathVariable("overId") long overId){
        return overService.getOverDetails(overId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TM')")
    @PutMapping("/update/{overId}")
    ResponseEntity updateOver(@PathVariable("overId") long overId, @Validated @RequestBody IdDto overData){
        return overService.updateOver(overId, overData);
    }
}
