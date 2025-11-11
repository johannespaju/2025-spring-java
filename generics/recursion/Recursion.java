package generics.recursion;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Recursion {

    public List<String> getParts(Path path) {
        List<String> parts = new ArrayList<>();
        while (path != null) {
            System.out.println(path.getFileName().toString());
            parts.addFirst(path.getFileName().toString());
            path = path.getParent();
        }
        return parts;
    }

    public List<String> getParts2(Path path) {

        // b)

        return null;
    }

    public List<String> getParts3(Path path, List<String> parts) {

        // c)

        return null;
    }

    public List<String> getParts4(Path path) {

        // d)

        return null;
    }
}
