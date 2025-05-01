package dev.ceccon.client.response;

public class BlockResponse {

    private String role;
    private String content;
    private UsageMetrics metrics;

    public BlockResponse(String role, String content, UsageMetrics metrics) {
        this.role = role;
        this.content = content;
        this.metrics = metrics;
    }

    public String getRole() {
        return this.role;
    }

    public String getContent() {
        return this.content;
    }

    public UsageMetrics getMetrics() {
        return this.metrics;
    }
}