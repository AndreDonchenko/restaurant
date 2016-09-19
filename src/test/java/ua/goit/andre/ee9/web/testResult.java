package ua.goit.andre.ee9.web;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Created by Andre on 19.09.2016.
 */
public class TestResult {
    private boolean isOk;
    private MockHttpServletResponse response;
    private Object result;

    public TestResult() {
    }

    public TestResult(boolean isOk, MockHttpServletResponse response, Object result) {
        this.isOk = isOk;
        this.response = response;
        this.result = result;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public MockHttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(MockHttpServletResponse response) {
        this.response = response;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
