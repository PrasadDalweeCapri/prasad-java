package com.rest.webservice.service;

import com.rest.webservice.dto.SearchStudentNameDto;
import com.rest.webservice.entity.Student;
import com.rest.webservice.enums.*;
import com.rest.webservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

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
        SearchNameType type = studentNameDto.type();
        SearchNameOrder order = studentNameDto.order();

        try {
            if (type == null) {
                result = switch (order) {
                    case DESC -> studentRepository.findByNameOrderByStudentIdDesc(name);
                    case ASC -> studentRepository.findByNameOrderByStudentIdAsc(name);   //Asc
                    case ID -> studentRepository.findByNameOrderByStudentId(name);
                    default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found.");
                };
            } else {
                result = switch (type) {
                    case IGNORECASE -> studentRepository.findByNameIgnoreCase(name);
                    case STARTINGWITH -> studentRepository.findByNameStartingWith(name);
                    case ENDINGWITH -> studentRepository.findByNameEndingWith(name);
                    case LIKE -> studentRepository.findByNameLike(name);
                    case CONTAINING -> studentRepository.findByNameContaining(name);
                    case DEFAULT -> studentRepository.findByName(name);
                    default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type not found.");
                };
            }
        } catch (ResponseStatusException r) {
            log.error("Error occurred: {}", r.getReason());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, r.getReason());
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Name. Request:{}, Error:{}", studentNameDto, t.getMessage());
            throw new RuntimeException("Error Occurred");
        }
        if (result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return result;
    }

    /*
    This method is used for Null, NotNull and IsNot types of search queries in StudentRepository.
    Parameters:
        type: Null, NotNUll, IsNot
        bio: targetBio
     */
    public List<Student> searchBio(SearchBioType type, String targetBio) {
        List<Student> result = null;
        try {
            result = switch (type) {
                case NULL -> studentRepository.findByBioIsNull();
                case NOTNULL -> studentRepository.findByBioIsNotNull();
                case ISNOT -> studentRepository.findByBioIsNot(targetBio);
                default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type not found.");
            };
        } catch (ResponseStatusException r) {
            log.error("Error occurred: {}", r.getReason());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, r.getReason());
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Bio for Type={}. Error:{}", type, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        if (result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return result;
    }

    /*
    This method searches for boolean field active
    type: true,false
     */
    public List<Student> searchActive(SearchActiveType type) {
        List<Student> result = null;
        try {
            result = switch (type) {
                case TRUE -> studentRepository.findByActiveTrue();
                case FALSE -> studentRepository.findByActiveFalse();
                default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type not found.");
            };
        } catch (ResponseStatusException r) {
            log.error("Error occurred: {}", r.getReason());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, r.getReason());
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Active for Type={}. Error:{}", type, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        if (result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return result;
    }

    /*
    This method searches for date field dob
    dob: LocalDate
    type: Before,After
     */
    public List<Student> filterDate(LocalDate dob, FilterDateType type) {
        List<Student> result = null;
        try {
            result = switch (type) {
                case BEFORE -> studentRepository.findByDobBefore(dob);
                case AFTER -> studentRepository.findByDobAfter(dob);
                default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type not found.");
            };
        } catch (ResponseStatusException r) {
            log.error("Error occurred: {}", r.getReason());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, r.getReason());
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Dob for Type={}. Error:{}", type, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        if (result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return result;
    }

    /*
    This method filters by studentID
    id: studentId
    type: GreaterThan, LessThan, GreaterThanEquals, LessThanEquals
     */
    public List<Student> filterId(Integer id, FilterIdType type) {
        List<Student> result = null;
        try {
            result = switch (type) {
                case GREATERTHAN -> studentRepository.findByStudentIdGreaterThan(id);
                case GREATERTHANEQUAL -> studentRepository.findByStudentIdGreaterThanEqual(id);
                case LESSTHAN -> studentRepository.findByStudentIdLessThan(id);
                case LESSTHANEQUAL -> studentRepository.findByStudentIdLessThanEqual(id);
                default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type not found.");
            };
        } catch (ResponseStatusException r) {
            log.error("Error occurred: {}", r.getReason());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, r.getReason());
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-StudentId for Type={}. Error:{}", type, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        if (result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return result;
    }

    /*
       This method filters by studentID with a collection
       idCollection: Collection of studentIds to be matched
        */
    public List<Student> fiterIdCollection(List<Integer> idCollection) {
        List<Student> result = null;
        try {
            result = studentRepository.findByStudentIdIn(idCollection);
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Id in Collection={}. Error:{}", idCollection, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        if (result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
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
            result = studentRepository.findByStudentIdBetween(start, end);
        } catch (Throwable t) {
            log.error("Error occurred while searching Student-Id in Range=[{},{}]. Error:{}", start, end, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        if (result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return result;
    }
}
