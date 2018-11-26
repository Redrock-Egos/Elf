package com.egos.elf.common.bean;

import java.util.List;

/**
 * create by:Fxymine4ever
 * time: 2018/11/25
 */
public class RecAlbum {

    /**
     * code : 200
     * info : Get recommend success
     * result : [{"title":"王家卫的映画世界","content":"王家卫，出生于上海，5岁时随家移居香港。毕业于香港理工学院美术设计系。1981年考进无线电视台第一期编导培训班。1988年王家卫自编自导第一部电影《旺角卡门》，影片由刘德华、张曼玉、万梓良等主演。这部电影在传统的江湖片中拍出新意，获得成功。让我们随着原声音乐走进他的电影世界。","owner":"甘涛vinson","bg_url":"https://s1.ax1x.com/2018/11/25/FkvLnK.jpg","id":11025839},{"title":"绝佳配乐 | 黑暗美学中的荒诞主义","content":"摩西默默扫视着正午烈日炙烤下的荒漠，隐藏在牛仔帽檐下的双眼透出无比坚定的意味。他骑着一匹高大但消瘦的老马，一手搭在腰畔枪套里的柯尔特M1873上，那坚硬踏实的触感令他安心不少。他的身后，茫茫风沙里，许多成年男子荷枪骑马，拱卫着一辆辆大篷车，长长的队伍迤逦前行\u2026\u2026","owner":"东野圭吾的烦恼","bg_url":"https://s1.ax1x.com/2018/11/26/FA5gDs.jpg","id":2336345537}]
     */

    private int code;
    private String info;
    private List<RecBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<RecBean> getResult() {
        return result;
    }

    public void setResult(List<RecBean> result) {
        this.result = result;
    }

    public static class RecBean {
        /**
         * title : 王家卫的映画世界
         * content : 王家卫，出生于上海，5岁时随家移居香港。毕业于香港理工学院美术设计系。1981年考进无线电视台第一期编导培训班。1988年王家卫自编自导第一部电影《旺角卡门》，影片由刘德华、张曼玉、万梓良等主演。这部电影在传统的江湖片中拍出新意，获得成功。让我们随着原声音乐走进他的电影世界。
         * owner : 甘涛vinson
         * bg_url : https://s1.ax1x.com/2018/11/25/FkvLnK.jpg
         * id : 11025839
         */

        private String title;
        private String content;
        private String owner;
        private String bg_url;
        private long id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getBg_url() {
            return bg_url;
        }

        public void setBg_url(String bg_url) {
            this.bg_url = bg_url;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
