package oz.anticaptcha.api.model;

/**
 * Created by Igor Ozol
 * on 11.10.2016.
 * Eldorado LLC
 */
public class NoCapthcaTask implements Task {

    public final String type = "NoCaptchaTask";

    String websiteURL;
    String websiteKey;
    String websiteSToken ="";
    ProxyType proxyType = ProxyType.http;
    String proxyAddress;
    Integer proxyPort;
    String proxyLogin;
    String proxyPassword;
    String userAgent;
    String cookies;
}
