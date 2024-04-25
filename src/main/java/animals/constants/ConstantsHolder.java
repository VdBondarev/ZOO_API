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
    int ZERO = 0;
    Long TWO = 2L;
    Long THREE = 3L;
    Long FOUR = 4L;
    int FIRST_CATEGORY_RANGE = 20;
    int SECOND_CATEGORY_RANGE_FROM = 21;
    int SECOND_CATEGORY_RANGE_TO = 40;
    int THIRD_CATEGORY_RANGE_FROM = 41;
    int THIRD_CATEGORY_RANGE_TO = 60;
    int FOURTH_CATEGORY_RANGE = 61;
}
