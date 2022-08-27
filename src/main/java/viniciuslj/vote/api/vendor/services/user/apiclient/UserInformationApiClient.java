package viniciuslj.vote.api.vendor.services.user.apiclient;

import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import viniciuslj.vote.api.services.exceptions.UnauthorizedException;
import viniciuslj.vote.api.vendor.services.user.UserInformation;
import viniciuslj.vote.api.vendor.services.user.UserInformationService;

@Service
@AllArgsConstructor
public class UserInformationApiClient implements UserInformationService {

    private final UserInformationFeignClient feignClient;

    @Override
    public UserInformation get(String userIdentity) {
        try {
            return feignClient.get(userIdentity);
        } catch (FeignException.NotFound | FeignException.Unauthorized exception) {
            throw new UnauthorizedException(exception.getMessage());
        }
    }
}
