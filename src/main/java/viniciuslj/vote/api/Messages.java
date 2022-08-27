package viniciuslj.vote.api;

public final class Messages {
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    public static class Agenda {
        public static final String ERROR_ID_NOT_NULL = "Agenda ID cannot be set";
        public static final String ERROR_SUBJECT_EMPTY = "The subject of the agenda cannot be empty";
        public static final String ERROR_NOT_FOUND = "Agenda not found";
    }

    public static class Session {
        public static final String ERROR_START_REQUIRED = "Start date and time is required";
        public static final String ERROR_EXISTING = "Existing session for this agenda";
        public static final String ERROR_START_NOT_FUTURE = "Start date and time must be in the future";
        public static final String ERROR_END_NOT_AFTER_START = "End date and time must be after the start";
        public static final String ERROR_NOT_OPEN = "The session is not open";
        public static final String ERROR_NOT_FOUND = "Session not found";
    }

    public static class Vote {
        public static final String ERROR_MEMBER_VOTE_EXISTS = "The member has already voted on this agenda";
        public static final String ERROR_INVALID_CPF = "Invalid member CPF";
        public static final String ERROR_UNAUTHORIZED_MEMBER = "Unauthorized Member";
    }
}
