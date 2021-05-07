package DataProcess.CountWord;

import DataProcess.CountWord.tool.CWTool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author GCJL
 * @date 2021/4/28 14:18
 */
public class Run {
    public static void main(String[] args) throws Exception {
        ToolRunner.run(new CWTool(),args);


    }
}
