package com.datajpa.relationship.services;

import com.datajpa.relationship.dto.requestDto.AuthorRequestDto;
import com.datajpa.relationship.dto.responseDto.AuthorResponseDto;
import com.datajpa.relationship.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto);
    public List<AuthorResponseDto> getAuthors();
    public AuthorResponseDto getAuthorById(Long id);
    public Author getAuthor(Long authorId);
    public AuthorResponseDto deleteAuthor(Long authorId);
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto);
    public AuthorResponseDto addZipCodeToAuthor(Long aithorId, Long zipCodeId);
    public AuthorResponseDto deleteZipCodeFromAuthor(Long authorId);
}
