package recyclerMicroservice.test;

import com.recyclerMicroservice.exception.NoContentFoundException;
import com.recyclerMicroservice.exception.RecyclerNotFoundException;
import com.recyclerMicroservice.model.Recycler;
import com.recyclerMicroservice.repository.RecyclerRepository;
import com.recyclerMicroservice.service.RecyclerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RecyclerServiceTest {

    @Mock
    private RecyclerRepository recyclerRepository;

    @InjectMocks
    private RecyclerService recyclerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recyclerService = new RecyclerService(recyclerRepository);
    }

    @Test
    void testGetAllRecyclers_ShouldReturnRecyclersList() throws NoContentFoundException {
        List<Recycler> recyclers = new ArrayList<>();
        recyclers.add(new Recycler());
        recyclers.add(new Recycler());

        when(recyclerRepository.findAll()).thenReturn(recyclers);

        List<Recycler> result = recyclerService.getAllRecyclers();

        assertEquals(2, result.size());
        verify(recyclerRepository, times(1)).findAll();
    }

    @Test
    void testGetAllRecyclers_ShouldThrowNoContentFoundException_WhenNoRecyclersFound() {
        when(recyclerRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NoContentFoundException.class, () -> recyclerService.getAllRecyclers());

        verify(recyclerRepository, times(1)).findAll();
    }

    @Test
    void testGetRecyclerById_ShouldReturnRecycler_WhenRecyclerExists() throws RecyclerNotFoundException {
        int recyclerId = 1;
        Recycler recycler = new Recycler();
        recycler.setRecyclerId(recyclerId);

        when(recyclerRepository.findById(recyclerId)).thenReturn(Optional.of(recycler));

        Recycler result = recyclerService.getRecyclerById(recyclerId);

        assertEquals(recyclerId, result.getRecyclerId());
        verify(recyclerRepository, times(1)).findById(recyclerId);
    }

    @Test
    void testGetRecyclerById_ShouldThrowRecyclerNotFoundException_WhenRecyclerDoesNotExist() {
        int recyclerId = 1;

        when(recyclerRepository.findById(recyclerId)).thenReturn(Optional.empty());

        assertThrows(RecyclerNotFoundException.class, () -> recyclerService.getRecyclerById(recyclerId));

        verify(recyclerRepository, times(1)).findById(recyclerId);
    }

    @Test
    void testSaveRecycler_ShouldReturnSavedRecycler() {
        Recycler recycler = new Recycler();

        when(recyclerRepository.save(recycler)).thenReturn(recycler);

        Recycler result = recyclerService.saveRecycler(recycler);

        assertEquals(recycler, result);
        verify(recyclerRepository, times(1)).save(recycler);
    }

    @Test
    void testDeleteRecycler_ShouldDeleteRecycler_WhenRecyclerExists() throws RecyclerNotFoundException {
        int recyclerId = 1;

        when(recyclerRepository.existsById(recyclerId)).thenReturn(true);

        recyclerService.deleteRecycler(recyclerId);

        verify(recyclerRepository, times(1)).existsById(recyclerId);
        verify(recyclerRepository, times(1)).deleteById(recyclerId);
    }

    @Test
    void testDeleteRecycler_ShouldThrowRecyclerNotFoundException_WhenRecyclerDoesNotExist() {
        int recyclerId = 1;

        when(recyclerRepository.existsById(recyclerId)).thenReturn(false);

        assertThrows(RecyclerNotFoundException.class, () -> recyclerService.deleteRecycler(recyclerId));

        verify(recyclerRepository, times(1)).existsById(recyclerId);
        verify(recyclerRepository, never()).deleteById(recyclerId);
    }
}
