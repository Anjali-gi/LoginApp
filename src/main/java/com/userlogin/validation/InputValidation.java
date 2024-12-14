package com.userlogin.validation;

import com.userlogin.entity.User;
import org.springframework.stereotype.Service;

@Service
public class InputValidation {


    public boolean validateUser(User user) {
    if(user.getCountry().equals("India")){
        return true;
    }
    return false;

    }
}
