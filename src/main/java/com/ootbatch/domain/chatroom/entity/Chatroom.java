package com.ootbatch.domain.chatroom.entity;

import com.ootbatch.common.entity.BaseEntity;
import com.ootbatch.domain.chatparticipatinguser.entity.ChatParticipatingUser;
import com.ootbatch.domain.salepost.entity.SalePost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "chatrooms")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Chatroom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatroom")
    private List<ChatParticipatingUser> chatParticipatingUsers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_post_id", nullable = false)
    private SalePost salePost;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}

