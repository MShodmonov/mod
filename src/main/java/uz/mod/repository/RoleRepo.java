package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Role;
import uz.mod.entity.enums.RoleEnumeration;


import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findRoleByName(RoleEnumeration roleEnumeration);
}
