package com.kauanallyson.encurtalink.controller;

import com.kauanallyson.encurtalink.dto.LongUrlRequest;
import com.kauanallyson.encurtalink.dto.ShortUrlResponse;
import com.kauanallyson.encurtalink.service.ShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ShortenerController {
    private final ShortenerService shortUrlService;

    @PostMapping("/shorten-url")
    public ResponseEntity<ShortUrlResponse> shorten(@RequestBody LongUrlRequest request) {
        String shortCode = shortUrlService.createShortenUrl(request.url());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ShortUrlResponse(shortCode));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = shortUrlService.fetchOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}