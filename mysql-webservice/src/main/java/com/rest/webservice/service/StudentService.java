package com.rest.webservice.service;

import com.rest.webservice.dto.SearchStudentNameDto;
import com.rest.webservice.entity.Student;
import com.rest.webservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;


    /*
    This method is used for utilisation of various findBy queries in StudentRepository.
    Requirements(Parameters) from studentNameDto:
        1. name : string to be searched : (necessary,not null)
        2. type: search type : {IgnoreCase, StartingWith, EndingWith, Like, Containing} : (null=>exact search)
        3. order: supported only for exact search : {Asc, Desc, Id} : (null=>ascending)
 */
    public List<Student> searchName(SearchStudentNameDto studentNameDto) {
        List<Student> result = null;
        String name = studentNameDto.name();
        String type = studentNameDto.type();
        String order = studentNameDto.order();

        try {
            if (type == null || type.isBlank()) {
                result = switch (order) {
                    case "Desc" -> studentRepository.findByNameOrderByStudentIdDesc(name);
                    case "Id" -> studentRepository.findByNameOrderByStudentId(name);
                    default -> studentRepository.findByNameOrderByStudentIdAsc(name);   //Asc
                };
            } else {
                result = switch (type) {
                    case "IgnoreCase" -> studentRepository.findByNameIgnoreCase(name);
                    case "StartingWith" -> studentRepository.findByNameStartingWith(name);
                    case "EndingWith" -> studentRepository.findByNameEndingWith(name);
                    case "Like" -> studentRepository.findByNameLike(name);
                    case "Containing" -> studentRepository.findByNameContaining(name);
                    default -> studentRepository.findByName(name);
                };
            }
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Name. Request:{}, Error:{}", studentNameDto, t.getMessage());
            throw new RuntimeException("Error Occurred");
        }
        return result;
    }

    /*
    This method is used for Null, NotNull and IsNot types of search queries in StudentRepository.
    Parameters:
        type: Null, NotNUll, IsNot
        bio: targetBio
     */
    public List<Student> searchBio(String type, String targetBio) {
        List<Student> result = null;
        try {
            result = switch (type) {
                case "Null" -> studentRepository.findByBioIsNull();
                case "NotNull" -> studentRepository.findByBioIsNotNull();
                case "IsNot" -> studentRepository.findByBioIsNot(targetBio);
                default -> throw new NoSuchElementException();
            };
        } catch (NoSuchElementException e) {
            log.error("Type={} doesn't exist.", type);
            throw new NoSuchElementException("Requested type doesn't exist. Supported Types: {Null, NotNull, IsNot}.");
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Bio for Type={}. Error:{}", type, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        return result;
    }

    /*
    This method searches for boolean field active
    type: true,false
     */
    public List<Student> searchActive(String type) {
        List<Student> result = null;
        try {
            result = switch (type) {
                case "true" -> studentRepository.findByActiveTrue();
                case "false" -> studentRepository.findByActiveFalse();
                default -> throw new NoSuchElementException();
            };
        } catch (NoSuchElementException e) {
            log.error("Type={} doesn't exist.", type);
            throw new NoSuchElementException("Requested type doesn't exist. Supported Types: {true, false}.");
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Active for Type={}. Error:{}", type, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        return result;
    }

    /*
    This method searches for date field dob
    dob: LocalDate
    type: Before,After
     */
    public List<Student> filterDate(LocalDate dob, String type) {
        List<Student> result = null;
        try {
            result = switch (type) {
                case "Before" -> studentRepository.findByDobBefore(dob);
                case "After" -> studentRepository.findByDobAfter(dob);
                default -> throw new NoSuchElementException();
            };
        } catch (NoSuchElementException e) {
            log.error("Type={} doesn't exist.", type);
            throw new NoSuchElementException("Requested type doesn't exist. Supported Types: {Before, After}.");
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Dob for Type={}. Error:{}", type, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        return result;
    }

    /*
    This method filters by studentID
    id: studentId
    type: GreaterThan, LessThan, GreaterThanEquals, LessThanEquals
     */
    public List<Student> filterId(Integer id, String type) {
        List<Student> result = null;
        try {
            result = switch (type) {
                case "GreaterThan" -> studentRepository.findByStudentIdGreaterThan(id);
                case "GreaterThanEqual" -> studentRepository.findByStudentIdGreaterThanEqual(id);
                case "LessThan" -> studentRepository.findByStudentIdLessThan(id);
                case "LessThanEqual" -> studentRepository.findByStudentIdLessThanEqual(id);
                default -> throw new NoSuchElementException();
            };
        } catch (NoSuchElementException e) {
            log.error("Type={} doesn't exist.", type);
            throw new NoSuchElementException("Requested type doesn't exist. Supported Types: {GreaterThan, LessThan, GreaterThanEquals, LessThanEquals}.");
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-StudentId for Type={}. Error:{}", type, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        return result;
    }
    /*
       This method filters by studentID with a collection
       idCollection: Collection of studentIds to be matched
        */
    public List<Student> fiterIdCollection(List<Integer> idCollection) {
        List<Student> result = null;
        try {
            result=studentRepository.findByStudentIdIn(idCollection);
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Id in Collection={}. Error:{}",idCollection, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        return result;
    }

    /*
       This method filters by studentID
       start: studentId(>=)
       end: studentId(<=)
       result: id>=start & id<=end
        */
    public List<Student> filterIdRange(Integer start, Integer end) {
        List<Student> result = null;
        try {
            result=studentRepository.findByStudentIdBetween(start,end);
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Id in Range=[{},{}]. Error:{}",start,end, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        return result;
    }
}
