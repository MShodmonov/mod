package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.User;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User>findUserByUsername(String username);

    Boolean existsByUsername(String username);

    List<User> findByIdIn(List<UUID> userIds);


}
