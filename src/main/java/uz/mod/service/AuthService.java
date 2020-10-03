package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mod.entity.Role;
import uz.mod.entity.User;
import uz.mod.entity.enums.RoleEnumeration;
import uz.mod.payload.UserSignInRequest;
import uz.mod.repository.RoleRepo;
import uz.mod.repository.UserRepo;
import uz.mod.security.MyAuthentication;
import uz.mod.security.MySecurityContext;


import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;



    @Autowired
     PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(s).orElseThrow(() -> new UsernameNotFoundException("Not registered user"));
    }

    public boolean isAuthenticate() {
        return !(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken);
    }

    public User saveUser(UserSignInRequest signUpRequest)
    {
        List<Role> list=new LinkedList<>();
        list.add(roleRepo.findRoleByName(RoleEnumeration.ROLE_USER).get());
        return userRepo.save(new User(signUpRequest.getUsername(),passwordEncoder.encode(signUpRequest.getPassword()),list));
    }
    public Authentication setSecurity(User user) {
        SecurityContext context = new MySecurityContext();
        MyAuthentication authentication = new MyAuthentication(user.getAuthorities());
        authentication.setPrincipal(user);
        authentication.setAuthenticated(true);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        return authentication;
    }

    public UserDetails loadUserById(String userId) {
        return  userRepo.findById(UUID.fromString(userId)).orElseThrow(() -> new UsernameNotFoundException("User not found user id: " + userId));

    }


}
