package com.emre.exception;

import com.emre.enums.ExceptionMessages;

public class ExceptionUtils {

    public static String getExceptionMessage(ExceptionMessages exceptionMessages) {
        if(exceptionMessages == ExceptionMessages.POST_NOT_FOUND_EXCEPTION) {
            return "Post Not Found Exception : ";
        }else if(exceptionMessages == ExceptionMessages.USER_NOT_FOUND_EXCEPTION) {
            return "User Not Found Exception : ";
        }else {
            return "";
        }
    }
}
