import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Dependencies {

    public static void main(String[] args) {

        //Return if not enough arguments are specified
        if (args.length < 2){
            return;
        }

        //Create new list for given dependencies, and reader to read them from file
        List<DependencyElem> info = new ArrayList<>();
        BufferedReader fileReader;

        try {
            fileReader = new BufferedReader(new FileReader(args[0]));
            String line;
            while ((line = fileReader.readLine()) != null) {
                info.add(getElement(line));
            }
        } catch (IOException e) {
            return;
        }

        //Put the arguments into a new array, requests
        String requests[] = new String[args.length-1];
        for (int i = 1; i < args.length; i++){
            requests[i-1] = args[i];
        }

        //Create a graph, and generate it using the given dependencies
        DepGraph graph = new DepGraph(info);
        graph.generate();

        //Create an array to store the solutions
        String solutions[] = graph.requests(requests);

        //Print out the solutions
        for (int i = 0; i < requests.length; i++) {
            System.out.println(solutions[i]);
        }

    }

    /* Creates a new DependencyElem, storing the title package
    * and its dependencies */
    private static DependencyElem getElement (String line) {
        String title;
        List<String> deps = new ArrayList<>();

        //Tokenise, delimiting by space
        StringTokenizer strTok = new StringTokenizer(line, " ");

        //First element is title
        title = strTok.nextToken();

        //Discard next element as it will be, and must be '->'
        String arrow = strTok.nextToken();
        assert (arrow.equals("->"));

        //Add the rest of the elements as dependencies.
        while (strTok.hasMoreElements()) {
            deps.add(strTok.nextToken());
        }
        return new DependencyElem(title, deps);
    }
}
