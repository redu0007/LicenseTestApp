package licensetestapp;

/**
 *
 * @author Redwanur rahman
 */
public class LicenseData {
   
    
    public static final int keyIndex = 2;
    
    private static final String AES_A_1 = "54BC332F048BB9195C179238340F8029";
    
    private static final String AES_B_1 = "C9219D717DF3E5C2FA1904648E868506";
    
    private static final String AES_A_2 = "001D0F13C703ECBA647ABA721A430AC4";
    
    private static final String AES_B_2 = "4380D99AC48AAE52660191886B8C55FF";
    
    private static final String AES_A_3 = "38B766F2D5DA7DA0910D6909C5660898";
    
    private static final String AES_B_3 = "695168F0D1C88E5BEF5957A66E17E2F1";
    
    private static final String publicRSAKey_1 =    "-----BEGIN PUBLIC KEY-----\n" +
                                                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuq7jhJR5T4wqAwE7H5/1\n" +
                                                    "co+1aVAdtWkcf/fgtpMDt6mKPqsDusDu7wgsGU3MS1qGMyB9HGoVlt/fbm8ogR6L\n" +
                                                    "hlt6ZvWyqnb2wt1oLA6KMkxdiBPA5xKvM5xQS2Br3BvvsIZlsh0JpXCm2IbjXdHo\n" +
                                                    "yJ3/yEKB44RqBPkcy88w05Z2vULgIfYW6LCG6ZX+t3jCFK9sBDLVrLIusQgCEboH\n" +
                                                    "lSb/tVdeS628ySwhEaTfJmBRskfIFfz0g7T5AUJnrgoKswYCKtkiMKAdaIZZvWWq\n" +
                                                    "Ug8mJSzdyVLmQH2pFN/lZiziGF4vcvs4fTO3jESv5BKNQ3m3qXbrC5a3ezY3Me/x\n" +
                                                    "qwIDAQAB\n" +
                                                    "-----END PUBLIC KEY-----";
    private static final String publicRSAKey_2 =    "-----BEGIN PUBLIC KEY-----\n" +
                                                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwRnQIzV0TjxmyZIwkQv3\n" +
                                                    "nDvrdOa8wzubPukV0A2K7Elyfumy46k/dvHHkCXxFQRdRq8oHpiLlUZPtDnvl+0Z\n" +
                                                    "jgIRyIuNMJ9/W3HTaKarovOux3rC6vlv43P7BtLA5BPH1vTfCZxb+JJAqaQSNNHt\n" +
                                                    "5BSviQh3ef8slyxSIXkxH/2eI6I5iCNw/zj53dZwjhOGjwZO7QhJv5vKBgq3qEcu\n" +
                                                    "xc0CCffuWi8EEMG8ir/QQogJbHa2t0Y4V1X0teJJ711Wc2XHtfDUNEha2O5TLbq3\n" +
                                                    "g+4NiWBfKQSkyxIiGhCRQY39tHx47Pi9xs1FyqQTVZZ3N+Muopc1x6rwH66LidTP\n" +
                                                    "OQIDAQAB\n" +
                                                    "-----END PUBLIC KEY-----";

   private static final String publicRSAKey_3 =     "-----BEGIN PUBLIC KEY-----\n" +
                                                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAisQ3pEjtS1kFd3KhIefR\n" +
                                                    "Xfbqtk7lARajFGy/h9daz7lDrBVvDSbBU5JPlvvaOrr07+6J149Zr7ET0k76YGLD\n" +
                                                    "+H/J2Dz+Vhc0ZypgVIpvhYxM8S8i4mgY6kKjSvfmTEvW5evEGPHpOxGZYChz9afA\n" +
                                                    "lSC/e4lBgONZf1S7gS69nG6S3G9oWDDuA7yf/2zd7UpMX/274ZKVL2pBWR8kopAl\n" +
                                                    "hWVltto4dQ/5QOqUIQ7u3Uz89s8WGnnSU2TniR+7kgcN5ycBO02c7/Fy/Qnc2Hes\n" +
                                                    "qNFPxBePcoutLMcuSkUBbtlhPlZx2JbncBDztVIkcuu4zuOZn7O7lRoQ1y9LqCyK\n" +
                                                    "JQIDAQAB\n" +
                                                    "-----END PUBLIC KEY-----";
   
    public static String getRSAPublicPem(int keyIndex) {
        
        String key = "";
        
        switch(keyIndex){
            case 1: 
                key = publicRSAKey_1;
                break;
            case 2: 
                key = publicRSAKey_2;
                break;
            case 3: 
                key = publicRSAKey_3;
                break;
        }
        
        return key;
    }
    
    public static String getAES_A(int keyIndex) {
        
        String key = "";
        
        switch(keyIndex){
            case 1: 
                key = AES_A_1;
                break;
            case 2: 
                key = AES_A_2;
                break;
            case 3: 
                key = AES_A_3;
                break;
        }
        
        return key;
    }
    public static String getAES_B(int keyIndex) {
        
        String key = "";
        
        switch(keyIndex){
            case 1: 
                key = AES_B_1;
                break;
            case 2: 
                key = AES_B_2;
                break;
            case 3: 
                key = AES_B_3;
                break;
        }
        
        return key;
    }
}
