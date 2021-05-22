package D_DataProcess.CountWordbyLocal.WordParticiple;

import D_DataProcess.CountWordbyLocal.GetData.getData;
import com.vdurmont.emoji.EmojiParser;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GCJL
 * @date 2021/5/7 15:11
 */
public class wordParticiple {
    public static Map<String, Integer> analyzeData(List<String> sourceData) {

        HashMap<String, Integer> targetData = new HashMap<String, Integer>();

        for (String data : sourceData) {
            if (data != null) {
                String filter = EmojiParser.removeAllEmojis(data);
                List<Word> segs = WordSegmenter.seg(filter);
                for (Word seg : segs) {
                    //判断有没有这个key
                    if (!targetData.containsKey(seg.getText())) {
                        //没有 加入seg，并且value设置为1
                        targetData.put(seg.getText(), 1);
                    }else {
                        //有 value+1
                        targetData.put(seg.getText(),targetData.get(seg.getText())+1);
                    }
                }
            }
        }

        return targetData;
    }

    public static void loadtoFile() throws IOException {
        Map<String, Integer> sourceData =analyzeData(getData.getDataFromLocal());

        String key = null;
        Integer value=0;


        BufferedWriter bw = new BufferedWriter(new FileWriter("./src/resources/targetData.txt"));


        for (String s : sourceData.keySet()) {
            key = s;
            value = sourceData.get(key);

            bw.write(String.format("%s\t%d\n",key,value));
        }
        bw.close();

    }

}
