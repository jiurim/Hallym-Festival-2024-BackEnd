package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.Authority;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.Manager;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class MemberDetailsService implements UserDetailsService {

    private final ManagerRepository managerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername 실행됨");
        return managerRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다"));
    }
    //이때 가져온 정보를 userdetails 객체로 만들어서 return
    private UserDetails createUserDetails(Manager manager) {
        log.info("createDetail 실행됨");
        List<SimpleGrantedAuthority> authList = manager.getAuthorities()
                .stream()
                .map(Authority::getAuthorityStatus)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        authList .forEach(o-> log.debug("authList -> {}",o.getAuthority()));
        return new User(
                String.valueOf(manager.getId()),
                manager.getPassword(),
                authList
        );
    }
}