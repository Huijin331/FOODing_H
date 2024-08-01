package com.sw.fd.service;

import com.sw.fd.dto.GroupDTO;
import com.sw.fd.entity.Group;
import com.sw.fd.entity.Member;
import com.sw.fd.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void save(Group group) {
        groupRepository.save(group);
    }

    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(group -> new GroupDTO(group.getGno(), group.getGname()))
                .collect(Collectors.toList());
    }

    public GroupDTO getGroupById(int gno) {
        Group group = groupRepository.findByGno(gno).orElse(null);
        if (group != null) {
            return new GroupDTO(group.getGno(), group.getGname());
        } else {
            return null;
        }
    }

    public List<GroupDTO> getGroupsByMember(Member member) {
        List<Group> groups = groupRepository.findGroupsByMember(member.getMno());
        return groups.stream()
                .map(group -> new GroupDTO(group.getGno(), group.getGname()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createGroup(GroupDTO groupDTO) {
        Group group = new Group();
        group.setGname(groupDTO.getGname());
        groupRepository.save(group);
    }
}