package com.sw.fd.service;

import com.sw.fd.entity.Group;
import com.sw.fd.entity.Member;
import com.sw.fd.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void save(Group group) { groupRepository.save(group); }

    public List<Group> getAllGroups() { return groupRepository.findAll(); }

    public Group getGroupById(int gno) { return groupRepository.findByGno(gno).orElse(null); }

    public List<Group> getGroupsByMember(Member member) {
        // 특정 멤버가 속한 그룹을 가져오는 로직 추가
        return groupRepository.findGroupsByMember(member.getMno());
    }

    public void createGroup(Group group) {
        groupRepository.save(group);
    }
}
