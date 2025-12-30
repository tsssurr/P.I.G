package com.inventaris.util;

import com.inventaris.model.Pengguna;

public class Session{
    private static Pengguna currentUser;

    public static void setCurrentUser(Pengguna user){
        currentUser = user;
    }

    public static Pengguna getCurrentUser(){
        return currentUser;
    }
    
    public static void logout(){
        currentUser = null;
    }
}
