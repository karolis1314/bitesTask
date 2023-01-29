package com.karolis.bite.Constants;

public class CommonMethods {
    public static <T> String formatErrorMessageForConstantMessage(String message, T id){
        return String.format(message, id);
    }
        }
