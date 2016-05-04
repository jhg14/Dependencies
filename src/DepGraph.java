import java.util.*;

/* Graph of packages */
public class DepGraph {

    private List<DependencyElem> givenDeps;
    private List<DepNode> allNodes;

    public DepGraph(List<DependencyElem> givenDeps) {
        this.givenDeps = givenDeps;
        this.allNodes = new ArrayList<>();
    }

    /* Generates the graph from the given dependencies */
    public void generate() {
        createNodes();
    }

    public DepNode getNodeFromTitle(String nodeTitle) {
        for (DepNode node : allNodes) {
            if (node.getTitle().equals(nodeTitle)) {
                return node;
            }
        }
        return null;
    }

    /* Retreives transitive dependencies for the list of supplied packages */
    public String[] requests(String nodeTitles[]) {
        String[] deps = new String[nodeTitles.length];
        for (int i = 0; i < nodeTitles.length; i++) {
            deps[i] = request(nodeTitles[i]);
        }
        return deps;
    }

    /* Retrieves 1 transitive dependency */
    private String request(String nodeTitle) {
        //return dependencies for a node
        DepNode startNode = getNodeFromTitle(nodeTitle);
        List<DepNode> nodeList = breadthFirstSearch(startNode);
        List<String> titleList = new ArrayList<>();

        nodeList.forEach((depNode -> titleList.add(depNode.getTitle())));
        flushVisitedNodes(nodeList);
        StringBuilder sb = new StringBuilder();

        titleList.sort((a, b) -> a.compareTo(b));
        sb.append(nodeTitle + " -> ");
        for (String s : titleList) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString();
    }

    /* Mark all nodes as not visited, after traversal */
    private void flushVisitedNodes(List<DepNode> visited) {
        visited.forEach((vis -> vis.flushVisited()));
    }

    /* Create nodes from the given depency list */
    private void createNodes() {
        /* For each given dependency, check if it is already in the graph.
           If it is not, create a new node with that title, otherwise retrieve
           it from the graph and set that to be the node being edited.
        */
        for (DependencyElem dep : givenDeps) {
            String title = dep.getTitle(); //gui
            DepNode newNode = new DepNode(title); //new node called gui

            boolean alreadyExisted = false;
            for (DepNode titleNode : allNodes) { //for all nodes (none so far)
                if (titleNode.getTitle().equals(title)) {
                    newNode = titleNode;
                    alreadyExisted = true;
                    break;
                }
            }

            if (!alreadyExisted) {
                allNodes.add(newNode); // gui now in list
            }

            /* For each of the depencies of the relevant package node,
               if the node is already on the graph, link the current package with
               that node, as one of its dependencies, otherwise create a new node
               of the depency's title and add it to the graph.

            */
            for (String str : dep.getDependencies()) { // for all of guis deps = 2
                boolean exists = false; //dont exist at first
                for (int i = 0; i < allNodes.size(); i++) {
                    DepNode node = allNodes.get(i); // only gui

                    if (str.equals(node.getTitle())) {
                        newNode.addDependency(node);
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    DepNode depNode = new DepNode(str);
                    newNode.addDependency(depNode);
                    allNodes.add(depNode);

                }
            }
        }
    }

    /* Performs a breadth first search, keeping track of the nodes
       that have been visited. This will give all the transitive
       dependencies of the package. Keeping track of the visited nodes
       will also prevent circular dependencies being added to the return list.
     */
    private List<DepNode> breadthFirstSearch(DepNode root) {
        Queue<DepNode> q = new LinkedList<>();
        q.add(root);
        root.setVisited();
        List<DepNode> deps = new ArrayList<>();

        while (!q.isEmpty()) {
            DepNode front = q.poll();
            if (front != null) {
                for (DepNode child : front.getDependencies()) {
                    if (!child.isVisited()) {
                        q.add(child);
                        child.setVisited();
                        deps.add(child);
                    }
                }
            } else {
                break;
            }
        }
        root.flushVisited();
        return deps;
    }
}
