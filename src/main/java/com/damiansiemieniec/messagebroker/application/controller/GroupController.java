package com.damiansiemieniec.messagebroker.application.controller;

import com.damiansiemieniec.messagebroker.application.dto.CreateGroupRequest;
import com.damiansiemieniec.messagebroker.application.dto.GeneralResponse;
import com.damiansiemieniec.messagebroker.domain.exception.DuplicateException;
import com.damiansiemieniec.messagebroker.domain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PutMapping("")
    public ResponseEntity<GeneralResponse> createGroup(@RequestBody CreateGroupRequest request) {
        try {
            this.groupService.create(request.getName(), request.getDescription());

            return new ResponseEntity<>(new GeneralResponse(true, "Created"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new GeneralResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(new GeneralResponse(false, e.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GeneralResponse(false, "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
