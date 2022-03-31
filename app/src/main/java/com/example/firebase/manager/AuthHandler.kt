package com.example.firebase.manager

import java.lang.Exception

interface AuthHandler {
    fun onSuccss()
    fun onError(exception: Exception)
}