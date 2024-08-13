package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "pfolder_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pfolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pfno;

    private String pfname;

}
