package co.edu.escuelaing.cvds.lab7.service;

import co.edu.escuelaing.cvds.lab7.model.Configuration;
import co.edu.escuelaing.cvds.lab7.repository.ConfigurationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigurationServiceTest {
    @Mock
    private ConfigurationRepository mockConfigurationRepository;

    @InjectMocks
    private ConfigurationService configurationService;

    @DisplayName("Given I have a configuration property called 'premio' in the database"+
    "When I search for all configuration properties at service level"+
    "Then the result is a configuration array of size 1 and a property called 'premio' with value 800000")
    @Test
    void getAllConfigurations() {
        // Arrange / Given - precondition or setup
        Configuration configuration = new Configuration("premio", "800000");
        when(mockConfigurationRepository.findAll()).thenReturn(Arrays.asList(configuration));
        // Act / When - action or the behaviour that we are going test
        List<Configuration> allConfigurations = configurationService.getAllConfigurations();
        // Assert / Then - verify the output
        assertEquals(1, allConfigurations.size());
        assertEquals("premio", allConfigurations.get(0).getPropiedad());
    }

    @DisplayName("""
    Given I have a configuration property called 'premio' with value '1000' in the database
    When I search for the 'premio' value at service level
    Then the result is the value '1000'
    """)
    @Test
    void getPremio() {
        // Arrange / Given - precondition or setup
        String property = "premio";
        String expectedValue = "1000";
        Configuration configuration = new Configuration(property, expectedValue);
        when(mockConfigurationRepository.findByPropiedad(property)).thenReturn(Arrays.asList(configuration));
        // Act / When - action or the behaviour that we are going test
        String value = configurationService.getPremio();
        // Assert / Then - verify the output
        assertEquals(expectedValue, value);
    }
}