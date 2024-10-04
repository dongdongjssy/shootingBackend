package io.goose.shooting.service.impl;

import io.goose.shooting.domain.Schedule;
import io.goose.shooting.mapper.ScheduleMapper;
import io.goose.shooting.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public Schedule selectScheduleById(Long id) {
        return scheduleMapper.selectScheduleById(id);
    }

    @Override
    public List<Schedule> selectScheduleByFkIdAndType(Schedule schedule) {
        return scheduleMapper.selectScheduleByFkIdAndType(schedule);
    }

    @Override
    public int insertSchedule(Schedule schedule) {
        return scheduleMapper.insertSchedule(schedule);
    }

    @Override
    public int updateSchedule(Schedule schedule) {
        return scheduleMapper.updateSchedule(schedule);
    }

    @Override
    public int deleteScheduleById(Long id) {
        return scheduleMapper.deleteScheduleById(id);
    }

    @Override
    public int deleteScheduleByIds(String[] ids) {
        return scheduleMapper.deleteScheduleByIds(ids);
    }
}
