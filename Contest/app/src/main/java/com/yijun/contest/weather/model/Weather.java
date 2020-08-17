package com.yijun.contest.weather.model;

import java.io.Serializable;

public class Weather implements Serializable {
    private String location_city;
    private int wind_chill;
    private int wind_direction;
    private float wind_speed;
    private int atmosphere_humidity;
    private int atmosphere_visibility;
    private float atmosphere_pressure;
    private String astronomy_sunrise;
    private String astronomy_sunset;
    private String condition_text;
    private int condition_code;
    private int condition_temperature;

    public Weather (){

    }

    public Weather(String location_city, int wind_chill, int wind_direction, float wind_speed,
                   int atmosphere_humidity, int atmosphere_visibility, float atmosphere_pressure, String astronomy_sunrise,
                   String astronomy_sunset, String condition_text, int condition_code, int condition_temperature) {
        this.location_city = location_city;
        this.wind_chill = wind_chill;
        this.wind_direction = wind_direction;
        this.wind_speed = wind_speed;
        this.atmosphere_humidity = atmosphere_humidity;
        this.atmosphere_visibility = atmosphere_visibility;
        this.atmosphere_pressure = atmosphere_pressure;
        this.astronomy_sunrise = astronomy_sunrise;
        this.astronomy_sunset = astronomy_sunset;
        this.condition_text = condition_text;
        this.condition_code = condition_code;
        this.condition_temperature = condition_temperature;
    }

    public String getLocation_city() {
        return location_city;
    }

    public void setLocation_city(String location_city) {
        this.location_city = location_city;
    }

    public int getWind_chill() {
        return wind_chill;
    }

    public void setWind_chill(int wind_chill) {
        this.wind_chill = wind_chill;
    }

    public int getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(int wind_direction) {
        this.wind_direction = wind_direction;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getAtmosphere_humidity() {
        return atmosphere_humidity;
    }

    public void setAtmosphere_humidity(int atmosphere_humidity) {
        this.atmosphere_humidity = atmosphere_humidity;
    }

    public int getAtmosphere_visibility() {
        return atmosphere_visibility;
    }

    public void setAtmosphere_visibility(int atmosphere_visibility) {
        this.atmosphere_visibility = atmosphere_visibility;
    }

    public float getAtmosphere_pressure() {
        return atmosphere_pressure;
    }

    public void setAtmosphere_pressure(float atmosphere_pressure) {
        this.atmosphere_pressure = atmosphere_pressure;
    }

    public String getAstronomy_sunrise() {
        return astronomy_sunrise;
    }

    public void setAstronomy_sunrise(String astronomy_sunrise) {
        this.astronomy_sunrise = astronomy_sunrise;
    }

    public String getAstronomy_sunset() {
        return astronomy_sunset;
    }

    public void setAstronomy_sunset(String astronomy_sunset) {
        this.astronomy_sunset = astronomy_sunset;
    }

    public String getCondition_text() {
        return condition_text;
    }

    public void setCondition_text(String condition_text) {
        this.condition_text = condition_text;
    }

    public int getCondition_code() {
        return condition_code;
    }

    public void setCondition_code(int condition_code) {
        this.condition_code = condition_code;
    }

    public int getCondition_temperature() {
        return condition_temperature;
    }

    public void setCondition_temperature(int condition_temperature) {
        this.condition_temperature = condition_temperature;
    }
}
