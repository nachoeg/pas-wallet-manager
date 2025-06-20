package com.archpatterns.walletmanager.dtos;

public record Response<T>(String message, T data) {
}
