package com.brac.power.plant.system.controller;

import com.brac.power.plant.system.dto.*;
import com.brac.power.plant.system.service.BatteriesService;
import com.brac.power.plant.system.entity.Batteries;
import com.brac.power.plant.system.exception.ExistingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/batteries")
public class BatteriesController {

    @Autowired
    private BatteriesService service;

    @Autowired
    private HttpServletRequest request;

    @PostMapping
    public ResponseEntity<?> saveBatteries(@RequestBody @Valid BatteriesDto userRequest, BindingResult result) throws ExistingException {

        if (result.hasErrors()){
            return buildValidationErrorResponse(result);
        }

        Batteries user = service.saveBatteries(userRequest);
        ResponseDto responseDto = new ResponseDto();
        if (user != null){
            responseDto.setCode(HttpStatus.CREATED.value());
            responseDto.setStatus("SUCCESS");
            responseDto.setMessage("Batteries has been saved successfully.");
            responseDto.setData(user);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    @GetMapping
    public BatteryResponse getBatteriesByPostCodeRange(@RequestParam String postCodeRange) {
        String[] range = postCodeRange.split("-");
        String startPostCode = range[0];
        String endPostCode = range[1];
        return service.getBatteriesByPostCodeRange(startPostCode, endPostCode);
    }

    public ResponseEntity<ErrorResponse> buildValidationErrorResponse(BindingResult result) {
        List<ErrorDetail> errors = result.getFieldErrors().stream()
                .map(error -> new ErrorDetail(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        errorResponse.setStatus("Bed Request");
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Validation failed");
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setData(new ErrorData(errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


}


