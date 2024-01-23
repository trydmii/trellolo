package ua.trydmi.trellolo.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.trydmi.trellolo.model.File;
import ua.trydmi.trellolo.model.User;
import ua.trydmi.trellolo.repository.FileRepository;
import ua.trydmi.trellolo.utils.TestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {
    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileServiceImpl fileServiceImpl;

    private User user;

    @BeforeEach
    void init() {
        user = TestUtils.getUser();
    }

    @Test
    void testFindByAuthor_WhenWithData_ThenShouldSucceed() {
        when(fileRepository.findByAuthor(user)).thenReturn(Collections.emptyList());
        List<File> byAuthor = fileServiceImpl.findByAuthor(user);
        assertNotNull(byAuthor);
        verify(fileRepository).findByAuthor(any(User.class));
    }

    @Test
    void testSave_WhenWithData_ThenShouldSucceed() {
        when(fileRepository.save(any(File.class))).thenReturn(TestUtils.getFile());
        File saved = fileServiceImpl.save(TestUtils.getFile());
        assertNotNull(saved);
        verify(fileRepository).save(any(File.class));
    }

    @Test
    void testFindById_WhenWithData_ThenShouldSucceed() {
        when(fileRepository.findById(any(Long.class))).thenReturn(java.util.Optional.ofNullable(TestUtils.getFile()));
        File byId = fileServiceImpl.findById(1L);
        assertNotNull(byId);
        verify(fileRepository).findById(any(Long.class));
    }

    @Test
    void testFindById_WhenNotFound_ThenShouldThrow() {
        when(fileRepository.findById(any(Long.class))).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> fileServiceImpl.findById(1L));
    }

    @Test
    void testDeleteById_WhenWithData_ThenShouldSucceed() {
        fileServiceImpl.deleteById(1L);
        verify(fileRepository).deleteById(any(Long.class));
    }

    @Test
    void testFindAll_WhenWithData_ThenShouldSucceed() {
        when(fileRepository.findAll()).thenReturn(Collections.emptyList());
        List<File> all = fileServiceImpl.findAll();
        assertNotNull(all);
        verify(fileRepository).findAll();
    }
}
