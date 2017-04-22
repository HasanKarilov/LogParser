package com.javarush.task.task39.task3913;

/** Для событий SOLVE_TASK и DONE_TASK существует дополнительный параметр, который указывается через пробел, это номер задачи.
 * status - статус:
 * OK - событие выполнилось успешно,
 * FAILED - событие не выполнилось,
 * ERROR - произошла ошибка.
 */
public enum Event {
    LOGIN,  // пользователь залогинился,
    DOWNLOAD_PLUGIN,  // пользователь скачал плагин,
    WRITE_MESSAGE,  // пользователь отправил сообщение,
    SOLVE_TASK,  // пользователь попытался решить задачу,
    DONE_TASK;  // пользователь решил задачу.
}