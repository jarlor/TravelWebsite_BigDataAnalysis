package D_DataProcess.AveragePrice;

import D_DataProcess.AveragePrice.tool.APTool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author GCJL
 * @date 2021/4/28 14:18
 */
public class

Run {
    public static void main(String[] args) throws Exception {
        ToolRunner.run(new APTool(),args);
    }
}
