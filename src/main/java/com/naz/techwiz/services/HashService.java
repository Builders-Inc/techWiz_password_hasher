package com.naz.techwiz.services;

import org.springframework.stereotype.Service;

@Service
public interface HashService {
    String passwordEncryption( String password);
}