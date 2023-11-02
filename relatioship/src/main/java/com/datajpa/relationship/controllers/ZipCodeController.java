package com.datajpa.relationship.controllers;

import com.datajpa.relationship.dto.requestDto.ZipCodeRequestDto;
import com.datajpa.relationship.model.ZipCode;
import com.datajpa.relationship.services.ZipCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zipcode")
public class ZipCodeController {

    private final ZipCodeService zipCodeService;

    @Autowired
    public ZipCodeController(ZipCodeService zipCodeService) {
        this.zipCodeService = zipCodeService;
    }

    @PostMapping("/add")
    public ResponseEntity<ZipCode> addZipCode(@RequestBody final ZipCodeRequestDto zipCodeRequestDto){
        ZipCode zipCode = zipCodeService.addZipCode(zipCodeRequestDto);
        return new ResponseEntity<>(zipCode, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ZipCode> getZipCode(@PathVariable final long id){
        ZipCode zipCode = zipCodeService.getZipCode(id);
        return new ResponseEntity<>(zipCode, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<ZipCode>> gztZipCodes(){
        List<ZipCode> zipCodes = zipCodeService.getZipCodes();
        return new ResponseEntity<>(zipCodes, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ZipCode> deleteZipCode(@PathVariable final long id){
        ZipCode zipCode = zipCodeService.deleteZipCode(id);
        return new ResponseEntity<>(zipCode, HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity editZipCode(@RequestBody final ZipCodeRequestDto zipCodeRequestDto, @PathVariable final long id){
        ZipCode zipCode = zipCodeService.editZipCode(id, zipCodeRequestDto);
        return new ResponseEntity(zipCode, HttpStatus.OK);
    }

    @PostMapping("/addCity/{cityId}/toZipcode/{zipcodeId}")
    public ResponseEntity<ZipCode> addCity(@PathVariable final long cityId, @PathVariable final long zipcodeId){
        ZipCode zipCode = zipCodeService.addCityToZipCode(zipcodeId, cityId);
        return new ResponseEntity<>(zipCode, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCity/{zipcodeId}")
    public ResponseEntity<ZipCode> deleteCity(@PathVariable final long zipcodeId){
        ZipCode zipCode = zipCodeService.deleteZipCode(zipcodeId);
        return new ResponseEntity<>(zipCode, HttpStatus.OK);
    }
}
