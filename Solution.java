package com.javarush.task.task39.task3913;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
//        String str = Solution.class.getPackage().getName() + ".logs/";
//        System.out.println(str.replace('.', '/'));
//
//        File file = new File(str.replace('.', '/'));
//        Path path = file.toPath().toAbsolutePath();
//        System.out.println(path);

        LogParser logParser = new LogParser(Paths.get("/media/hanaria/Local Disk2/Logger/Task39/"));
        System.out.println("Number of Unique IPs : " + logParser.getNumberOfUniqueIPs(null, new Date()));
        System.out.println("Unique IPs : " + logParser.getUniqueIPs(null, new Date()));
        System.out.println("IPs for user 'Vasya Pupkin' : " + logParser.getIPsForUser("Vasya Pupkin", null, null));
        System.out.println("IPs for event 'LOGIN' : " + logParser.getIPsForEvent(Event.LOGIN, null, null));
        System.out.println("IPs for status 'OK' : " + logParser.getIPsForStatus(Status.OK, null, null));
    }
}