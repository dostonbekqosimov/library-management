package doston.code.security;
import doston.code.entity.Librarian;
import doston.code.exception.UnauthorizedException;
import doston.code.repository.LibrarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final LibrarianRepository librarianRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Librarian> optional = librarianRepository.findByUsername(username);

        if (optional.isEmpty()){
            throw new UnauthorizedException("Login or password is wrong");
        }
        Librarian librarian = optional.get();


        return new CustomUserDetails(librarian);
    }
}
