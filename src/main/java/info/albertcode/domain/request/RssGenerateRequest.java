package info.albertcode.domain.request;

import info.albertcode.utils.pair.OneKeyOneValue;

/**
 * @Description:
 * overview：暂空
 *
 * header：储存channel的基本信息，包括 title url description，采用json储存，如
 * {"title":"channel标题","url":"channel链接","description":"channel说明"}
 *
 * body：储存映射信息，包括 titleAlias authorAlias textAlias urlAlias，如：
 * {"titleAlias":"name","authorAlias":"author","textAlias":"text","urlAlias":"link"}
 *
 * @Author: Albert Shen
 */

public class RssGenerateRequest extends Request{
    private OneKeyOneValue keyValueHeader;
    private OneKeyOneValue keyValueBody;

    public RssGenerateRequest(Request request){
        this.setId(request.getId());
        this.setOverview(request.getOverview());
        this.setHeader(request.getHeader());
        this.setBody(request.getBody());
    }

    public String getTitleAlias() {
        return (String) keyValueBody.getValue("titleAlias");
    }

    public void setTitleAlias(String titleAlias) {
        keyValueBody.addValue("titleAlias", titleAlias);
        this.body = keyValueBody.toJsonString();
    }

    public String getAuthorAlias() {
        return (String) keyValueBody.getValue("authorAlias");
    }

    public void setAuthorAlias(String authorAlias) {
        keyValueBody.addValue("authorAlias", authorAlias);
        this.body = keyValueBody.toJsonString();
    }

    public String getTextAlias() {
        return (String) keyValueBody.getValue("textAlias");
    }

    public void setTextAlias(String textAlias) {
        keyValueBody.addValue("textAlias", textAlias);
        this.body = keyValueBody.toJsonString();
    }

    public String getUrlAlias() {
        return (String) keyValueBody.getValue("urlAlias");
    }

    public void setUrlAlias(String urlAlias) {
        keyValueBody.addValue("urlAlias", urlAlias);
        this.body = keyValueBody.toJsonString();
    }

    public void setBody(String titleAlias, String authorAlias,
                          String textAlias, String urlAlias){
        keyValueBody.addValue("titleAlias", titleAlias);
        keyValueBody.addValue("authorAlias", authorAlias);
        keyValueBody.addValue("textAlias", textAlias);
        keyValueBody.addValue("urlAlias", urlAlias);
        this.body = keyValueBody.toJsonString();
    }

    @Override
    public void setBody(String jsonString){
        keyValueBody = new OneKeyOneValue(jsonString);
        this.body = keyValueBody.toJsonString();
    }

    public void setBody(OneKeyOneValue keyValueBody){
        this.keyValueBody = keyValueBody;
        this.body = keyValueBody.toJsonString();
    }

    public String getChannelTitle() {
        return (String) keyValueHeader.getValue("channelTitle");
    }

    public void setChannelTitle(String channelTitle) {
        keyValueHeader.addValue("channelTitle", channelTitle);
        this.header = keyValueHeader.toJsonString();
    }

    public String getChannelUrl() {
        return (String) keyValueHeader.getValue("channelUrl");
    }

    public void setChannelUrl(String channelUrl) {
        keyValueHeader.addValue("channelUrl", channelUrl);
        this.header = keyValueHeader.toJsonString();
    }

    public String getChannelDescription() {
        return (String) keyValueHeader.getValue("channelDescription");
    }

    public void setChannelDescription(String channelDescription) {
        keyValueHeader.addValue("channelDescription", channelDescription);
        this.header = keyValueHeader.toJsonString();
    }

    public OneKeyOneValue getKeyValueBody() {
        return keyValueHeader;
    }

    public void setHeader(String channelTitle, String channelUrl, String channelDescription){
        keyValueHeader.addValue("channelTitle", channelTitle);
        keyValueHeader.addValue("channelUrl", channelUrl);
        keyValueHeader.addValue("channelDescription", channelDescription);
        this.header = keyValueHeader.toJsonString();
    }

    @Override
    public void setHeader(String jsonString){
        keyValueHeader = new OneKeyOneValue(jsonString);
        this.header = keyValueHeader.toJsonString();
    }

    public void setHeader(OneKeyOneValue keyValueBody){
        this.keyValueHeader = keyValueBody;
        this.header = keyValueHeader.toJsonString();
    }
}
