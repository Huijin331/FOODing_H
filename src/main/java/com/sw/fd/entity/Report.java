package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "report_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rpno;

    @ManyToOne
    @JoinColumn(name = "rno", nullable = false)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "mno", nullable = false)
    private Member member;

    @Column(name = "rptype", nullable = false)
    private int rptype;

    @Column(name = "rpdate", nullable = false)
    private LocalDate rpdate;

    @PrePersist
    protected void onCreate() {
        rpdate = LocalDate.now();
    }
}
