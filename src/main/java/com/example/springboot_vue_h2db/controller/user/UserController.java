package com.example.springboot_vue_h2db.controller.user;

import com.example.springboot_vue_h2db.advice.exception.CustomNotFoundException;
import com.example.springboot_vue_h2db.model.User;
import com.example.springboot_vue_h2db.model.response.CommonResult;
import com.example.springboot_vue_h2db.model.response.ListResult;
import com.example.springboot_vue_h2db.model.response.SingleResult;
import com.example.springboot_vue_h2db.repo.UserRepository;
import com.example.springboot_vue_h2db.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. User"})
@RequiredArgsConstructor // final로 선언된 객체에 대해서 생성자 주입을 수행한다. 이걸 사용하지 않고 @Autowired를 사용해도 됨.
@RestController // 결과값을 JSON으로 출력
@RequestMapping("/v1")
public class UserController {

    private final UserRepository userRepository;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 조회", notes = "모든 회원 조회")
    @GetMapping(value = "/user")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userRepository.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원 조회")
    @GetMapping(value = "/user/") // request가 들어오면 security filter를 먼저 거친다.
    public SingleResult<User> findUserById(@ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Bean(전역에 선언된) SecurityContextHolder에서 현재 인증된 사용자의 정보를 가져온다.
        String id = authentication.getName();
        return responseService.getSingleResult(userRepository.findByUid(id).orElseThrow(CustomNotFoundException::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원 정보 수정")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(@ApiParam(value = "회원번호", required = true) @RequestParam Long msrl,
                                     @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
                                     @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원을 삭제")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(@ApiParam(value = "회원번호", required = true) @PathVariable Long msrl) {
        userRepository.deleteById(msrl);
        return responseService.getSuccessResult();
    }
}
