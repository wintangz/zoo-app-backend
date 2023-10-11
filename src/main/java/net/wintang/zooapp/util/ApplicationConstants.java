package net.wintang.zooapp.util;

public class ApplicationConstants {

    private ApplicationConstants() {
    }

    public static class ResponseStatus {

        private ResponseStatus() {
        }

        public static final String OK = "Ok";
        public static final String FAILED = "Failed";
        public static final String NOT_MODIFIED = "Nothing changed";
        public static final String EXISTED = "Existed";
    }

    public static class ResponseMessage {

        private ResponseMessage() {
        }

        public static final String NOT_MODIFIED = "Nothing changed";
        public static final String EXISTED = "Existed";
        public static final String SUCCESS = "Success";
        public static final String INVALID = "Invalid";
    }

    public static class SecurityConstants {

        private SecurityConstants() {
        }

        public static class Roles {


            private Roles() {
            }

            public static final String ADMIN = "ADMIN";
            public static final String STAFF = "STAFF";
            public static final String ZOO_TRAINER = "ZOO_TRAINER";
            public static final String CUSTOMER = "CUSTOMER";
        }

        public static final int JWT_EXPIRATION = 1000 * 60 * 60 * 24; //24 hours

    }
}
