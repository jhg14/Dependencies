import java.util.List;
/*
 * An object for storing a package title and its immediate dependencies
 */
public class DependencyElem {

    private String title;
    private List<String> dependencies;

    public DependencyElem (String title, List<String> dependencies) {
        this.title = title;
        this.dependencies = dependencies;
    }

    public String getTitle () {
        return title;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

}
