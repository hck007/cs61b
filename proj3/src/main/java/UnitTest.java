import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitTest {
    public static List<Integer> UnitTest() {
        Map<String, Double> param = new HashMap<>();
        param.put("lrlon", -122.279243517);
        param.put("ullon", -122.297715593);
        param.put("w", 841.0);
        param.put("h", 586.0);
        param.put("ullat", 37.853836257);
        param.put("lrlat", 37.840965108);
        Rasterer raster = new Rasterer();
        int depth = 4;
        List<Integer> ans = raster.getRaster_lon(4, -122.297715593, -122.279243517);
        //return ans;
        //List<Integer> ans = raster.getRaster_lat(2,37.870213571328854,37.8318576119893);
        return ans;
    }

        public static void main(String[] args) {
        List<Integer> ans = UnitTest();
        for (int i = 0; i < ans.size(); i++) {
            System.out.println(ans.get(i));
        }
        }
    }

