package com.datajpa.relationship.services;

import com.datajpa.relationship.dto.mapper;
import com.datajpa.relationship.dto.requestDto.AuthorRequestDto;
import com.datajpa.relationship.dto.responseDto.AuthorResponseDto;
import com.datajpa.relationship.model.Author;
import com.datajpa.relationship.model.ZipCode;
import com.datajpa.relationship.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ZipCodeService zipCodeService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ZipCodeService zipCodeService) {
        this.authorRepository = authorRepository;
        this.zipCodeService = zipCodeService;
    }

    @Transactional
    @Override
    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto) {
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        if(authorRequestDto.getZipCodeId() == null){
            throw new IllegalArgumentException("Author need a zipCode.");
        }

        ZipCode zipCode = zipCodeService.getZipCode(authorRequestDto.getZipCodeId());
        author.setZipCode(zipCode);
        authorRepository.save(author);
        return mapper.authorToAuthorResponseDto(author);
    }

    @Override
    public List<AuthorResponseDto> getAuthors() {
        List<Author> authors = StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return mapper.authorsToAuthorResponseDtos(authors);
    }

    @Override
    public AuthorResponseDto getAuthorById(Long authorId) {
        return mapper.authorToAuthorResponseDto(getAuthor(authorId));
    }

    @Override
    public Author getAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(
                () -> new IllegalArgumentException("Author with id : "+authorId+" could not be found.")
        );
        return author;
    }

    @Override
    public AuthorResponseDto deleteAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        authorRepository.delete(author);
        return mapper.authorToAuthorResponseDto(author);
    }

    @Transactional
    @Override
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto) {
        Author authorToEdit = getAuthor(authorId);
        authorToEdit.setName(authorRequestDto.getName());
        if(authorRequestDto.getZipCodeId() != null){
            ZipCode zipCode = zipCodeService.getZipCode(authorRequestDto.getZipCodeId());
            authorToEdit.setZipCode(zipCode);
        }
        return mapper.authorToAuthorResponseDto(authorToEdit);
    }

    @Transactional
    @Override
    public AuthorResponseDto addZipCodeToAuthor(Long authorId, Long zipCodeId) {
        Author author = getAuthor(authorId);
        ZipCode zipCode = zipCodeService.getZipCode(zipCodeId);
        if(Objects.nonNull(author.getZipCode())){
            throw new RuntimeException("author already has a zipcode");
        }
        author.setZipCode(zipCode);
        return mapper.authorToAuthorResponseDto(author);
    }

    @Transactional
    @Override
    public AuthorResponseDto deleteZipCodeFromAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        author.setZipCode(null);
        return mapper.authorToAuthorResponseDto(author);
    }
}
