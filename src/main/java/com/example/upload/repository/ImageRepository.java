package com.example.upload.repository;

import com.example.upload.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByOriginName(String originName);

    Optional<Image> findByUuidName(String uuidName);

    // DATE_FORMAT(:startTime, '%Y%m%d)
    @Query("SELECT i FROM Image i WHERE i.owner = :user ORDER BY i.uploadDate")
    Optional<List<Image>> findAllByOwner(@Param("user") String user);

    @Modifying
    void deleteByUuidName(String uuidName);
}
