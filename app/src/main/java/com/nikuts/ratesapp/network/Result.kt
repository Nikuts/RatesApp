package com.nikuts.ratesapp.network

sealed class Result<out T> {
    data class Success<out T> (val value: T): Result<T> ()
    data class Failure(val error: ResponseError): Result<Nothing> ()
}