package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.Authority;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerRequestDto {
    private String username;
    private String password;

    public Manager toManager(PasswordEncoder passwordEncoder){
        return Manager.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_ADMIN)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(username,password);
    }
}