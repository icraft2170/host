package com.host.host.api;
import com.host.host.api.request.CreateHostRequest;
import com.host.host.api.request.DeleteHostRequest;
import com.host.host.api.request.GetHostRequest;
import com.host.host.api.request.ModifyHostRequest;
import com.host.host.api.response.HostResponse;
import com.host.host.dto.Result;
import com.host.host.exception.DuplicateHostException;
import com.host.host.exception.HostException;
import com.host.host.service.HostQueryService;
import com.host.host.service.HostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;

import static com.host.host.consts.HostConst.MAX_HOST_COUNT;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j @RequiredArgsConstructor
@RestController
@RequestMapping("/host")
public class HostApiController {
    private final HostService hostService;
    private final HostQueryService hostQueryService;


    @PostMapping("/post")
    public ResponseEntity<Map<String,Object>> createHost(
//            @Validated @RequestBody CreateHostRequest createHostRequest
              HttpServletRequest request){

        String clientIp = ApiUtil.getClientIp(request);
        String remoteHost = request.getRemoteHost();

        if(!hostService.isSizeGreaterThanMaxCount()){
            throw new HostException("호스트 제한수가 초과하였습니다.");
        }
        if (hostService.isDuplicateHost(createHostRequest.getName(),clientIp)){
            throw new DuplicateHostException("중복된 호스트 등록 예외");
        }

        hostService.joinHost(createHostRequest);







        Map<String, Object> map = new ConcurrentHashMap();
//        map.put("msg");

        return new ResponseEntity<Map<String,Object>>(HttpStatus.OK);
    }

    @PatchMapping("/patch")
    public String modifyHost(
            @Validated @RequestBody ModifyHostRequest modifyHostRequest
            , BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new HostException("잘못된 형식의 데이터입니다.");
        }
        if (hostService.isDuplicateHost(modifyHostRequest.getNewName(),modifyHostRequest.getNewAddress())){
            throw new DuplicateHostException("중복된 호스트 수정 예외");
        }

         String name = hostService.updateHost(modifyHostRequest);
        return null;
    }

    @DeleteMapping("/delete")
    public String deleteHost(
            @Validated @RequestBody DeleteHostRequest deleteHostRequest
            , BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new HostException("잘못된 형식의 데이터입니다.");
        }
        hostService.deleteHostByDeleteHostRequest(deleteHostRequest);
        return null;
    }

    @GetMapping("/get")
    public Result<HostResponse> getHost(@Validated @RequestBody GetHostRequest getHostRequest
                ,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new HostException("잘못된 형식의 데이터입니다.");
        }

        HostResponse hostResponse= hostQueryService.findHost(getHostRequest);
        Result<HostResponse> hostResponseResult = new Result<>();
        hostResponseResult.setData(hostResponse);

        return hostResponseResult;
    }

    @GetMapping("/gets")
    public Page<HostResponse> getHosts(@PageableDefault(size = MAX_HOST_COUNT) Pageable pageable){
        return hostQueryService.findHosts(pageable);
    }

//
//    private void isAlive(String address){
//        InetAddress inetAddress = null;
//        try {
////            inetAddress = InetAddress.getB();
//        }
//        catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        try {
//            if (inetAddress.isReachable(2000))
//            { //응답한 경우
//
//            }else
//            {
//                //응답이 안된 경우
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


}
