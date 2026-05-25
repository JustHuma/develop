package ru.yandex.practicum.gym;

import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, tuesdaySessions.size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size());
        assertEquals(13, thursdaySessions.get(0).getTimeOfDay().getHours());
        assertEquals(20, thursdaySessions.get(1).getTimeOfDay().getHours());

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, tuesdaySessions.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> sessionsAt13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, sessionsAt13.size());

        List<TrainingSession> sessionsAt14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertEquals(0, sessionsAt14.size());
    }

    @Test
    void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals(1, result.size());
        assertEquals("Васильев", result.get(0).getCoach().getSurname());
        assertEquals(1, result.get(0).getCount());
    }

    @Test
    void testGetCountByCoachesMultipleCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Алексей", "Иванович");

        // Васильев — 3 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.FRIDAY, new TimeOfDay(14, 0)));

        // Петров — 1 тренировка
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals(2, result.size());

        // Первым должен быть Васильев (3 тренировки)
        assertEquals("Васильев", result.get(0).getCoach().getSurname());
        assertEquals(3, result.get(0).getCount());

        // Вторым — Петров (1 тренировка)
        assertEquals("Петров", result.get(1).getCoach().getSurname());
        assertEquals(1, result.get(1).getCount());
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals(0, result.size());
    }

}