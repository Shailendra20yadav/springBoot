/**
 * Response type for resource requested through rest API.
 */
package com.sk.spring.umgmt.response;

import com.sk.spring.umgmt.type.ResponseItemType;

/**
 * @author shailendra.kum
 *
 */
public class RestResponse {

    private ResponseItemType responseType;

    private Object responseObject;

    public RestResponse(ResponseItemType responseType, Object responseObject) {
        this.responseType = responseType;
        this.responseObject = responseObject;
    }

    public ResponseItemType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseItemType responseType) {
        this.responseType = responseType;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

}
