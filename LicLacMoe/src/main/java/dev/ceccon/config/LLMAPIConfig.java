package dev.ceccon.config;

public class LLMAPIConfig {

    private String protocol = "http";
    private String host = "localhost";
    private String port = "8080";
    private String endpoint = "v1/chat/completions";
    private String model = "";
    private double temperature = 0.9;
    private boolean streaming = false;
    private boolean verbose = false;

    public String getFullUrl() {
        return protocol + "://" + host + ":" + port + "/" + endpoint;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isStreaming() {
        return streaming;
    }

    public void setStreaming(boolean streaming) {
        this.streaming = streaming;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
