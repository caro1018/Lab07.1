package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.Configuration;
import co.edu.escuelaing.cvds.lab7.repository.ConfigurationRepository;
import co.edu.escuelaing.cvds.lab7.service.ConfigurationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigurationControllerTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private ConfigurationRepository mockConfigurationRepository;

    @Mock
    private Model model;

    @InjectMocks
    private ConfigurationService configurationService;

    private ConfigurationController configurationController;

    @BeforeEach
    void setUp() {
        // To be implemented, remove if not needed
        System.setOut(new PrintStream(outputStreamCaptor));
        configurationController = new ConfigurationController(configurationService);
    }

    @AfterEach
    void tearDown() {
        // To be implemented, remove if not needed
        System.setOut(standardOut);
    }

    @Test
    void example() {
        // Arrange / Given / Dado - precondition or setup
        Configuration configuration = new Configuration("premio", "800000");
        when(mockConfigurationRepository.findByPropiedad("premio")).thenReturn(Arrays.asList(configuration));
        // Act / When / Cuando - action or the behaviour that we are going test
        String value = configurationController.example(model);
        // Assert / Then / Entonces - verify the output
        standardOut.println(value);
        assertEquals("example", value);
    }
}
