package com.rova.transactionservice.util;

public class Constants {

    public final static String JDBC_H2_URL = "jdbc:h2:mem:rova_transaction_service;DB_CLOSE_DELAY=-1;MODE=MySQL";
    public final static String DRIVER_CLASS_NAME = "org.h2.Driver";

    public final static String PROCESSED_AUTH_TOKEN = "Processed-Auth";

    public static final String SCOPES = "scopes";

    public final static String ENABLED = "enabled";

    public static final String TYPE = "type";

    public static final String BASIC_AUTHS = "basic_auths";

    public final static String IAPPENDABLE_REF_SEPARATOR = "_";

    public static class EntityColumns {

        public final static String ID = "id";
        public final static String GOAL_ID = "goal_id";
        public final static String USERNAME = "username";
        public final static String EMAIL = "email";
        public final static String PHONE_NO = "phone_no";

        public final static String USER_ID = "user_id";
    }

    public static class LockPrefix {
        public final static String ACCOUNT_CREDIT = "ACCOUNT_CREDIT";

        public final static String ACCOUNT_DEBIT = "ACCOUNT_DEBIT";
    }
}
