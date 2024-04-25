package animals.constants;

public interface ConstantsHolder {
    String SPLIT_XML_LINE_REGEX = "(?<=\\</?[a-zA-Z0-9]+\\>)|(?=\\</?[a-zA-Z0-9]+\\>)";
    String AUTHORIZATION = "Authorization";
    String BEARER_AUTH = "BearerAuth";
    String ROLE_ADMIN = "ADMIN";
    String ROLE_USER = "USER";
    String EMPTY_STRING = "";
    String BEARER = "Bearer";
    int TOKEN_START = 7;
    String CSV = "CSV";
    String JWT = "JWT";
    String XML = "XML";
    int ONE = 1;
}
