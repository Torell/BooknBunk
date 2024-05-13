package com.example.booknbunk.services.interfaces;

public interface BlacklistService {

    void addToBlacklist(String email);

    boolean checkBlacklist(String email);
}
