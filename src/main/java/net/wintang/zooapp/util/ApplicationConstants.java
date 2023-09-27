package net.wintang.zooapp.util;

public class ApplicationConstants {

    private ApplicationConstants() {
    }

    public static class ResponseStatusMessage {

        private ResponseStatusMessage() {}

        public static final String OK = "Ok";
        public static final String SUCCESS = "Success";
        public static final String FAILED = "Failed";
        public static final String NOT_MODIFIED = "Nothing changed";
    }

    public static class SecurityConstants {

        private SecurityConstants() {}
        public static final int JWT_EXPIRATION = 1000*60*60*24; //24 hours
        public static final String ADMIN = "ADMIN";
        public static final String STAFF = "STAFF";
        public static final String ZOO_TRAINER = "ZOO_TRAINER";
        public static final String CUSTOMER = "CUSTOMER";
    }
}
