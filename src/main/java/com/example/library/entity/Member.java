package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    @Column(updatable = false)
    private LocalDate membershipDate;

    // A member can have multiple borrow records
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<BorrowRecord> borrowRecords;

    // Automatically sets the date right before saving to the database
    @PrePersist
    protected void onCreate() {
        this.membershipDate = LocalDate.now();
    }
}