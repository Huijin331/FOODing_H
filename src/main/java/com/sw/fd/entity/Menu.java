package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "menu_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mnno")
    private int mnno;

    @Column(name = "mnname")
    private String mnname;

    @Column(name = "mnprice")
    private String mnprice;

    @ManyToOne
    @JoinColumn(name = "sno", nullable = false)
    private Store store;
}
