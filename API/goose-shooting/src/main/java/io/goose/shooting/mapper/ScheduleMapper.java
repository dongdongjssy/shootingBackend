package io.goose.shooting.mapper;

import io.goose.shooting.domain.Schedule;

import java.util.List;

public interface ScheduleMapper {

    public Schedule selectScheduleById(Long id);

    public List<Schedule> selectScheduleByFkIdAndType(Schedule schedule);

    public int insertSchedule(Schedule schedule);

    public int updateSchedule(Schedule schedule);

    public int deleteScheduleById(Long id);

    public int deleteScheduleByIds(String[] ids);
}
