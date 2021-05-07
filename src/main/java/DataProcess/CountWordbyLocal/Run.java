package DataProcess.CountWordbyLocal;

import DataProcess.CountWordbyLocal.GetData.getData;
import DataProcess.CountWordbyLocal.WordParticiple.wordParticiple;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author GCJL
 * @date 2021/5/7 15:21
 */
public class Run {
    public static void main(String[] args) throws IOException {

        wordParticiple.loadtoFile();
    }
}
