package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "join_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jno")
    private int jno;

    @ManyToOne
    @JoinColumn(name = "gno")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "mno")
    private Member member;

    @Column(name = "jauth")
    private int jauth;
}
