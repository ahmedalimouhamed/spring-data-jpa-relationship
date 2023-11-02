package com.datajpa.relationship.services;

import com.datajpa.relationship.dto.requestDto.ZipCodeRequestDto;
import com.datajpa.relationship.model.City;
import com.datajpa.relationship.model.ZipCode;
import com.datajpa.relationship.repository.ZipCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ZipCodeServiceImpl implements ZipCodeService {

    private final ZipCodeRepository zipCodeRepository;
    private final CityService cityService;

    @Autowired
    public ZipCodeServiceImpl(ZipCodeRepository zipCodeRepository, CityService cityService) {
        this.zipCodeRepository = zipCodeRepository;
        this.cityService = cityService;
    }

    @Transactional
    @Override
    public ZipCode addZipCode(ZipCodeRequestDto zipCodeRequestDto) {
        ZipCode zipCode = new ZipCode();
        zipCode.setName(zipCodeRequestDto.getName());
        if(zipCodeRequestDto.getCityId() == null){
            return zipCodeRepository.save(zipCode);
        }
        City city = cityService.getCity(zipCodeRequestDto.getCityId());
        zipCode.setCity(city);
        return zipCodeRepository.save(zipCode);
    }

    @Override
    public List<ZipCode> getZipCodes() {
        return StreamSupport
                .stream(zipCodeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public ZipCode getZipCode(Long zipCodeId) {
        return zipCodeRepository.findById(zipCodeId).orElseThrow(() ->
                new IllegalArgumentException("Zipcode with id: "+zipCodeId+" could not be found.")
        );
    }

    @Override
    public ZipCode deleteZipCode(Long zipCodeId) {
        ZipCode zipCode = getZipCode(zipCodeId);
        zipCodeRepository.delete(zipCode);
        return zipCode;
    }

    @Transactional
    @Override
    public ZipCode editZipCode(Long zipCodeId, ZipCodeRequestDto zipCodeRequestDto) {
        ZipCode zipCodeToEdit = getZipCode(zipCodeId);
        zipCodeToEdit.setName(zipCodeRequestDto.getName());
        if(zipCodeRequestDto.getCityId() == null){
            return zipCodeToEdit;
        }
        City city = cityService.getCity(zipCodeRequestDto.getCityId());
        zipCodeToEdit.setCity(city);
        return  zipCodeToEdit;
    }

    @Transactional
    @Override
    public ZipCode addCityToZipCode(Long zipCodeId, Long cityId) {
        ZipCode zipCode = getZipCode(zipCodeId);
        City city = cityService.getCity(cityId);
        if(Objects.nonNull(zipCode.getCity())){
            throw new IllegalArgumentException("Zipcode already has a city");
        }
        zipCode.setCity(city);
        return zipCode;
    }

    @Transactional
    @Override
    public ZipCode removeCityFromZipCode(Long zipCodeId) {
        ZipCode zipCode = getZipCode(zipCodeId);
        if(!Objects.nonNull(zipCode.getCity())){
            throw new IllegalArgumentException("Zipcode does not have a city");
        }
        zipCode.setCity(null);
        return zipCode;
    }
}
