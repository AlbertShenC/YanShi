package info.albertcode.domain.request;

/**
 * @Description:
 * overview：
 * header：
 * body：
 * @Author: Albert Shen
 */

public class RegisterForWebPageRequest extends Request{
    public RegisterForWebPageRequest() {
        this.type = "RegisterForWebPage";
    }

    public RegisterForWebPageRequest(Request request){
        this.setId(request.getId());
        this.type = "RegisterForWebPage";
        this.setOverview(request.getOverview());
        this.setHeader(request.getHeader());
        this.setBody(request.getBody());
    }
}
