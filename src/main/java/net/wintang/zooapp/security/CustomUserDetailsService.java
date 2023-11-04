package net.wintang.zooapp.security;

import net.wintang.zooapp.entity.Role;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(Integer.parseInt(userId)).orElseThrow(() -> new UsernameNotFoundException("UID not found"));
        if (user.isStatus()) {
            return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        }
        throw new UsernameNotFoundException("UID not found");
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
