package ua.example.springboot.service.Impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.example.springboot.model.UrlShortener;
import ua.example.springboot.repository.UrlShortenerRepository;
import ua.example.springboot.service.UrlShortenerService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
/*@Primary*/
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final UrlShortenerRepository repository;

    @Override
    public List<UrlShortener> findAllUrlShortener() {
        return repository.findAll();
    }

    @Override
    public UrlShortener saveUrlShortener(UrlShortener tinyurl) {
        UrlShortener existingTinyurl = repository.findUrlshortenerByLongUrl(tinyurl.getLongUrl());
        if (existingTinyurl != null) {
            return existingTinyurl;
        }
        Integer counter = repository.getCounter();

        String generatedString = Stream.of(counter/52/52/52/52/52 %52 + 1,
                        counter/52/52/52/52 %52 + 1,
                        counter/52/52/52 %52 + 1,
                        counter/52/52 %52 +1,
                        counter/52 %52 + 1,
                        counter%52 + 1)
                .map(x -> String.valueOf((char) (x >= 27 ? x + 38 : x + 96)))
                .collect(Collectors.joining());
        tinyurl.setTinyUrl(generatedString);
        return repository.save(tinyurl);
    }

    @Override
    public String findByUrlShortener(String tinyUrl) {
        UrlShortener tinyurl = repository.findUrlShortenerByTinyUrl(tinyUrl);
        return tinyurl == null ? "" : tinyurl.getLongUrl();
    }

    @Override
    @Transactional
    public void deleteUrlShortener(String url) {
        repository.deleteByLongUrl(url);
    }

}
