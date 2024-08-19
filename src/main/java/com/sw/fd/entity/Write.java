package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "write_t") // 데이터베이스 테이블과 매핑
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Write {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wno")
    private int wno;

    @ManyToOne
    @JoinColumn(name = "bno", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "mno", nullable = false)
    private Member member;

    @Column(name = "wdate", nullable = false)
    private LocalDateTime wdate;

    @Column(name = "wtitle", nullable = false)
    private String wtitle;

    @Column(name = "wcontent", nullable = false)
    private String wcontent;

    @PrePersist
    protected void onCreate() {
        wdate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Write{wno=" + wno + ", wtitle='" + wtitle + "'}";
    }
}
