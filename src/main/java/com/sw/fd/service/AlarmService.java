package com.sw.fd.service;

import com.sw.fd.entity.Alarm;
import com.sw.fd.entity.Member;
import com.sw.fd.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    // 로그인한 사용자의 모든 알림을 조회
    public List<Alarm> getAlarms(Member member) {
        return alarmRepository.findByMember(member);
    }

    // 알림이 존재하는지 확인
    public boolean hasAlarms(Member member) {
        List<Alarm> alarms = getAlarms(member);
        return !alarms.isEmpty();
    }

    public void saveAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    // 회원의 ID로 알림을 조회
    public List<Alarm> getAlarmsByMember(String memberId) {
        return alarmRepository.findByMember_Mid(memberId);
    }
}
