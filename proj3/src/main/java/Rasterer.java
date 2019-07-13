import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private double[] lonDPPList;
    private static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
    ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    private static final double[] WIDTH_UNIT = {0.087890625, 0.0439453125, 0.02197265625, 0.010986328125,
            0.0054931640625, 0.00274658203125, 0.001373291015625, 0.0006866455078125};
    private static final double[] HEIGHT_UNIT = {0.06939311371679224, 0.03469655685839257, 0.017348278429196284,
            0.008674139214598142, 0.004337069607295518, 0.0021685348036442065, 0.0010842674018221032,
            0.0005421337009110516};
    private double raster_ul_lon = 0.0;
    private double raster_lr_lon = 0.0;
    private double raster_ul_lat = 0.0;
    private double raster_lr_lat = 0.0;

    public Rasterer() {
        double reference = 0.00034332275;
        lonDPPList = new double[8];
        for (int d = 0; d <= 7; d++) {
            double lonDPP = reference / Math.pow(2, d);
            lonDPPList[d] = lonDPP;
        }
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlon = params.get("lrlon");
        double lrlat = params.get("lrlat");
        double width = params.get("w");
        double height = params.get("h");
        double lonDPP = (lrlon - ullon) / width;
        int depth = 0;
        for (int i = 0; i < lonDPPList.length; i++) {
            if (lonDPPList[i] <= lonDPP) {
                depth = i;
                break;
            }
            depth = i;
        }
        if (!validateInput(ullon, ullat, lrlon, lrlat, width, height)) {
            boolean query_success = false;
            results.put("render_grid", null);
            results.put("raster_ul_lon", null);
            results.put("raster_ul_lat", null);
            results.put("raster_lr_lon", null);
            results.put("raster_lr_lat", null);
            results.put("depth", depth);
            results.put("query_success", query_success);
        }
        else {
            boolean query_success = true;
            String[][] render_grid = getImage(depth, ullon, ullat, lrlon, lrlat);
            results.put("render_grid", render_grid);
            results.put("raster_ul_lon", raster_ul_lon);
            results.put("raster_ul_lat", raster_ul_lat);
            results.put("raster_lr_lon", raster_lr_lon);
            results.put("raster_lr_lat", raster_lr_lat);
            results.put("depth", depth);
            results.put("query_success", query_success);
        }
        return results;
    }

    private String[][] getImage(int depth, double ullon, double ullat, double lrlon, double lrlat) {
        List<Integer> x_k = getRaster_lon(depth, ullon, lrlon);
        List<Integer> y_k = getRaster_lat(depth, ullat, lrlat);
        String[][] render_grid = new String[y_k.size()][x_k.size()];
        for(int row = 0; row < y_k.size(); row++) {
            for (int col = 0; col < x_k.size(); col++) {
                render_grid[row][col] = "d" + depth + "_" + "x" + x_k.get(col) + "_" + "y" + y_k.get(row) + ".png";
            }
        }
        return render_grid;
    }

    private List<Integer> getRaster_lon(int depth, double ullon, double lrlon) {
        double width = WIDTH_UNIT[depth];
        int startingIndex = 0;
        int finalIndex = 0;
        List<Integer> lon = new ArrayList<>();
        for (int k = 0; k <= Math.pow(2, depth) - 1; k++) {
            raster_ul_lon = ROOT_ULLON + width * k;
            if (raster_ul_lon <= ullon && Math.abs(raster_ul_lon - ullon) < width ) {
                startingIndex = k;
                break;
            }
            startingIndex = k;
        }
        for (int k = startingIndex; k <= Math.pow(2, depth) - 1; k++) {
            raster_lr_lon = ROOT_ULLON + width * (k + 1);
            if (raster_lr_lon >= lrlon && Math.abs(raster_lr_lon - lrlon) < width) {
                finalIndex = k;
                break;
            }
            finalIndex = k;
        }
        for (int i = startingIndex; i <= finalIndex; i++) {
            lon.add(i);
        }
        return lon;
    }

    private List<Integer> getRaster_lat(int depth, double ullat, double lrlat) {
        double height = HEIGHT_UNIT[depth];
        int startingIndex = 0;
        int finalIndex = 0;
        List<Integer> lat = new ArrayList<>();
        for (int k = 0; k <= Math.pow(2, depth) - 1; k++) {
            raster_ul_lat = ROOT_ULLAT - k * height;
            if (raster_ul_lat >= ullat && Math.abs(raster_ul_lat - ullat) < height) {
                startingIndex = k;
                break;
            }
            startingIndex = k;
        }
        for (int k = startingIndex; k <= Math.pow(2, depth) - 1; k++) {
            raster_lr_lat = ROOT_ULLAT - height * (k + 1);
            if (raster_lr_lat <= lrlat && Math.abs(raster_lr_lat - lrlat) < height) {
                finalIndex = k;
                break;
            }
            finalIndex = k;
        }
        for (int i = startingIndex; i <= finalIndex; i++) {
            lat.add(i);
        }
        return lat;
    }

    private boolean validateInput(double ullon, double ullat, double lrlon, double lrlat, double w, double h) {
        if (ullon >= lrlon || w <= 0 || h <= 0 || ullat <= lrlat) {
            return false;
        }
        if (lrlon <= ROOT_ULLON || ullon >= ROOT_LRLON || lrlat >= ROOT_ULLAT || ullat <= ROOT_LRLAT) {
            return false;
        }
        return true;
    }

}
