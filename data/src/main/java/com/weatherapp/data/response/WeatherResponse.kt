package com.weatherapp.data.response

data class WeatherResponse(
    val coord : Coord,
    val weather : List<Weather>,
    val base : String,
    val main : Main,
    val visibility : Int,
    val wind : Wind,
    val clouds : Clouds,
    val dt : Int,
    val sys : Sys,
    val id : Int,
    val name : String,
    val cod : Int
)

data class Clouds (val all : Int)

data class Coord (
    val lon : Double,
    val lat : Double
)

data class Main (
    val temp : Double,
    val pressure : Int,
    val humidity : Int,
    val temp_min : Double,
    val temp_max : Double
)

data class Sys (
    val type : Int,
    val id : Int,
    val message : Double,
    val country : String,
    val sunrise : Int,
    val sunset : Int
)

data class Weather (
    val id : Int,
    val main : String,
    val description : String,
    val icon : String
)

data class Wind (
    val speed : Double,
    val deg : Int
)