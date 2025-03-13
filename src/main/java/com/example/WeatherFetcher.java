package com.example;

import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherFetcher {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter city name: ");
        String city = scanner.nextLine(); // User enters the city name
        String apiKey = "63a24587d6507ebf94645a771a62f93a"; // Replace with your OpenWeatherMap API key
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    String json = response.body().string();
                    WeatherResponse weatherResponse = objectMapper.readValue(json, WeatherResponse.class);

                    System.out.println("🌡️ Temperature: " + weatherResponse.getMain().getTemp() + "°C");
                    System.out.println("🤔 Feels Like: " + weatherResponse.getMain().getFeels_like() + "°C");
                    System.out.println("💧 Humidity: " + weatherResponse.getMain().getHumidity() + "%");
                    System.out.println("🌀 Wind Speed: " + weatherResponse.getWind().getSpeed() + " m/s");
                    System.out.println("🌬️ Wind Direction: " + weatherResponse.getWind().getDeg() + "°");
                    System.out.println("⛅ Condition: " + weatherResponse.getWeather().get(0).getMain() + " - " + weatherResponse.getWeather().get(0).getDescription());
                } else {
                    System.err.println("Response body is null");
                }
            } else {
                System.err.println("❌ Request failed: " + response.code());
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
