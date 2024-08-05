package com.sw.fd.service;

import com.sw.fd.entity.Group;
import com.sw.fd.entity.Member;
import com.sw.fd.entity.MemberGroup;
import com.sw.fd.repository.MemberGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberGroupService {

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    public boolean isMemberInGroup(String memberId, int gno) {
        return memberGroupRepository.existsByGroupGnoAndMemberMid(gno, memberId);
    }

    public void addMemberToGroup(Member member, Group group, int jauth) {
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setGroup(group);
        memberGroup.setMember(member);
        memberGroup.setJauth(jauth);

        memberGroupRepository.save(memberGroup);
    }

    // 회원이 모임장인 모임을 찾는 메서드 추가
    public List<Group> findGroupsWhereMemberIsLeader(String memberId) {
        List<MemberGroup> memberGroups = memberGroupRepository.findByMemberMidAndJauth(memberId, 1);
        return memberGroups.stream()
                .map(MemberGroup::getGroup)
                .collect(Collectors.toList());
    }
}