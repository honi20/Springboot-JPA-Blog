package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세션저장소에 저장해줌.
@Data
public class PrincipalDetail implements UserDetails {
    private User user;      // 콤포지션 (객체를 품고있는 것)

    public PrincipalDetail(User user){
        this.user=user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 리턴 (true: 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있는지 아닌지 리턴 (true: 안잠김)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴 (true: 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 리턴 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정이 갖고있는 권한 목록 리턴 (권한이 여러개 있을 수 있어서 루프 돌어야 하는데 우리는 한개만)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        /*
        collectors.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_"+user.getRole();      // ROLE_USER 리턴
            }
        });
        */

        collectors.add(()->{ return "ROLE_"+user.getRole(); });
        return collectors;
    }
}
