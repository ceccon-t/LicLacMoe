package dev.ceccon;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import dev.ceccon.client.LLMClient;
import dev.ceccon.config.LLMAPIConfig;
import dev.ceccon.gui.MainView;

import javax.swing.*;

public class LicLacMoe {

    public static void main( String[] args ) {
        System.out.println("Starting LicLacMoe...");

        LLMAPIConfig apiConfig = new LLMAPIConfig();
        LLMClient llmClient = new LLMClient(apiConfig);

        parseArguments(args, apiConfig);

        SwingUtilities.invokeLater(() -> {
            new MainView(llmClient);
        });
    }

    public static void parseArguments(String[] args, LLMAPIConfig apiConfig) {
        LicLacMoeArgs llmArgs = new LicLacMoeArgs();
        JCommander cmd = JCommander.newBuilder()
                .addObject(llmArgs)
                .build();

        try {
            cmd.parse(args);
        } catch(ParameterException e) {
            throw new IllegalArgumentException("Could not parse parameter.");
        }

        if (llmArgs.hasPort()) {
            Integer port = llmArgs.getPort();
            apiConfig.setPort(port.toString());
        }

        if (llmArgs.hasModel()) {
            String model = llmArgs.getModel();
            apiConfig.setModel(model);
        }

        if (llmArgs.hasTemperature()) {
            Double temperature = llmArgs.getTemperature();
            apiConfig.setTemperature(temperature);
        }
    }

    static class LicLacMoeArgs {
        @Parameter(
                names = {"-p", "--port"},
                description = "LLM server port",
                required = false,
                arity = 1
        )
        private Integer port;

        @Parameter(
                names = {"-m", "--model"},
                description = "LLM server model",
                required = false,
                arity = 1
        )
        private String model;

        @Parameter(
                names = {"-t", "--temperature"},
                description = "Temperature value to use when generating answers.",
                required = false,
                arity = 1
        )
        private Double temperature;

        public Integer getPort() {
            return port;
        }

        public String getModel() {
            return model;
        }

        public Double getTemperature() {
            return temperature;
        }

        public boolean hasPort() {
            return port != null;
        }

        public boolean hasModel() {
            return model != null;
        }

        public boolean hasTemperature() {
            return temperature != null;
        }
    }

}
