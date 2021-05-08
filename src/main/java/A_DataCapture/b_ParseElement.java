package A_DataCapture;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 解析并提取HTML元素
 */
public class b_ParseElement {

    //获取所有链接
    public static Elements getLinks(Document doc) {
        Elements links = doc.select("link[href]");
        return links;
    }

    //获取第一个class为“pop_attention”的div
    public static Element getDiv(Document doc) {
        Element first = doc.select("div.pop_attention").first();
        return first;
    }

    //获取所有li之后的i标签
    public static Elements getI(Document doc) {
        Elements resultLinks = doc.select("li > i");
        return resultLinks;
    }

    public static void main(String[] args) throws Exception {
        Document doc = null;
        doc = a_GetSourceData.getSourceData();

        Elements Links = b_ParseElement.getLinks(doc);
        Elements I = b_ParseElement.getI(doc);
        Element Div = b_ParseElement.getDiv(doc);
        System.out.println("Links:" + Links.size() + ",I:" + I.size());
        for (int i = 0; i < Links.size(); i++)
            System.out.println(Links.get(i));
        for (int i = 0; i < I.size(); i++)
            System.out.println(I.get(i));
        if (Div != null)
            System.out.println(Div.toString().replace("<div", "<span").replace("/div>", "/span>"));
    }

}
