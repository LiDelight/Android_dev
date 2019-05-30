package edu.cqut.MobileOrderFood.FirstPage;

import java.util.ArrayList;
import java.util.List;

import edu.cqut.MobileOrderFood.R;

public class ImgItem_content {

    public static List<ImgItem> imgItemList=new ArrayList<ImgItem>();

    private static int imgcount=3;

    static{
        imgItemList.add(new ImgItem("1",R.raw.post1));
        imgItemList.add(new ImgItem("2",R.raw.post2));
        imgItemList.add(new ImgItem("3",R.raw.post3));
    }

    public static class ImgItem{
    public  String id;
    public int firstpage_img;

    ImgItem(String id, int img) {
        this.id = id;
        this.firstpage_img = img;
    }
    }
}
