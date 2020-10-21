package uz.mod.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.File;
import uz.mod.entity.PostConception;
import uz.mod.entity.PostResource;

import java.util.List;
import java.util.UUID;

public interface PostResourceRepo extends JpaRepository<PostResource, UUID> {

    List<PostResource> findAllByIsFavouriteTrueAndFile_IdOrderByCreatedAtDesc(UUID fileId);

    List<PostResource> findAllByIsFavouriteTrueOrderByCreatedAtDesc();

    Page<PostResource> findAllByIsEnabledTrueOrderByCreatedAtDesc( Pageable pageable);

    Page<PostResource> findAllByIsEnabledFalseOrderByCreatedAtDesc( Pageable pageable);

    List<PostResource> findAllByIsEnabledFalseOrderByCreatedAtDesc();

    Page<PostResource>findAllByFile_IdOrderByCreatedAtDesc(UUID id,Pageable pageable);

    List<PostResource>findAllByFileOrderByCreatedAtDesc(File file);

}
