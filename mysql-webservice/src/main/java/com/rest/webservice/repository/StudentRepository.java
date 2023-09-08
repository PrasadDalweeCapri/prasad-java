package com.rest.webservice.repository;

import com.rest.webservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

        /*
            findByXyz-> find(Introducer) + ByXyz(criteria)
            Xyz should be name of variable in entity and not column name
         */

    //searching by fields
        /*
            SUPPORTED: exact, ignoreCase, prefix, suffix, containing, pattern
         */
    public List<Student> findByName(String name);   //equivalent to findByNameIs and findByNameEquals
    public List<Student> findByNameIgnoreCase(String name);
    public List<Student> findByNameStartingWith(String prefix);
    public List<Student> findByNameEndingWith(String suffix);
    public List<Student> findByNameContaining(String word);
    public List<Student> findByNameLike(String pattern);    //refer to the notes at bottom for detailed info


    //searching by fields: combination of multiple fields
        /*
            SUPPORTED: And, Or
            Although this is supported and JPA doesn't have a limit on conditions, we should be considerate as this
            compromises the readability of method's name.
         */
    public List<Student> findByNameAndActiveOrDob(String name, Boolean active, LocalDate dob);


    //searching by fields: not and null
    public List<Student> findByBioIsNot(String name);
    public List<Student> findByBioIsNull();
    public List<Student> findByBioIsNotNull();


    //searching by fields: boolean
    public List<Student> findByActiveTrue();
    public List<Student> findByActiveFalse();


    //filtering by comparison
    public List<Student> findByStudentIdGreaterThan(Integer id);
    public List<Student> findByStudentIdLessThan(Integer id);
    public List<Student> findByStudentIdGreaterThanEqual(Integer id);
    public List<Student> findByStudentIdLessThanEqual(Integer id);


    //filtering by range
    public List<Student> findByStudentIdBetween(Integer start, Integer end);


    //filtering by collection
    public List<Student> findByStudentIdIn(Collection<Integer> targetStudentList);


    //ordering
    public List<Student> findByNameOrderByStudentIdAsc(String name);     //Ascending(default)
    public List<Student> findByNameOrderByStudentIdDesc(String name);    //Descending


    //ordering by fields
    public List<Student> findByNameOrderByStudentId(String name);      //order by Id


    //filtering by date
    public List<Student> findByDobAfter(LocalDate dob);
    public List<Student> findByDobBefore(LocalDate dob);
}

/*
+ Patterns
    *Strings
    -MySql allows querying strings by pattern (similar to regex and fuzzy pattern matching POC)
    -underscores("_") are used as placeholders for a single character
    -% are used as variable sized blocks
    Eg.: "Prasad" will match with "%r%asa%", "%sad%", "___sad", "%Prasad%"

    *Dates
    -MySql also allows querying Dates
    -Standard Format: yyyy-mm-dd
    Eg.:
        1. "Dates with day=01" -> "%-01"
        2. "Dates with month=11" -> "%-11-%"
        3. "Dates with year=2000" -> "2000-%"

    *NULL
    -MySql doesn't support pattern matching for NULL values
 */
