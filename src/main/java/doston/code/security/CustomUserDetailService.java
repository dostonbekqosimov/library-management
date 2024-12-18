package doston.code.security;
import doston.code.entity.Profile;
import doston.code.exception.UnauthorizedException;
import doston.code.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final ProfileRepository profileRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Profile> optional = profileRepository.findByUsername(username);

        if (optional.isEmpty()){
            throw new UnauthorizedException("Login or password is wrong");
        }
        Profile profile = optional.get();


        return new CustomUserDetails(profile);
    }
}
