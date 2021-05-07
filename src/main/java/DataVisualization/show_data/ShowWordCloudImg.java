package DataVisualization.show_data;

import DataVisualization.CompareImg;
import DataVisualization.WordCloud_img;
import com.kennycason.kumo.WordCloud;

import java.io.IOException;
import java.util.Scanner;

public class ShowWordCloudImg {
    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
        String youImg = scanner.nextLine();
        String img = scanner.nextLine();
        WordCloud wordCloud=new WordCloud_img().get();
        int width = wordCloud.getBufferedImage().getWidth();
        int height = wordCloud.getBufferedImage().getHeight();
        if(width!=500 || height!=312){
            System.out.println("图片像素与预期不符");
        }else{
            CompareImg cImg = new CompareImg();
            float percent = cImg.compare(cImg.getData(youImg),cImg.getData(img));
            if (percent == 0) {
                System.out.println("无法比较");
            }else if(percent < 60) {
                System.out.println("生成图片与预期图片相似度为"+percent+"%");
            }else{
                System.out.println("生成图片与预期图片大体相似");
            }
        }
    }
}
