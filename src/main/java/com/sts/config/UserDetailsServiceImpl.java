package com.sts.config;

import com.sts.entity.StaffUser;
import com.sts.repository.StaffUserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private StaffUserRepository staffUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Repository se Optional data nikalna
        Optional<StaffUser> staffUserOptional = staffUserRepository.findByUsername(username);
        
        // 2. Agar user nahi milta toh exception throw karna, aur mil jata hai toh .get() se nikalna
        StaffUser staffUser = staffUserOptional.orElseThrow(() -> 
            new UsernameNotFoundException("User not found with username: " + username)
        );
        
        // 3. Wrapper class mein send karna
        return new CustomUserDetails(staffUser);
    }
}