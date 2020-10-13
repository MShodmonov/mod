package uz.mod.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.PostConception;
import uz.mod.entity.PostResource;

import java.util.List;
import java.util.UUID;

public interface PostResourceRepo extends JpaRepository<PostResource, UUID> {

    List<PostResource> findAllByIsFavouriteTrue();

    Page<PostResource> findAllByIsEnabled(Boolean isEnabled, Pageable pageable);

    List<PostResource> findAllByIsEnabledFalse();

    List<PostResource>findAllByFile_Id(UUID id);
}
