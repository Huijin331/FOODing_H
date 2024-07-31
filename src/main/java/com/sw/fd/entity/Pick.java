package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "pick_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;

    private String pfold;
    private int mno;
    private int sno;

    public Pick(int mno, int sno) {
        this.mno = mno;
        this.sno = sno;
    }
}
