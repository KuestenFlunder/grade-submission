package com.ltp.gradesubmission;

import com.ltp.gradesubmission.pojo.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.service.GradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {
    //Creates a Mock of the GradeRepository class
    @Mock
    private GradeRepository gradeRepository;

    // Injects the Mock into the GradeService like @Autowire
    @InjectMocks
    private GradeService gradeService;

    @Test
    //Arrange
    public void getGradesFromRepoTest() {
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
                new Grade("Harry", "Potions", "C-"),
                new Grade("Hermine", "Arithmancy", "A+")
        ));

        //Act
        List<Grade> result = gradeService.getGrades();
        //Assert
        assertEquals("Harry", result.get(0).getName());
        assertEquals("Arithmancy", result.get(1).getSubject());

    }

    @Test
    //Arrange
    public void gradeIndexTest() {
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
                grade
        ));

        when(gradeRepository.getGrade(0)).thenReturn(grade);

        //first call the getGrades from the Service to have access to indexes

        //Act
        int valid = gradeService.getGradeIndex(grade.getId());
        int notFound = gradeService.getGradeIndex("123");

        //Assert
        assertEquals(0, valid);
        assertEquals(Constants.NOT_FOUND, notFound);
    }

    @Test
    public void returnGradeByIdTest() {
        //Arrange
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        String id = grade.getId();

        Grade result = gradeService.getGradeById(id);
        Grade unknownId = gradeService.getGradeById("123");
        assertEquals(result,grade);
        assertNotEquals(unknownId, grade);
    }

    @Test
    public void submitGradeTest(){
        //Arrange
        //* getGradeId
        //* index 0
        //* Constant.NOT_FOUND
        //Act

        //Assert
    }

    @Test
    public void addGradeTest(){
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        Grade newGrade = new Grade("Hermione","Artithmancy","A+");
        gradeService.submitGrade(newGrade);
        verify(gradeRepository, times(1)).addGrade(newGrade);
    }

    @Test
    public void updateGradeTest(){
        //Arrange
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);
        //Act
        grade.setScore("C-");
        gradeService.submitGrade(grade);
        //Assert
        verify(gradeRepository,times(1)).updateGrade(grade,0);

    }
}
