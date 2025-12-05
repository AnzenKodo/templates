import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Build {
    final static String mainClass = "Main";
    final static boolean isDebugBuild = true;
    final static boolean enableVerbose = true;
    final static String buildDir = "./build";
    static String javacPath;
    static String javaPath;

    public static void main(String[] args) {
        if (getOs() == "windows") {
            javacPath = "C://Program Files/Java/jdk1.8.0_151/bin/javac";
            javaPath = "C://Program Files/Java/jre1.8.0_151/bin/java";
        } else {
            System.err.println("Error: this OS `javac` and `java` paths are not defined.");
            System.exit(1);
        }

        System.out.println("# Compiling ===========");
        BuildCommand cmd = new BuildCommand();
            cmd.add(javacPath);
            // Set Error & Warning Messages
            cmd.add("-Xlint:unchecked");
            cmd.add("-deprecation");
            if (isDebugBuild) {
                // Enable Debug Info
                cmd.add("-g");
            }
            // Set output directory
            cmd.add("-d"); cmd.add(buildDir);
            cmd.add(mainClass + ".java");
        cmd.run();

        System.out.println("\n# Running =============");
        cmd = new BuildCommand();
            cmd.add(javaPath);
            cmd.add("-classpath"); cmd.add(buildDir);
            if (isDebugBuild) {
                // Enalbe Asserts
                cmd.add("-ea");
            }
            cmd.add(mainClass);
        cmd.run();
    }

    final static class BuildCommand {
        ArrayList<String> cmd;

        public BuildCommand() {
            cmd = new ArrayList<String>();
        }
        public BuildCommand(String str) {
            this();
            cmd.add(str);
        }

        void create() {
        }

        void add(String str) {
            cmd.add(str);
        }

        public String toString() {
            return cmd.toString();
        }

        void run() {
            run(".");
        }
        void run(String run_path) {
            LinkedList<String> command = new LinkedList<>();

            if (getOs() == "windows") {
                command.addAll(0, Arrays.asList("cmd.exe", "/c", "\""));
                command.addAll(cmd);
                command.add("\"");
            } else {
                command.addAll(0, Arrays.asList("/bin/sh", "-c"));
                command.addAll(cmd);
            }

            if (enableVerbose) {
                System.out.println("Command: "+ String.join(" ", command));
            }
            final ProcessBuilder builder = new ProcessBuilder(command.toArray(new String[0]));
            builder.inheritIO();
            builder.directory(new File(run_path));

            try {
                final Process process = builder.start();

                final int status = process.waitFor();
                if (status != 0) {
                    System.exit(status);
                }
            } catch (InterruptedException|IOException e) {
                System.out.println("Build failed with:\n" + e);
            }
        }
    }

    static String getOs() {
        String result = "";
        String os_prop = System.getProperty("os.name").toLowerCase();

        if (os_prop.startsWith("windows")) {
            result = "windows";
        }

        return result;
    }
}

