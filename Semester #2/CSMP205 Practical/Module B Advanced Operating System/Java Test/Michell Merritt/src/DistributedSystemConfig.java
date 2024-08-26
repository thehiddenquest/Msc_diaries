import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Site {
    String id;
    List<Resource> resources = new ArrayList<>();
    List<Process> processes = new ArrayList<>();

    Site(String id) {
        this.id = id;
    }
}

class Resource {
    String name;
    Site site;

    Resource(String name, Site site) {
        this.name = name;
        this.site = site;
    }
}

class Process {
    String name;
    Site site;

    Process(String name, Site site) {
        this.name = name;
        this.site = site;
    }
}

public class DistributedSystemConfig {
    Map<String, Site> sites = new HashMap<>();
    List<Resource> resources = new ArrayList<>();
    List<Process> processes = new ArrayList<>();

    public void readConfig(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String currentSection = "";

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                if (line.endsWith(":")) {
                    currentSection = line.substring(0, line.length() - 1).trim();
                    System.out.println("Parsing section: " + currentSection);
                    continue;
                }

                switch (currentSection) {
                    case "Sites":
                        parseSites(line);
                        break;
                    case "Resources":
                        if (!line.isEmpty()) {
                            parseResources(line);
                        } else {
                            System.out.println("Warning: Resources section is empty");
                        }
                        break;
                    case "Processes":
                        parseProcesses(line);
                        break;
                    default:
                        System.out.println("Unknown section: " + currentSection);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void parseSites(String line) {
        System.out.println("Parsing sites: " + line);
        String[] siteIds = line.split(",");
        for (String id : siteIds) {
            id = id.trim();
            if (!id.isEmpty()) {
                sites.put(id, new Site(id));
            }
        }
    }

    private void parseResources(String line) {
        System.out.println("Parsing resources: " + line);
        String[] resourceInfos = line.split(",");
        for (String info : resourceInfos) {
            info = info.trim();
            if (info.isEmpty())
                continue;

            int openParen = info.indexOf('(');
            int closeParen = info.indexOf(')');
            if (openParen == -1 || closeParen == -1 || openParen >= closeParen) {
                System.out.println("Error: Invalid resource format: " + info);
                continue;
            }

            String name = info.substring(0, openParen).trim();
            String siteId = info.substring(openParen + 1, closeParen).trim();
            Site site = sites.get(siteId);
            if (site == null) {
                System.out.println("Error: Site " + siteId + " not found for resource " + name);
                continue;
            }
            Resource resource = new Resource(name, site);
            resources.add(resource);
            site.resources.add(resource);
        }
    }

    private void parseProcesses(String line) {
        System.out.println("Parsing processes: " + line);
        String[] processInfos = line.split(",");
        for (String info : processInfos) {
            info = info.trim();
            if (info.isEmpty())
                continue;

            int openParen = info.indexOf('(');
            int closeParen = info.indexOf(')');
            if (openParen == -1 || closeParen == -1 || openParen >= closeParen) {
                System.out.println("Error: Invalid process format: " + info);
                continue;
            }

            String name = info.substring(0, openParen).trim();
            String siteId = info.substring(openParen + 1, closeParen).trim();
            Site site = sites.get(siteId);
            if (site == null) {
                System.out.println("Error: Site " + siteId + " not found for process " + name);
                continue;
            }
            Process process = new Process(name, site);
            processes.add(process);
            site.processes.add(process);
        }
    }

    public void printConfig() {
        System.out.println("Configuration:");
        System.out.println("Sites:");
        for (Site site : sites.values()) {
            System.out.println("  " + site.id);
        }

        System.out.println("\nResources:");
        if (resources.isEmpty()) {
            System.out.println("  No resources defined");
        } else {
            for (Resource resource : resources) {
                System.out.println("  " + resource.name + " (Site " + resource.site.id + ")");
            }
        }

        System.out.println("\nProcesses:");
        if (processes.isEmpty()) {
            System.out.println("  No processes defined");
        } else {
            for (Process process : processes) {
                System.out.println("  " + process.name + " (Site " + process.site.id + ")");
            }
        }
    }
    

    public static void main(String[] args) {
        try {
            DistributedSystemConfig config = new DistributedSystemConfig();
            config.readConfig("src\\Input 1.txt");
            config.printConfig();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}