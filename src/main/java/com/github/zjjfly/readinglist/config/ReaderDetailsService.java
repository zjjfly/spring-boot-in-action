package com.github.zjjfly.readinglist.config;

import com.github.zjjfly.readinglist.model.Reader;
import com.github.zjjfly.readinglist.repository.ReaderRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zjjfly
 * @date 2017/7/15
 */
@Service
public class ReaderDetailsService implements UserDetailsService {

    private final ReaderRepository readerRepository;

    public ReaderDetailsService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Reader> reader = readerRepository.findByUsername(name);
        return reader.orElseThrow(() -> new UsernameNotFoundException("User:" + name + " not found"));
    }
}
