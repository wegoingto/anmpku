package com.example.what.model

data class Order(
    var cart: MutableList<Cart>,
    var tbl: Int
);