package com.example.apresentacao.controller;


//import lombok.NoArgsConstructor;
import com.example.apresentacao.services.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.apresentacao.dto.SumResponse;
import com.example.apresentacao.dto.sumRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


//@RestController ("/sum")
//@Data
@RestController
@RequestMapping("/sum")
public class SumController {

    @Autowired
    private CalculateService calculateService;


    @PostMapping(value ="/calculate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SumResponse> calculateSum(@RequestBody sumRequest request) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        SumResponse result = calculateService.sum(request.getA(), request.getB());

        return ResponseEntity.ok(result);

    }



//    public String calculateSum() {
//        int result = getSum();
//        String message = "O Resultado da Soma é = " + result;
//        return message;
//    }

    // how  to get these 2 number from user that send thorught post request 2 numbers and it sum them and return the result
    // 1. create a method that takes 2 numbers as input
    // 2. create a post request that takes 2 numbers as input
    // 3. create a post request that takes 2 numbers as input and return the sum of them

//s





}
