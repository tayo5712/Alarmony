package com.slembers.alarmony.alarm.repository;

import com.slembers.alarmony.alarm.dto.AlarmListDetailDto;
import com.slembers.alarmony.alarm.entity.Alarm;
import com.slembers.alarmony.alarm.entity.MemberAlarm;
import com.slembers.alarmony.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberAlarmRepository extends JpaRepository<MemberAlarm, Long> {

    /**
     * 멤버를 기준으로 멤버 알람을 모두 조회한다.
     *
     * @param member 멤버 정보
     * @return 멤버의 알람 정보
     */
    List<MemberAlarm> findAllByMember(Member member);

    /**
     * 멤버와 알람 아이디 정보로 멤버 알람을 조회한다.
     *
     * @param member 멤버 정보
     * @param alarm  알람 정보
     * @return 멤버 알람 정보를 optional로 반환
     */
    Optional<MemberAlarm> findByMemberAndAlarm(Member member, Alarm alarm);

    /**
     * 멤버와 알람 아이디 정보로 멤버 알람 존재 여부를 확인한다. 멤버가 알람에 속했는지 확인한다.
     *
     * @param member 멤버 정보
     * @param alarm  알람 정보
     * @return 멤버 알람 정보 존재 여부
     */
    boolean existsByMemberAndAlarm(Member member, Alarm alarm);

    /**
     * 멤버 아이디로 알람 목록을 모두 가져온다.
     *
     * @param memberId 멤버 아이디
     * @return 알람 리스트
     */
    @Query("SELECT new com.slembers.alarmony.alarm.dto.AlarmListDetailDto( " +
        "CASE WHEN ar.host.id = :memberId THEN true ELSE false END, " +
        "ar.id, ar.title, ar.content, hour(ar.time), minute(ar.time), ar.alarmDate, " +
        "ar.soundName, ar.soundVolume, ar.vibrate ) " +
        "from member_alarm as ma inner join alarm as ar on ma.alarm.id = ar.id " +
        "where ma.member.id = :memberId")
    List<AlarmListDetailDto> getAlarmDtosByMember(Long memberId);

    /**
     * 그룹에 속한 멤버 유저네임 목록을 얻어온다.
     *
     * @param groupId 그룹 id
     * @return 유저네임 목록
     */
    @Query("SELECT m.username "
        + "FROM member_alarm ma "
        + "JOIN member m ON m.id = ma.member.id "
        + "WHERE ma.alarm.id = :groupId "
        + "AND m.username <> :hostUsername")
    List<String> getUsernameByGroupIdWithoutHost(Long groupId, String hostUsername);

    /**
     * 알람에 속한 멤버 수를 반환한다.
     *
     * @param alarmId 알람 id
     * @return 알람에 속한 멤버 수
     */
    int countByAlarmId(Long alarmId);

    /**
     * 알람 id와 일치하는 모든 멤버 알람 정보를 삭제한다.
     *
     * @param alarmId 알람 id
     */
    @Modifying
    @Query("DELETE FROM member_alarm ma WHERE ma.alarm.id = :alarmId")
    void deleteByAlarmId(Long alarmId);

}
