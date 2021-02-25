package info.albertcode.domain.request;

/**
 * @Description:
 * overview：暂空
 * header：储存映射信息，使用 & 连接，并按照 titleAlias author text url的顺序，分别记录其别名
 *         如：BiaoTi&ZuoZhe&ZhengWen&LianJie
 * body：储存channel的基本信息，使用 & 连接，并按照 title url description 的顺序记录其指
 *       如：我的网站首页&https://albertcode.info&一些乱七八糟的东西
 * @Author: Albert Shen
 */

public class RssGenerateRequest extends Request{
    private String titleAlias;
    private String authorAlias;
    private String textAlias;
    private String urlAlias;

    private String channelTitle;
    private String channelUrl;
    private String channelDescription;

    public RssGenerateRequest(Request request){
        this.setId(request.getId());
        this.setOverview(request.getOverview());
        this.setHeader(request.getHeader());
        this.setBody(request.getBody());
    }

    public String getTitleAlias() {
        return titleAlias;
    }

    public void setTitleAlias(String titleAlias) {
        this.titleAlias = titleAlias;
        this.setHeader(this.titleAlias + "&" + this.authorAlias + "&"
                + this.textAlias + "&" + this.urlAlias);
    }

    public String getAuthorAlias() {
        return authorAlias;
    }

    public void setAuthorAlias(String authorAlias) {
        this.authorAlias = authorAlias;
        this.setHeader(this.titleAlias + "&" + this.authorAlias + "&"
                + this.textAlias + "&" + this.urlAlias);
    }

    public String getTextAlias() {
        return textAlias;
    }

    public void setTextAlias(String textAlias) {
        this.textAlias = textAlias;
        this.setHeader(this.titleAlias + "&" + this.authorAlias + "&"
                + this.textAlias + "&" + this.urlAlias);
    }

    public String getUrlAlias() {
        return urlAlias;
    }

    public void setUrlAlias(String urlAlias) {
        this.urlAlias = urlAlias;
        this.setHeader(this.titleAlias + "&" + this.authorAlias + "&"
                + this.textAlias + "&" + this.urlAlias);
    }

    public void setHeader(String titleAlias, String authorAlias,
                          String textAlias, String urlAlias){
        this.titleAlias = titleAlias;
        this.authorAlias = authorAlias;
        this.textAlias = textAlias;
        this.urlAlias = urlAlias;
        this.setHeader(this.titleAlias + "&" + this.authorAlias + "&"
                + this.textAlias + "&" + this.urlAlias);
    }

    @Override
    public void setHeader(String header){
        String[] values = header.split("&");
        this.setHeader(values[0], values[1], values[2], values[3]);
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
        this.setBody(this.channelTitle + "&" + this.channelUrl + "&"
                + this.channelDescription);
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
        this.setBody(this.channelTitle + "&" + this.channelUrl + "&"
                + this.channelDescription);
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
        this.setBody(this.channelTitle + "&" + this.channelUrl + "&"
                + this.channelDescription);
    }

    public void setBody(String channelTitle, String channelUrl, String channelDescription){
        this.channelTitle = channelTitle;
        this.channelUrl = channelUrl;
        this.channelDescription = channelDescription;
        this.setBody(this.channelTitle + "&" + this.channelUrl + "&"
                + this.channelDescription);
    }

    @Override
    public void setBody(String body){
        String[] values = body.split("&");
        setBody(values[0], values[1], values[2]);
    }
}
