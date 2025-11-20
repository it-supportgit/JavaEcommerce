package com.example.service;

public class UserService {

    public String getUserName(int id) {
        return "John Doe";
    }

    public void updateUser(int id) {
        // ISSUE 1: System.out.println (should be detected)
        System.out.println("Updating user: " + id);

        // ISSUE 2: Missing @Override (should NOT be detected)
        // because this method overrides a parent class method
        saveUser(id); 
    }

    private void saveUser(int id) {
        // some logic
    }
}
