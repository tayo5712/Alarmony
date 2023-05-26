package com.slembers.alarmony.alarm.repository;

import com.slembers.alarmony.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm,Long> {
}
