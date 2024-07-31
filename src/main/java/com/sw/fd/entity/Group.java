package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group_table")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gno")
    private int gno;

    @OneToMany(mappedBy = "group")
    private List<MemberGroup> memberGroupList;

    @Column(name = "gname")
    private String gname;
}
