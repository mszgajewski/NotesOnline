package com.mszgajewski.NotesOnline.config;

import com.mszgajewski.NotesOnline.entity.User;
import com.mszgajewski.NotesOnline.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@NoArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if (user!=null){
            CustomUser customUser = new CustomUser(user);
            return customUser;
        } else {
            throw new UsernameNotFoundException("Brak uzytkownika");
        }
    }
}
