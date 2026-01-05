package com.kauanallyson.encurtalink.repository;

import com.kauanallyson.encurtalink.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortUrl(String shortUrl);

    boolean existsByShortUrl(String shortUrl);
}
