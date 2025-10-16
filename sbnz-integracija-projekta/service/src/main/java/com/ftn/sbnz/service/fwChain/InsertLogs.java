package com.ftn.sbnz.service.fwChain;

import com.ftn.sbnz.model.enums.LogTag;
import com.ftn.sbnz.model.enums.LogType;
import com.ftn.sbnz.model.models.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertLogs {
    public static ArrayList<Log> generateLogs() {
        Log log1 = new Log();
        log1.setType(LogType.INFO);
        log1.setLogTag(LogTag.AUTH);
        log1.setDescription("Login failed due to invalid password");
        log1.setSourceIP("192.168.1.99");
        log1.setDatetime(new Date());

        Log log2 = new Log();
        log2.setType(LogType.INFO);
        log2.setLogTag(LogTag.AUTH);
        log2.setDescription("Login failed due to invalid password");
        log2.setSourceIP("192.168.1.99");
        log2.setDatetime(new Date());

        Log log3 = new Log();
        log3.setType(LogType.INFO);
        log3.setLogTag(LogTag.AUTH);
        log3.setDescription("Login failed due to invalid password");
        log3.setSourceIP("192.168.1.99");
        log3.setDatetime(new Date());

        Log log4 = new Log();
        log4.setType(LogType.WARNING);
        log4.setLogTag(LogTag.ACTION);
        log4.setSourceIP("192.168.1.99");
        log4.setDescription("Possible access to private parts of system");
        log4.setDatetime(new Date());


        Log log5 = new Log();
        log5.setType(LogType.WARNING);
        log5.setLogTag(LogTag.ACCESS);
        log5.setSourceIP("192.168.1.99");
        log5.setDescription("Possible access to private parts of system");
        log5.setDatetime(new Date());


        ArrayList<Log> logs = new ArrayList<>();
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        logs.add(log4);
        logs.add(log5);

        return logs;
    }
}
