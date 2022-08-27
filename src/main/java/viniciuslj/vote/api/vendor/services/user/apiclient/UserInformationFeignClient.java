package viniciuslj.vote.api.vendor.services.user.apiclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import viniciuslj.vote.api.vendor.services.user.UserInformation;

@FeignClient(value = "UserInformation", url = "${vendor.service.user-info.url}")
public interface UserInformationFeignClient {
    @GetMapping(value = "/users/{cpf}", produces = "application/json")
    UserInformation get(@PathVariable("cpf") String cpf);
}
