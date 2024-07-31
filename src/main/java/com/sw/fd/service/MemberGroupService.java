package com.sw.fd.service;

import com.sw.fd.entity.Group;
import com.sw.fd.entity.Member;
import com.sw.fd.entity.MemberGroup;
import com.sw.fd.repository.MemberGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberGroupService {

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    public void addMemberToGroup(Member member, Group group, int jauth) {
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setGroup(group);
        memberGroup.setMember(member);
        memberGroup.setJauth(jauth);

        memberGroupRepository.save(memberGroup);
    }
}