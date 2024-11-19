package kr.ac.kopo.logintest.security;

import kr.ac.kopo.logintest.entity.ClubMember;
import kr.ac.kopo.logintest.entity.clubMemberRole;
import kr.ac.kopo.logintest.repository.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.*;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {
    @Autowired
    private ClubMemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies(){
        //user1-user80: USER
        //user81-90:USER,MANAGER
        //user91-100:USER,MANAGER,ADMIN

        IntStream.rangeClosed(1,100).forEach(i ->{
            ClubMember clubMember =ClubMember.builder()
                    .email("user"+i+"@kopo.ac.kr")
                    .name("사용자"+i)
                    .password(passwordEncoder.encode("1234"))
                    .fromSocial(false)
                    .build();

            clubMember.addMemberRole(clubMemberRole.USER);

            if(i>80){
                clubMember.addMemberRole(clubMemberRole.MANAGER);
            }
            if(i>90){
                clubMember.addMemberRole(clubMemberRole.ADMIN);
            }
            repository.save(clubMember);
        });
    }

    @Test
    public void testRead(){
      Optional<ClubMember> result= repository.findByEmail("user1@kopo.ac.kr",false);
      ClubMember clubMember =result.get();
      System.out.println("★★★"+clubMember);
    }
}
