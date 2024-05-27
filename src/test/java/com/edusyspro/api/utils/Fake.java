package com.edusyspro.api.utils;

import com.arakelian.faker.model.Gender;
import com.arakelian.faker.model.Person;
import com.arakelian.faker.service.RandomAddress;
import com.arakelian.faker.service.RandomPerson;
import com.edusyspro.api.entities.*;
import com.edusyspro.api.entities.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Fake {

    public static List<Teacher> getMultipleTeachers(
            int numberOfTeacher,
            School school,
            Classe[][] classes,
            Course[][] courses
    ) {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < numberOfTeacher; i++) {
            teachers.add(getTeacher(school, classes[i], courses[i]));
        }
        return teachers;
    }

    public static Teacher getTeacher(School school, Classe[] classes, Course[] courses) {
        Person p = RandomPerson.get().next();
        com.arakelian.faker.model.Address a = RandomAddress.get().next();

        String[] streetParts = a.getStreet().split(" ");
        int number = Integer.parseInt(streetParts[0]);
        String street = streetParts[1];

        List<Course> courseList = Arrays.stream(courses).filter(c -> c.getId() != 0).toList();

        return Teacher.builder()
                .birthDate(LocalDate.from(p.getBirthdate()))
                .emailId((oneWord(randomWord()) + "@" + oneWord(randomWord(3)) + "." + oneWord(randomWord(3))).toLowerCase())
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .gender(p.getGender() == Gender.MALE ? com.edusyspro.api.entities.enums.Gender.HOMME : com.edusyspro.api.entities.enums.Gender.FEMME)
                .status(randomStatus())
                .telephone(randomNumber())
                .hireDate(randomDate())
                .address(Address.builder()
                        .borough(randomWord())
                        .city(a.getCity())
                        .country(a.getState())
                        .neighborhood(randomWord())
                        .number(number)
                        .street(street)
                        .zipCode(a.getPostalCode())
                        .build()
                )
                .classes(List.of(classes))
                .courses(courseList.isEmpty() ? null : courseList)
                .school(school)
                .salaryByHour(1500.00)
                .build();
    }

    public static List<Student> getStudents(int numberOfStudents, School school) {
        return IntStream.range(0, numberOfStudents)
                .mapToObj(i -> getStudent(school))
                .toList();
    }

    public static Student getStudent(School school) {
        Person p = RandomPerson.get().next();
        Person g = RandomPerson.get().next();
        com.arakelian.faker.model.Address a = RandomAddress.get().next();

        String emailId = (oneWord(randomWord()) + "@" + oneWord(randomWord(3)) + "." + oneWord(randomWord(3))).toLowerCase();

        String[] streetParts = a.getStreet().split(" ");
        int number = Integer.parseInt(streetParts[0]);
        String street = streetParts[1];

        Address address = Address.builder()
                .borough(randomWord())
                .city(a.getCity())
                .country(a.getState())
                .neighborhood(randomWord())
                .number(number)
                .street(street)
                .zipCode(a.getPostalCode())
                .build();

        return Student.builder()
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .gender(p.getGender() == Gender.MALE ? com.edusyspro.api.entities.enums.Gender.HOMME : com.edusyspro.api.entities.enums.Gender.FEMME)
                .birthDate(randomDate(1990))
                .birthCity("Brazzaville")
                .nationality("Congolaise")
                .telephone(randomNumber())
                .dadName(oneWord(randomWord()) + " " + oneWord(randomWord()))
                .momName(oneWord(randomWord()) + " " + oneWord(randomWord()))
                .reference(school.getAbbr() + randomNumber().substring(0, 6))
                .address(address)
                .guardian(Guardian.builder()
                        .firstName(g.getFirstName())
                        .lastName(g.getLastName())
                        .status(randomStatus())
                        .genre(g.getGender() == Gender.MALE ? com.edusyspro.api.entities.enums.Gender.HOMME : com.edusyspro.api.entities.enums.Gender.FEMME)
                        .emailId(emailId)
                        .jobTitle(g.getTitle())
                        .company(oneWord(randomWord()))
                        .telephone(randomNumber())
                        .address(address)
                        .build()
                )
                .school(school)
                .build();
    }

    public static List<Enrollment> studentToEnroll(int numberOfStudents, String academicYear, School school, Classe classe) {
        return IntStream.range(0, numberOfStudents)
                .mapToObj(i -> setEnrollment(academicYear, school, classe))
                .toList();
    }

    private static Enrollment setEnrollment(String academicYear, School school, Classe classe) {
        return Enrollment.builder()
                .academicYear(academicYear)
                .student(getStudent(school))
                .classe(classe)
                .enrollmentDate(LocalDateTime.now())
                .isArchived(false)
                .school(school)
                .build();
    }

    private static String randomWord() {
        return randomWord(randomDigit());
    }

    private static String randomWord(int length) {
        Person p = RandomPerson.get().next();
        return p.getComments().substring(0, length);
    }

    private static String oneWord(String sentence) {
        return sentence.replaceAll("\\s", "");
    }

    private static LocalDate randomDate() {
        return randomDate(2010, 2020);
    }

    private static LocalDate randomDate(int startYear) {
        return randomDate(startYear, Year.now().getValue());
    }

    private static LocalDate randomDate(int startYear, int endYear) {
        LocalDate startDate = LocalDate.of(startYear, 1, 1);
        long daysBetween = ChronoUnit.DAYS.between(startDate, LocalDate.of(endYear, 12, 31));
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);
        return startDate.plusDays(randomDays);
    }

    private static int randomDigit() {
        Random random = new Random();
        return random.nextInt(11) + 5;
    }

    private static String randomNumber() {
        Random random = new Random();
        int lowerBound = 100_000_000;
        int upperBound = 999_999_999;
        return Long.toString(lowerBound + random.nextInt(upperBound - lowerBound + 1));
    }

    private static Status randomStatus() {
        Random random = new Random();
        int randomNumber = random.nextInt(30);
        switch (randomNumber) {
            case 0 -> { return Status.WIDOW; }
            case 1 -> { return Status.WIDOWER; }
            case 2, 3, 4, 5, 6, 7, 8 -> { return Status.MARRIED; }
            default -> { return Status.SINGLE; }
        }
    }

}
