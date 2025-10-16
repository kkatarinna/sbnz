package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.enums.LogTag;
import com.ftn.sbnz.model.enums.LogType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Log {
    private Date datetime;
    private LogType type;
    private String sourceIP;
    private String description;
    private LogTag logTag;
}
