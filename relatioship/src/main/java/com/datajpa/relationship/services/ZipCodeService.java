package com.datajpa.relationship.services;

import com.datajpa.relationship.dto.requestDto.ZipCodeRequestDto;
import com.datajpa.relationship.model.ZipCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ZipCodeService {
    public ZipCode addZipCode(ZipCodeRequestDto zipCodeRequestDto);
    public List<ZipCode> getZipCodes();
    public ZipCode getZipCode(Long zipCodeid);
    public ZipCode deleteZipCode(Long zipCodeid);
    public ZipCode editZipCode(Long zipCodeId, ZipCodeRequestDto zipCodeRequestDto);
    public ZipCode addCityToZipCode(Long zipCodeId, Long cityId);
    public ZipCode removeCityFromZipCode(Long zipCodeId);
}
