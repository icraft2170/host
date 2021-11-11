package com.host.host.api;
import com.host.host.api.response.HostResponse;
import com.host.host.dto.Result;
import com.host.host.exception.DuplicateHostException;
import com.host.host.exception.HostException;
import com.host.host.service.HostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.host.host.consts.HostConst.MAX_HOST_COUNT;
import static com.host.host.api.ApiUtil.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j @RequiredArgsConstructor
@RestController
@RequestMapping("/host")
public class HostApiController {
    private final HostService hostService;
    Map<String, Object> map = new ConcurrentHashMap();

    @PostMapping("/post")
    public ResponseEntity<Map<String,Object>> createHost(HttpServletRequest request){
        String clientIp = getClientIp(request);
        String hostName = getHostname(clientIp);

        if(!hostService.isSizeGreaterThanMaxCount()){
            throw new HostException("호스트 제한수가 초과하였습니다.");
        }
        if (hostService.isDuplicateHost(hostName,clientIp)){
            throw new DuplicateHostException("중복된 호스트 등록 예외");
        }

        hostService.joinHost(clientIp, hostName);


        map.put("clientIp", clientIp);
        map.put("hostName", hostName);
        map.put("msg",hostName + "를 가입하였습니다.");
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }

    @PatchMapping("/patch")
    public ResponseEntity<Map<String,Object>> modifyHost(HttpServletRequest request){
        String clientIp = getClientIp(request);
        String hostName = getHostname(clientIp);

        hostService.updateHost(clientIp,hostName);

        map.put("clientIp", clientIp);
        map.put("hostName", hostName);
        map.put("msg",hostName + "를 수정하였습니다.");
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String,Object>> deleteHost(HttpServletRequest request){
        String clientIp = getClientIp(request);
        String hostName = getHostname(clientIp);

        hostService.deleteHostByDeleteHostRequest(clientIp,hostName);

        map.put("clientIp", clientIp);
        map.put("hostName", hostName);
        map.put("msg",hostName + "가 삭제하였습니다.");
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }

    @GetMapping("/get")
    public Result<HostResponse> host(HttpServletRequest request){
        String clientIp = getClientIp(request);
        String hostName = getHostname(clientIp);

        HostResponse hostResponse= hostService.findHost(hostName);
        Result<HostResponse> hostResponseResult = new Result<>();
        hostResponseResult.setData(hostResponse);

        return hostResponseResult;
    }

    @GetMapping("/gets")
    public Page<HostResponse> hosts(@PageableDefault(size = MAX_HOST_COUNT) Pageable pageable){
        return hostService.findHosts(pageable);
    }

}
