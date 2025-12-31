package com.kauanallyson.encurtalink.repository;

import com.kauanallyson.encurtalink.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {
}
