package com.example.ecommercePlatform.user;

import com.example.ecommercePlatform.user.Persistance.Role;
import com.example.ecommercePlatform.user.Persistance.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRole(Long id) {
      return  roleRepository.findById(id)
              .orElseThrow(()->new RuntimeException("Role not found"));
    }
}
