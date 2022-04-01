package com.example.githubuser.Data

data class UserDetail(
    val login : String,
    val avatar_url: String,
    val id : Int,
    val followers_url : String,
    val following_url : String,
    val company : String,
    val public_repos : Int,
    val location : String,
    val name : String,
    val followers : Int,
    val following : Int
)
