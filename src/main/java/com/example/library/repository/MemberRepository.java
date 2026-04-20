package com.example.library.repository;

import com.example.library.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    /**
     * Search members by a name fragment (matches first name OR last name, case-insensitive).
     */
    @Query("SELECT m FROM Member m WHERE " +
           "LOWER(m.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(m.lastName)  LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Member> searchByName(@Param("name") String name, Pageable pageable);
}
