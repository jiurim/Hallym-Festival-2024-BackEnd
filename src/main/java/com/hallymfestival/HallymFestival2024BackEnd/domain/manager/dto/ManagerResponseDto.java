
package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.Authority;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.Manager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerResponseDto {
    private String username;
    private String password;
    private Set<Authority> authorities;

    public static ManagerResponseDto of(Manager manager) {
        return new ManagerResponseDto(manager.getUsername(), manager.getPassword(), manager.getAuthorities());
    }
}
