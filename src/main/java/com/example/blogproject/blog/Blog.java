package com.example.blogproject.blog;

import com.example.blogproject.account.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Entity
@AllArgsConstructor
public class Blog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="blog_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;


    @Column(name="blog_name")
    private String blogName;

    private String description;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Blog(Long id){
        this.id = id;
    }
}
