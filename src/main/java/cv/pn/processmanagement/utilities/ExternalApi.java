package cv.pn.processmanagement.utilities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
public class ExternalApi {

    private String hostAuto;
    private String tokenAuto;

    private String hostPdf;
    private String tokenPdf;

    public String getHostPdf() {
        return hostPdf;
    }

    public void setHostPdf(String hostPdf) {
        this.hostPdf = hostPdf;
    }

    public String getTokenPdf() {
        return tokenPdf;
    }

    public void setTokenPdf(String tokenPdf) {
        this.tokenPdf = tokenPdf;
    }

    public String getHostAuto() {
        return hostAuto;
    }

    public void setHostAuto(String hostAuto) {
        this.hostAuto = hostAuto;
    }

    public String getTokenAuto() {
        return tokenAuto;
    }

    public void setTokenAuto(String tokenAuto) {
        this.tokenAuto = tokenAuto;
    }
}