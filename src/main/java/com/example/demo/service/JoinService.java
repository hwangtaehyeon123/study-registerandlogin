package com.example.demo.service;


import com.example.demo.dto.JoinDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDto joinDto) {

        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        Boolean isExist = userRepository.existsByUsername(username); //아이디 중복 필터

        if(isExist){  // true(아이디 있으면) -> 아무값 반환 x
            return;
        }

        UserEntity data = new UserEntity(); //새로운 엔티티

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password)); //암호화된 패스워드 입력
        data.setRole("ROLE_ADMIN"); //어드민 권한 주기.

        userRepository.save(data);
    }
}
