package com.kauanallyson.encurtalink.service;

import com.kauanallyson.encurtalink.exceptions.ResourceNotFoundException;
import com.kauanallyson.encurtalink.model.ShortUrl;
import com.kauanallyson.encurtalink.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortenerService {
    private final ShortUrlRepository shortUrlRepository;

    public String createShortenUrl(String longUrl) {
        String randomCode;

        do {
            randomCode = generateShortCode();
        } while (shortUrlRepository.existsById(randomCode)); // loop até ser válido

        ShortUrl shortUrl = new ShortUrl(randomCode, longUrl);
        shortUrlRepository.save(shortUrl);
        return shortUrl.getShortUrl();
    }

    public String fetchOriginalUrl(String shortCode) {
        Optional<ShortUrl> url = shortUrlRepository.findById(shortCode);

        if (url.isEmpty()) {
            throw new ResourceNotFoundException("Short URL not found");
        }
        return url.get().getLongUrl();
    }

    private String generateShortCode() {
        int size = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}