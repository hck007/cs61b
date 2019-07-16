import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;
//import java.util.zip.GZIPInputStream;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    private Map<Long, Node> allVertices = new HashMap<>();
    private Map<Long, Edge> allEdges = new HashMap<>();

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            //GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        Map<Long, Node> updateAllVertices = new HashMap<>();
        for (Node x : allVertices.values()) {
            if (x.adj.size() != 0) {
                updateAllVertices.put(x.id, x);
            }
        }
        allVertices = updateAllVertices;
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return allVertices.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        List<Long> neighbors = new ArrayList<>();
        Node x = allVertices.get(v);
        for (long id : x.adj) {
            neighbors.add(id);
        }
        return neighbors;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);
        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double minDistance = Double.POSITIVE_INFINITY;
        double currentDistance;
        long currentID;
        long finalID = 111;
        for (long id : allVertices.keySet()) {
            currentID = id;
            currentDistance = distance(lon, lat, allVertices.get(id).lon, allVertices.get(id).lat);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                finalID = currentID;
            }
        }
        return finalID;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        if (allVertices.containsKey(v)) {
            return allVertices.get(v).lon;
        }
        throw new IllegalArgumentException("This node is not present");
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        if (allVertices.containsKey(v)) {
            return allVertices.get(v).lat;
        }
        throw new IllegalArgumentException("This Node is not present");
    }

    void addEdge(long id, List<Long> vertexList) {
        Edge e = new Edge(id, vertexList);
        allEdges.put(id, e);
        for (int i = 0; i < vertexList.size() - 1; i++) {
            long v_id = vertexList.get(i);
            long w_id = vertexList.get(i + 1);
            Node v = allVertices.get(v_id);
            Node w = allVertices.get(w_id);
            v.adj.add(w_id);
            w.adj.add(v_id);
        }
    }

    void addNode(long id, double lon, double lat) {
        allVertices.put(id, new Node(id, lon, lat));
    }

    void removeNode(long id) {
        Node nodeRemoved = allVertices.get(id);
        allVertices.remove(id);
        for (Node x : allVertices.values()) {
            if (x.adj.contains(nodeRemoved)) {
                x.adj.remove(nodeRemoved);
            }
        }
    }

    void setNodeName(long id, String name) {
        allVertices.get(id).name = name;
    }

    Node getNode(long id) {
        return allVertices.get(id);
    }

    //Node class
    static class Node {
        long id;
        double lon;
        double lat;
        String name = null;
        List<Long> adj = new ArrayList<>();

        Node(long idNum, double longitude, double latitude) {
            id = idNum;
            lon = longitude;
            lat = latitude;
        }
    }

    static class Edge {
        private long id;
        private String name;
        private List<Long> vertexList;

        Edge(long id, List<Long> vertexList) {
            this.id = id;
            this.vertexList = vertexList;
        }
    }

}
