package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class LogParser implements IPQuery
{

    private Path logDir;

    public LogParser(Path logDir)
    {
        this.logDir = logDir;
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before)
    {
        return getUniqueIPsSet(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before)
    {
        return getUniqueIPsSet(after, before);
    }

    /**
     * Возвращяет IP адресса для указанного пользователя
     * @param user
     */
    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before)
    {
        Set<String> IPsForUser = new HashSet<>();

        for (String line : getLinesList())
        {
            String[] parts = line.split("\\t");

            if (user.equals(parts[1]))
            {
                addIP(after, before, IPsForUser, parts);
            }
        }
        return IPsForUser;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before)
    {
        Set<String> IPsForEvent = new HashSet<>();

        for (String line : getLinesList())
        {
            String[] parts = line.split("\\t");
            // Если событие равна четвертой части строки
            if (event.toString().equals(parts[3].split(" ")[0]))
            {
                addIP(after, before, IPsForEvent, parts);
            }
        }
        return IPsForEvent;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before)
    {
        Set<String> IPsForEvent = new HashSet<>();

        for (String line : getLinesList())
        {
            String[] parts = line.split("\\t");

            if (status.toString().equals(parts[4]))
            {
                addIP(after, before, IPsForEvent, parts);
            }
        }
        return IPsForEvent;
    }

    /**
     * Метод проходит по всем строкам log файлов и добавляет их в списко строк
     * @return список строк
     */
    private List<String> getLinesList()
    {
        String[] files = logDir.toFile().list(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".log");
            }
        });

        List<String> lines = new ArrayList<>();
        for (String file : files)
        {
            try
            {
                lines.addAll(Files.readAllLines(Paths.get(logDir + File.separator + file), Charset.defaultCharset()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return lines;
    }

    /**
     * @param after - Дата фильтрации от
     * @param before - Дата фильтрации до
     * @return Возвращяет уникальные IP адресса фильтрация от after по before
     */
    private Set<String> getUniqueIPsSet(Date after, Date before)
    {
        Set<String> uniqueIPs = new HashSet<>();

        for (String line : getLinesList())
        {
            String[] parts = line.split("\\t");

            addIP(after, before, uniqueIPs, parts);
        }
        return uniqueIPs;
    }

    /**
     * @param after - Дата фильтрации от
     * @param before - Дата фильтрации до
     * @param IPs - Множество IP адрессов
     * @param parts - Разделенная часть каждой строки в log, т.е. каждую строку в лог файле делим на части
     */
    private void addIP(Date after, Date before, Set<String> IPs, String[] parts)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        long partDate = 0;
        try
        {
            // Lines 3rd element is Date "dd.MM.yyyy HH:mm:ss"
            partDate = dateFormat.parse(parts[2]).getTime();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        // Если даты не указаны то добавить все IP адресса
        if (after == null && before == null)
        {
            IPs.add(parts[0]);
        }
        // Если начальная дата не указана (after) то добавить все IP до указанной даты (befor)
        else if (after == null)
        {
            if (partDate <= before.getTime())
            {
                IPs.add(parts[0]);
            }
        }
        // Если конечная дата не указана (after) то добавить все IP с начальной даты (after)
        else if (before == null)
        {
            if (partDate >= after.getTime())
            {
                IPs.add(parts[0]);
            }
        }
        // Если указаны даты от до. То фильтровать IP перед добавлением
        else {
            if (partDate >= after.getTime() && partDate <= before.getTime())
            {
                IPs.add(parts[0]);
            }
        }
    }
}