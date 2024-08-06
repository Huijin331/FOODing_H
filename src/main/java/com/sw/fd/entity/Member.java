package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "member_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;

    @OneToMany(mappedBy = "member")
    private List<MemberGroup> memberGroupList;

    private String mid;
    private String mname;
    private String mpass;

    private transient String mpassConfirm;

    private int mtype;
    private String mnick;
    private String mbirth;
    private String mphone;
    private String memail;
    private String maddr;
    private LocalDate mdate;

    private int mwarning;

    @PrePersist
    protected void onCreate() {
        mdate = LocalDate.now();
    }

    public String getMpassConfirm() {
        return mpassConfirm;
    }

    public void setMpassConfirm(String mpassConfirm) {
        this.mpassConfirm = mpassConfirm;
    }
}