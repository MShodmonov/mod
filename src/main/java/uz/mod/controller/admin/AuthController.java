package uz.mod.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.User;
import uz.mod.payload.JwtAuthenticationResponse;
import uz.mod.payload.UserSignInRequest;
import uz.mod.repository.UserRepo;
import uz.mod.security.JwtTokenProvider;
import uz.mod.service.AuthService;


import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserSignInRequest signInRequest) {
        if (!authService.isAuthenticate()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignInRequest signUpRequest, BindingResult bindingResult) {
        if (!authService.isAuthenticate()) {
            if (!bindingResult.hasErrors()) {
                if (userRepo.existsByUsername(signUpRequest.getUsername()))
                    return ResponseEntity.badRequest().build();
                User user = authService.saveUser(signUpRequest);
                Authentication authentication = authService.setSecurity(user);
                String token = tokenProvider.generateToken(authentication);
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.badRequest().build();
    }
//    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER')")
//    public UserSummary getCurrentUser(@CurrentUser User currentUser) {
//        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername());
//        return userSummary;
//    }

}
