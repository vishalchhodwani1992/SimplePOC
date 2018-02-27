package poc.test.com.drawerwithormlite.model;

import java.io.Serializable;


public class GetUserDetailsResponse  implements Serializable {

    String total_sum;
    String eligible_post_sum;
    String response_code;
    String message;
    String secretKey;
    String bank_status;



    public String getBank_status() {
        return bank_status;
    }

    public void setBank_status(String bank_status) {
        this.bank_status = bank_status;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEligible_post_sum() {
        return eligible_post_sum;
    }

    public void setEligible_post_sum(String eligible_post_sum) {
        this.eligible_post_sum = eligible_post_sum;
    }


}
