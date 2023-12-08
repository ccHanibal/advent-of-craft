package ci.dependencies;

public interface BuildStep {

    String getMailMessage();
    boolean run(Project project);
}
