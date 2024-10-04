package io.goose.shooting.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Schedule {

    public Schedule() {
    }

    public Schedule(Long fkId, String type) {
        this.fkId = fkId;
        this.type = type;
    }

    private Long id;
    private Long fkId;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
