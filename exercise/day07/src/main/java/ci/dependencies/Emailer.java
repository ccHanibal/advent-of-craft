package ci.dependencies;

public interface Emailer {
    void sendPipelineFailed(String message);
    void sendPipelineSucceeded(String message);
}
