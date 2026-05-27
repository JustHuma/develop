package ru.yandex.practicum.gym;

import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {
    private final int hours;
    private final int minutes;

    public TimeOfDay(int hours, int minutes) {
        if (hours < 0 || hours > 23) {
            throw new IllegalArgumentException("Часы должны быть в диапазоне от 0 до 23. Получено: " + hours);
        }
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Минуты должны быть в диапазоне от 0 до 59. Получено: " + minutes);
        }
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public int compareTo(TimeOfDay other) {
        if (other == null) {
            throw new NullPointerException("Нельзя сравнить с null");
        }
        if (this.hours != other.hours) {
            return Integer.compare(this.hours, other.hours);
        }
        return Integer.compare(this.minutes, other.minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeOfDay that = (TimeOfDay) o;
        return hours == that.hours && minutes == that.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hours, minutes);
    }
}