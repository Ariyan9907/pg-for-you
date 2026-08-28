package org.bridgelabz.pgforyou.junittesting;

import org.bridgelabz.pgforyou.dto.response.PGResponseDTO;
import org.bridgelabz.pgforyou.model.PG;
import org.bridgelabz.pgforyou.repository.PGRepository;
import org.bridgelabz.pgforyou.service.PGService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PGServiceTest {

    @Mock
    private PGRepository pgRepository;

    @InjectMocks
    private PGService pgService;

    @Test
    void shouldGetPGById() {

        PG pg = new PG();

        pg.setId(1L);
        pg.setName("Sri Sai PG");
        pg.setLocation("Electronic City");
        pg.setRent(new BigDecimal("8000"));
        pg.setImageUrl("/images/pg/pg1.jpg");

        when(pgRepository.findById(1L))
                .thenReturn(Optional.of(pg));

        PGResponseDTO response =
                pgService.getPGById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Sri Sai PG", response.getName());
        assertEquals("Electronic City", response.getLocation());
        assertEquals(new BigDecimal("8000"), response.getRent());
    }
}