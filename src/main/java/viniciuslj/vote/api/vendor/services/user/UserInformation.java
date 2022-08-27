package viniciuslj.vote.api.vendor.services.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInformation {
    public static class STATUS {
        public static final String ABLE = "ABLE_TO_VOTE";
        public static final String UNABLE = "UNABLE_TO_VOTE";
    }

    private String status;
}
