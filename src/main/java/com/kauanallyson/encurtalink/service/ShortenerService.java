package com.kauanallyson.encurtalink.service;

import com.kauanallyson.encurtalink.dto.ShortUrlResponse;
import com.kauanallyson.encurtalink.exceptions.ExpiredDateException;
import com.kauanallyson.encurtalink.exceptions.ResourceNotFoundException;
import com.kauanallyson.encurtalink.model.ShortUrl;
import com.kauanallyson.encurtalink.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortenerService {
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlResponse createShortenUrl(String longUrl) {
        String randomCode;

        do {
            randomCode = generateShortCode();
        } while (shortUrlRepository.existsByShortUrl(randomCode)); // loopa até ser válido

        ShortUrl shortUrl = ShortUrl.builder()
                .longUrl(longUrl)
                .shortUrl(randomCode)
                .expirationDate(LocalDateTime.now().plusDays(30)).build();
        shortUrlRepository.save(shortUrl);

        String responseUrl = "https://xxx.com/" + shortUrl.getShortUrl();
        return new ShortUrlResponse(responseUrl);
    }

    public String fetchOriginalUrl(String shortCode) {
        Optional<ShortUrl> url = shortUrlRepository.findByShortUrl(shortCode);

        if (url.isEmpty()) {
            throw new ResourceNotFoundException("Short URL not found");
        }

        if (url.get().getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ExpiredDateException("Short URL has expired");
        }
        return url.get().getLongUrl();
    }

    private String generateShortCode() {
        SecureRandom random = new SecureRandom();
        int size = 5 + random.nextInt(6);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}