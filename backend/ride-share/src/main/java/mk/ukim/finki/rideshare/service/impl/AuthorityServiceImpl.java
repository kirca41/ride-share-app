package mk.ukim.finki.rideshare.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.model.Authority;
import mk.ukim.finki.rideshare.repository.AuthorityRepository;
import mk.ukim.finki.rideshare.service.AuthorityService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public Authority getByAuthority(String authority) {
        return authorityRepository.findByAuthority(authority)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }
}
