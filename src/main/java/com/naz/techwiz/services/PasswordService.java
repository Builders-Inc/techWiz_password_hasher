package com.naz.techwiz.services;

import org.springframework.stereotype.Service;

@Service
public interface PasswordService {
    String passwordEncryption( String password);
    String passwordDecryption(String password);

}
