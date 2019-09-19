import java.util.Collection;

public interface Node {

    Collection<Node> getChildren();

    String getText();

}
