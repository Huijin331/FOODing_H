package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "store_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @Column(name = "sno")
    private int sno;

    @Column(name = "sname")
    private String sname;

    @Column(name = "saddr")
    private String saddr;

    @Column(name = "stel")
    private String stel;

    @Column(name = "seg")
    private String seg;

    @Column(name = "scate")
    private String scate;

    @Column(name = "stime")
    private String stime;

    @Column(name = "spark")
    private String spark;
    @Transient
    private String photoUrl; // @Transient 어노테이션을 추가해서 DB에 저장되지 않음
}
