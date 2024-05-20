package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.utils.Blacklist;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface BlacklistService {

    void addToBlacklist(Blacklist blacklist) throws JsonProcessingException;

    void removeFromBlacklist(Blacklist blacklist) throws JsonProcessingException;

    boolean checkBlacklist(String email);
}
