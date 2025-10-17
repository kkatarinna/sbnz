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

        Log log6 = new Log();
        log6.setType(LogType.INFO);
        log6.setLogTag(LogTag.DATABASE);
        log6.setSourceIP("192.168.1.99");
        log6.setDescription("failed to enter database");
        log6.setDatetime(new Date());

        Log log7 = new Log();
        log7.setType(LogType.INFO);
        log7.setLogTag(LogTag.DATABASE);
        log7.setSourceIP("192.168.1.99");
        log7.setDescription("failed to enter database");
        log7.setDatetime(new Date());

        Log log8 = new Log();
        log8.setType(LogType.INFO);
        log8.setLogTag(LogTag.DATABASE);
        log8.setSourceIP("192.168.1.99");
        log8.setDescription("failed to enter database");
        log8.setDatetime(new Date());

        Log log9 = new Log();
        log9.setType(LogType.WARNING);
        log9.setLogTag(LogTag.ACCESS);
        log9.setSourceIP("192.168.1.99");
        log9.setDescription("Access denied on query union select|select - SQL injection warning");
        log9.setDatetime(new Date());


        ArrayList<Log> logs = new ArrayList<>();
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        logs.add(log4);
        logs.add(log5);
        logs.add(log6);
        logs.add(log7);
        logs.add(log8);
        logs.add(log9);


        return logs;
    }
}
