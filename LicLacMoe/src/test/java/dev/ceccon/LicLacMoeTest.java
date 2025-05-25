package dev.ceccon;

import dev.ceccon.config.LLMAPIConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LicLacMoeTest {

    @Test
    void cliOptionPortWithoutValueThrowsException() {
        String[] args = new String[]{"-p"};

        assertThrows(IllegalArgumentException.class, () -> {
            LicLacMoe.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionPortWithNonNumericValueThrowsException() {
        String port = "port";
        String[] args = new String[]{"-p", port};

        assertThrows(IllegalArgumentException.class, () -> {
            LicLacMoe.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionPortWithNumericValueSetsPortParameterOnApiConfig() {
        String port = "8089";
        String[] args = new String[]{"-p", port};

        LLMAPIConfig apiConfig = new LLMAPIConfig();

        LicLacMoe.parseArguments(args, apiConfig);

        String portOnApiConfig = apiConfig.getPort();

        assertEquals(port, portOnApiConfig);
    }

    @Test
    void cliOptionModelWithoutValueThrowsException() {
        String[] args = new String[]{"-m"};

        assertThrows(IllegalArgumentException.class, () -> {
            LicLacMoe.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionModelWithValueSetsModelParameterOnApiConfig() {
        String model = "test-model";
        String[] args = new String[]{"-m", model};

        LLMAPIConfig apiConfig = new LLMAPIConfig();

        LicLacMoe.parseArguments(args, apiConfig);

        String modelOnApiConfig = apiConfig.getModel();

        assertEquals(model, modelOnApiConfig);
    }

    @Test
    void cliOptionTemperatureWithoutValueThrowsException() {
        String[] args = new String[]{"-t"};

        assertThrows(IllegalArgumentException.class, () -> {
            LicLacMoe.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionTemperatureWithNonNumericValueThrowsException() {
        String temperature = "temperature";
        String[] args = new String[]{"-t", temperature};

        assertThrows(IllegalArgumentException.class, () -> {
            LicLacMoe.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionTemperatureWithWholeNumberSetsTemperatureOnApiConfig() {
        String temperature = "5";
        String[] args = new String[]{"-t", temperature};

        LLMAPIConfig apiConfig = new LLMAPIConfig();

        LicLacMoe.parseArguments(args, apiConfig);

        double temperatureOnApiConfig = apiConfig.getTemperature();

        assertEquals(Double.valueOf(temperature), temperatureOnApiConfig);
    }

    @Test
    void cliOptionTemperatureWithDecimalNumberSetsTemperatureOnApiConfig() {
        String temperature = "0.3";
        String[] args = new String[]{"-t", temperature};

        LLMAPIConfig apiConfig = new LLMAPIConfig();

        LicLacMoe.parseArguments(args, apiConfig);

        double temperatureOnApiConfig = apiConfig.getTemperature();

        assertEquals(Double.valueOf(temperature), temperatureOnApiConfig);
    }

    @Test
    void cliOptionVerboseWithoutValueThrowsException() {
        String[] args = new String[]{"-v"};

        assertThrows(IllegalArgumentException.class, () -> {
            LicLacMoe.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionVerboseWithNonBooleanValueThrowsException() {
        String verbose = "error";
        String[] args = new String[]{"-v", verbose};

        assertThrows(IllegalArgumentException.class, () -> {
            LicLacMoe.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionVerboseWithTrueCausesVerboseOnAPIConfig() {
        String verbose = "true";
        String[] args = new String[]{"-v", verbose};

        LLMAPIConfig apiConfig = new LLMAPIConfig();

        LicLacMoe.parseArguments(args, apiConfig);

        boolean verboseOnApiConfig = apiConfig.isVerbose();

        assertEquals(Boolean.valueOf(verbose), verboseOnApiConfig);
    }

    @Test
    void cliOptionVerboseWithFalseCausesNonVerboseOnAPIConfig() {
        String verbose = "false";
        String[] args = new String[]{"-v", verbose};

        LLMAPIConfig apiConfig = new LLMAPIConfig();

        LicLacMoe.parseArguments(args, apiConfig);

        boolean verboseOnApiConfig = apiConfig.isVerbose();

        assertEquals(Boolean.valueOf(verbose), verboseOnApiConfig);
    }

    @Test
    void cliOptionInexistentThrowsException() {
        String[] args = new String[]{"-thisisnotanoption"};

        assertThrows(IllegalArgumentException.class, () -> {
            LicLacMoe.parseArguments(args, new LLMAPIConfig());
        });
    }
}
