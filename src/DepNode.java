import java.util.ArrayList;
import java.util.List;

/* Node used to represent a package in the graph */
public class DepNode {

    private String title;
    private List<DepNode> dependencies;
    private boolean visited;

    public DepNode(String title) {
        this.title = title;
        dependencies = new ArrayList<>();
        visited = false;
    }

    public boolean isVisited(){
        return visited;
    }

    public void addDependency (DepNode dep) {
        dependencies.add(dep);
    }


    public void setVisited() {
        visited = true;
    }

    public void flushVisited() {
        visited = false;
    }

    public List<DepNode> getDependencies() {
        return dependencies;
    }

    public String getTitle() {
        return title;
    }

}
