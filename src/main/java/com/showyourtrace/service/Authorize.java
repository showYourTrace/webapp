package com.showyourtrace.service;


public interface Authorize {

    boolean authorize(String username, String password);

    String ticket(String hash);

}
